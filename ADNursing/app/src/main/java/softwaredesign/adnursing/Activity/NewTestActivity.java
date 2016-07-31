package softwaredesign.adnursing.Activity;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import softwaredesign.adnursing.ApplicationManager;
import softwaredesign.adnursing.Custom.NewTestFragment;
import softwaredesign.adnursing.Custom.NewTestResultFragment;
import softwaredesign.adnursing.Data.UserData;
import softwaredesign.adnursing.HttpApplication;
import softwaredesign.adnursing.R;
import softwaredesign.adnursing.Utils.HttpUtils;

public class NewTestActivity extends AppCompatActivity implements NewTestFragment.MyListener, View.OnClickListener {
    // 测试的内容
    private String items_page_1[] = {
            "几乎整天和衣躺着看电视",
            "什么兴趣爱好都没有",
            "没有一个可以亲密交谈的朋友",
            "平时讨厌外出，常常闷在家里",
            "在家庭中不起什么作用",
            "不关心世事，不读书看报",
            "觉得活着没什么意思",
            "懒得活动，无精打采",
            "讨厌说和听玩笑话",
    };
    private String items_page_2[] = {
            "有高血压或低血压",
            "平时净发牢骚或埋怨",
            "把“想死”作为口头禅",
            "被人说成神经过敏，过分认真",
            "过分忧虑",
            "经常焦躁易发脾气",
            "懒得活动，无精打采",
            "对任何事情都不会激动，无动于衷",
            "什么事若非亲自动手，便不放心",
    };
    private String items_page_3[] = {
            "不听别人的意见，固执己见",
            "沉默寡言",
            "配偶去世5年以上",
            "不轻易对人说谢谢",
            "老讲自己过去值得自豪的事",
            "对新的事物缺乏兴趣",
            "觉得活着没什么意思",
            "凡事以自己为中心",
            "对任何事都缺乏忍耐",
    };

    private int currentPage = 0;
    private ArrayList<NewTestFragment> fg = new ArrayList<NewTestFragment>();     // 三个测试页面
    private NewTestResultFragment rfg;                              // 一个结果页面
    private int checkNumPerPage[] = {0, 0, 0};                      // 每页选中的项数
    private ImageView top_bar_back_icon;
    private UserData userData;
    private Bitmap sculpture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationManager.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test);

        initView();
        // 新建三个测试页面，并显示第一页
        fg.add(NewTestFragment.newInstance(1, 3, items_page_1));
        fg.add(NewTestFragment.newInstance(2, 3, items_page_2));
        fg.add(NewTestFragment.newInstance(3, 3, items_page_3));
        getSupportFragmentManager().beginTransaction().replace(R.id.new_test_fragment_container, fg.get(currentPage)).commit();
    }


    /**
     * 初始化View
     */
    private void initView() {
        top_bar_back_icon = (ImageView) findViewById(R.id.top_bar_back_icon);

        top_bar_back_icon.setOnClickListener(this);
    }


    /**
     * Fragment中提供的借口，跳转到下一个Fragment
     * @param checkedNum
     */
    @Override
    public void nextPage(int checkedNum) {
        checkNumPerPage[currentPage] = checkedNum;
        getSupportFragmentManager().beginTransaction().replace(R.id.new_test_fragment_container, fg.get(++currentPage)).commit();
    }


    /**
     * Fragment中提供的借口，跳转到上一个Fragment
     * @param checkedNum
     */
    @Override
    public void lastPage(int checkedNum) {
        checkNumPerPage[currentPage] = checkedNum;
        getSupportFragmentManager().beginTransaction().replace(R.id.new_test_fragment_container, fg.get(--currentPage)).commit();
    }


    /**
     * Fragment中提供的借口，跳转到结果页面
     * @param checkedNum
     */
    @Override
    public void finishPage(int checkedNum) {
        checkNumPerPage[currentPage] = checkedNum;
        int score = 0;
        for (int i = 0; i < checkNumPerPage.length; i++) {
            score += checkNumPerPage[i];
        }
        rfg = NewTestResultFragment.newInstance("name", score);
        getSupportFragmentManager().beginTransaction().replace(R.id.new_test_fragment_container, rfg).commit();
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
        }
    }


    @Override
    protected void onDestroy() {
        ApplicationManager.getInstance().deleteActivity(this);
        super.onDestroy();
    }
}
