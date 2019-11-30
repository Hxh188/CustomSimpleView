package com.hxh.simpleview_lib.app;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * 验证码框
 */
public class VerifyCodeTextView extends AppCompatTextView {
    private long sendLeftTime = 59 * 1000;// 重新发送验证码时间,1分钟
    private CountDownTimer countDownTimer = new CountDownTimer(sendLeftTime, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            long remainedSecs = millisUntilFinished / 1000;
            setEnabled(false);
            setText(String.format("%02d", remainedSecs % 60) + "秒后重发");
        }

        @Override
        public void onFinish() {
            setText("获取验证码");
            setEnabled(true);
            cancel();
        }
    };

    public VerifyCodeTextView(Context context) {
        super(context);
    }

    public VerifyCodeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void start()
    {
        countDownTimer.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        countDownTimer.cancel();
    }
}
