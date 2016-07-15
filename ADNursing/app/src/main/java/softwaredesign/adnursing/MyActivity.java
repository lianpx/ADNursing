package softwaredesign.adnursing;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView bottomHomeIcon;
    private ImageView bottomCommunicateIcon;
    private ImageView bottomTestIcon;
    private ImageView bottomMyIcon;

    private LinearLayout my_collection;
    private LinearLayout my_post;
    private LinearLayout my_review;
    private LinearLayout my_setting;
    private RelativeLayout my_basic_info;

    private UserData userData;

    private TextView my_name;
    private TextView my_id;


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x001:
                    if (userData != null) {
                        my_name.setText(userData.getName());
                        my_id.setText("默友号："+HttpApplication.getUserId());
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ApplicationManager.getInstance().addActivity(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        initView();
        initUserInfo();
    }

    private void initUserInfo() {
        new Thread() {
            public void run() {
                userData = HttpUtils.getUserInfoWithIdByGet(HttpApplication.getUserId());
                System.out.println(userData.getName());
                handler.sendEmptyMessage(0x001);

            };
        }.start();
    }

    private void initView() {
        bottomHomeIcon = (ImageView) findViewById(R.id.bottom_bar_home_icon);
        bottomCommunicateIcon = (ImageView) findViewById(R.id.bottom_bar_communicate_icon);
        bottomTestIcon = (ImageView) findViewById(R.id.bottom_bar_test_icon);
        bottomMyIcon = (ImageView) findViewById(R.id.bottom_bar_my_icon);

        my_collection = (LinearLayout) findViewById(R.id.my_collection);
        my_post = (LinearLayout) findViewById(R.id.my_post);
        my_review = (LinearLayout) findViewById(R.id.my_review);
        my_setting = (LinearLayout) findViewById(R.id.my_setting);
        my_basic_info = (RelativeLayout) findViewById(R.id.my_basic_info);

        my_name = (TextView) findViewById(R.id.my_name);
        my_id = (TextView) findViewById(R.id.my_id);

        bottomHomeIcon.setOnClickListener(this);
        bottomCommunicateIcon.setOnClickListener(this);
        bottomTestIcon.setOnClickListener(this);
        bottomMyIcon.setOnClickListener(this);

        my_collection.setOnClickListener(this);
        my_post.setOnClickListener(this);
        my_review.setOnClickListener(this);
        my_setting.setOnClickListener(this);
        my_basic_info.setOnClickListener(this);

        bottomMyIcon.setSelected(true);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        Bundle bundle;
        switch (view.getId()) {
            case R.id.bottom_bar_home_icon:
                intent = new Intent(MyActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.bottom_bar_communicate_icon:
                intent = new Intent(MyActivity.this, CommunicateActivity.class);
                startActivity(intent);
                break;
            case R.id.bottom_bar_test_icon:
                intent = new Intent(MyActivity.this, TestActivity.class);
                startActivity(intent);
                break;
            case R.id.my_collection:
                intent = new Intent(MyActivity.this, PostSetActivity.class);
                bundle = new Bundle();
                bundle.putString("title", "收藏");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.my_post:
                intent = new Intent(MyActivity.this, PostSetActivity.class);
                bundle = new Bundle();
                bundle.putString("title", "我的帖子");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.my_setting:
                intent = new Intent(MyActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.my_basic_info:
                intent = new Intent(MyActivity.this, ProfileActivity.class);
                startActivity(intent);
                break;
        }
    }


    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定要退出吗?");
        builder.setTitle("提示");
        builder.setPositiveButton("确认",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ApplicationManager.getInstance().exit();
                    }
                });
        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            dialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
