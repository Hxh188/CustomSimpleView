package com.hxh.customsimpleview;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hxh.simpleview_lib.txwebview.TxWebView;

public class MyWebActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TxWebView wv = new TxWebView(this);
        setContentView(wv);
        wv.showUrl("http://www.nlinks.cn/appapi/web/takePhotoAnyTime/guidePage?userId=551fc295ba524bb7a07faeb878ddb0fd");
    }
}
