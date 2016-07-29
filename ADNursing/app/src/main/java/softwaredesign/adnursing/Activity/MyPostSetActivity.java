package softwaredesign.adnursing.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import softwaredesign.adnursing.Adapter.PostPreviewAdapter;
import softwaredesign.adnursing.ApplicationManager;
import softwaredesign.adnursing.Custom.MyListView;
import softwaredesign.adnursing.Custom.RefreshLayout;
import softwaredesign.adnursing.Data.PostData;
import softwaredesign.adnursing.HttpApplication;
import softwaredesign.adnursing.R;
import softwaredesign.adnursing.Utils.HttpUtils;

public class MyPostSetActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView top_bar_back_icon;
    private TextView top_bar_info_txt;
    private MyListView my_post_set_list;
    private RefreshLayout refresh_ly;

    private String postType;                // 帖子类型
    private ArrayList<PostData> postDatas;  // 页面中帖子的具体信息
    private PostPreviewAdapter postPreviewAdapter = new PostPreviewAdapter(MyPostSetActivity.this, postDatas);;


    /**
     * Handler处理点击帖子预览跳转到帖子详情
     */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x001:
                    postPreviewAdapter = new PostPreviewAdapter(MyPostSetActivity.this, postDatas);
                    my_post_set_list.setAdapter(postPreviewAdapter);

                    my_post_set_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(MyPostSetActivity.this, ForumActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("postId", postPreviewAdapter.getItem(i).getPostId());
                            bundle.putString("userName", postPreviewAdapter.getItem(i).getUser().getName());
                            bundle.putString("imageDir", postPreviewAdapter.getItem(i).getImagesDir());
                            bundle.putString("userImage", postPreviewAdapter.getItem(i).getUser().getImageDir());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });

                    my_post_set_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                            final int finalI = i;
                            AlertDialog.Builder builder = new AlertDialog.Builder(MyPostSetActivity.this);
                            builder.setMessage("确定要删除该帖吗?");
                            builder.setTitle("提示");
                            builder.setPositiveButton("确认",
                                    new android.content.DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            deletePost(postPreviewAdapter.getItem(finalI).getPostId(), finalI);
                                        }
                                    });
                            builder.setNegativeButton("取消",
                                    new android.content.DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            builder.create().show();
                            return true;
                        }
                    });
                    refresh_ly.setRefreshing(false);
//                    postPreviewAdapter.notifyDataSetChanged();
                    break;
                case 0x002:
                    postPreviewAdapter.remove(msg.arg1);
                    break;
                case 0x003:
                    postPreviewAdapter.remove(msg.arg1);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationManager.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post_set);

        initView();
        initTile();
        requestPost();
    }


    /**
     * 初始化View
     */
    private void initView() {
        // 获取View
        top_bar_back_icon = (ImageView) findViewById(R.id.top_bar_back_icon);
        top_bar_info_txt = (TextView) findViewById(R.id.top_bar_info_txt);
        my_post_set_list = (MyListView) findViewById(R.id.my_post_set_list);
        refresh_ly = (RefreshLayout) findViewById(R.id.refresh_ly);

        refresh_ly.setRefreshing(true);

        // 设置Listener
        top_bar_back_icon.setOnClickListener(this);

        refresh_ly.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestPost();
            }
        });

        refresh_ly.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                refresh_ly.setLoading(false);
            }
        });
    }


    /**
     * 设置页面顶部栏标题
     */
    private void initTile() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        postType = title;
        top_bar_info_txt.setText(title);
    }


    /**
     * 根据类型请求帖子数据
     */
    private void requestPost() {
        if (postType.equals("收藏")) {
            new Thread() {
                public void run() {
                    postDatas = HttpUtils.getCollectedPost(HttpApplication.getUserId());
                    handler.sendEmptyMessage(0x001);
                };
            }.start();
        } else if (postType.equals("我的帖子")) {
            new Thread() {
                public void run() {
                    postDatas = HttpUtils.getPostWithUserId(HttpApplication.getUserId());
                    handler.sendEmptyMessage(0x001);
                };
            }.start();
        }
    }


    private void deletePost(final int posId, final int i) {
        if (postType.equals("我的帖子")) {
            new Thread() {
                public void run() {
                    System.out.println("delete post");
                    int result = HttpUtils.deletePostUrl(HttpApplication.getUserId(), posId);
                    System.out.println(result);
                    if (result == posId) {
                        Message msg = Message.obtain();
                        msg.arg1 = i;
                        msg.what = 0x002;
                        handler.sendMessage(msg);
                    }
                };
            }.start();
        } else if (postType.equals("收藏")) {
            new Thread() {
                public void run() {
                    System.out.println("delete collect");
                    int result = HttpUtils.cancelCollectPost(HttpApplication.getUserId(), posId);
                    System.out.println(result);
                    if (result == HttpApplication.getUserId()) {
                        Message msg = Message.obtain();
                        msg.arg1 = i;
                        msg.what = 0x003;
                        handler.sendMessage(msg);
                    }
                };
            }.start();
        }
    }


    /**
     * View的OnClick监听器
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back_icon:
                finish();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        ApplicationManager.getInstance().deleteActivity(this);
        super.onDestroy();
    }

}
