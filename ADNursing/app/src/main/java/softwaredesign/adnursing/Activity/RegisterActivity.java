package softwaredesign.adnursing.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import softwaredesign.adnursing.ApplicationManager;
import softwaredesign.adnursing.HttpApplication;
import softwaredesign.adnursing.Utils.HttpUtils;
import softwaredesign.adnursing.R;

public class RegisterActivity extends AppCompatActivity {

    private TextView register_user_text;
    private TextView register_password_text;
    private TextView register_confirm_password_text;

    String name;
    String pass;
    String confirm;


    /**
     * Handler处理注册成功或失败后的响应
     */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x001:
                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 0x002:
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationManager.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 获取View
        register_user_text = (TextView) findViewById(R.id.register_user_text);
        register_password_text = (TextView) findViewById(R.id.register_password_text);
        register_confirm_password_text = (TextView) findViewById(R.id.register_confirm_password_text);
        Button register_button = (Button) findViewById(R.id.register_button);
        ImageView register_top_bar_back_icon = (ImageView) findViewById(R.id.register_top_bar_back_icon);

        // 设置Listener
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (attamptRegister()) {
                    new Thread() {
                        public void run() {
                            int userId = HttpUtils.registerByPost(name, pass);
                            if (userId != -1) {
                                System.out.println(userId);
                                HttpApplication.setUserId(userId);
                                handler.sendEmptyMessage(0x001);
                            } else {
                                handler.sendEmptyMessage(0x002);
                            }
                        }
                    }.start();
                }
            }
        });

        register_top_bar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    /**
     * 判断输入框是否为空，不为空则将注册信息发送给服务器
     * @return
     */
    private boolean attamptRegister() {
        name = register_user_text.getText().toString();
        pass = register_password_text.getText().toString();
        confirm = register_confirm_password_text.getText().toString();
        if (name.equals("")) {
            Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return false;
        } else if (pass.equals("")) {
            Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        } else if (confirm.equals("")) {
            Toast.makeText(RegisterActivity.this, "请确认密码", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!pass.equals(confirm)) {
            System.out.println(pass);
            System.out.println(confirm);
            Toast.makeText(RegisterActivity.this, "两次密码不相同", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    /**
     * 当输入框失去焦点是隐藏软键盘
     * @param view
     */
    public void OnClickHideKeyboard(View view) {
        if (view.getId() == R.id.register_background) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    @Override
    protected void onDestroy() {
        ApplicationManager.getInstance().deleteActivity(this);
        super.onDestroy();
    }

}
