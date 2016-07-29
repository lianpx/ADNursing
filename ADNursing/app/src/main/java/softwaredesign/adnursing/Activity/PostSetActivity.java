package softwaredesign.adnursing.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import softwaredesign.adnursing.ApplicationManager;
import softwaredesign.adnursing.Custom.RefreshLayout;
import softwaredesign.adnursing.Utils.HttpUtils;
import softwaredesign.adnursing.Custom.MyListView;
import softwaredesign.adnursing.Data.PostData;
import softwaredesign.adnursing.Adapter.PostPreviewAdapter;
import softwaredesign.adnursing.R;

public class PostSetActivity extends AppCompatActivity  implements View.OnClickListener {

    private ImageView top_bar_back_icon;
    private TextView top_bar_info_txt;
    private MyListView post_list;
    private RefreshLayout refresh_ly;

    private String postType;                // 帖子类型
    private ArrayList<PostData> postDatas;  // 页面中帖子的具体信息


    /**
     * Handler处理点击帖子预览跳转到帖子详情
     */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x001:
                    final PostPreviewAdapter postPreviewAdapter = new PostPreviewAdapter(PostSetActivity.this, postDatas);
                    post_list.setAdapter(postPreviewAdapter);
                    post_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(PostSetActivity.this, ForumActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("postId", postPreviewAdapter.getItem(i).getPostId());
                            bundle.putString("userName", postPreviewAdapter.getItem(i).getUser().getName());
                            bundle.putString("imageDir", postPreviewAdapter.getItem(i).getImagesDir());
                            bundle.putString("userImage", postPreviewAdapter.getItem(i).getUser().getImageDir());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                    refresh_ly.setRefreshing(false);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationManager.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_set);

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
        post_list = (MyListView) findViewById(R.id.post_list);
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
        final String type = postType;
        new Thread() {
            public void run() {
                postDatas = HttpUtils.getPostsWithType(type, "newest");
                handler.sendEmptyMessage(0x001);
            };
        }.start();
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
