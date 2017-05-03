package test.bwie.com.ymsharedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button qq;
    private TextView mName;
    private ImageView mImage;
    private Button mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      initView();


    }

    private void initView() {
        qq = (Button) findViewById(R.id.qq);
        mName = (TextView) findViewById(R.id.name);
        mImage = (ImageView) findViewById(R.id.image1);
        qq.setOnClickListener(this);
        mButton1 = (Button) findViewById(R.id.button1);
        mButton1.setOnClickListener(this);
    }

    private UMShareListener mUmShareListener=new UMShareListener() {

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {

        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    };
    UMAuthListener umAuthListener = new UMAuthListener() {
        private UMShareListener mUmShareListener=new UMShareListener() {

            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }
            @Override
            public void onResult(SHARE_MEDIA share_media) {

            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {

            }
        };
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            String qq_Name = data.get("screen_name");
            String iconurl=data.get("profile_image_url");
          mName.setText(qq_Name);
            Glide.with(MainActivity.this).load(iconurl).error(R.mipmap.ic_launcher).into(mImage);

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.qq:
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.QQ, umAuthListener);
               break;
            case  R.id.button1:
                UMShareConfig config = new UMShareConfig();
                config.isNeedAuthOnGetUserInfo(true);
                config.isOpenShareEditActivity(true);
                UMShareAPI.get(this).setShareConfig(config);
                UMImage image1 = new UMImage(this,R.drawable.umeng_socialize_back_icon);
                UMWeb web = new UMWeb("http://w.baidu.com");
                web.setTitle("我的新闻");//标题
                web.setThumb(image1);  //缩略图
                web.setDescription("就是你的新闻");//描述
                new ShareAction(MainActivity.this).setDisplayList(SHARE_MEDIA.QZONE,SHARE_MEDIA.QQ)
                        .withMedia(image1)
                        .setCallback(mUmShareListener)
                        .open();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }
}
