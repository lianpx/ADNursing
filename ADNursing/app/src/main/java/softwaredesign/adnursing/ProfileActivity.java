package softwaredesign.adnursing;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.AddressPicker;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView top_bar_back_icon;
    private Spinner profile_edit_gender_spinner;
    private TextView profile_edit_name_text;
    private EditText profile_edit_diggest_text;
    private TextView profile_edit_address_text;
    private TextView profile_edit_id;

    private UserData userData;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x001:
                    if (userData != null) {
                        profile_edit_name_text.setText(userData.getName());
                        profile_edit_id.setText(String.valueOf(HttpApplication.getUserId()));
                        profile_edit_diggest_text.setText(userData.getDesc());
                        profile_edit_address_text.setText(userData.getAddress());
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ApplicationManager.getInstance().addActivity(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initUserInfo();

        initView();
        initSpinner();
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
        top_bar_back_icon = (ImageView) findViewById(R.id.top_bar_back_icon);
        profile_edit_gender_spinner = (Spinner) findViewById(R.id.profile_edit_gender_spinner);
        profile_edit_name_text = (TextView) findViewById(R.id.profile_edit_name_text);
        profile_edit_diggest_text = (EditText) findViewById(R.id.profile_edit_diggest_text);
        profile_edit_address_text = (TextView) findViewById(R.id.profile_edit_address_text);
        profile_edit_id = (TextView) findViewById(R.id.profile_edit_id);

        top_bar_back_icon.setOnClickListener(this);
        profile_edit_address_text.setOnClickListener(this);
        profile_edit_diggest_text.setOnFocusChangeListener(mOnFocusChangeListener);
    }

    private void initSpinner() {
        List<String> list = new ArrayList<String>();
        list.add("请选择");
        list.add("男");
        list.add("女");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = null;
                if (position == 0) {
                    TextView tv = new TextView(getContext());
                    tv.setHeight(0);
                    tv.setVisibility(View.GONE);
                    v = tv;
                }
                else {
                    v = super.getDropDownView(position, null, parent);
                }
                parent.setVerticalScrollBarEnabled(false);
                return v;
            }
        };

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profile_edit_gender_spinner.setAdapter(dataAdapter);
    }

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back_icon:
                finish();
                break;
            case R.id.profile_edit_address_text:
//                Toast.makeText(ProfileActivity.this, "profile_edit_address_text", Toast.LENGTH_SHORT).show();
                final TextView profile_edit_address_text = (TextView) findViewById(R.id.profile_edit_address_text);
                ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
                String json = AssetsUtils.readText(this, "city.json");
                data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
                AddressPicker picker = new AddressPicker(this, data);
                picker.setSelectedItem("贵州", "贵阳", "花溪");
                //picker.setHideProvince(true);//加上此句举将只显示地级及县级
                picker.setHideCounty(true);//加上此句举将只显示省级及地级
                picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                    @Override
                    public void onAddressPicked(String province, String city, String county) {
                        profile_edit_address_text.setText(province + city);
                    }
                });
                picker.show();
                break;
        }
    }
}
