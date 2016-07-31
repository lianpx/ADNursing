package softwaredesign.adnursing.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import softwaredesign.adnursing.Custom.MyListView;
import softwaredesign.adnursing.Utils.HttpUtils;
import softwaredesign.adnursing.Data.PostData;
import softwaredesign.adnursing.R;
import softwaredesign.adnursing.Utils.ListViewImageUtils;

public class PostPreviewAdapter extends BaseAdapter {

    private Context myContext;                  // 上下文
    private ArrayList<PostData> myPostData;     // 数据列表
    private ViewHolder holder;
    private ArrayList<Bitmap> bitmaps;          // 配图数据
    private ArrayList<ImageView> imageViews;    // 配图对应的ImageView

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
            holder.vImage = (ImageView) convertView.findViewById(R.id.preview_img);
            holder.vTitle = (TextView) convertView.findViewById(R.id.preview_title);
            holder.vContent = (TextView) convertView.findViewById(R.id.preview_content);
            holder.vTime = (TextView) convertView.findViewById(R.id.preview_time);
            convertView.setTag(holder);
        } else {
            if(viewGroup instanceof MyListView){
                if(((MyListView) viewGroup).isOnMeasure()){
                    return convertView;
                }
            }
            holder = (ViewHolder) convertView.getTag();
        }

        System.out.println("getView " + i + ": " + convertView);

//        imageViews.set(i, holder.vImage);
//        getImage(myPostData.get(i).getImagesDir(), i);
        ListViewImageUtils listViewImageUtils = new ListViewImageUtils();
        listViewImageUtils.setListViewImage(holder.vImage, myPostData.get(i).getImagesDir());
        holder.vTitle.setText(myPostData.get(i).getTitle());
        holder.vContent.setText(myPostData.get(i).getContent());
        holder.vTime.setText(myPostData.get(i).getModifiedTime());

        return convertView;
    }

    /**
     * 创建静态类用来保存View
     */
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

    /**
     * 向服务器获取配图
     * @param imagesDir
     * @param i
     */
    private void getImage(String imagesDir, int i) {
        System.out.println("getImage");
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
                bitmaps.set(finalI, HttpUtils.loadImage(dir));
                handler.sendEmptyMessage(finalI);
            };
        }.start();
    }


    public void remove(int i) {
        myPostData.remove(i);
        notifyDataSetChanged();
    }
}



