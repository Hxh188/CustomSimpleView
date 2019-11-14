package com.hxh.simpleview_lb;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;

public class SearchEditText extends AppCompatEditText {

    private OnSearchListener searchListener;

    public SearchEditText(Context context) {
        super(context);
        init();
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化
     */
    private void init()
    {
        Drawable draLeft = getResources().getDrawable(R.drawable.icon_search);
        draLeft.setBounds(0, 0, draLeft.getIntrinsicWidth(), draLeft.getIntrinsicHeight());
        setCompoundDrawables(draLeft, null, null, null);
        setPadding(UIUtils.dip2px(getContext(), 15), 0, 0, 0);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        setCompoundDrawablePadding(UIUtils.dip2px(getContext(), 5));
        setMinHeight(UIUtils.dip2px(getContext(), 30));
        setGravity(Gravity.CENTER_VERTICAL);
        setBackgroundResource(R.drawable.shape_rect_circle_light_gray);
        setMaxLines(1);
        setInputType(InputType.TYPE_CLASS_TEXT);
        setSingleLine(true);
        setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideSoftInput(SearchEditText.this);
                    if(searchListener != null)
                    {
                        searchListener.onSearch(SearchEditText.this.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void hideSoftInput(View view){
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    public void setSearchListener(OnSearchListener searchListener) {
        this.searchListener = searchListener;
    }

    public interface OnSearchListener{
        public void onSearch(String str);
    }
}
