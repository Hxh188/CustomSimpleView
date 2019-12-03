package com.hxh.simpleview_lib.txwebview;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchUIUtil;
import io.reactivex.functions.Consumer;

/**
 * @author huangxiaohui
 * @date 2019-12-02
 */
public class TxWebActivity extends AppCompatActivity {
    public static final String INTENT_URL = "url";
    private static final int REQUEST_GET_IMAGE = 1000;
    private ProgressBar pb_progress;
    private TxWebView txwebv;
    private View v_back, v_close;
    private TextView tv_title;
    private String url;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = new TxWebView(this);
        setContentView(R.layout.txwebview_activity_tx_web);
        url = getIntent().getStringExtra(INTENT_URL);
        findviews();
        setListeners();
        txwebv.loadUrl(url);

        // 自定义图片加载器
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
    }

    private void setListeners() {
        v_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        v_close.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txwebv.setOperationListener(new TxWebView.OnOperationListener() {
            @Override
            public void showProgress(int newProgress) {
                if(newProgress < 100)
                {
                    pb_progress.setVisibility(View.VISIBLE);
                    pb_progress.setProgress(newProgress);
                }else
                {
                    pb_progress.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void showTitle(String title) {
                tv_title.setText(title);
            }

            @Override
            public void choosePicture() {
                new RxPermissions(TxWebActivity.this)
                        .request(Manifest.permission.READ_EXTERNAL_STORAGE
                                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                                , Manifest.permission.CAMERA)
                        .subscribe(new Consumer <Boolean>()
                        {

                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if(!aBoolean)
                                {
                                    Toast.makeText(TxWebActivity.this, "相关权限未获取，建议您开启后使用", Toast.LENGTH_SHORT).show();
                                    txwebv.resetImageSelect();
                                }else{
                                    selectImage();
                                }
                            }
                        });

            }
        });
    }

    private void selectImage() {
        // 自由配置选项
        ISListConfig config = new ISListConfig.Builder()
                // 是否多选, 默认true
                .multiSelect(false)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(false)
                // “确定”按钮背景色
                .btnBgColor(Color.GRAY)
                // “确定”按钮文字颜色
                .btnTextColor(Color.BLUE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#3F51B5"))
                // 标题
                .title("图片")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(Color.parseColor("#3F51B5"))
                // 裁剪大小。needCrop为true的时候配置
                .cropSize(1, 1, 200, 200)
                //.needCrop(true)
                // 第一个是否显示相机，默认true
                .needCamera(true)
                // 最大选择图片数量，默认9
                .maxNum(9)
                .build();

        // 跳转到图片选择器
        ISNav.getInstance().toListActivity(TxWebActivity.this, config, REQUEST_GET_IMAGE);
    }

    private void findviews() {
        pb_progress = findViewById(R.id.pb_progress);
        txwebv = findViewById(R.id.txwebv);
        v_back = findViewById(R.id.v_back);
        v_close = findViewById(R.id.v_close);
        tv_title = findViewById(R.id.tv_title);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String imgFile = null;
        if(data != null)
        {
            List<String> pathList = data.getStringArrayListExtra("result");
            if(pathList != null && pathList.size() > 0)
            {
                imgFile = pathList.get(0);
            }
        }
        txwebv.loadImage(requestCode == REQUEST_GET_IMAGE, imgFile);
    }

    @Override
    public void onBackPressed() {
        if(txwebv.onBack())
        {
            return;
        }
        super.onBackPressed();
    }

    public static void start(Context context, String url)
    {
        Intent intent = new Intent(context, TxWebActivity.class);
        intent.putExtra(INTENT_URL, url);
        context.startActivity(intent);
    }

}
