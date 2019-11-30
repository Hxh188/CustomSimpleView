package com.hxh.simpleview_lib.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxh.simpleview_lib.R;

public class SettingLineView extends RelativeLayout {
    private TextView tv_title, tv_subtitle, tv_right_text;
    private ImageView iv_right;
    private SettingValue settingValue;

    public SettingLineView(Context context) {
        super(context);
        init(null);
    }

    public SettingLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.simple_view_layout_setting_line, this, true);
        tv_title = findViewById(R.id.tv_title);
        tv_subtitle = findViewById(R.id.tv_subtitle);
        tv_right_text = findViewById(R.id.tv_right_text);
        iv_right = findViewById(R.id.iv_right);

        if(attrs != null)
        {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SimpleViewSetting);
            settingValue = new SettingValue();
            settingValue.title = a.getString(R.styleable.SimpleViewSetting_setting_title);
            settingValue.subTitle = a.getString(R.styleable.SimpleViewSetting_setting_subtitle);
            settingValue.rightText = a.getString(R.styleable.SimpleViewSetting_setting_right_text);
            settingValue.isRightIconVisible = a.getBoolean(R.styleable.SimpleViewSetting_setting_right_icon_visible, true);
            a.recycle();
        }

        initValue();
    }

    private void initValue() {
        if(settingValue != null)
        {
            tv_title.setText(settingValue.title);
            tv_subtitle.setText(settingValue.subTitle);
            tv_right_text.setText(settingValue.rightText);
            if(!TextUtils.isEmpty(settingValue.subTitle))
            {
                tv_subtitle.setVisibility(View.VISIBLE);
            }else
            {
                tv_subtitle.setVisibility(View.GONE);
            }
            iv_right.setVisibility(settingValue.isRightIconVisible? View.VISIBLE:View.GONE);
        }
    }

    private static class SettingValue{
        String title, subTitle, rightText;
        boolean isRightIconVisible;
    }
}
