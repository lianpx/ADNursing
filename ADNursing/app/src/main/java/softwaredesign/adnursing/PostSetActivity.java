package softwaredesign.adnursing;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PostSetActivity extends AppCompatActivity  implements View.OnClickListener {

    private ImageView top_bar_back_icon;
    private TextView top_bar_info_txt;
    private MyListView post_list;
    private String postType;
    private ArrayList<PostData> postDatas;


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x001:
                    final PostPreviewAdapter postPreviewAdapter = new PostPreviewAdapter(PostSetActivity.this, postDatas);
                    post_list.setAdapter(postPreviewAdapter);
                    post_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(PostSetActivity.this, ForumActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("postId", postPreviewAdapter.getItem(i).getPostId());
                            bundle.putString("userName", postPreviewAdapter.getItem(i).getUser().getName());
                            bundle.putString("imageDir", postPreviewAdapter.getItem(i).getImagesDir());
                            bundle.putString("userImage", postPreviewAdapter.getItem(i).getUser().getImageDir());

                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ApplicationManager.getInstance().addActivity(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_set);

        initView();
        initTile();
        requestPost();

    }

    private void initView() {
        top_bar_back_icon = (ImageView) findViewById(R.id.top_bar_back_icon);
        top_bar_info_txt = (TextView) findViewById(R.id.top_bar_info_txt);
        post_list = (MyListView) findViewById(R.id.post_list);

        top_bar_back_icon.setOnClickListener(this);
    }

    private void initTile() {
        Intent intent = getIntent();
        String title = (String) intent.getStringExtra("title");
        postType = title;
        top_bar_info_txt.setText(title);
    }

    private void requestPost() {
        final String type = postType;
        new Thread() {
            public void run() {
                postDatas = HttpUtils.getPostWithTypeTimeOrderByGet(type);
                handler.sendEmptyMessage(0x001);
            };
        }.start();
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
