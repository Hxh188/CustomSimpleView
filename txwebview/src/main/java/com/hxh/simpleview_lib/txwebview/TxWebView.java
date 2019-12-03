package com.hxh.simpleview_lib.txwebview;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.File;

public class TxWebView extends WebView {
    //选择图片回调
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessageAboveL;

    private OnOperationListener operationListener;

    public TxWebView(Context context) {
        super(context);
        init();
    }

    public TxWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        initWebView();
    }

    private void initWebView() {
        TxWebView wv_content = this;
        WebSettings webSettings = wv_content.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setGeolocationEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setAllowFileAccess(true); // 允许访问文件

        webSettings.setPluginState(WebSettings.PluginState.ON);
//
        webSettings.setJavaScriptEnabled(true);  //支持js
//
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);  //提高渲染的优先级
        webSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//

        webSettings.setBuiltInZoomControls(true); //设置可以缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
//
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        webSettings.supportMultipleWindows();  //多窗口
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings. setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点

        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        //wv_content.setWebViewClient(new WebViewClient());

        if (Build.VERSION.SDK_INT >= 21) {
            webSettings.setMixedContentMode(WebSettings.LOAD_NORMAL);
        }

        //增加接口方法,让html页面调用
        // wv_content.addJavascriptInterface(this,"app");
        wv_content.setWebViewClient(new MyWebViewClient());

        wv_content.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                showProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                showTitle(title);
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
                mUploadMessageAboveL = valueCallback;
                choosePicture();
                return true;
            }

            // Android > 4.1.1 调用这个方法
            public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                        String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                choosePicture();

            }

            // 3.0 + 调用这个方法
            public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                        String acceptType) {
                mUploadMessage = uploadMsg;
                choosePicture();

            }

            // Android < 3.0 调用这个方法
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                choosePicture();
            }
        });
    }

    /**
     * 返回处理
     * @return
     */
    public boolean onBack()
    {
        TxWebView wv_content = this;
        if (wv_content.canGoBack()) {
            wv_content.goBack();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 选择图片后显示图片
     * @param requeCodeEqual
     */
    public void loadImage(boolean requeCodeEqual, String imgFile)
    {
        if(TextUtils.isEmpty(imgFile) || !new File(imgFile).exists())
        {
            resetImageSelect();
            return;
        }

        if(mUploadMessage != null) {
            if (requeCodeEqual) {
                Uri result = Uri.fromFile(new File(imgFile));
                mUploadMessage.onReceiveValue(result);
            } else {
                mUploadMessage.onReceiveValue(null);
            }
            mUploadMessage = null;
        }else if(mUploadMessageAboveL != null)
        {
            if(requeCodeEqual)
            {
                Uri result = Uri.fromFile(new File(imgFile));
                mUploadMessageAboveL.onReceiveValue(new Uri[]{result});
            }else
            {
                mUploadMessageAboveL.onReceiveValue(null);
            }
            mUploadMessageAboveL = null;
        }
    }

    public void resetImageSelect()
    {
        if(mUploadMessage != null) {
            mUploadMessage.onReceiveValue(null);
            mUploadMessage = null;
        }else if(mUploadMessageAboveL != null)
        {
            mUploadMessageAboveL.onReceiveValue(null);
            mUploadMessageAboveL = null;
        }
    }

    /**
     * 显示网址内容
     * @param url
     */
    public void showUrl(String url)
    {
        loadUrl(url);
    }

    /**
     * 显示进度
     * @param newProgress
     */
    private void showProgress(int newProgress) {
        if(operationListener != null)
        {
            operationListener.showProgress(newProgress);
        }
    }

    /**
     * 显示标题
     * @param title
     */
    private void showTitle(String title)
    {
        if(operationListener != null)
        {
            operationListener.showTitle(title);
        }
    }

    /**
     * 跳转选择图片
     */
    private void choosePicture() {
        if(operationListener != null)
        {
            operationListener.choosePicture();
        }
    }

    public void setOperationListener(OnOperationListener operationListener) {
        this.operationListener = operationListener;
    }

    final class MyWebViewClient extends WebViewClient {
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
        }
    }

    public interface OnOperationListener
    {
        /**
         * 显示进度
         * @param newProgress
         */
        public void showProgress(int newProgress);

        /**
         * 显示标题
         * @param title
         */
        public void showTitle(String title);

        /**
         * 跳转选择图片
         */
        public void choosePicture();
    }
}
