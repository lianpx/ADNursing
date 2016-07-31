package softwaredesign.adnursing.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import softwaredesign.adnursing.ApplicationManager;
import softwaredesign.adnursing.Custom.MyListView;
import softwaredesign.adnursing.Adapter.PostPreviewAdapter;
import softwaredesign.adnursing.Adapter.PostPreviewDetailAdapter;
import softwaredesign.adnursing.R;
import softwaredesign.adnursing.Adapter.ReviewAdapter;
import softwaredesign.adnursing.Data.ReviewData;
import softwaredesign.adnursing.Data.UserData;

public class TTTestActivity extends AppCompatActivity {

    private MyListView post_list;
    private ArrayList<ReviewData> myData;
    private PostPreviewAdapter postPreviewAdapter;
    private PostPreviewDetailAdapter postPreviewDetailAdapter;
    private ReviewAdapter reviewAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ApplicationManager.getInstance().addActivity(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tttest);

        context = TTTestActivity.this;
        post_list = (MyListView) findViewById(R.id.post_list);
        myData = new ArrayList<ReviewData>();

        reviewAdapter = new ReviewAdapter(context, myData);
        post_list.setAdapter(reviewAdapter);

        UserData user = new UserData("王源", R.mipmap.sculpture_4);
        String content = "荷兰鹿特丹伊拉斯漠斯大学医学中心的研究人员日前公布研究报告说,...";
        int floor = 1;
        String time = "1分钟前";
        int image[] = {R.mipmap.banner_1, R.mipmap.ref_image_2};


        UserData user2 = new UserData("王俊凯", R.mipmap.sculpture_2);
        String content2 = "假设我们有一个很大的列表，它里面的元素已经排好序了，这个列表可能是,...";
        int floor2 = 2;
        String time2 = "5分钟前";
        int image2[] = {};

        UserData user3 = new UserData("王源", R.mipmap.sculpture_4);
        String content3 = "假设我们有一个很大的列表，它里面的元素已经排好序了，这个列表可能是,...";
        int floor3 = 3;
        String time3 = "9分钟前";
        int image3[] = {R.mipmap.banner_2};

        String title4 = "老年痴呆症的护理要领";
        String content4 = "JSP九大对象及作用域 内部类与匿名内部类 J2EE六种范围类型,...";
        String time4 = "1分钟前";
        int image4[] = {R.mipmap.banner_3};

        myData.add(new ReviewData(user, content, floor, time, image));
        myData.add(new ReviewData(user2, content2, floor2, time2, image2));
        myData.add(new ReviewData(user3, content3, floor3, time3, image3));


    }


    @Override
    protected void onDestroy() {
        ApplicationManager.getInstance().deleteActivity(this);
        super.onDestroy();
    }

}
