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
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by huacan liang on 2016/7/10.
 */
public class PostPreviewDetailAdapter extends BaseAdapter {

    private Context myContext;
    private ArrayList<PostData> myPostData;
    private ImageView sculptureView[];
    private ImageView imageView[];


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    Bitmap bm1 = msg.getData().getParcelable("bitmap");
                    sculptureView[msg.arg1].setImageBitmap(bm1);
                    break;
                case 2:
                    Bitmap bm2 = msg.getData().getParcelable("bitmap");
                    imageView[msg.arg1].setImageBitmap(bm2);
                    break;
            }
        }
    };

    public PostPreviewDetailAdapter(Context myContext, ArrayList<PostData> myPostData) {
        this.myContext = myContext;
        this.myPostData = myPostData;
        if (myPostData != null) {
            sculptureView = new ImageView[myPostData.size()];
            imageView = new ImageView[myPostData.size()];
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(myContext).inflate(R.layout.layout_post_preview_detail, viewGroup, false);
            holder = new ViewHolder();
            holder.vSculpture = (ImageView) convertView.findViewById(R.id.post_preview_detail_sculpture);
            holder.vImage = (ImageView) convertView.findViewById(R.id.post_preview_detail_image);
            holder.vName = (TextView) convertView.findViewById(R.id.post_preview_detail_name);
            holder.vTitle = (TextView) convertView.findViewById(R.id.post_preview_detail_title);
            holder.vContent = (TextView) convertView.findViewById(R.id.post_preview_detail_content);
            holder.vTime = (TextView) convertView.findViewById(R.id.post_preview_detail_time);
            holder.vType = (TextView) convertView.findViewById(R.id.post_preview_detail_type);
            holder.vVisitorNum = (TextView) convertView.findViewById(R.id.post_preview_detail_visitor_num);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        sculptureView[i] = holder.vSculpture;
        imageView[i] = holder.vImage;

//        holder.vSculpture.setImageResource(myPostData.get(i).getUser().getSculpture());
//        holder.vImage.setImageResource(myPostData.get(i).getImage()[0]);
        getImage(myPostData.get(i).getUser().getImageDir(), i, 1);
        getImage(myPostData.get(i).getImagesDir(), i, 2);

        holder.vName.setText(myPostData.get(i).getUser().getName());
        holder.vTitle.setText(myPostData.get(i).getTitle());
        holder.vContent.setText(myPostData.get(i).getContent());
        holder.vTime.setText(myPostData.get(i).getModifiedTime());
        holder.vType.setText(myPostData.get(i).getType());
        holder.vVisitorNum.setText(myPostData.get(i).getVisitorNum()+"");

        return convertView;
    }

    private static class ViewHolder {
        ImageView vSculpture;
        ImageView vImage;
        TextView vName;
        TextView vTitle;
        TextView vContent;
        TextView vTime;
        TextView vType;
        TextView vVisitorNum;
    }

    public void add(PostData data) {
        if (myPostData == null) {
            myPostData = new ArrayList<>();
        }
        myPostData.add(data);
        notifyDataSetChanged();
    }

    private void getImage(String imagesDir, int i, int what) {
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
        final int finalWhat = what;
        new Thread() {
            public void run() {
                Bitmap bm = HttpUtils.getImageByGet(dir);
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putParcelable("bitmap", bm);
                msg.what = finalWhat;
                msg.arg1 = finalI;
                msg.setData(bundle);
                handler.sendMessage(msg);
            };
        }.start();
    }
}
