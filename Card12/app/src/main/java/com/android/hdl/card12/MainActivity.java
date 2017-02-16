package com.android.hdl.card12;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button button,button1,button2;
    private ImageView image;
    private TextView tv;


    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            tv.setText("欢迎"+data.get("name")+"登录");
            Toast.makeText( getApplicationContext(), data.get("name"), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);

            Toast.makeText(MainActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(MainActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(MainActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        image = (ImageView) findViewById(R.id.image);
        tv = (TextView) findViewById(R.id.textView);
        button.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        Glide.with(this).load("https://www.baidu.com/img/bd_logo1.png").placeholder(R.mipmap.ic_launcher).error(R.drawable.umeng_socialize_back_icon).into(image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        App.getUMShareAPI().onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                new ShareAction(MainActivity.this)
                        .withText("就是怪我怪我怪我咯，O(∩_∩)O哈哈~")
                        .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                        .withTitle("欢迎来到“怪我咯”的主页")
                        .withTargetUrl("http://user.qzone.qq.com/1179446501")

                        .withMedia(new UMImage(this,R.mipmap.myicon))
                        .setCallback(umShareListener)
                        .open();
                break;
            case R.id.button1:
                new ShareAction(MainActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .withText("hello")
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.button2:
                App.getUMShareAPI().getPlatformInfo(this, SHARE_MEDIA.QQ, umAuthListener);
                break;
        }
    }
}
