package softwaredesign.adnursing;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class TestResultActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView bottomHomeIcon;
    private ImageView bottomCommunicateIcon;
    private ImageView bottomTestIcon;
    private ImageView bottomMyIcon;

    private Button test_begin_button;

    private TextView test_result_name;
    private TextView test_result_times;
    private TextView test_result_recent_time;
    private TextView cell[][] = new TextView[5][2];

    private String testName;
    private String testGender;
    private String testBirth;

    private ArrayList<TestData> testDatas = new ArrayList<>();

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x001:
                    if (!testDatas.isEmpty()) {
                        test_result_times.setText("一共进行了"+String.valueOf(testDatas.size())+"次测试");
                        test_result_recent_time.setText("最近测试时间"+testDatas.get(0).getTestDate());
                        for (int i = 0; i < testDatas.size(); i++) {
                            if (i == 5) break;
                            cell[i][0].setVisibility(View.VISIBLE);
                            cell[i][1].setVisibility(View.VISIBLE);
                            cell[i][0].setText(testDatas.get(i).getTestDate());
                            cell[i][1].setText(String.valueOf(testDatas.get(i).getTestScore()));
                        }
                    }
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

//        setTable();
    }

    private void initData() {
        Intent intent = getIntent();
        testName = (String) intent.getStringExtra("testName");
        testGender = (String) intent.getStringExtra("testGender");
        testBirth = (String) intent.getStringExtra("testBirth");

        new Thread() {
            public void run() {
                testDatas = HttpUtils.getTestResultByGet(HttpApplication.getUserId());
                Message message = Message.obtain();
                handler.sendEmptyMessage(0x001);
//                userData = HttpUtils.getUserInfoWithIdByGet(HttpApplication.getUserId());
//                System.out.println(userData.getName());
//                handler.sendEmptyMessage(0x001);

            };
        }.start();
    }

    private void initView() {
        bottomHomeIcon = (ImageView) findViewById(R.id.bottom_bar_home_icon);
        bottomCommunicateIcon = (ImageView) findViewById(R.id.bottom_bar_communicate_icon);
        bottomTestIcon = (ImageView) findViewById(R.id.bottom_bar_test_icon);
        bottomMyIcon = (ImageView) findViewById(R.id.bottom_bar_my_icon);

        test_begin_button = (Button) findViewById(R.id.test_begin_button);

        test_result_name = (TextView) findViewById(R.id.test_result_name);
        test_result_times = (TextView) findViewById(R.id.test_result_times);
        test_result_recent_time = (TextView) findViewById(R.id.test_result_recent_time);

        cell[0][0] = (TextView) findViewById(R.id.tvt1);
        cell[1][0] = (TextView) findViewById(R.id.tvt2);
        cell[2][0] = (TextView) findViewById(R.id.tvt3);
        cell[3][0] = (TextView) findViewById(R.id.tvt4);
        cell[4][0] = (TextView) findViewById(R.id.tvt5);

        cell[0][1] = (TextView) findViewById(R.id.tvs1);
        cell[1][1] = (TextView) findViewById(R.id.tvs2);
        cell[2][1] = (TextView) findViewById(R.id.tvs3);
        cell[3][1] = (TextView) findViewById(R.id.tvs4);
        cell[4][1] = (TextView) findViewById(R.id.tvs5);

        test_result_name.setText("你好，"+testName);

        bottomHomeIcon.setOnClickListener(this);
        bottomCommunicateIcon.setOnClickListener(this);
        bottomTestIcon.setOnClickListener(this);
        bottomMyIcon.setOnClickListener(this);

        test_begin_button.setOnClickListener(this);

        bottomTestIcon.setSelected(true);

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


    private void setTable() {

    }

}
