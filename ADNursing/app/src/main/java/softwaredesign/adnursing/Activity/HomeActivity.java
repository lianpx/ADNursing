package softwaredesign.adnursing.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import softwaredesign.adnursing.ApplicationManager;
import softwaredesign.adnursing.Adapter.BannerAdapter;
import softwaredesign.adnursing.Utils.HttpUtils;
import softwaredesign.adnursing.Custom.MyListView;
import softwaredesign.adnursing.Data.PostData;
import softwaredesign.adnursing.Adapter.PostPreviewAdapter;
import softwaredesign.adnursing.R;
import softwaredesign.adnursing.Utils.HttpUtils3;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView bottomHomeIcon;
    private ImageView bottomCommunicateIcon;
    private ImageView bottomTestIcon;
    private ImageView bottomMyIcon;
    private LinearLayout post_info_1;
    private LinearLayout post_info_2;
    private LinearLayout post_info_3;
    private ViewPager bannerViewPager;
    private LinearLayout bannerPointLayout;
    private MyListView notice_post_list;
    private MyListView news_post_list;
    private MyListView research_post_list;

    private BannerAdapter bannerAdapter;

    private int pointIndex = 0;                     // banner指示点的下标
    private boolean isStop = false;                 // 判断banner轮换是否停止
    private List<ImageView> imageViewList;
    private int[] bannerImages = {R.mipmap.banner_2, R.mipmap.banner_3, R.mipmap.banner_4, R.mipmap.banner_1};  // banner上的四幅图片
    // 分别保存三个栏目的帖子信息
    private ArrayList<PostData> noticePostDatas;
    private ArrayList<PostData> newsPostDatas;
    private ArrayList<PostData> researchPostDatas;


    /**
     * Handler处理点击more后页面跳转
     */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x001:
                    final PostPreviewAdapter postPreviewAdapter1 = new PostPreviewAdapter(HomeActivity.this, noticePostDatas);
//                    System.out.println("1 "+noticePostDatas.get(0).getTitle());
                    notice_post_list.setAdapter(postPreviewAdapter1);
                    notice_post_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(HomeActivity.this, ForumActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("postId", postPreviewAdapter1.getItem(i).getPostId());
                            bundle.putString("userName", postPreviewAdapter1.getItem(i).getUser().getName());
                            bundle.putString("imageDir", postPreviewAdapter1.getItem(i).getImagesDir());
                            bundle.putString("userImage", postPreviewAdapter1.getItem(i).getUser().getImageDir());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                    break;
                case 0x002:
                    final PostPreviewAdapter postPreviewAdapter2 = new PostPreviewAdapter(HomeActivity.this, newsPostDatas);
//                    System.out.println("2 "+newsPostDatas.get(0).getTitle());
                    news_post_list.setAdapter(postPreviewAdapter2);
                    news_post_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(HomeActivity.this, ForumActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("postId", postPreviewAdapter2.getItem(i).getPostId());
                            bundle.putString("userName", postPreviewAdapter2.getItem(i).getUser().getName());
                            bundle.putString("imageDir", postPreviewAdapter2.getItem(i).getImagesDir());
                            bundle.putString("userImage", postPreviewAdapter2.getItem(i).getUser().getImageDir());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                    break;
                case 0x003:
                    final PostPreviewAdapter postPreviewAdapter3 = new PostPreviewAdapter(HomeActivity.this, researchPostDatas);
//                    System.out.println("3 "+researchPostDatas.get(0).getTitle());
                    research_post_list.setAdapter(postPreviewAdapter3);
                    research_post_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(HomeActivity.this, ForumActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("postId", postPreviewAdapter3.getItem(i).getPostId());
                            bundle.putString("userName", postPreviewAdapter3.getItem(i).getUser().getName());
                            bundle.putString("imageDir", postPreviewAdapter3.getItem(i).getImagesDir());
                            bundle.putString("userImage", postPreviewAdapter3.getItem(i).getUser().getImageDir());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationManager.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
        initBanner();
        initAction();
        // 分别获取三个栏目的帖子信息
        requestPost("护理须知", 0x001);
        requestPost("临床新闻", 0x002);
        requestPost("研究前沿", 0x003);

        // 新建子线程让banner每四秒图片轮换播放
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop) {
                    SystemClock.sleep(4000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bannerViewPager.setCurrentItem(bannerViewPager.getCurrentItem() + 1);
                        }
                    });
                }
            }
        }).start();
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

        post_info_1 = (LinearLayout) findViewById(R.id.post_info_1);
        post_info_2 = (LinearLayout) findViewById(R.id.post_info_2);
        post_info_3 = (LinearLayout) findViewById(R.id.post_info_3);

        bannerViewPager = (ViewPager) findViewById(R.id.home_banner);
        bannerPointLayout = (LinearLayout) findViewById(R.id.home_banner_points);

        notice_post_list = (MyListView) findViewById(R.id.notice_post_list);
        news_post_list = (MyListView) findViewById(R.id.news_post_list);
        research_post_list = (MyListView) findViewById(R.id.research_post_list);

        // 设置View的Listener
        bottomHomeIcon.setOnClickListener(this);
        bottomCommunicateIcon.setOnClickListener(this);
        bottomTestIcon.setOnClickListener(this);
        bottomMyIcon.setOnClickListener(this);

        post_info_1.setOnClickListener(this);
        post_info_2.setOnClickListener(this);
        post_info_3.setOnClickListener(this);

        // 底部导航栏选中
        bottomHomeIcon.setSelected(true);
    }


    /**
     * 初始化banner
     */
    private void initBanner() {
        imageViewList = new ArrayList<>();
        //设置banner样式
        for (int i = 0; i < bannerImages.length; i++) {
            ImageView imageView = new ImageView(HomeActivity.this);
            imageView.setBackgroundResource(bannerImages[i]);
            imageViewList.add(imageView);

            View pointView = new View(HomeActivity.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(25, 25);
            layoutParams.leftMargin = 20;
            pointView.setBackgroundResource(R.drawable.banner_point);
            pointView.setLayoutParams(layoutParams);
            pointView.setEnabled(false);
            bannerPointLayout.addView(pointView);
        }

        bannerAdapter = new BannerAdapter(imageViewList);
        bannerViewPager.setAdapter(bannerAdapter);
    }


    /**
     * 设置banner的行为
     */
    private void initAction() {
        bannerViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                int newPosition = position % bannerImages.length;
                bannerPointLayout.getChildAt(newPosition).setEnabled(true);
                bannerPointLayout.getChildAt(pointIndex).setEnabled(false);
                pointIndex = newPosition;
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        int index = (Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2 % imageViewList.size());
        bannerViewPager.setCurrentItem(index);
        bannerPointLayout.getChildAt(pointIndex).setEnabled(true);
    }


    /**
     * 请求不同栏目的帖子信息
     * @param postType
     * @param message
     */
    private void requestPost(String postType, int message) {
        final String type = postType;
        final int msg = message;
        new Thread() {
            public void run() {
                switch (msg) {
                    case 0x001:
                        noticePostDatas = HttpUtils.getPostsWithType(type, "newest");
                        handler.sendEmptyMessage(msg);
                        break;
                    case 0x002:
                        newsPostDatas = HttpUtils.getPostsWithType(type, "newest");
                        handler.sendEmptyMessage(msg);
                        break;
                    case 0x003:
                        researchPostDatas = HttpUtils.getPostsWithType(type, "newest");
                        handler.sendEmptyMessage(msg);
                        break;
                }
            };
        }.start();
    }


    /**
     * 设置View的Listener
     * @param view
     */
    @Override
    public void onClick(View view) {
        Intent intent;
        Bundle bundle;
        switch (view.getId()) {
            case R.id.bottom_bar_communicate_icon:
                intent = new Intent(HomeActivity.this, CommunicateActivity.class);
                startActivity(intent);
                break;
            case R.id.bottom_bar_my_icon:
                intent = new Intent(HomeActivity.this, MyActivity.class);
                startActivity(intent);
                break;
            case R.id.bottom_bar_test_icon:
                intent = new Intent(HomeActivity.this, TestActivity.class);
                startActivity(intent);
                break;
            case R.id.top_bar_search_icon:
                intent = new Intent(HomeActivity.this, NewTestActivity.class);
                startActivity(intent);
                break;
            case R.id.post_info_1:
                intent = new Intent(HomeActivity.this, PostSetActivity.class);
                bundle = new Bundle();
                bundle.putString("title", "护理须知");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.post_info_2:
                intent = new Intent(HomeActivity.this, PostSetActivity.class);
                bundle = new Bundle();
                bundle.putString("title", "临床新闻");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.post_info_3:
                intent = new Intent(HomeActivity.this, PostSetActivity.class);
                bundle = new Bundle();
                bundle.putString("title", "研究前沿");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }


    /**
     * 按下手机返回键，弹出对话框
     */
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


    @Override
    protected void onDestroy() {
        isStop = true;
        ApplicationManager.getInstance().deleteActivity(this);
        super.onDestroy();
    }
}
