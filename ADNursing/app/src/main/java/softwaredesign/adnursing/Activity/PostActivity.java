package softwaredesign.adnursing.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.FilePicker;
import softwaredesign.adnursing.ApplicationManager;
import softwaredesign.adnursing.HttpApplication;
import softwaredesign.adnursing.Utils.HttpUtils;
import softwaredesign.adnursing.R;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView top_bar_back_icon;
    private Spinner post_edit_type_spinner;
    private EditText post_edit_title_text;
    private TextView top_bar_release_text;
    private EditText post_edit_content_field;
    private ImageView post_edit_upload_image;

    private ImageView imageViews[] = new ImageView[3];

    private String typeString;
    private ArrayList<String> imagePath = new ArrayList<>();


    /**
     * Handler处理发布帖子成功后跳转返回
     */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x001:
                    Toast.makeText(PostActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                    finishPage();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationManager.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        initView();
        initSpinner();
    }


    /**
     * 初始化View
     */
    private void initView() {
        // 获取View
        top_bar_back_icon = (ImageView) findViewById(R.id.top_bar_back_icon);
        post_edit_type_spinner = (Spinner) findViewById(R.id.post_edit_type_spinner);
        post_edit_title_text = (EditText) findViewById(R.id.post_edit_title_text);
        top_bar_release_text = (TextView) findViewById(R.id.top_bar_release_text);
        post_edit_content_field = (EditText) findViewById(R.id.post_edit_content_field);
        post_edit_upload_image = (ImageView) findViewById(R.id.post_edit_upload_image);
        imageViews[0] = (ImageView) findViewById(R.id.post_edit_image_1);
        imageViews[1] = (ImageView) findViewById(R.id.post_edit_image_2);
        imageViews[2] = (ImageView) findViewById(R.id.post_edit_image_3);

        // 设置Listener
        top_bar_back_icon.setOnClickListener(this);
        top_bar_release_text.setOnClickListener(this);
        post_edit_upload_image.setOnClickListener(this);

        post_edit_title_text.setOnFocusChangeListener(mOnFocusChangeListener);

        typeString = (String) post_edit_type_spinner.getSelectedItem();
    }


    /**
     * 初始化下拉菜单
     */
    private void initSpinner() {
        List<String> list = new ArrayList<String>();
        list.add("选择类别");
        list.add("病症疑问");
        list.add("名医推荐");
        list.add("药物推荐");
        list.add("交流分享");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = null;
                // 点击下拉菜单后取出默认项
                if (position == 0) {
                    TextView tv = new TextView(getContext());
                    tv.setHeight(0);
                    tv.setVisibility(View.GONE);
                    v = tv;
                } else {
                    v = super.getDropDownView(position, null, parent);

                }
                parent.setVerticalScrollBarEnabled(false);
                return v;
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        post_edit_type_spinner.setAdapter(dataAdapter);
        // 设置下拉菜单监听器
        post_edit_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeString = ((TextView) post_edit_type_spinner.getSelectedView()).getText().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    /**
     * 从手机SD卡获取到图片后显示在ImageView上
     */
    private void setImageView() {
        int num = imagePath.size() - 1;
        Bitmap bm = BitmapFactory.decodeFile(imagePath.get(num));
        imageViews[num].setImageBitmap(bm);
//        bm.recycle();
    }


    /**
     * 判断帖子输入信息是否完整，完整则向服务器提交数据
     */
    private void attempRelease() {
        final String title = post_edit_title_text.getText().toString();
        final String type = typeString;
        final String content = post_edit_content_field.getText().toString();

        if (title.equals("")) {
            Toast.makeText(PostActivity.this, "请填写标题", Toast.LENGTH_SHORT).show();
            return;
        } else if (type == "选择类别" || type.equals("")) {
            Toast.makeText(PostActivity.this, "请选择类别", Toast.LENGTH_SHORT).show();
            return;
        } else if (content.equals("")) {
            Toast.makeText(PostActivity.this, "请填写正文", Toast.LENGTH_SHORT).show();
            return;
        }
        // 根据图片路径新建File
        final ArrayList<File> file = new ArrayList<File>();
        for (int i = 0; i < imagePath.size(); i++) {
            file.add(new File(imagePath.get(i)));
        }
        // 创建线程上传数据
        new Thread() {
            public void run() {
                int postId = HttpUtils.uploadPostText(HttpApplication.getUserId(), title, content, type);
                String result = HttpUtils.uploadPostImages(file, postId);
                handler.sendEmptyMessage(0x001);
                System.out.println(result);
            };
        }.start();
    }


    /**
     * 编辑框的焦点监听器，获取焦点是提示文字隐藏，失去焦点是若内容为空则显示提示文字
     */
    private View.OnFocusChangeListener mOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            EditText editText = (EditText) v;
            String hint;
            if (hasFocus) {
                hint = editText.getHint().toString();
                editText.setTag(hint);
                editText.setHint("");
            } else {
                hint = editText.getTag().toString();
                editText.setHint(hint);
            }
        }
    };


    /**
     * 结束当前页面，返回
     */
    private void finishPage() {
        finish();
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
            case R.id.top_bar_release_text:
                attempRelease();
                break;
            case R.id.post_edit_upload_image:
                FilePicker picker = new FilePicker(this, FilePicker.FILE);
                picker.setShowHideDir(false);
                picker.setAllowExtensions(new String[]{".png, .jpg, .bmp"});
                picker.setOnFilePickListener(new FilePicker.OnFilePickListener() {
                    @Override
                    public void onFilePicked(String currentPath) {
                        Toast.makeText(PostActivity.this, currentPath, Toast.LENGTH_SHORT).show();
                        if (imagePath.size() >= 3) {
                            Toast.makeText(PostActivity.this, "最多只能上传三张图片", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        imagePath.add(currentPath);
                        setImageView();
                    }
                });
                picker.show();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        ApplicationManager.getInstance().deleteActivity(this);
        super.onDestroy();
    }
}
