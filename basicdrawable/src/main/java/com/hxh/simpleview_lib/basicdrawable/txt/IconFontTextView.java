package com.hxh.simpleview_lib.basicdrawable.txt;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.hxh.simpleview_lib.basicdrawable.util.TypefaceUtils;


/**
 * IconFont支持
 */
public class IconFontTextView extends AppCompatTextView {

    public IconFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public IconFontTextView(Context context) {
        super(context);
        init();
    }

    public IconFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }


    public void init() {
        Typeface typeface = TypefaceUtils.get(getContext(), "fonts/iconfont.ttf");
        setTypeface(typeface);
    }
}
