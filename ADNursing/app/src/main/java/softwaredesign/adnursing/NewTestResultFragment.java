package softwaredesign.adnursing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by huacan liang on 2016/7/14.
 */
public class NewTestResultFragment extends Fragment {

    private String name;
    private int score;

    TextView new_test_result_score;
    TextView new_test_result_advise;
    Button new_test_result_back_button;

    public static NewTestResultFragment newInstance(String name, int score) {
        NewTestResultFragment fragment = new NewTestResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putInt("score", score);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            name = args.getString("name");
            score = args.getInt("score");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_new_test_result, container, false);

        new_test_result_score = (TextView) view.findViewById(R.id.new_test_result_score);
        new_test_result_advise = (TextView) view.findViewById(R.id.new_test_result_advise);
        new_test_result_back_button = (Button) view.findViewById(R.id.new_test_result_back_button);

        new_test_result_score.setText("您此次得分为："+score+"（每项一分）");

        if (score >= 0 && score <= 7) {
            new_test_result_advise.setText("有0～7种符合个人目前的情况可暂放宽心");
        } else if (score > 7 && score <= 14) {
            new_test_result_advise.setText("有8～14种符合个人目前的情况应加以注意");
        } else if (score > 14) {
            new_test_result_advise.setText("有15种以上符合个人目前的情况，患痴呆的可能性很大");
        }

        new_test_result_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    public void run() {
                        int testId = HttpUtils.uploadTestResult(HttpApplication.getUserId(), score);
                        Intent intent = new Intent(getActivity(), TestActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }.start();
            }
        });

        return view;
    }

}
