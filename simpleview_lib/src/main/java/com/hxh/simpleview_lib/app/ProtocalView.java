package com.hxh.simpleview_lib.app;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hxh.simpleview_lib.R;

/**
 * 注册页、登录页用到的协议视图
 */
public class ProtocalView extends LinearLayout {
    private CheckBox cb_protocol;
    private TextView tv_protocol;
    public ProtocalView(Context context) {
        super(context);
        init();
    }

    public ProtocalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.basicdra_layout_protocal, this, true);

        cb_protocol = findViewById(R.id.cb_protocol);
        tv_protocol = findViewById(R.id.tv_protocol);

        showUserProtocal();
    }

    public boolean isChecked()
    {
        return cb_protocol.isChecked();
    }

    /**
     * 显示用户协议
     */
    private void showUserProtocal() {
        //用户协议
        String strService = "《服务协议》";
        String strYinsi = "《隐私政策》";
        String str = new StringBuilder().append(strService).append("、").append(strYinsi).toString();
        SpannableString protocolStr = new SpannableString(str);
        protocolStr.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(getContext(), "用户协议", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(ds.linkColor);
                ds.setUnderlineText(false);
            }
        }, str.indexOf(strService), str.indexOf(strService) + strService.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        protocolStr.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(getContext(), "隐私政策", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(ds.linkColor);
                ds.setUnderlineText(false);
            }
        }, str.indexOf(strYinsi), str.indexOf(strYinsi) + strYinsi.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_protocol.setText(protocolStr);
        tv_protocol.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
