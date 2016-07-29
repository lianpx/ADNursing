package softwaredesign.adnursing.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import softwaredesign.adnursing.ApplicationManager;
import softwaredesign.adnursing.HttpApplication;
import softwaredesign.adnursing.Utils.HttpUtils;
import softwaredesign.adnursing.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login_button;
    private TextView login_user_text;
    private TextView login_password_text;
    private TextView register_text;

    private String username;
    private String password;
    private int userId;

    private boolean isCache = false;

    private SharedPreferences sp;


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x001:
                    if (userId != -1) {
                        successLogin();
                    } else {
                        if (isCache) {
                            Toast.makeText(LoginActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_login);

        initView();
        checkPreferrence();
    }


    /**
     * 初始化View
     */
    private void initView() {
        // 获取View
        login_button = (Button) findViewById(R.id.login_button);
        login_user_text = (TextView) findViewById(R.id.login_user_text);
        login_password_text = (TextView) findViewById(R.id.login_password_text);
        register_text = (TextView) findViewById(R.id.register_text);
        // 给View设置Listener
        login_button.setOnClickListener(this);
        login_user_text.setOnClickListener(this);
        login_password_text.setOnClickListener(this);
        register_text.setOnClickListener(this);
    }


    /**
     * 查看SharePreferrence中是否有登录信息
     */
    private void checkPreferrence() {
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        username = sp.getString("USERNAME", "");
        password = sp.getString("PASSWORD", "");
        userId = sp.getInt("USERID", -1);
        if (username.equals("") || password.equals("") || userId == -1) return;
        isCache = true;
        checkLogin();
    }


    /**
     * 判断是否能提交登录信息
     * @return 能否提交登录
     */
    private boolean checkInputBox() {
        username = login_user_text.getText().toString();
        password = login_password_text.getText().toString();
        if (username.equals("")) {
            Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.equals("")) {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    /**
     * 提交登录信息到服务器，并根据response做出响应
     */
    private void checkLogin() {
        new Thread() {
            public void run() {
                userId = HttpUtils.loginByPost(username, password);
                handler.sendEmptyMessage(0x001);
            };
        }.start();
        System.out.println("userId: " + userId);
    }


    /**
     * 登录成功，跳转页面
     */
    private void successLogin() {
        HttpApplication.setUserId(userId);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("USERNAME", username);
        editor.putString("PASSWORD", password);
        editor.putInt("USERID", userId);
        editor.commit();

        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }


    /**
     * 监听手机返回键
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            dialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                if (checkInputBox()) {
                    checkLogin();
                }
                break;
            case R.id.register_text:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }


    /**
     * 监听输入框失去焦点，隐藏软键盘
     * @param view
     */
    public void OnClickHideKeyboard(View view) {
        if (view.getId() == R.id.login_background) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    /**
     * 按下手机返回键后弹出“确定退出”对话框
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
    protected void onDestroy() {
        ApplicationManager.getInstance().deleteActivity(this);
        super.onDestroy();
    }
}
