package softwaredesign.adnursing.Custom;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import softwaredesign.adnursing.R;

public class NewTestFragment extends Fragment {
    // Actitvity继承该接口，然后使得Fragment能调用Actitity中的函数
    public interface MyListener {
        void nextPage(int checkedNum);
        void lastPage(int checkedNum);
        void finishPage(int checkedNum);
    }

    private MyListener myListener;

    private int currentPage = -1;
    private int totalPage = -1;
    private String items[] = null;

    private CheckBox cb[] = new CheckBox[9];
    private TextView new_test_page;
    private Button new_test_last;
    private Button new_test_next;

    public static NewTestFragment newInstance(int currentPage, int totalPage, String[] items) {
        NewTestFragment fragment = new NewTestFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("currentPage", currentPage);
        bundle.putInt("totalPage", totalPage);
        bundle.putStringArray("items", items);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            currentPage = args.getInt("currentPage");
            totalPage = args.getInt("totalPage");
            items = args.getStringArray("items");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_new_test, container, false);

        cb[0] = (CheckBox) view.findViewById(R.id.checkbox0);
        cb[1] = (CheckBox) view.findViewById(R.id.checkbox1);
        cb[2] = (CheckBox) view.findViewById(R.id.checkbox2);
        cb[3] = (CheckBox) view.findViewById(R.id.checkbox3);
        cb[4] = (CheckBox) view.findViewById(R.id.checkbox4);
        cb[5] = (CheckBox) view.findViewById(R.id.checkbox5);
        cb[6] = (CheckBox) view.findViewById(R.id.checkbox6);
        cb[7] = (CheckBox) view.findViewById(R.id.checkbox7);
        cb[8] = (CheckBox) view.findViewById(R.id.checkbox8);

        new_test_page = (TextView) view.findViewById(R.id.new_test_page);

        new_test_last = (Button) view.findViewById(R.id.new_test_last);
        new_test_next = (Button) view.findViewById(R.id.new_test_next);

        if (currentPage == 1) {
            new_test_last.setVisibility(View.GONE);
        } else {
            new_test_last.setVisibility(View.VISIBLE);
        }

        if (currentPage == totalPage) {
            new_test_next.setText("完成");
        } else {
            new_test_next.setText("下一页");
        }

        for (int i = 0; i < cb.length; i++) {
            cb[i].setText(items[i]);
        }

        new_test_page.setText(currentPage+"/"+totalPage);

        new_test_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPage > 1) {
                    System.out.println("lastPage");
                    myListener.lastPage(getChecked());
                }
            }
        });

        new_test_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPage < totalPage) {
                    System.out.println("nextPage");
                    myListener.nextPage(getChecked());
                } else if (currentPage == totalPage) {
                    System.out.println("finishPage");
                    myListener.finishPage(getChecked());
                }
            }
        });

        return view;
    }


    /**
     * 计算选中项的数目
     * @return
     */
    public int getChecked() {
        int checkedNum = 0;
        for (int i = 0; i < items.length; i++) {
            if (cb[i].isChecked()) {
                checkedNum++;
            }
        }
        return checkedNum;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try{
            myListener = (MyListener) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString() + "must implement MyListener");
        }
    }
}
