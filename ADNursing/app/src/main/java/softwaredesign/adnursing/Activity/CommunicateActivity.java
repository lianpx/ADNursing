package softwaredesign.adnursing.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import softwaredesign.adnursing.ApplicationManager;
import softwaredesign.adnursing.Utils.HttpUtils;
import softwaredesign.adnursing.Custom.MyListView;
import softwaredesign.adnursing.Data.PostData;
import softwaredesign.adnursing.Adapter.PostPreviewDetailAdapter;
import softwaredesign.adnursing.R;

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

    private MyListView post_list;

    private String postOrder = "newest";        // 帖子默认排序为按照时间排序
    private String postType = "病症疑问";       // 帖子默认类型为“病症疑问”
    private ArrayList<PostData> postDatas;

    HashMap<String, Object> postNum;
    /**
     * handler处理点击帖子跳转到帖子详情页面
     */
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
                    break;
                case 0x002:
                    post_set_topic_1.setText("病症疑问\n"+postNum.get("BZYW")+"条");
                    post_set_topic_2.setText("名医推荐\n"+postNum.get("MYTJ")+"条");
                    post_set_topic_3.setText("药物推荐\n"+postNum.get("YWTJ")+"条");
                    post_set_topic_4.setText("交流分享\n"+postNum.get("JLFX")+"条");
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationManager.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicate);

        initView();
        requestPostNum();
        requestPost();
    }


    /**
     * 初始化View
     */
    private void initView() {
        // 获取View
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

        // 设置Listener
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
        // 当排序方法改变时触发该Listener
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


    /**
     * 根据类型和排序，向服务器请求帖子的数据
     */
    private void requestPost() {
        final String type = postType;
        if (postOrder == "newest") {
            new Thread() {
                public void run() {
                    postDatas = HttpUtils.getPostsWithType(type, "newest");
                    handler.sendEmptyMessage(0x001);
                }
            }.start();
        } else {
            new Thread() {
                public void run() {
                    postDatas = HttpUtils.getPostsWithType(type, "hottest");
                    handler.sendEmptyMessage(0x001);
                }
            }.start();
        }
    }


    private void requestPostNum() {
        new Thread() {
            public void run() {
                postNum = HttpUtils.getPostNum();
                handler.sendEmptyMessage(0x002);
            }
        }.start();
    }


    /**
     * 设置View的OnClick监听器
     * @param view
     */
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
                requestPostNum();
                break;
            case R.id.post_set_topic_3:
                postType = "药物推荐";
                requestPost();
                requestPostNum();
                break;
            case R.id.post_set_topic_4:
                postType = "交流分享";
                requestPost();
                requestPostNum();
                break;
        }
    }


    /**
     * 用户按下手机返回键触发该Listener
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            dialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
    protected void onDestroy() {
        ApplicationManager.getInstance().deleteActivity(this);
        super.onDestroy();
    }
}
