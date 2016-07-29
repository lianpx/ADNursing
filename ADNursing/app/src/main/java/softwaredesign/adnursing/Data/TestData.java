package softwaredesign.adnursing.Data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huacan liang on 2016/7/15.
 */
public class TestData {
    private int testId;
    private String testName;
    private String testType;
    private int testScore;
    private String testDate;


    public TestData(int testId, String testName, String testType, int testScore, String testDate) {
        this.testId = testId;
        this.testName = testName;
        this.testType = testType;
        this.testScore = testScore;
        this.testDate = testDate;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public int getTestScore() {
        return testScore;
    }

    public void setTestScore(int testScore) {
        this.testScore = testScore;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public String getModifiedDate() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat format2 = new SimpleDateFormat("MM-dd HH:mm");
        Date date = null;
        try {
            date = format.parse(testDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format2.format(date);
    }
}
