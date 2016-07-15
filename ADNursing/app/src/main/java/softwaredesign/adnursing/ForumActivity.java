package softwaredesign.adnursing;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ForumActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView top_bar_back_icon;
    private LinearLayout formu_background;
    private MyListView post_list;
    private int postId;
    private String userName;
    private String imageDir;
    private String userImage;
    private PostData postData;
    private Bitmap postBitmaps[] = new Bitmap[3];
    private Bitmap reviewBitmaps[] = new Bitmap[3];
    private Bitmap userSculpture;

    private ArrayList<String> imagePath = new ArrayList<>();

    private ImageView main_sculpture;
    private TextView main_name;
    private TextView main_title;
    private TextView main_content;
    private TextView main_time;
    private TextView main_type;
    private TextView main_visitor_num;
    private ImageView[] imageViews = new ImageView[3];
    private ArrayList<ReviewData> reviewDatas = new ArrayList<>();

    private EditText review_text_upload_text;
    private Button review_image_upload_button;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x004:
                    main_name.setText(postData.getUser().getName());
                    main_title.setText(postData.getTitle());
                    main_content.setText(postData.getContent());
                    main_time.setText(postData.getModifiedTime());
                    main_type.setText(postData.getType());
                    main_visitor_num.setText(postData.getVisitorNum()+"");
                    break;
                case 0:
                    imageViews[0].setImageBitmap(postBitmaps[0]);
                    break;
                case 1:
                    imageViews[1].setImageBitmap(postBitmaps[1]);
                    break;
                case 2:
                    imageViews[2].setImageBitmap(postBitmaps[2]);
                    break;
                case 0x005:
                    if (userSculpture != null) {
                        main_sculpture.setImageBitmap(userSculpture);
                    }
                    break;
                case 0x006:
                    setListView();
                    review_text_upload_text.setText("");
                    break;
                case 0x007:
                    Toast.makeText(ForumActivity.this, "评论发表成功", Toast.LENGTH_SHORT).show();
                    review_text_upload_text.setText("");
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
        setListView();
    }

    private void initView() {
        top_bar_back_icon = (ImageView) findViewById(R.id.top_bar_back_icon);
        formu_background = (LinearLayout) findViewById(R.id.formu_background);

        main_sculpture = (ImageView) findViewById(R.id.main_sculpture);
        main_name = (TextView) findViewById(R.id.main_name);
        main_title = (TextView) findViewById(R.id.main_title);
        main_content = (TextView) findViewById(R.id.main_content);
        main_time = (TextView) findViewById(R.id.main_time);
        main_type = (TextView) findViewById(R.id.main_type);
        main_visitor_num = (TextView) findViewById(R.id.main_visitor_num);

        imageViews[0] = (ImageView) findViewById(R.id.main_image_1);
        imageViews[1] = (ImageView) findViewById(R.id.main_image_2);
        imageViews[2] = (ImageView) findViewById(R.id.main_image_3);

        review_text_upload_text = (EditText) findViewById(R.id.review_text_upload_text);
        review_image_upload_button = (Button) findViewById(R.id.review_image_upload_button);

        post_list = (MyListView) findViewById(R.id.post_list);

        top_bar_back_icon.setOnClickListener(this);
        formu_background.setOnClickListener(this);
        review_image_upload_button.setOnClickListener(this);

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
                        new Thread() {
                            public void run() {
                                int reviewId = HttpUtils.uploadReviewText(HttpApplication.getUserId(), postId, content);
                                if (reviewId != -1) {
//                                    Toast.makeText(ForumActivity.this, "评论发表成功", Toast.LENGTH_SHORT).show();
                                    String result = HttpUtils.uploadReviewImage(file, reviewId);
                                    System.out.println(result);

                                    reviewDatas = HttpUtils.getReciewWithPostIdByGet(postId);
                                    handler.sendEmptyMessage(0x006);
                                } else {
                                    Toast.makeText(ForumActivity.this, "评论发表失败", Toast.LENGTH_SHORT).show();
                                }

                            };
                        }.start();
                        handled = true;
                        break;
                }
                return false;
            }
        });
    }

    public void setPostMain() {
        Intent intent = getIntent();
        postId = intent.getIntExtra("postId", -1);
        userName = intent.getStringExtra("userName");
        imageDir = intent.getStringExtra("imageDir");
        userImage = intent.getStringExtra("userImage");
        requestPost(postId);

        if (imageDir.equals("")) {
            ((LinearLayout) findViewById(R.id.main_image_set)).setVisibility(View.GONE);
        } else {
            ((LinearLayout) findViewById(R.id.main_image_set)).setVisibility(View.VISIBLE);
        }
    }

    private void requestPost(final int postId) {
        final int id = postId;
        final String postImageDir[] = imageDir.split("\\|");

        new Thread() {
            public void run() {

                userSculpture = HttpUtils.getImageByGet(userImage);
                handler.sendEmptyMessage(0x005);

                postData = HttpUtils.getPostWithIdByGet(id);
                handler.sendEmptyMessage(0x004);

                for (int i = 0; i < postImageDir.length; i++) {
                    postBitmaps[i] = HttpUtils.getImageByGet(postImageDir[i]);
                    handler.sendEmptyMessage(i);
                }

                reviewDatas = HttpUtils.getReciewWithPostIdByGet(postId);
                handler.sendEmptyMessage(0x006);

            }
        }.start();
    }


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
        }
    }

    private void setListView() {
        Context context = this;
        post_list = (MyListView) findViewById(R.id.post_list);
        ReviewAdapter reviewAdapter = new ReviewAdapter(context, reviewDatas);
        post_list.setAdapter(reviewAdapter);
    }
}
