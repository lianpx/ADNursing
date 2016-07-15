package softwaredesign.adnursing;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login_button;
    private TextView login_user_text;
    private TextView login_password_text;

    private String username;
    private String password;

    private HttpApplication application;

    private int userId = -1;


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x001:
                    if (userId > 0) {
                        login();
                    } else {
                        Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
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

        application = (HttpApplication) getApplication();

        initView();
    }

    private void initView() {
        login_button = (Button) findViewById(R.id.login_button);
        login_user_text = (TextView) findViewById(R.id.login_user_text);
        login_password_text = (TextView) findViewById(R.id.login_password_text);

        login_button.setOnClickListener(this);
        login_user_text.setOnClickListener(this);
        login_password_text.setOnClickListener(this);
    }


    private boolean attemptLogin() {
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

    private void checkLogin() {
        new Thread() {
            public void run() {
                userId = HttpUtils.loginByPost((HttpApplication)getApplication(), username, password);
                handler.sendEmptyMessage(0x001);
            };
        }.start();
        System.out.println(userId);
    }

    private void login() {
        application.setUserId(userId);
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userId);
        bundle.putString("username", username);
        intent.putExtras(bundle);
        startActivity(intent);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                if (attemptLogin()) {
                    checkLogin();
                }
        }
    }

    public void OnClickToRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void OnClickHideKeyboard(View view) {
        if (view.getId() == R.id.login_background) {
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
}
