package softwaredesign.adnursing;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewTestActivity extends AppCompatActivity implements NewTestFragment.MyListener, View.OnClickListener {

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

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private int currentPage = 0;
    private ArrayList<NewTestFragment> fg = new ArrayList<NewTestFragment>();
    private NewTestResultFragment rfg;

    private int checkNumPerPage[] = {0, 0, 0};

    private ImageView top_bar_back_icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ApplicationManager.getInstance().addActivity(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test);

        initView();

        fg.add(NewTestFragment.newInstance(1, 3, items_page_1));
        fg.add(NewTestFragment.newInstance(2, 3, items_page_2));
        fg.add(NewTestFragment.newInstance(3, 3, items_page_3));

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        getSupportFragmentManager().beginTransaction().replace(R.id.new_test_fragment_container, fg.get(currentPage)).commit();
    }

    private void initView() {
        top_bar_back_icon = (ImageView) findViewById(R.id.top_bar_back_icon);

        top_bar_back_icon.setOnClickListener(this);
    }

    @Override
    public void nextPage(int checkedNum) {
        checkNumPerPage[currentPage] = checkedNum;
        getSupportFragmentManager().beginTransaction().replace(R.id.new_test_fragment_container, fg.get(++currentPage)).commit();
    }

    @Override
    public void lastPage(int checkedNum) {
        checkNumPerPage[currentPage] = checkedNum;
        getSupportFragmentManager().beginTransaction().replace(R.id.new_test_fragment_container, fg.get(--currentPage)).commit();
    }

    @Override
    public void finishPage(int checkedNum) {
        checkNumPerPage[currentPage] = checkedNum;
        int score = 0;
        for (int i = 0; i < checkNumPerPage.length; i++) {
            score += checkNumPerPage[i];
        }

        rfg = NewTestResultFragment.newInstance("name", score);
        getSupportFragmentManager().beginTransaction().replace(R.id.new_test_fragment_container, rfg).commit();
//        Toast.makeText(this, checkNumPerPage[0]+" "+checkNumPerPage[1]+" "+checkNumPerPage[2], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back_icon:
                finish();
                break;
        }
    }
}
