package softwaredesign.adnursing;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by huacan liang on 2016/7/10.
 */
public class ReviewAdapter extends BaseAdapter {

    private Context myContext;
    private ArrayList<ReviewData> myReviewData;
    private ImageView sculptureViews[];
    private ImageView imageViews[][];

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    Bitmap bm1 = msg.getData().getParcelable("bitmap");
                    sculptureViews[msg.arg1].setImageBitmap(bm1);
                    break;
                case 2:
                    Bitmap bm2 = msg.getData().getParcelable("bitmap");
                    imageViews[msg.arg1][msg.arg2].setImageBitmap(bm2);
                    break;
            }
        }
    };


    public ReviewAdapter(Context myContext, ArrayList<ReviewData> myReviewData) {
        this.myContext = myContext;
        this.myReviewData = myReviewData;
        if (myReviewData != null) {
            sculptureViews = new ImageView[myReviewData.size()];
            imageViews = new ImageView[myReviewData.size()][3];
        }
    }

    @Override
    public int getCount() {
        return myReviewData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(myContext).inflate(R.layout.layout_review, viewGroup, false);
            holder = new ViewHolder();
            holder.vSculpture = (ImageView) convertView.findViewById(R.id.review_scul);
            holder.vImage[0] = (ImageView) convertView.findViewById(R.id.review_image_1);
            holder.vImage[1] = (ImageView) convertView.findViewById(R.id.review_image_2);
            holder.vImage[2] = (ImageView) convertView.findViewById(R.id.review_image_3);
            holder.vName = (TextView) convertView.findViewById(R.id.review_name);
            holder.vContent = (TextView) convertView.findViewById(R.id.review_content);
            holder.vFloor = (TextView) convertView.findViewById(R.id.review_floor);
            holder.vTime = (TextView) convertView.findViewById(R.id.review_time);
            holder.vImagefield = (LinearLayout) convertView.findViewById(R.id.review_image_field);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        imageViews[i][0] = holder.vImage[0];
        imageViews[i][1] = holder.vImage[1];
        imageViews[i][2] = holder.vImage[2];
        sculptureViews[i] = holder.vSculpture;

        if ((myReviewData.get(i).getImageDir().equals(""))) {
            holder.vImagefield.setVisibility(View.GONE);
        } else {
            holder.vImagefield.setVisibility(View.VISIBLE);
            getImages(myReviewData.get(i).getImageDir(), i);

        }

        if (!myReviewData.get(i).getUser().getImageDir().equals("")) {
            getSculpture(myReviewData.get(i).getUser().getImageDir(), i);
        }

        holder.vName.setText(myReviewData.get(i).getUser().getName());
        holder.vContent.setText(myReviewData.get(i).getContent());
        holder.vTime.setText(myReviewData.get(i).getModifiedTime());

        return convertView;
    }

    private static class ViewHolder {
        ImageView vSculpture;
        TextView vName;
        TextView vContent;
        TextView vFloor;
        TextView vTime;
        ImageView[] vImage = new ImageView[3];
        LinearLayout vImagefield;
    }

    public void add(ReviewData data) {
        if (myReviewData == null) {
            myReviewData = new ArrayList<>();
        }
        myReviewData.add(data);
        notifyDataSetChanged();
    }

    public void getSculpture(String userImageDir, int i) {
        final String dir = userImageDir;
        final int finalI = i;
        new Thread() {
            public void run() {
                Bitmap bm = HttpUtils.getImageByGet(dir);
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putParcelable("bitmap", bm);
                msg.what = 1;
                msg.arg1 = finalI;
                msg.setData(bundle);
                handler.sendMessage(msg);
            };
        }.start();
    }

    public void getImages(String imagesDir, int i) {
        if (imagesDir.equals("")) {
            return;
        }
        final String dir[] = imagesDir.split("\\|");
        final int finalI = i;
        for (int j = 0; j < dir.length; j++) {
            final int finalJ = j;
            new Thread() {
                public void run() {
                    Bitmap bm = HttpUtils.getImageByGet(dir[finalJ]);
                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("bitmap", bm);
                    msg.what = 2;
                    msg.arg1 = finalI;
                    msg.arg2 = finalJ;
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            }.start();
        }
    }
}
