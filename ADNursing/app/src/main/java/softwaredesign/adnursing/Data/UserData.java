package softwaredesign.adnursing.Data;

import android.os.Parcel;
import android.os.Parcelable;

import softwaredesign.adnursing.R;

/**
 * Created by huacan liang on 2016/7/9.
 */
public class UserData implements Parcelable {

    private String name;
    private String password;
    private int sculpture;
    private String imageDir;
    private String gender;
    private String address;
    private String desc;
    private String testName;
    private String testBirth;
    private String testGender;
    private int userId;

    public UserData(String name, String imageDir, String gender, String address, String desc, String testName, String testBirth, String testGender, int userId) {
        this.name = name;
        this.imageDir = imageDir;
        this.gender = gender;
        this.address = address;
        this.desc = desc;
        this.testName = testName;
        this.testBirth = testBirth;
        this.testGender = testGender;
        this.userId = userId;
    }

    public UserData(String name, String imageDir) {

        this.name = name;
        this.imageDir = imageDir;
    }

    private int defaultSculpture = R.mipmap.sculpture_unknown_default;

    public UserData(String name, int sculpture)  {
        this.name = name;
        this.sculpture = sculpture;
    }

    public UserData(String name) {
        this.name = name;
        this.sculpture= defaultSculpture;
    }

    protected UserData(Parcel in) {
        name = in.readString();
        password = in.readString();
        sculpture = in.readInt();
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(password);
        parcel.writeInt(sculpture);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSculpture() {
        return sculpture;
    }

    public void setSculpture(int sculpture) {
        this.sculpture = sculpture;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestBirth() {
        return testBirth;
    }

    public void setTestBirth(String testBirth) {
        this.testBirth = testBirth;
    }

    public String getTestGender() {
        return testGender;
    }

    public void setTestGender(String testGender) {
        this.testGender = testGender;
    }

    public String getImageDir() {
        return imageDir;
    }

    public void setImageDir(String imageDir) {
        this.imageDir = imageDir;
    }

}
