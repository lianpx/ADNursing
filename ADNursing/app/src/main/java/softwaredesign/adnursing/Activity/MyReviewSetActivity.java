package softwaredesign.adnursing.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import softwaredesign.adnursing.ApplicationManager;
import softwaredesign.adnursing.R;

public class MyReviewSetActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView top_bar_back_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review_set);

        initView();
//        requestReview();
        setReview();
    }

    private void initView() {
        top_bar_back_icon = (ImageView) findViewById(R.id.top_bar_back_icon);

        top_bar_back_icon.setOnClickListener(this);
    }

    private void setReview() {

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
}
