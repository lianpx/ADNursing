package softwaredesign.adnursing;

import android.app.Application;
import android.content.Context;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

public class HttpApplication extends Application {

    private static int UserId;

    private static Context context;

    private static HttpClient mHttpClient = null;

    private static final String CHARSET = HTTP.UTF_8;

    @Override
    public void onCreate() {
        super.onCreate();
        mHttpClient = this.createHttpClient();
        context = getApplicationContext();
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        this.shutdownHttpClient();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        this.shutdownHttpClient();
    }

    /**创建HttpClient实例
     * @return
     */
    private HttpClient createHttpClient(){
        HttpParams params = new BasicHttpParams();
        //设置基本参数
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, CHARSET);
        HttpProtocolParams.setUseExpectContinue(params, true);
        //超时设置
		/*从连接池中取连接的超时时间*/
        ConnManagerParams.setTimeout(params, 2000);
		/*连接超时*/
        HttpConnectionParams.setConnectionTimeout(params, 5000);
		/*请求超时*/
        HttpConnectionParams.setSoTimeout(params, 5000);
        //设置HttpClient支持HTTp和HTTPS两种模式
        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        //使用线程安全的连接管理来创建HttpClient
        ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
//        ClientConnectionManager conMgr = new PoolingClientConnectionManager(schReg);
        HttpClient client = new DefaultHttpClient(conMgr, params);
        return client;
    }
    private void shutdownHttpClient(){
        if(mHttpClient != null && mHttpClient.getConnectionManager() != null){
            mHttpClient.getConnectionManager().shutdown();
        }
    }
    public static HttpClient getHttpClient(){
        return mHttpClient;
    }

    public static Context getContext() {
        return context;
    }

    public static int getUserId() {
        return UserId;
    }

    public static void setUserId(int userId) {
        UserId = userId;
    }

}