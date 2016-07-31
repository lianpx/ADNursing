package softwaredesign.adnursing.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.FilePicker;
import softwaredesign.adnursing.ApplicationManager;
import softwaredesign.adnursing.Utils.AssetsUtils;
import softwaredesign.adnursing.HttpApplication;
import softwaredesign.adnursing.Utils.HttpUtils;
import softwaredesign.adnursing.R;
import softwaredesign.adnursing.Data.UserData;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView top_bar_back_icon;
    private Spinner profile_edit_gender_spinner;
    private TextView profile_edit_name_text;
    private EditText profile_edit_diggest_text;
    private TextView profile_edit_address_text;
    private TextView profile_edit_id;
    private ImageView profile_edit_sculpture;
    private TextView profile_save_text;

    private UserData userData;                  // 保存用户数据
    private String sculptureDir = null;         // 用户头像图片路径
    private Bitmap sculpture;


    /**
     * Handler获取个人资料后显示
     */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x001:
                    if (userData != null) {
                        profile_edit_name_text.setText(userData.getName());
                        profile_edit_id.setText(String.valueOf(HttpApplication.getUserId()));
                        profile_edit_diggest_text.setText(userData.getDesc());
                        profile_edit_address_text.setText(userData.getAddress());
                        if (userData.getGender().equals("男")) {
                            profile_edit_gender_spinner.setSelection(1);
                        } else if (userData.getGender().equals("女")) {
                            profile_edit_gender_spinner.setSelection(2);
                        }
                    }
                    break;
                case 0x002:
                    Toast.makeText(ProfileActivity.this, "用户信息已更新", Toast.LENGTH_SHORT).show();
                    break;
                case 0x003:
                    profile_edit_sculpture.setImageBitmap(sculpture);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationManager.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initView();
        initSpinner();
        requestUserInfo();
    }


    /**
     * 向服务器获取用户信息
     */
    private void requestUserInfo() {
        new Thread() {
            public void run() {
                userData = HttpUtils.getUserInfoWithId(HttpApplication.getUserId());
                System.out.println(userData.getName());
                handler.sendEmptyMessage(0x001);

                sculpture= HttpUtils.loadImage(userData.getImageDir());
                handler.sendEmptyMessage(0x003);
            };
        }.start();
    }


    /**
     * 初始化View
     */
    private void initView() {
        // 获取View
        top_bar_back_icon = (ImageView) findViewById(R.id.top_bar_back_icon);
        profile_edit_gender_spinner = (Spinner) findViewById(R.id.profile_edit_gender_spinner);
        profile_edit_name_text = (TextView) findViewById(R.id.profile_edit_name_text);
        profile_edit_diggest_text = (EditText) findViewById(R.id.profile_edit_diggest_text);
        profile_edit_address_text = (TextView) findViewById(R.id.profile_edit_address_text);
        profile_edit_id = (TextView) findViewById(R.id.profile_edit_id);
        profile_edit_sculpture = (ImageView) findViewById(R.id.profile_edit_sculpture);
        profile_save_text = (TextView) findViewById(R.id.profile_save_text);
        // 设置Listener
        top_bar_back_icon.setOnClickListener(this);
        profile_edit_address_text.setOnClickListener(this);
        profile_edit_sculpture.setOnClickListener(this);
        profile_save_text.setOnClickListener(this);
        profile_edit_diggest_text.setOnFocusChangeListener(mOnFocusChangeListener);
    }


    /**
     * 初始化下拉菜单
     */
    private void initSpinner() {
        List<String> list = new ArrayList<String>();
        list.add("请选择");
        list.add("男");
        list.add("女");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = null;
                // 点击下拉菜单后，隐藏默认项
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
        profile_edit_gender_spinner.setAdapter(dataAdapter);
    }


    private void uploadUserInfo() {
        final String gender = ((String) profile_edit_gender_spinner.getSelectedItem()).equals("请选择") ? "" : ((String) profile_edit_gender_spinner.getSelectedItem());
        final String address = profile_edit_address_text.getText().toString();
        final String diggest = profile_edit_diggest_text.getText().toString();

        new Thread() {
            public void run() {
                int resultId = HttpUtils.uploadUserInfo(HttpApplication.getUserId(), gender, address, diggest);
                System.out.println(resultId);
                System.out.println(sculptureDir);
                if (sculptureDir != null && !sculptureDir.equals("")) {
                    File file = new File(sculptureDir);
                    System.out.println(file);

                    if (file.exists()) {
                        System.out.println(file);

                        String result = HttpUtils.uploadUserImage(file, HttpApplication.getUserId());
                        System.out.println(result);
                    }
                }
                handler.sendEmptyMessage(0x002);
            };
        }.start();
    }


    /**
     * 编辑框焦点监听器，获得焦点时隐藏提示文字，失去焦点并且输入框为空则显示提示文字
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
     * View的OnClick监听器
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back_icon:
                finish();
                break;
            case R.id.profile_edit_address_text:
                // 显示地址选择器
                final TextView profile_edit_address_text = (TextView) findViewById(R.id.profile_edit_address_text);
                ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
                String json = AssetsUtils.readText(this, "city.json");
                data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
                AddressPicker addressPicker = new AddressPicker(this, data);
                addressPicker.setSelectedItem("广东", "广州", "番禺");
                addressPicker.setHideCounty(true);//加上此句举将只显示省级及地级
                addressPicker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                    @Override
                    public void onAddressPicked(String province, String city, String county) {
                        profile_edit_address_text.setText(province + city);
                    }
                });
                addressPicker.show();
                break;
            case R.id.profile_edit_sculpture:
                FilePicker filePicker = new FilePicker(this, FilePicker.FILE);
                filePicker.setShowHideDir(false);
                filePicker.setAllowExtensions(new String[]{".png, .jpg, .bmp"});
                filePicker.setOnFilePickListener(new FilePicker.OnFilePickListener() {
                    @Override
                    public void onFilePicked(String currentPath) {
                        sculptureDir = currentPath;
                        Bitmap bm = BitmapFactory.decodeFile(sculptureDir);
                        profile_edit_sculpture.setImageBitmap(bm);
                    }
                });
                filePicker.show();
                break;
            case R.id.profile_save_text:
                uploadUserInfo();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        ApplicationManager.getInstance().deleteActivity(this);
        super.onDestroy();
    }
}
