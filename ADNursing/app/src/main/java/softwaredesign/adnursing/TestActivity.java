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
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import cn.qqtheme.framework.picker.DatePicker;

public class TestActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView bottomHomeIcon;
    private ImageView bottomCommunicateIcon;
    private ImageView bottomTestIcon;
    private ImageView bottomMyIcon;

    private TextView test_init_info_birth;
    private Spinner test_init_info_gender_spinner;
    private EditText test_init_info_name;
    private Button test_info_submit_button;

    private UserData userData;

    private String name = "";
    private String gender = "";
    private String birth = "";

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x001:
                    if (!userData.getTestName().equals("")) {
                        Intent intent = new Intent(TestActivity.this, TestResultActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("testName", userData.getTestName());
                        bundle.putString("testGender", userData.getTestGender());
                        bundle.putString("testBirth", userData.getTestBirth());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                    break;
                case 0x002:
                    Intent intent = new Intent(TestActivity.this, TestResultActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", name);
                    bundle.putString("gender", gender);
                    bundle.putString("birth", birth);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ApplicationManager.getInstance().addActivity(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initUserInfo();

        if (name != "") {
            Intent intent = new Intent(TestActivity.this, TestResultActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("testName", name);
            bundle.putString("testGender", gender);
            bundle.putString("testBirth", birth);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }

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
        bottomHomeIcon = (ImageView) findViewById(R.id.bottom_bar_home_icon);
        bottomCommunicateIcon = (ImageView) findViewById(R.id.bottom_bar_communicate_icon);
        bottomTestIcon = (ImageView) findViewById(R.id.bottom_bar_test_icon);
        bottomMyIcon = (ImageView) findViewById(R.id.bottom_bar_my_icon);

        TextView test_init_info_birth = (TextView) findViewById(R.id.test_init_info_birth);
        test_init_info_gender_spinner = (Spinner) findViewById(R.id.test_init_info_gender_spinner);
        test_init_info_name = (EditText) findViewById(R.id.test_init_info_name);
        test_init_info_birth = (EditText) findViewById(R.id.test_init_info_birth);
        test_info_submit_button = (Button) findViewById(R.id.test_info_submit_button);

        bottomHomeIcon.setOnClickListener(this);
        bottomCommunicateIcon.setOnClickListener(this);
        bottomTestIcon.setOnClickListener(this);
        bottomMyIcon.setOnClickListener(this);

        test_init_info_birth.setOnClickListener(this);
        test_info_submit_button.setOnClickListener(this);
        test_init_info_name.setOnFocusChangeListener(mOnFocusChangeListener);

        bottomTestIcon.setSelected(true);
    }

    private void initSpinner() {
        List<String> list = new ArrayList<String>();
        list.add("性别");
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
        test_init_info_gender_spinner.setAdapter(dataAdapter);

        test_init_info_gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = ((TextView) test_init_info_gender_spinner.getSelectedView()).getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
    };

    @Override
    public void onClick(View view) {
        Intent intent;
        Bundle bundle;
        switch (view.getId()) {
            case R.id.bottom_bar_home_icon:
                intent = new Intent(TestActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.bottom_bar_communicate_icon:
                intent = new Intent(TestActivity.this, CommunicateActivity.class);
                startActivity(intent);
                break;
            case R.id.bottom_bar_my_icon:
                intent = new Intent(TestActivity.this, MyActivity.class);
                startActivity(intent);
                break;
            case R.id.test_info_submit_button:
                if (test_init_info_name.getText().toString().equals("")) {
                    Toast.makeText(TestActivity.this, "请填写受测者姓名", Toast.LENGTH_SHORT).show();
                    return;
                } else if (gender == "性别" || gender.equals("")) {
                    Toast.makeText(TestActivity.this, "请选择受测者性别", Toast.LENGTH_SHORT).show();
                    return;
                } else if (birth.equals("")) {
                    Toast.makeText(TestActivity.this, "请填写受测者出生日期", Toast.LENGTH_SHORT).show();
                    return;
                }



                new Thread() {
                    public void run() {
                        HttpUtils.uploadTestInfo(HttpApplication.getUserId(), name, birth, gender);
//                        System.out.println(userData.getName());
                        handler.sendEmptyMessage(0x002);
                    };
                }.start();

                break;
            case R.id.test_init_info_birth:
                final TextView test_init_info_birth = (TextView) findViewById(R.id.test_init_info_birth);
                DatePicker picker = new DatePicker(this, DatePicker.YEAR_MONTH_DAY);
                picker.setRange(1900, 2017);
                picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        test_init_info_birth.setText(year + "-" + month + "-" + day);
                        birth = (year + "-" + month + "-" + day);
                    }
                });
                picker.show();
                break;
        }
    }

    public void OnClickHideKeyboard(View view) {
        if (view.getId() == R.id.test_background) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
