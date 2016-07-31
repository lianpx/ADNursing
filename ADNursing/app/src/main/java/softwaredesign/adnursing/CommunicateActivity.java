package softwaredesign.adnursing;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CommunicateActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView bottomHomeIcon;
    private ImageView bottomCommunicateIcon;
    private ImageView bottomTestIcon;
    private ImageView bottomMyIcon;

    private TextView post_set_topic_1;
    private TextView post_set_topic_2;
    private TextView post_set_topic_3;
    private TextView post_set_topic_4;

    private ImageView top_bar_plus_icon;

    private RadioButton select_text_newest;
    private RadioButton select_text_hottest;
    private View select_bar_newest;
    private View select_bar_hottest;

    private String postOrder = "newest";
    private String postType = "病症疑问";
    private ArrayList<PostData> postDatas;

    private MyListView post_list;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x001:
                    final PostPreviewDetailAdapter postPreviewDetailAdapter = new PostPreviewDetailAdapter(CommunicateActivity.this, postDatas);
                    post_list.setAdapter(postPreviewDetailAdapter);
                    post_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(CommunicateActivity.this, ForumActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("postId", postPreviewDetailAdapter.getItem(i).getPostId());
                            bundle.putString("userName", postPreviewDetailAdapter.getItem(i).getUser().getName());
                            bundle.putString("imageDir", postPreviewDetailAdapter.getItem(i).getImagesDir());
                            bundle.putString("userImage", postPreviewDetailAdapter.getItem(i).getUser().getImageDir());

                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ApplicationManager.getInstance().addActivity(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicate);


        initView();
        requestPost();

//        setListView();
    }

    private void initView() {
        bottomHomeIcon = (ImageView) findViewById(R.id.bottom_bar_home_icon);
        bottomCommunicateIcon = (ImageView) findViewById(R.id.bottom_bar_communicate_icon);
        bottomTestIcon = (ImageView) findViewById(R.id.bottom_bar_test_icon);
        bottomMyIcon = (ImageView) findViewById(R.id.bottom_bar_my_icon);

        post_set_topic_1 = (TextView) findViewById(R.id.post_set_topic_1);
        post_set_topic_2 = (TextView) findViewById(R.id.post_set_topic_2);
        post_set_topic_3 = (TextView) findViewById(R.id.post_set_topic_3);
        post_set_topic_4 = (TextView) findViewById(R.id.post_set_topic_4);

        select_text_newest = (RadioButton) findViewById(R.id.select_text_newest);
        select_text_hottest = (RadioButton) findViewById(R.id.select_text_hottest);
        select_bar_newest = (View) findViewById(R.id.select_bar_newest);
        select_bar_hottest = (View) findViewById(R.id.select_bar_hottest);

        top_bar_plus_icon = (ImageView) findViewById(R.id.top_bar_plus_icon);

        post_list = (MyListView) findViewById(R.id.post_list);

        bottomHomeIcon.setOnClickListener(this);
        bottomCommunicateIcon.setOnClickListener(this);
        bottomTestIcon.setOnClickListener(this);
        bottomMyIcon.setOnClickListener(this);

        post_set_topic_1.setOnClickListener(this);
        post_set_topic_2.setOnClickListener(this);
        post_set_topic_3.setOnClickListener(this);
        post_set_topic_4.setOnClickListener(this);

        select_text_newest.setChecked(true);
        select_bar_hottest.setVisibility(View.INVISIBLE);

        top_bar_plus_icon.setOnClickListener(this);

        bottomCommunicateIcon.setSelected(true);

        select_text_newest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    select_bar_newest.setVisibility(View.VISIBLE);
                    select_bar_hottest.setVisibility(View.INVISIBLE);
                    postOrder = "newest";
                    requestPost();
                }
            }
        });

        select_text_hottest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    select_bar_hottest.setVisibility(View.VISIBLE);
                    select_bar_newest.setVisibility(View.INVISIBLE);
                    postOrder = "hottest";
                    requestPost();
                }
            }
        });
    }


    private void requestPost() {
        final String type = postType;
        if (postOrder == "newest") {
            new Thread() {
                public void run() {
                    postDatas = HttpUtils.getPostWithTypeTimeOrderByGet(type);
                    handler.sendEmptyMessage(0x001);
                }
            }.start();
        } else {
            new Thread() {
                public void run() {
                    postDatas = HttpUtils.getPostWithTypeReviewOrderByGet(type);
                    handler.sendEmptyMessage(0x001);
                }
            }.start();
        }
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        Bundle bundle;
        switch (view.getId()) {
            case R.id.bottom_bar_home_icon:
                intent = new Intent(CommunicateActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.bottom_bar_test_icon:
                intent = new Intent(CommunicateActivity.this, TestActivity.class);
                startActivity(intent);
                break;
            case R.id.bottom_bar_my_icon:
                intent = new Intent(CommunicateActivity.this, MyActivity.class);
                startActivity(intent);
                break;
            case R.id.top_bar_plus_icon:
                intent = new Intent(CommunicateActivity.this, PostActivity.class);
                startActivity(intent);
                break;
            case R.id.post_set_topic_1:
                postType = "病症疑问";
                requestPost();
                break;
            case R.id.post_set_topic_2:
                postType = "名医推荐";
                requestPost();
                break;
            case R.id.post_set_topic_3:
                postType = "药物推荐";
                requestPost();
                break;
            case R.id.post_set_topic_4:
                postType = "交流分享";
                requestPost();
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
