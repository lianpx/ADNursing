package softwaredesign.adnursing.Data;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huacan liang on 2016/7/9.
 */
public class PostData implements Parcelable {

    private int postId;
    private String title;
    private String content;
    private String type;
    private String time;
    private int image[];
    private String imagesDir;
    private UserData user;
    private int visitorNum;

    private Bitmap firstBitmap;

    public static final int defultImage[] = {};

//    public static final Parcelable.Creator<T> CREATOR;

    public PostData(String title, String content, String time, int[] image) {
        this.title = title;
        this.content = content;
        this.time = time;
        this.image = image;
        if (image.length <= 0) {
            this.image = defultImage;
        }
    }


    protected PostData(Parcel in) {
        postId = in.readInt();
        title = in.readString();
        content = in.readString();
        type = in.readString();
        time = in.readString();
        image = in.createIntArray();
        imagesDir = in.readString();
        user = in.readParcelable(UserData.class.getClassLoader());
        visitorNum = in.readInt();
        firstBitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<PostData> CREATOR = new Creator<PostData>() {
        @Override
        public PostData createFromParcel(Parcel in) {
            return new PostData(in);
        }

        @Override
        public PostData[] newArray(int size) {
            return new PostData[size];
        }
    };

    public String getImagesDir() {
        return imagesDir;
    }

    public void setImagesDir(String imagesDir) {
        this.imagesDir = imagesDir;
    }

    public PostData(int postId, String title, String content, String type, String time, String imagesDir, UserData user, int visitorNum) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.type = type;
        this.time = time;
        this.imagesDir = imagesDir;
        this.user = user;
        this.visitorNum = visitorNum;
    }


    public PostData(String title, String content, String type, String time, int[] image, int visitorNum, UserData user) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.time = time;
        this.image = image;
        this.visitorNum = visitorNum;
        this.user = user;
    }





    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(postId);
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeString(type);
        parcel.writeString(time);
        parcel.writeIntArray(image);
        parcel.writeParcelable(user, i);
        parcel.writeInt(visitorNum);
    }


    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int[] getImage() {
        return image;
    }

    public void setImage(int[] image) {
        this.image = image;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public int getVisitorNum() {
        return visitorNum;
    }

    public void setVisitorNum(int visitorNum) {
        this.visitorNum = visitorNum;
    }

    public String getModifiedTime() {
        String diffStr = "";

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat format2 = new SimpleDateFormat("MM月dd日");
        Date current_date = new Date();
        Date post_date = null;
        try {
            post_date = format.parse(time);
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
        } else if (days < 3) {
            diffStr = String.valueOf(days) + "天前";
        } else {
            diffStr = format2.format(post_date);
        }

        return diffStr;
    }

}
