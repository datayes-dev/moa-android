package com.datayes.dyoa.module;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.datayes.dyoa.R;
import com.datayes.dyoa.module.code.activity.ScanCodeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jump(View view) {
        switch(view.getId()) {
            case R.id.btn_scan_code:
                startActivity(new Intent(this, ScanCodeActivity.class));
                break;
        }
    }

}
