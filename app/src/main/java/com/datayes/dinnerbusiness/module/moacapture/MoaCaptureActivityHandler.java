package com.datayes.dinnerbusiness.module.moacapture;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.uuzuche.lib_zxing.camera.CameraManager;
import com.uuzuche.lib_zxing.decoding.CaptureActivityHandler;
import com.uuzuche.lib_zxing.view.ViewfinderResultPointCallback;
import com.uuzuche.lib_zxing.view.ViewfinderView;

import java.util.Vector;

/**
 * Created by shenen.gao on 2016/11/9.
 */

public class MoaCaptureActivityHandler extends Handler {

    private static final String TAG = CaptureActivityHandler.class.getSimpleName();

    private final MoaCaptureFragment fragment;
    private final MoaDecodeTread decodeThread;
    private MoaCaptureActivityHandler.State state;

    private enum State {
        PREVIEW,
        SUCCESS,
        DONE
    }

    public MoaCaptureActivityHandler(MoaCaptureFragment fragment, Vector<BarcodeFormat> decodeFormats,
                                  String characterSet, ViewfinderView viewfinderView) {
        this.fragment = fragment;
        decodeThread = new MoaDecodeTread(fragment, decodeFormats, characterSet,
                new ViewfinderResultPointCallback(viewfinderView));
        decodeThread.start();
        state = MoaCaptureActivityHandler.State.SUCCESS;
        // Start ourselves capturing previews and decoding.
        CameraManager.get().startPreview();
        restartPreviewAndDecode();
    }

    @Override
    public void handleMessage(Message message) {
        if (message.what == com.uuzuche.lib_zxing.R.id.auto_focus) {
            //Log.d(TAG, "Got auto-focus message");
            // When one auto focus pass finishes, start another. This is the closest thing to
            // continuous AF. It does seem to hunt a bit, but I'm not sure what else to do.
            if (state == MoaCaptureActivityHandler.State.PREVIEW) {
                CameraManager.get().requestAutoFocus(this, com.uuzuche.lib_zxing.R.id.auto_focus);
            }
        } else if (message.what == com.uuzuche.lib_zxing.R.id.restart_preview) {
            Log.d(TAG, "Got restart preview message");
            restartPreviewAndDecode();
        } else if (message.what == com.uuzuche.lib_zxing.R.id.decode_succeeded) {
            Log.d(TAG, "Got decode succeeded message");
            state = MoaCaptureActivityHandler.State.SUCCESS;
            Bundle bundle = message.getData();

            /***********************************************************************/
            Bitmap barcode = bundle == null ? null :
                    (Bitmap) bundle.getParcelable(MoaDecodeTread.BARCODE_BITMAP);//���ñ����߳�

            fragment.handleDecode((Result) message.obj, barcode);//���ؽ��
            /***********************************************************************/
        } else if (message.what == com.uuzuche.lib_zxing.R.id.decode_failed) {
            // We're decoding as fast as possible, so when one decode fails, start another.
            state = MoaCaptureActivityHandler.State.PREVIEW;
            CameraManager.get().requestPreviewFrame(decodeThread.getHandler(), com.uuzuche.lib_zxing.R.id.decode);
        } else if (message.what == com.uuzuche.lib_zxing.R.id.return_scan_result) {
            Log.d(TAG, "Got return scan result message");
            fragment.getActivity().setResult(Activity.RESULT_OK, (Intent) message.obj);
            fragment.getActivity().finish();
        } else if (message.what == com.uuzuche.lib_zxing.R.id.launch_product_query) {
            Log.d(TAG, "Got product query message");
            String url = (String) message.obj;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            fragment.getActivity().startActivity(intent);
        }
    }

    public void quitSynchronously() {
        state = MoaCaptureActivityHandler.State.DONE;
        CameraManager.get().stopPreview();
        Message quit = Message.obtain(decodeThread.getHandler(), com.uuzuche.lib_zxing.R.id.quit);
        quit.sendToTarget();
        try {
            decodeThread.join();
        } catch (InterruptedException e) {
            // continue
        }

        // Be absolutely sure we don't send any queued up messages
        removeMessages(com.uuzuche.lib_zxing.R.id.decode_succeeded);
        removeMessages(com.uuzuche.lib_zxing.R.id.decode_failed);
    }

    private void restartPreviewAndDecode() {
        if (state == MoaCaptureActivityHandler.State.SUCCESS) {
            state = MoaCaptureActivityHandler.State.PREVIEW;
            CameraManager.get().requestPreviewFrame(decodeThread.getHandler(), com.uuzuche.lib_zxing.R.id.decode);
            CameraManager.get().requestAutoFocus(this, com.uuzuche.lib_zxing.R.id.auto_focus);
            fragment.drawViewfinder();
        }
    }

}
