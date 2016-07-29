package softwaredesign.adnursing.Utils;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

/**
 * Created by huacan liang on 2016/7/29.
 */
public class ListViewImageUtils {

    private String imageUrl;
    private ImageView imageView;
    private LoadImageHandler handler;

    public void setListViewImage(ImageView view, String url) {
        this.imageView = view;
        this.imageUrl = url;
        handler = new LoadImageHandler();
        Thread t = new LoadImageThread();
        t.start();
    }

    class LoadImageThread extends Thread{
        @Override
        public void run() {
            Bitmap bm = HttpUtils.loadImage(imageUrl);
            Message msg = handler.obtainMessage();
            msg.what = 0x000;
            msg.obj = bm;
            handler.sendMessage(msg);
        }
    }

    class LoadImageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x000:
                    Bitmap bm = (Bitmap) msg.obj;
                    imageView.setImageBitmap(bm);
                    break;
            }
        }
    }
}
