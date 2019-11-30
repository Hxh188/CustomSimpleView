package com.hxh.customsimpleview;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author huangxiaohui
 * @date 2019-11-28
 */
public class TestBasicDrawActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basicdra_test);
    }

    public void sendVerify(View view) {
        ((com.hxh.simpleview_lib.app.VerifyCodeTextView)view).start();
    }
}
