package softwaredesign.adnursing.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import softwaredesign.adnursing.ApplicationManager;
import softwaredesign.adnursing.Custom.MyListView;
import softwaredesign.adnursing.Data.UserData;
import softwaredesign.adnursing.HttpApplication;
import softwaredesign.adnursing.Utils.HttpUtils;
import softwaredesign.adnursing.R;
import softwaredesign.adnursing.Data.TestData;

public class TestResultActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView bottomHomeIcon;
    private ImageView bottomCommunicateIcon;
    private ImageView bottomTestIcon;
    private ImageView bottomMyIcon;
    private ImageView test_result_sculpture;
    private Button test_begin_button;
    private TextView test_result_name;
    private TextView test_result_times;
    private TextView test_result_recent_time;
    private TextView cell[][] = new TextView[5][2];
    private MyListView test_result_listview;

    private String testName;
    private String testGender;
    private String testBirth;
    private ArrayList<TestData> testDatas = new ArrayList<>();

    private UserData userData;
    private Bitmap sculpture;


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x001:
                    if (!testDatas.isEmpty()) {
                        test_result_times.setText("一共进行了"+String.valueOf(testDatas.size())+"次测试");
                        test_result_recent_time.setText("最近测试时间"+testDatas.get(0).getTestDate());
                        initTableRow();
                    } else {
                        Toast.makeText(TestResultActivity.this, "记录为空，请尝试刷新", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 0x002:
                    test_result_sculpture.setImageBitmap(sculpture);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationManager.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        initData();
        initView();
    }


    private void initData() {
        Intent intent = getIntent();
        testName = intent.getStringExtra("testName");
        testGender = intent.getStringExtra("testGender");
        testBirth = intent.getStringExtra("testBirth");

        new Thread() {
            public void run() {
                testDatas = HttpUtils.getTestResultsWithId(HttpApplication.getUserId());
                handler.sendEmptyMessage(0x001);

                userData = HttpUtils.getUserInfoWithId(HttpApplication.getUserId());
                sculpture= HttpUtils.loadImage(userData.getImageDir());
                handler.sendEmptyMessage(0x002);
            };
        }.start();
    }


    private void initView() {
        bottomHomeIcon = (ImageView) findViewById(R.id.bottom_bar_home_icon);
        bottomCommunicateIcon = (ImageView) findViewById(R.id.bottom_bar_communicate_icon);
        bottomTestIcon = (ImageView) findViewById(R.id.bottom_bar_test_icon);
        bottomMyIcon = (ImageView) findViewById(R.id.bottom_bar_my_icon);
        test_result_sculpture = (ImageView) findViewById(R.id.test_result_sculpture);
        test_begin_button = (Button) findViewById(R.id.test_begin_button);
        test_result_name = (TextView) findViewById(R.id.test_result_name);
        test_result_times = (TextView) findViewById(R.id.test_result_times);
        test_result_recent_time = (TextView) findViewById(R.id.test_result_recent_time);
        test_result_listview = (MyListView) findViewById(R.id.test_result_listview);

        test_result_name.setText("你好，"+testName);
        bottomHomeIcon.setOnClickListener(this);
        bottomCommunicateIcon.setOnClickListener(this);
        bottomTestIcon.setOnClickListener(this);
        bottomMyIcon.setOnClickListener(this);
        test_begin_button.setOnClickListener(this);
        bottomTestIcon.setSelected(true);
    }


    private void initTableRow() {
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<>();
        for (int i = 0; i < testDatas.size(); i++) {
            String time_txt = testDatas.get(i).getTestDate();
            String score_txt = String.valueOf(testDatas.get(i).getTestScore());

            HashMap<String, Object> map = new HashMap<>();
            map.put("time", time_txt);
            map.put("score", score_txt);
            listItem.add(map);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItem, R.layout.layout_table_row, new String[] {"time", "score"}, new int[] {R.id.test_result_row_time, R.id.test_result_row_score});
        test_result_listview.setAdapter(simpleAdapter);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.test_begin_button:
                Intent intent = new Intent(TestResultActivity.this, NewTestActivity.class);
                startActivity(intent);
                break;
            case R.id.bottom_bar_home_icon:
                intent = new Intent(TestResultActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.bottom_bar_communicate_icon:
                intent = new Intent(TestResultActivity.this, CommunicateActivity.class);
                startActivity(intent);
                break;
            case R.id.bottom_bar_my_icon:
                intent = new Intent(TestResultActivity.this, MyActivity.class);
                startActivity(intent);
                break;
        }
    }


    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TestResultActivity.this);
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
        ApplicationManager.getInstance().deleteActivity(this);
        super.onDestroy();
    }
}
