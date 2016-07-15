package softwaredesign.adnursing;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by huacan liang on 2016/7/9.
 */
public class PostPreviewAdapter extends BaseAdapter {

    private Context myContext;
    private ArrayList<PostData> myPostData;
    private ViewHolder holder;
    private Bitmap bitmap = null;
    private ArrayList<Bitmap> bitmaps;
    private ArrayList<ImageView> imageViews;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (bitmaps.get(msg.what) != null) {
                imageViews.get(msg.what).setImageBitmap(bitmaps.get(msg.what));
            }
        }
    };

    public PostPreviewAdapter(Context myContext, ArrayList<PostData> myPostData) {
        this.myContext = myContext;
        this.myPostData = myPostData;
        bitmaps  = new ArrayList<>();
        imageViews = new ArrayList<>();

        if (myPostData != null) {
            for (int i = 0; i < myPostData.size(); i++) {
                bitmaps.add(null);
                imageViews.add(null);
            }
        }
    }

    @Override
    public int getCount() {
        return myPostData.size();
    }

    @Override
    public PostData getItem(int i) {
        return myPostData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(myContext).inflate(R.layout.layout_post_preview, viewGroup, false);
            holder = new ViewHolder();
            holder.vImage = (ImageView) convertView.findViewById(R.id.priview_img);
            holder.vTitle = (TextView) convertView.findViewById(R.id.priview_title);
            holder.vContent = (TextView) convertView.findViewById(R.id.priview_content);
            holder.vTime = (TextView) convertView.findViewById(R.id.priview_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        imageViews.set(i, holder.vImage);
        getImage(myPostData.get(i).getImagesDir(), i);
        holder.vTitle.setText(myPostData.get(i).getTitle());
        holder.vContent.setText(myPostData.get(i).getContent());
        holder.vTime.setText(myPostData.get(i).getModifiedTime());

        return convertView;
    }

    private static class ViewHolder {
        ImageView vImage;
        TextView vTitle;
        TextView vContent;
        TextView vTime;
    }

    public void add(PostData data) {
        if (myPostData == null) {
            myPostData = new ArrayList<>();
        }
        myPostData.add(data);
        notifyDataSetChanged();
    }

    private void getImage(String imagesDir, int i) {
        if (imagesDir.equals("")) {
            return;
        }
        String tmpString;
        if (imagesDir.indexOf("|") == -1) {
            tmpString = imagesDir;
        } else {
            tmpString = imagesDir.substring(0, imagesDir.indexOf("|"));
        }
        final String dir = tmpString;
        final int finalI = i;
        new Thread() {
            public void run() {
                bitmaps.set(finalI, HttpUtils.getImageByGet(dir));
                handler.sendEmptyMessage(finalI);
            };
        }.start();
    }

    private String getTime(String releaseDate) {
        String diffStr = "";

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date current_date = new Date();
        Date post_date = null;
        try {
            post_date = format.parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = 0;
        if (post_date != null) {
            diff = current_date.getTime() - post_date.getTime();
        }
        long days = diff / (1000 * 60 * 60 * 24);

        if (days < 1) {
            days = diff / (1000 * 60 * 60);
            if (days < 1) {
                days = diff / (1000 * 60);
                diffStr = String.valueOf(days)+"分钟前";
            } else {
                diffStr = String.valueOf(days)+"小时前";
            }
        } else if (days > 3) {
            diffStr = String.valueOf(days) + "天前";
        } else {
            diffStr = format.format(post_date);
        }

        return diffStr;
    }
}



