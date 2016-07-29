package softwaredesign.adnursing.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import cn.qqtheme.framework.picker.FilePicker;
import softwaredesign.adnursing.ApplicationManager;
import softwaredesign.adnursing.HttpApplication;
import softwaredesign.adnursing.Utils.HttpUtils;
import softwaredesign.adnursing.Custom.MyListView;
import softwaredesign.adnursing.Data.PostData;
import softwaredesign.adnursing.R;
import softwaredesign.adnursing.Adapter.ReviewAdapter;
import softwaredesign.adnursing.Data.ReviewData;

public class ForumActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView main_sculpture;
    private TextView main_name;
    private TextView main_title;
    private TextView main_content;
    private TextView main_time;
    private TextView main_type;
    private TextView main_visitor_num;
    private ImageView[] imageViews = new ImageView[3];

    private EditText review_text_upload_text;
    private Button review_image_upload_button;
    private ImageView forum_collection_icon;

    private ImageView top_bar_back_icon;
    private LinearLayout formu_background;
    private MyListView post_list;
    private SwipeRefreshLayout forum_swipe_ly;

    private int postId;
    private String userName;
    private String imageDir;
    private String userImage;
    private PostData postData;
    private Bitmap postBitmaps[] = new Bitmap[3];                   // 保存帖子的图片
    private Bitmap userSculpture;                                   // 保存作者头像
    private int isCollected = 0;

    private ArrayList<String> imagePath = new ArrayList<>();        // 保存图片在服务器的路径
    private ArrayList<ReviewData> reviewDatas = new ArrayList<>();  // 保存帖子评论信息


    /**
     * 子线程通过Handle修改View
     */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                // 设置post的三张配图
                case 0x000:
                    imageViews[msg.arg1].setImageBitmap(postBitmaps[msg.arg1]);
                    break;
                // 设置post文字部分
                case 0x004:
                    main_name.setText(postData.getUser().getName());
                    main_title.setText(postData.getTitle());
                    main_content.setText(postData.getContent());
                    main_time.setText(postData.getModifiedTime());
                    main_type.setText(postData.getType());
                    main_visitor_num.setText(postData.getVisitorNum()+"");
                    break;
                // 设置作者头像
                case 0x005:
                    if (userSculpture != null) {
                        main_sculpture.setImageBitmap(userSculpture);
                    }
                    break;
                // 初试化评论
                case 0x006:
                    setReviews();
                    review_text_upload_text.setText("");
                    forum_swipe_ly.setRefreshing(false);
                    break;
                // 设置收藏icon样式
                case 0x008:
                    if (isCollected == 1) {
                        forum_collection_icon.setImageResource(R.mipmap.forum_collect_icon);
                    } else {
                        forum_collection_icon.setImageResource(R.mipmap.forum_nocollect_icon);
                    }
                    break;
                // 收藏成功
                case 0x009:
                    forum_collection_icon.setImageResource(R.mipmap.forum_collect_icon);
                    isCollected = 1;
                    Toast.makeText(ForumActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                    break;
                // 取消收藏
                case 0x010:
                    forum_collection_icon.setImageResource(R.mipmap.forum_nocollect_icon);
                    isCollected = 0;
                    Toast.makeText(ForumActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                    break;
                // 发布评论成功
                case 0x011:
                    Toast.makeText(ForumActivity.this, "评论发表成功", Toast.LENGTH_SHORT).show();
                    review_text_upload_text.setText("");
                    imagePath.clear();
                    review_image_upload_button.setText("+");
                    break;
                // 发布评论失败
                case 0x012:
                    Toast.makeText(ForumActivity.this, "评论发表失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationManager.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        initView();
        setPostMain();
        setReviews();
    }


    /**
     * 初始化View
     */
    private void initView() {
        //获取View
        top_bar_back_icon = (ImageView) findViewById(R.id.top_bar_back_icon);
        formu_background = (LinearLayout) findViewById(R.id.formu_background);

        main_sculpture = (ImageView) findViewById(R.id.main_sculpture);
        main_name = (TextView) findViewById(R.id.main_name);
        main_title = (TextView) findViewById(R.id.main_title);
        main_content = (TextView) findViewById(R.id.main_content);
        main_time = (TextView) findViewById(R.id.main_time);
        main_type = (TextView) findViewById(R.id.main_type);
        main_visitor_num = (TextView) findViewById(R.id.main_visitor_num);
        forum_collection_icon = (ImageView) findViewById(R.id.forum_collection_icon);

        imageViews[0] = (ImageView) findViewById(R.id.main_image_1);
        imageViews[1] = (ImageView) findViewById(R.id.main_image_2);
        imageViews[2] = (ImageView) findViewById(R.id.main_image_3);

        review_text_upload_text = (EditText) findViewById(R.id.review_text_upload_text);
        review_image_upload_button = (Button) findViewById(R.id.review_image_upload_button);

        post_list = (MyListView) findViewById(R.id.post_list);
        forum_swipe_ly = (SwipeRefreshLayout) findViewById(R.id.forum_swipe_ly);

        forum_swipe_ly.setRefreshing(true);

        // 设置Listener
        top_bar_back_icon.setOnClickListener(this);
        formu_background.setOnClickListener(this);
        review_image_upload_button.setOnClickListener(this);
        forum_collection_icon.setOnClickListener(this);

        review_text_upload_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                switch (actionId) {
                    case EditorInfo.IME_ACTION_DONE:
                        final String content = review_text_upload_text.getText().toString();
                        final ArrayList<File> file = new ArrayList<File>();
                        for (int i = 0; i < imagePath.size(); i++) {
                            file.add(new File(imagePath.get(i)));
                        }
                        if (content.equals("")) {
                            return true;
                        }
                        // 开辟新线程提交评论信息
                        new Thread() {
                            public void run() {
                                int reviewId = HttpUtils.uploadReviewText(HttpApplication.getUserId(), postId, content);
                                if (reviewId != -1) {
                                    handler.sendEmptyMessage(0x011);
                                    String result = HttpUtils.uploadReviewImages(file, reviewId);
                                    System.out.println(result);

                                    reviewDatas = HttpUtils.getReciewsWithPostId(postId);
                                    handler.sendEmptyMessage(0x006);
                                } else {
                                    handler.sendEmptyMessage(0x012);
                                }

                            };
                        }.start();
                        handled = true;
                        break;
                }
                return handled;
            }
        });

        forum_swipe_ly.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkCollection();
                requestPost();
                setReviews();
//                forum_swipe_ly.setRefreshing(false);
            }
        });
    }


    /**
     * 设置帖子页面正文内容
     */
    public void setPostMain() {
        Intent intent = getIntent();
        postId = intent.getIntExtra("postId", -1);
        userName = intent.getStringExtra("userName");
        imageDir = intent.getStringExtra("imageDir");
        userImage = intent.getStringExtra("userImage");
        // 若没有图片则隐藏ImageView
        if (imageDir.equals("")) {
            findViewById(R.id.main_image_set).setVisibility(View.GONE);
        } else {
            findViewById(R.id.main_image_set).setVisibility(View.VISIBLE);
        }
        checkCollection();
        requestPost();
    }


    private void checkCollection() {
        new Thread() {
            public void run() {
                isCollected = HttpUtils.checkPostIfCollected(HttpApplication.getUserId(), postId);
                handler.sendEmptyMessage(0x008);
            }
        }.start();
    }


    /**
     * 向服务器请求帖子的数据
     */
    private void requestPost() {
        final String postImageDir[] = imageDir.split("\\|");

        new Thread() {
            public void run() {
                // 请求用户头像，并通过handler设置
                userSculpture = HttpUtils.loadImage(userImage);
                handler.sendEmptyMessage(0x005);
                // 请求文字信息，并通过handler设置
                postData = HttpUtils.getPostWithId(postId);
                handler.sendEmptyMessage(0x004);
                // 请求帖子配图，并通过handler设置
                for (int i = 0; i < postImageDir.length; i++) {
                    postBitmaps[i] = HttpUtils.loadImage(postImageDir[i]);
                    Message msg = Message.obtain();
                    msg.what = 0x000;
                    msg.arg1 = i;
                    handler.sendMessage(msg);
                }
                // 请求评论信息
                reviewDatas = HttpUtils.getReciewsWithPostId(postId);
                System.out.println(reviewDatas.size());
                handler.sendEmptyMessage(0x006);

            }
        }.start();
    }


    /**
     * 设置每条评论的内容
     */
    private void setReviews() {
        Context context = this;
        post_list = (MyListView) findViewById(R.id.post_list);
        ReviewAdapter reviewAdapter = new ReviewAdapter(context, reviewDatas);
        post_list.setAdapter(reviewAdapter);
//        forum_swipe_ly.setRefreshing(false);
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
            case R.id.formu_background:
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;
            case R.id.review_image_upload_button:
                FilePicker picker = new FilePicker(this, FilePicker.FILE);
                picker.setShowHideDir(false);
                picker.setAllowExtensions(new String[]{".png, .jpg, .bmp"});
                picker.setOnFilePickListener(new FilePicker.OnFilePickListener() {
                    @Override
                    public void onFilePicked(String currentPath) {
                        if (imagePath.size() >= 3) {
                            Toast.makeText(ForumActivity.this, "最多只能上传三张图片", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(ForumActivity.this, currentPath, Toast.LENGTH_SHORT).show();
                        imagePath.add(currentPath);
                        review_image_upload_button.setText(String.valueOf(imagePath.size()));
                    }
                });
                picker.show();
                break;
            case R.id.forum_collection_icon:
                new Thread() {
                    public void run() {
                        if (isCollected == 0) {
                            int result = HttpUtils.collectPost(HttpApplication.getUserId(), postId);
                            if (result == HttpApplication.getUserId()) {
                                handler.sendEmptyMessage(0x009);
                            }
                        } else if (isCollected == 1) {
                            int result = HttpUtils.cancelCollectPost(HttpApplication.getUserId(), postId);
                            System.out.println(result);
                            if (result == HttpApplication.getUserId()) {
                                handler.sendEmptyMessage(0x010);
                            }
                        }

                    }
                }.start();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        ApplicationManager.getInstance().deleteActivity(this);
        super.onDestroy();
    }
}
