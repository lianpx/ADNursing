package softwaredesign.adnursing;

/**
 * Created by huacan liang on 2016/7/14.
 */
import java.util.LinkedList;
import java.util.List;
import android.app.Activity;
import android.app.Application;
import android.util.Log;

public class ApplicationManager extends Application {

    private List<Activity> activitys = null;
    private static ApplicationManager instance;

    private ApplicationManager() {
        activitys = new LinkedList<Activity>();
    }

    /**
     * 单例模式中获取唯一的MyApplication实例
     * @return
     */
    public static ApplicationManager getInstance() {
        if (null == instance) {
            instance = new ApplicationManager();
        }
        return instance;
    }

    /**
     * 添加Activity到容器中
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activitys != null && activitys.size() > 0) {
            if(!activitys.contains(activity)){
                activitys.add(activity);
            }
        }else{
            activitys.add(activity);
        }
    }

    public void deleteActivity(Activity activity) {
        if (activitys.contains(activity)) {
            activitys.remove(activity);
            Log.e("remove", activity.toString());
        }
    }

    /**
     * 遍历所有Activity并finish
     */
    public void exit() {
        if (activitys != null && activitys.size() > 0) {
            for (Activity activity : activitys) {
                activity.finish();
            }
        }
        activitys.clear();
        System.exit(0);
    }
}