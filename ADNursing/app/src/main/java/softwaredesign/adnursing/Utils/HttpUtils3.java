package softwaredesign.adnursing.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import softwaredesign.adnursing.HttpApplication;
import softwaredesign.adnursing.Data.PostData;
import softwaredesign.adnursing.R;
import softwaredesign.adnursing.Data.ReviewData;
import softwaredesign.adnursing.Data.TestData;
import softwaredesign.adnursing.Data.UserData;

public class HttpUtils3 {

    private static String ip = "http://172.18.42.155:8080";
//    private static String ip = "http://172.18.42.155:8080";
    private static String loginUrl = ip + "/ADNursingServer/android/login.jsp?mode=android";
    private static String RegisterUrl = ip + "/ADNursingServer/android/register.jsp?mode=android";
    private static String downImageUrl = ip + "/ADNursingServer/servlet/DownloadFileServlet";
    private static String getPostByTypeTimeOrderUrl = ip + "/ADNursingServer/servlet/ViewPostByKindServlet?mode=android";
    private static String getPostByTypeReviewOrderUrl = ip + "/ADNursingServer/servlet/ViewPostsByKindAndCommentNum?mode=android";
    private static String getPostByIdUrl = ip + "/ADNursingServer/servlet/ViewOnePostServlet?mode=android&";
    private static String getReviewWithPostIdUrl = ip + "/ADNursingServer/servlet/ViewPostCommentServlet?mode=android";
    private static String uploadPostUrl = ip + "/ADNursingServer/android/addPost.jsp?mode=android";
    private static String uploadPostImageUrl = ip + "/ADNursingServer/servlet/AddPostImgUrlServlet?mode=android";
    private static String uploadReviewUrl = ip + "/ADNursingServer/servlet/AddCommentInforServlet?mode=android";
    private static String uploadReviewImageUrl = ip + "/ADNursingServer/servlet/AddCommentImgUrlServlet?mode=android";
    private static String getUserInfoWithIdUrl = ip + "/ADNursingServer/servlet/ViewUserServlet?mode=android";
    private static String getTestResultUrl = ip + "/ADNursingServer/servlet/ViewOwnerTestServlet?mode=android";
    private static String uploadTestInfoUrl = ip + "/ADNursingServer/android/updateTestInfor.jsp?mode=android";
    private static String uploadTestResultUrl = ip + "/ADNursingServer/android/addTest.jsp?mode=android";


    /**
     * 提交登录信息
     * @param username
     * @param password
     * @return
     */
    public static int loginByPost(String username, String password) {
        int userId = -1;
        try {
            HttpClient httpClient = HttpApplication.getHttpClient();
            HttpPost httpPost = new HttpPost(loginUrl);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();
            String jsonString = EntityUtils.toString(httpEntity);

            JSONObject jsonObject = new JSONObject(jsonString);

            userId = jsonObject.getInt("userId");
            Log.w("userId", String.valueOf(userId));

            return userId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 提交注册信息
     * @param username
     * @param password
     * @return
     */
    public static int RegisterByPost(String username, String password) {
        int userId = -1;
        try {
            HttpClient httpClient = HttpApplication.getHttpClient();
            HttpPost httpPost = new HttpPost(RegisterUrl);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("userName", username));
            params.add(new BasicNameValuePair("userPassword", password));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();
            String jsonString = EntityUtils.toString(httpEntity);

            JSONObject jsonObject = new JSONObject(jsonString);

            userId = jsonObject.getInt("userId");
            Log.w("userId", String.valueOf(userId));

            return userId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 通过类型获取帖子，获得的帖子以时间顺序排列
     * @param type
     * @return
     */
    public static ArrayList<PostData> getPostWithTypeTimeOrderByGet(String type) {
        try {
            HttpClient httpClient = HttpApplication.getHttpClient();
            HttpPost httpPost = new HttpPost(getPostByTypeTimeOrderUrl +"&postKind="+type);

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();
            String jsonString = EntityUtils.toString(httpEntity);

            JSONArray jsonArray = new JSONArray(jsonString);

            ArrayList<PostData> postDatas = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                PostData postData = parseJsonToPost1(jsonObject);
                postDatas.add(postData);
            }

            return postDatas;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过类型获取帖子，获得的帖子以评论者个数排列
     * @param type
     * @return
     */
    public static ArrayList<PostData> getPostWithTypeReviewOrderByGet(String type) {
        try {
            HttpClient httpClient = HttpApplication.getHttpClient();
            HttpPost httpPost = new HttpPost(getPostByTypeReviewOrderUrl +"&postKind="+type);

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();
            String jsonString = EntityUtils.toString(httpEntity);

            JSONArray jsonArray = new JSONArray(jsonString);

            ArrayList<PostData> postDatas = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                PostData postData = parseJsonToPost1(jsonObject);
                postDatas.add(postData);
            }

            return postDatas;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过postId获取某篇帖子的评论
     * @param postId
     * @return
     */
    public static ArrayList<ReviewData> getReciewWithPostIdByGet(int postId) {
        try {
            HttpClient httpClient = HttpApplication.getHttpClient();
            HttpPost httpPost = new HttpPost(getReviewWithPostIdUrl+"&postId="+postId);

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();
            String jsonString = EntityUtils.toString(httpEntity);

            System.out.println(jsonString);

            JSONArray jsonArray = new JSONArray(jsonString);

            System.out.println("len "+jsonArray.length());

            ArrayList<ReviewData> reviewDatas = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                ReviewData reviewData = parseJsonToReview(jsonObject);
                reviewDatas.add(reviewData);
            }

            return reviewDatas;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过图片路径获取图片
     * @param dir
     * @return
     */
    public static Bitmap getImageByGet(String dir) {
        if(dir.indexOf("|") > 0) {
            dir = dir.substring(0, dir.indexOf("|"));
        }
        Bitmap decodeResource;

        URL myurl = null;
        Bitmap bitmap = null;
        String name = dir.substring(dir.lastIndexOf("\\")+1);
        String temp = downImageUrl;
        temp += "?filename=" + name + "&url=" + dir;

        try {
            myurl = new URL(temp);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            ////////////////////////
            // 可能有空指针
            ////////////////////////
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(5000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.89 Safari/537.36");
            conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            conn.setRequestProperty("Accept-Encoding","gzip, deflate, sdch");
            conn.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8");
            conn.setRequestProperty("Host", ip.substring(ip.lastIndexOf("//")+2));
            conn.setRequestProperty("Connection","keep-alive");
            conn.setRequestProperty("HTTPS","1");
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");
            conn.connect();

            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);

            is.close();
            return bitmap;

        } catch (IOException e) {
            e.printStackTrace();
            decodeResource = BitmapFactory.decodeResource(HttpApplication.getContext().getResources(), R.mipmap.image_default);
            return decodeResource;
        }
    }

    /**
     * 通过postId获取一篇帖子的信息
     * @param id
     * @return
     */
    public static PostData getPostWithIdByGet(int id) {
        try {
            String url = getPostByIdUrl+"&postId="+id;
            HttpClient httpClient = HttpApplication.getHttpClient();
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();
            String jsonString = EntityUtils.toString(httpEntity);

            JSONObject jsonObject = new JSONObject(jsonString);

            PostData postData = parseJsonToPost1(jsonObject);

            return postData;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上传图片，一次可传多张
     * @param file
     * @param postid
     * @return
     */
    public static String uploadPostImage(ArrayList<File> file, int postid) {
        ArrayList<String> srcPath = new ArrayList<String>();
        for (int i = 0; i < file.size(); i++) {
            srcPath.add(file.get(i).getPath());
        }
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "----WebKitFormBoundaryxGQCnJT3HJdffDQA";
        String displayString = "";

        try {
            URL url = new URL(uploadPostImageUrl+"&postId="+String.valueOf(postid));
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setChunkedStreamingMode(100*1024);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "utf-8");
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());

            for (int i = 0; i < srcPath.size(); i++) {
                dos.writeBytes(twoHyphens + boundary + end);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploadfile\"; filename=\""
                        + srcPath.get(i).substring(srcPath.get(i).lastIndexOf("/") + 1) + "\"" + end);
                dos.writeBytes("Content-Type: image/png" + end);
                dos.writeBytes(end);

                FileInputStream fis = new FileInputStream(srcPath.get(i));
                byte[] buffer = new byte[8192];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    dos.write(buffer, 0, count);
                }
                fis.close();
                dos.writeBytes(end);
            }

            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            dos.flush();

            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);

            displayString = br.readLine();

            dos.close();
            is.close();

        } catch (Exception e) {
            Log.e("dialog", "上传失败" + e);
            e.printStackTrace();
        }

        return displayString;
    }


    /**
     * 上传帖子的文字部分
     * @param userid
     * @param title
     * @param content
     * @param type
     * @return
     */
    public static int uploadPostText(int userid, String title, String content, String type) {
        int postId = -1;
        try {
            HttpClient httpClient = HttpApplication.getHttpClient();
            HttpPost httpPost = new HttpPost(uploadPostUrl+"&userId="+userid);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("postTitle", title));
            params.add(new BasicNameValuePair("postText", content));
            params.add(new BasicNameValuePair("postKind", type));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();
            String jsonString = EntityUtils.toString(httpEntity);

            JSONObject jsonObject = new JSONObject(jsonString);

            postId = jsonObject.getInt("postId");
            Log.w("postId", String.valueOf(postId));

            return postId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postId;
    }

    /**
     * 上传评论的图片，可传多张
     * @param file
     * @param reviewId
     * @return
     */
    public static String uploadReviewImage(ArrayList<File> file, int reviewId) {
        ArrayList<String> srcPath = new ArrayList<String>();
        for (int i = 0; i < file.size(); i++) {
            srcPath.add(file.get(i).getPath());
        }
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "----WebKitFormBoundaryxGQCnJT3HJdffDQA";
        String displayString = "";

        try {
            URL url = new URL(uploadReviewImageUrl + "&commentId=" + String.valueOf(reviewId));
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setChunkedStreamingMode(100 * 1024);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "utf-8");
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());

            for (int i = 0; i < srcPath.size(); i++) {
                dos.writeBytes(twoHyphens + boundary + end);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploadfile\"; filename=\""
                        + srcPath.get(i).substring(srcPath.get(i).lastIndexOf("/") + 1) + "\"" + end);
                dos.writeBytes("Content-Type: image/png" + end);
                dos.writeBytes(end);

                FileInputStream fis = new FileInputStream(srcPath.get(i));
                byte[] buffer = new byte[8192];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    dos.write(buffer, 0, count);
                }
                fis.close();
                dos.writeBytes(end);
            }

            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            dos.flush();

            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);

            displayString = br.readLine();

            dos.close();
            is.close();

        } catch (Exception e) {
            Log.e("dialog", "上传失败" + e);
            e.printStackTrace();
        }
        return displayString;
    }

    /**
     * 上传帖子的文字部分
     * @param userid
     * @param postId
     * @param content
     * @return
     */
    public static int uploadReviewText(int userid, int postId, String content) {
        int reviewId = -1;
        try {
            HttpClient httpClient = HttpApplication.getHttpClient();
            HttpPost httpPost = new HttpPost(uploadReviewUrl+"&userId="+userid);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("postId", String.valueOf(postId)));
            params.add(new BasicNameValuePair("commentText", content));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();
            String jsonString = EntityUtils.toString(httpEntity);

            JSONObject jsonObject = new JSONObject(jsonString);

            reviewId = jsonObject.getInt("commentId");
            Log.w("reviewId", String.valueOf(reviewId));

            return reviewId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviewId;
    }

    /**
     * 通过userId获取用户信息
     * @param userId
     * @return
     */
    public static UserData getUserInfoWithIdByGet(int userId) {
        try {
            HttpClient httpClient = HttpApplication.getHttpClient();
            HttpPost httpPost = new HttpPost(getUserInfoWithIdUrl +"&userId="+userId);

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();
            String jsonString = EntityUtils.toString(httpEntity);

            JSONObject jsonObject = new JSONObject(jsonString);

            UserData userData = parseJsonToUser(jsonObject);

            return userData;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过userId获取受测者测试结果
     * @param userId
     * @return
     */
    public static ArrayList<TestData> getTestResultByGet(int userId) {
        try {
            HttpClient httpClient = HttpApplication.getHttpClient();
            HttpPost httpPost = new HttpPost(getTestResultUrl +"&userId="+userId);

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();
            String jsonString = EntityUtils.toString(httpEntity);

            JSONArray jsonArray = new JSONArray(jsonString);
            ArrayList<TestData> testDatas = new ArrayList<TestData>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                TestData testData = parseJsonToTest(jsonObject);
                testDatas.add(testData);
            }

            return testDatas;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上传受测者信息
     * @param userid
     * @param name
     * @param birth
     * @param gender
     */
    public static void uploadTestInfo(int userid, String name, String birth, String gender) {
        String result;
        try {
            HttpClient httpClient = HttpApplication.getHttpClient();
            HttpPost httpPost = new HttpPost(uploadTestInfoUrl+"&userId="+userid);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("testedPerson", name));
            params.add(new BasicNameValuePair("testedBirth", birth));
            params.add(new BasicNameValuePair("testedGender", gender));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();
            String jsonString = EntityUtils.toString(httpEntity);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 上传受测者测试结果
     * @param userid
     * @param score
     * @return
     */
    public static int uploadTestResult(int userid, int score) {
        int testId = -1;
        try {
            HttpClient httpClient = HttpApplication.getHttpClient();
            HttpPost httpPost = new HttpPost(uploadTestResultUrl+"&userId="+userid);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("testPoint", String.valueOf(score)));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();
            String jsonString = EntityUtils.toString(httpEntity);

            JSONObject jsonObject = new JSONObject(jsonString);

            testId = jsonObject.getInt("testId");
            Log.w("testId", String.valueOf(testId));

            return testId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return testId;
    }


    /**
     * 将Json转为PostData
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    private static PostData parseJsonToPost1(JSONObject jsonObject) throws JSONException {
        int post_id = jsonObject.getInt("post_id");
        String post_title = jsonObject.getString("post_title");
        String post_content = jsonObject.getString("post_text");
        String post_type = jsonObject.getString("post_kind");
        String post_time = jsonObject.getString("post_date");
        int comment_num = jsonObject.getInt("comment_num");
        String user_name = jsonObject.getString("owner");
        String post_image = jsonObject.getString("img_url");
        String user_image = jsonObject.getString("userImgUrl");


        PostData postData = new PostData(post_id, post_title, post_content, post_type, post_time, post_image, new UserData(user_name, user_image), comment_num);
        return postData;
    }

    /**
     * 将Json转为TestData
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    private static TestData parseJsonToTest(JSONObject jsonObject) throws JSONException {
        String owner = jsonObject.getString("owner");
        String testType = jsonObject.getString("testType");
        int testId = jsonObject.getInt("testId");
        int testPoint = jsonObject.getInt("testPoint");
        String testDate = jsonObject.getString("testDate");

        TestData testData = new TestData(testId, owner, testType, testPoint, testDate);
        return testData;
    }

    /**
     * 将Json转为UserData
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    private static UserData parseJsonToUser(JSONObject jsonObject) throws JSONException {

        int user_id = jsonObject.getInt("user_id");
        String user_name = jsonObject.getString("username");
        String user_image = jsonObject.getString("img_url");
        String user_gender = jsonObject.getString("gender");
        String user_address = jsonObject.getString("address");
        String user_desc = jsonObject.getString("description");
        String test_name = jsonObject.getString("testedPerson");
        String test_birth = jsonObject.getString("testedBirth");
        String test_gender = jsonObject.getString("testedGender");

        UserData userData = new UserData(user_name, user_image, user_gender, user_address, user_desc, test_name, test_birth, test_gender, user_id);
        return userData;
    }

    /**
     * 将Json转为ReviewData
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    private static ReviewData parseJsonToReview(JSONObject jsonObject) throws JSONException {
        int review_id = jsonObject.getInt("comment_id");
        String review_content = jsonObject.getString("comment_text");
        String review_time = jsonObject.getString("comment_date");
        int post_id = jsonObject.getInt("post_id");
        String user_name = jsonObject.getString("owner");
        String review_image = jsonObject.getString("img_url");
        String user_image = jsonObject.getString("userImgUrl");

        ReviewData reviewData = new ReviewData(new UserData(user_name, user_image), review_content, review_time, review_image);
        return reviewData;
    }
}
