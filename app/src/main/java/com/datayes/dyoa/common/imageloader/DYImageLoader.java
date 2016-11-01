/**
 * 通联数据机密
 * --------------------------------------------------------------------
 * 通联数据股份公司版权所有 © 2013-2016
 * <p/>
 * 注意：本文所载所有信息均属于通联数据股份公司资产。本文所包含的知识和技术概念均属于
 * 通联数据产权，并可能由中国、美国和其他国家专利或申请中的专利所覆盖，并受商业秘密或
 * 版权法保护。
 * 除非事先获得通联数据股份公司书面许可，严禁传播文中信息或复制本材料。
 * <p/>
 * DataYes CONFIDENTIAL
 * --------------------------------------------------------------------
 * Copyright © 2013-2016 DataYes, All Rights Reserved.
 * <p/>
 * NOTICE: All information contained herein is the property of DataYes
 * Incorporated. The intellectual and technical concepts contained herein are
 * proprietary to DataYes Incorporated, and may be covered by China, U.S. and
 * Other Countries Patents, patents in process, and are protected by trade
 * secret or copyright law.
 * Dissemination of this information or reproduction of this material is
 * strictly forbidden unless prior written permission is obtained from DataYes.
 */
package com.datayes.dyoa.common.imageloader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.datayes.baseapp.tools.DYLog;
import com.datayes.dinnercustom.App;
import com.datayes.dyoa.module.user.CurrentUser;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.HashMap;
import java.util.Map;

/**
 * 图片加载接口
 */
public class DYImageLoader {
    private static DYImageLoader ourInstance = new DYImageLoader();

    private ImageLoader mImageLoader;

    private static final int DISK_CACHE_SIZE = 200 * 1024 * 1024;//200MB

    public static DYImageLoader getInstance() {
        return ourInstance;
    }

    private DYImageLoader() {
        mImageLoader = ImageLoader.getInstance();
        ImageLoaderConfiguration configuration;
        if (DYLog.DEBUG) {
            configuration = new ImageLoaderConfiguration.Builder(App.getInstance())
                    .imageDownloader(new SecureImageDownloader(App.getInstance()))
                    .writeDebugLogs()
                    .diskCacheSize(DISK_CACHE_SIZE)
                    .build();
        } else {
            configuration = new ImageLoaderConfiguration.Builder(App.getInstance())
                    .imageDownloader(new SecureImageDownloader(App.getInstance()))
                    .diskCacheSize(DISK_CACHE_SIZE)
                    .build();
        }
        mImageLoader.init(configuration);
    }

    /**
     * 加载头像
     * @param url
     * @param view
     */
    public void displayAvatar(String url, ImageView view) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading()
//                .showImageOnFail()
//                .showImageForEmptyUri()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .build();
        mImageLoader.displayImage(url, view, options);
    }

    /**
     * 加载缩略图
     * @param url
     * @param view
     */
    public void displayThumbnail(String url, ImageView view) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading()
//                .showImageOnFail()
//                .showImageForEmptyUri()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .build();
        mImageLoader.displayImage(url, view, options);
    }

    /**
     * 加载原图
     * @param url
     * @param view
     */
    public void displayFullImage(String url, ImageView view) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading()
//                .showImageOnFail()
//                .showImageForEmptyUri()
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .build();
        mImageLoader.displayImage(url, view, options);
    }

    /**
     * 加载原图
     * @param url
     * @param view
     * @param listener
     */
    public void displayFullImage(String url, ImageView view, ImageLoadingListener listener) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading()
//                .showImageOnFail()
//                .showImageForEmptyUri()
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .build();
        mImageLoader.displayImage(url, view, options, listener);
    }

    /**
     * 加载原图(可以设置缓存)
     * @param url
     * @param view
     */
    public void displayFullImage(String url, ImageView view,
                                 boolean cacheInMemory, boolean cacheOnDisk) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading()
//                .showImageOnFail()
//                .showImageForEmptyUri()
                .cacheInMemory(cacheInMemory)
                .cacheOnDisk(cacheOnDisk)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .build();
        mImageLoader.displayImage(url, view, options);
    }

    /**
     * 加载原图
     * @param url
     * @param view
     * @param listener
     */
    public void displayFullImage(String url, ImageView view, boolean cacheInMemory, boolean cacheOnDisk, ImageLoadingListener listener) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.update)
//                .showImageOnFail(R.drawable.update)
//                .showImageForEmptyUri()
                .cacheInMemory(cacheInMemory)
                .cacheOnDisk(cacheOnDisk)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .build();
        mImageLoader.displayImage(url, view, options, listener);
    }

    /**
     * 加载原图(附加 Authorization token)
     * @param url
     * @param view
     * @param cacheInMemory
     * @param cacheOnDisk
     * @param listener
     */
    public void displayFullImageAuthorization(String url, ImageView view, boolean cacheInMemory, boolean cacheOnDisk, ImageLoadingListener listener) {

        Map<String, String> extra = null;

        if (CurrentUser.getInstance().isLogin()) {

            extra = new HashMap<String, String>();
            extra.put("Authorization", "Bearer " + CurrentUser.getInstance().getAccess_token());
        }

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(cacheInMemory)
                .cacheOnDisk(cacheOnDisk)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .extraForDownloader(extra)
                .considerExifParams(true)
                .build();

        mImageLoader.displayImage(url, view, options, listener);
    }

    /**
     * 暂停图片加载
     */
    public void pause() {
        mImageLoader.pause();
    }

    /**
     * 继续图片加载
     */
    public void resume() {
        mImageLoader.resume();
    }

    /**
     * 取消一个图片加载任务
     * @param view
     */
    public void cancel(ImageView view) {
        mImageLoader.cancelDisplayTask(view);
    }

    /**
     * 清除内存缓存
     */
    public void clearMemoryCache() {
        mImageLoader.clearMemoryCache();
    }

    /**
     * 清除本地文件缓存
     */
    public void clearDiskCache() {
        mImageLoader.clearDiskCache();
    }
}
