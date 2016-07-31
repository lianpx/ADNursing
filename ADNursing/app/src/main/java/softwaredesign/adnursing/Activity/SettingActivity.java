package softwaredesign.adnursing.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import softwaredesign.adnursing.ApplicationManager;
import softwaredesign.adnursing.Custom.RefreshLayout;
import softwaredesign.adnursing.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView top_bar_back_icon;
    private RelativeLayout setting_notify_bar;
    private RelativeLayout setting_volumn_bar;
    private RelativeLayout setting_shake_bar;
    private RelativeLayout setting_logout_bar;
    private CheckBox setting_notify_checkbox;
    private CheckBox setting_volumn_checkbox;
    private CheckBox setting_shake_checkbox;

    private SharedPreferences sp;
    SharedPreferences.Editor editor;

    private boolean is_notify;
    private boolean is_volumn;
    private boolean is_shake;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationManager.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
        checkPreferrence();
    }


    /**
     * 初始化View
     */
    private void initView() {
        top_bar_back_icon = (ImageView) findViewById(R.id.top_bar_back_icon);
        setting_logout_bar = (RelativeLayout) findViewById(R.id.setting_logout_bar);
        setting_notify_checkbox = (CheckBox) findViewById(R.id.setting_notify_checkbox);
        setting_volumn_checkbox = (CheckBox) findViewById(R.id.setting_volumn_checkbox);
        setting_shake_checkbox = (CheckBox) findViewById(R.id.setting_shake_checkbox);

        top_bar_back_icon.setOnClickListener(this);
        setting_logout_bar.setOnClickListener(this);

        setting_notify_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                is_notify = b;
                System.out.println("is_notify"+is_notify);
                editor.putBoolean("IS_NOTIFY", is_notify);
                editor.commit();
            }
        });

        setting_volumn_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                is_volumn = b;
                System.out.println("is_volumn"+is_volumn);
                editor.putBoolean("IS_VOLUMN", is_volumn);
                editor.commit();
            }
        });

        setting_shake_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                is_shake = b;
                System.out.println("is_shake"+is_shake);
                editor.putBoolean("IS_SHAKE", is_shake);
                editor.commit();
            }
        });
    }


    private void checkPreferrence() {
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        editor = sp.edit();
        is_notify = sp.getBoolean("IS_NOTIFY", false);
        is_volumn = sp.getBoolean("IS_VOLUMN", false);
        is_shake = sp.getBoolean("IS_SHAKE", false);
        setting_notify_checkbox.setChecked(is_notify);
        setting_volumn_checkbox.setChecked(is_volumn);
        setting_shake_checkbox.setChecked(is_shake);

        System.out.println(is_notify);
        System.out.println(is_volumn);
        System.out.println(is_shake);
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
            case R.id.setting_logout_bar:
                sp.edit().clear().commit();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                ApplicationManager.getInstance().exit();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        ApplicationManager.getInstance().deleteActivity(this);
        super.onDestroy();
    }
}
