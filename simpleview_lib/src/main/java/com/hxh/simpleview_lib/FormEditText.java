package com.hxh.simpleview_lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FormEditText extends LinearLayout implements View.OnClickListener {
    private EditText et_input;
    private ImageView iv_clear, iv_pwd_visible;

    private FormType inputFormType = FormType.TEXT;
    private String hintText;
    private int maxInputLength;

    public FormEditText(Context context) {
        super(context);
        init(null);
    }

    public FormEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        LayoutInflater.from(getContext()).inflate(R.layout.simple_view_layout_form_edit_text, this, true);

        findViews();

        showClearIcon(et_input.getText().toString());

        setListeners();

        if(attrs != null)
        {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SimpleViewFormEditText);
            int type = a.getInt(R.styleable.SimpleViewFormEditText_form_et_type, FormType.TEXT.value);
            inputFormType = FormType.getEnum(type);
            hintText = a.getString(R.styleable.SimpleViewFormEditText_form_et_hint);
            maxInputLength = a.getInt(R.styleable.SimpleViewFormEditText_form_et_max_input_length, 15);
            a.recycle();
        }

        setHintText();
        setMaxInputLengthFilter();
        initInputTypeType();
    }

    /**
     * 设置监听
     */
    private void setListeners() {
        iv_clear.setOnClickListener(this);
        iv_pwd_visible.setTag(false);
        iv_pwd_visible.setOnClickListener(this);
        et_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showClearIcon(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 设置输入长度限制
     */
    private void setMaxInputLengthFilter() {
        et_input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxInputLength)});
    }

    /**
     * 设置提示文字
     */
    private void setHintText() {
        et_input.setHint(hintText);
    }

    /**
     * 根据输入框输入类型设置属性
     */
    private void initInputTypeType() {
        switch (inputFormType)
        {
            case TEXT:
                iv_pwd_visible.setVisibility(View.GONE);
                break;

            case PHONE:
                iv_pwd_visible.setVisibility(View.GONE);
                setPhoneType();
                break;

            case PASSWORD:
                iv_pwd_visible.setVisibility(View.VISIBLE);
                setPasswordType();
                break;
        }
    }

    /**
     * 设置为数字输入
     */
    private void setPhoneType() {
        et_input.setInputType(InputType.TYPE_CLASS_NUMBER );
    }

    /**
     * 根据文本是否为空判断是否显示清除文字内容图标
     * @param s
     */
    private void showClearIcon(String s) {
        if(!TextUtils.isEmpty(s))
        {
            iv_clear.setVisibility(View.VISIBLE);
        }else
        {
            iv_clear.setVisibility(View.INVISIBLE);
        }
    }

    private void findViews() {
        et_input = findViewById(R.id.simple_view_et_input);
        iv_clear = findViewById(R.id.simple_view_iv_clear);
        iv_pwd_visible = findViewById(R.id.simple_view_iv_pwd_visible);
    }

    /**
     * 设置为密码输入类型
     */
    private void setPasswordType()
    {
        String limits = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        et_input.setTransformationMethod(PasswordTransformationMethod.getInstance());
        et_input.setKeyListener(DigitsKeyListener.getInstance(limits));
    }

    @Override
    public void onClick(View v) {
        int vid = v.getId();
        if(vid == R.id.simple_view_iv_clear)
        {
            et_input.setText("");
        }else if(vid == R.id.simple_view_iv_pwd_visible)
        {
            boolean isVisible = !(boolean) iv_pwd_visible.getTag();
            iv_pwd_visible.setTag(isVisible);
            changePwdVisible(isVisible);
        }
        et_input.requestFocus();
    }

    /**
     * 设置密码是否可见
     * @param isVisible
     */
    private void changePwdVisible(boolean isVisible) {
        if(isVisible)
        {
            iv_pwd_visible.setImageResource(R.drawable.simple_view_icon_view_off);
            et_input.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else
        {
            iv_pwd_visible.setImageResource(R.drawable.simple_view_icon_view);
            et_input.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        et_input.setSelection(et_input.getText().toString().length());
    }

    /**
     * 获取输入框内容
     * @return
     */
    public String getEditContent()
    {
        return et_input.getText().toString();
    }

    public enum FormType {

        TEXT(0),
        PHONE(1),
        PASSWORD(2);

        int value;
        FormType(int value)
        {
            this.value = value;
        }

        public static FormType getEnum(int value) {
            FormType[] vs = FormType.values();
            for(FormType t:vs)
            {
                if(t.value == value)
                {
                    return t;
                }
            }
            return null;
        }
    }


}
