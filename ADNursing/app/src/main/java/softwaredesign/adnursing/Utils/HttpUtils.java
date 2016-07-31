package softwaredesign.adnursing.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import softwaredesign.adnursing.Data.PostData;
import softwaredesign.adnursing.Data.ReviewData;
import softwaredesign.adnursing.Data.TestData;
import softwaredesign.adnursing.Data.UserData;
import softwaredesign.adnursing.HttpApplication;
import softwaredesign.adnursing.R;

/**
 * Created by huacan liang on 2016/7/20.
 */
public class HttpUtils {

    private static final int TIMEOUT = 6000;

    private static String ip = "http://120.76.115.235:8080";
//    private static String ip = "http://172.18.42.155:8080";
//    private static String ip = "http://192.168.191.6:8080";
//    private static String ip = "http://192.168.253.2:8080";
    private static String loginUrl = ip + "/ADNursingServer/android/login.jsp?mode=android";
    private static String RegisterUrl = ip + "/ADNursingServer/android/register.jsp?mode=android";
    private static String downImageUrl = ip + "/ADNursingServer/servlet/DownloadFileServlet";
    private static String getPostWithTypeTimeOrderUrl = ip + "/ADNursingServer/servlet/ViewPostByKindServlet?mode=android";
    private static String getPostWithTypeReviewOrderUrl = ip + "/ADNursingServer/servlet/ViewPostsByKindAndCommentNum?mode=android";
    private static String getPostWithPostIdUrl = ip + "/ADNursingServer/servlet/ViewOnePostServlet?mode=android";
    private static String getPostWithUserIdUrl = ip + "/ADNursingServer/servlet/ViewOwnerPostServlet?mode=android";
    private static String getPostNumdUrl = ip + "/ADNursingServer/servlet/ViewKindNumServlet?mode=android";
    private static String getReviewWithPostIdUrl = ip + "/ADNursingServer/servlet/ViewPostCommentServlet?mode=android";
    private static String uploadPostUrl = ip + "/ADNursingServer/android/addPost.jsp?mode=android";
    private static String uploadPostImageUrl = ip + "/ADNursingServer/servlet/AddPostImgUrlServlet?mode=android";
    private static String uploadReviewUrl = ip + "/ADNursingServer/servlet/AddCommentInforServlet?mode=android";
    private static String uploadReviewImageUrl = ip + "/ADNursingServer/servlet/AddCommentImgUrlServlet?mode=android";
    private static String getUserInfoWithIdUrl = ip + "/ADNursingServer/servlet/ViewUserServlet?mode=android";
    private static String getTestResultUrl = ip + "/ADNursingServer/servlet/ViewOwnerTestServlet?mode=android";
    private static String uploadUserInfoUrl = ip + "/ADNursingServer/android/updateUserInfor.jsp?mode=android";
    private static String uploadUserImageUrl = ip + "/ADNursingServer/android/updateUserImgUrl.jsp?mode=android";
    private static String uploadTestInfoUrl = ip + "/ADNursingServer/android/updateTestInfor.jsp?mode=android";
    private static String uploadTestResultUrl = ip + "/ADNursingServer/android/addTest.jsp?mode=android";
    private static String deletePostUrl = ip + "/ADNursingServer/servlet/DelPostServlet?mode=android";
    private static String collectPostUrl = ip + "/ADNursingServer/android/addFavorites.jsp?mode=android";
    private static String cancelCollectedPostUrl = ip + "/ADNursingServer/servlet/DelFavoriteServlet?mode=android";
    private static String checkPostIfCollectedUrl = ip + "/ADNursingServer/servlet/IsFavoriteServlet?mode=android";
    private static String getCollectedPostUrl = ip + "/ADNursingServer/servlet/ViewOwnerFavoritesServlet?mode=android";


    private static String sendPostRequest(String url, Map<String, String> params) {

        String jsonString = "";
        HttpClient httpClient = HttpApplication.getHttpClient();
        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> valuePairs = new ArrayList<>();

        if (params.isEmpty()) {
            System.out.println("Attention Empty http params!");
        } else {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                valuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }

        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(valuePairs, "utf-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            jsonString = EntityUtils.toString(httpEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonString;
    }


    private static String sendGetRequest(String url, Map<String, String> params) {


        StringBuilder sb = new StringBuilder(url);

        if (params.isEmpty()) {
            System.out.println("Attention Empty http params!");
        } else {
            sb.append('?');
            for (Map.Entry<String, String> entry : params.entrySet()) {
                try {
                    sb.append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(), "utf-8")).append('&');
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            sb.deleteCharAt(sb.length() - 1);
        }

        String jsonString = "";
        HttpClient httpClient = HttpApplication.getHttpClient();
        HttpGet httpGet = new HttpGet(sb.toString());

        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            jsonString = EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonString;
    }


    public static Bitmap loadImage(String dir) {
        if(dir.indexOf("|") > 0) {
            dir = dir.substring(0, dir.indexOf("|"));
        }

        URL myUrl = null;
        Bitmap bitmap = null;

        String name = dir.substring(dir.lastIndexOf("\\")+1);
        String url = downImageUrl + "?filename=" + name + "&url=" + dir;

        try {
            myUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = null;
            if (myUrl != null) {
                conn = (HttpURLConnection) myUrl.openConnection();
                conn.setConnectTimeout(TIMEOUT);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.89 Safari/537.36");
                conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                conn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
                conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
                conn.setRequestProperty("Host", ip.substring(ip.lastIndexOf("//")+2));
                conn.setRequestProperty("Connection", "keep-alive");
                conn.setRequestProperty("HTTPS", "1");
                conn.setRequestMethod("GET");
                conn.connect();
                InputStream is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                is.close();
            } else {
                System.out.println("ERROR connection is null!");
            }

            return bitmap;

        } catch (IOException e) {
            e.printStackTrace();
            return BitmapFactory.decodeResource(HttpApplication.getContext().getResources(), R.mipmap.image_default);
        }
    }


    private static String uploadImage(String url, ArrayList<File> file) {
        ArrayList<String> srcPath = new ArrayList<>();
        for (int i = 0; i < file.size(); i++) {
            srcPath.add(file.get(i).getPath());
        }
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "----WebKitFormBoundarydFEfsdHF";
        String result = "";

        try {
            URL myUrl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) myUrl.openConnection();
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

            result = br.readLine();

            dos.close();
            is.close();

        } catch (Exception e) {
            Log.e("dialog", "上传失败" + e);
            e.printStackTrace();
        }

        return result;
    }


    public static int loginByPost(String username, String password) {
        int userId = -1;
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);

        String jsonString = sendPostRequest(loginUrl, map);

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            userId = jsonObject.getInt("userId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userId;
    }


    public static int registerByPost(String username, String password) {
        int userId = -1;
        Map<String, String> map = new HashMap<>();
        map.put("userName", username);
        map.put("userPassword", password);

        String jsonString = sendPostRequest(RegisterUrl, map);

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            userId = jsonObject.getInt("userId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userId;
    }


    public static HashMap<String, Object> getPostNum() {
        HashMap<String, Object> numMap = new HashMap<>();
        Map<String, String> map = new HashMap<>();

        String jsonString = sendPostRequest(getPostNumdUrl, map);

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            numMap.put("BZYW", jsonObject.getInt("BZYW"));
            numMap.put("MYTJ", jsonObject.getInt("MYTJ"));
            numMap.put("YWTJ", jsonObject.getInt("YWTJ"));
            numMap.put("JLFX", jsonObject.getInt("JLFX"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return numMap;
    }


    public static PostData getPostWithId(int postId) {
        PostData postData = null;
        Map<String, String> map = new HashMap<>();
        map.put("postId", String.valueOf(postId));

        String jsonString = sendPostRequest(getPostWithPostIdUrl, map);

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            postData = parseJsonToPost(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postData;
    }


    public static ArrayList<PostData> getPostWithUserId(int userId) {
        ArrayList<PostData> postDatas = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(userId));

        String jsonString = sendPostRequest(getPostWithUserIdUrl, map);

        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                PostData postData = parseJsonToPost(jsonObject);
                postDatas.add(postData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postDatas;
    }


    public static int deletePostUrl(int userId, int postId) {
        int result = -1;
        Map<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(userId));
        map.put("postId", String.valueOf(postId));

        String jsonString = sendPostRequest(deletePostUrl, map);

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            result = jsonObject.getInt("postId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }


    public static ArrayList<PostData> getPostsWithType(String type, String order) {
        ArrayList<PostData> postDatas = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("postKind", type);

        String jsonString = "";
        if (order.equals("newest")) {
            jsonString = sendPostRequest(getPostWithTypeTimeOrderUrl, map);
        } else if (order.equals("hottest")) {
            jsonString = sendPostRequest(getPostWithTypeReviewOrderUrl, map);
        } else {
            System.out.println("ERROR wrong order!");
            return null;
        }

        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                PostData postData = parseJsonToPost(jsonObject);
                postDatas.add(postData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postDatas;
    }


    public static int collectPost(int userId, int postId) {
        int result = -1;
        Map<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(userId));
        map.put("postId", String.valueOf(postId));

        String jsonString = sendPostRequest(collectPostUrl, map);

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            result = jsonObject.getInt("userId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static int cancelCollectPost(int userId, int postId) {
        int result = -1;
        Map<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(userId));
        map.put("postId", String.valueOf(postId));

        String jsonString = sendPostRequest(cancelCollectedPostUrl, map);

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            result = jsonObject.getInt("userId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static int checkPostIfCollected(int userId, int postId) {
        int result = 0;
        Map<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(userId));
        map.put("postId", String.valueOf(postId));

        String jsonString = sendPostRequest(checkPostIfCollectedUrl, map);

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            result = jsonObject.getInt("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }


    public static ArrayList<PostData> getCollectedPost(int userId) {
        ArrayList<PostData> postDatas = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(userId));

        String jsonString = sendPostRequest(getCollectedPostUrl, map);

        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                PostData postData = parseJsonToPost(jsonObject);
                postDatas.add(postData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postDatas;
    }


    public static ArrayList<ReviewData> getReciewsWithPostId(int postId) {
        ArrayList<ReviewData> reviewDatas = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("postId", String.valueOf(postId));

        String jsonString = sendPostRequest(getReviewWithPostIdUrl, map);

        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ReviewData reviewData = parseJsonToReview(jsonObject);
                reviewDatas.add(reviewData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviewDatas;
    }


    public static int uploadPostText(int userId, String title, String content, String type) {
        int postId = -1;
        Map<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(userId));
        map.put("postTitle", title);
        map.put("postText", content);
        map.put("postKind", type);

        String jsonString = sendPostRequest(uploadPostUrl, map);

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            postId = jsonObject.getInt("postId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postId;
    }


    public static int uploadReviewText(int userId, int postId, String content) {
        int reviewId = -1;
        Map<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(userId));
        map.put("postId", String.valueOf(postId));
        map.put("commentText", content);

        String jsonString = sendPostRequest(uploadReviewUrl, map);

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            reviewId = jsonObject.getInt("commentId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviewId;
    }


    public static String uploadPostImages(ArrayList<File> file, int postId) {
        return uploadImage(uploadPostImageUrl+"&postId="+String.valueOf(postId), file);
    }


    public static String uploadReviewImages(ArrayList<File> file, int reviewId) {
        return uploadImage(uploadReviewImageUrl+"&commentId="+String.valueOf(reviewId), file);
    }


    public static UserData getUserInfoWithId(int userId) {
        UserData userData = null;
        Map<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(userId));

        String jsonString = sendPostRequest(getUserInfoWithIdUrl, map);

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            userData = parseJsonToUser(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userData;
    }


    public static ArrayList<TestData> getTestResultsWithId (int userId) {
        ArrayList<TestData> testDatas = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(userId));

        String jsonString = sendPostRequest(getTestResultUrl, map);

        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                TestData testData = parseJsonToTest(jsonObject);
                testDatas.add(testData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return testDatas;
    }


    public static int uploadUserInfo(int userId, String gender, String address, String description) {
        int result = -1;
        Map<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(userId));
        map.put("gender", gender);
        map.put("address", address);
        map.put("description", description);

        String jsonString = sendPostRequest(uploadUserInfoUrl, map);
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            result = jsonObject.getInt("userId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }


    public static int uploadTestInfo(int userId, String name, String birth, String gender) {
        int result = -1;
        Map<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(userId));
        map.put("testedPerson", name);
        map.put("testedBirth", birth);
        map.put("testedGender", gender);

        String jsonString = sendPostRequest(uploadTestInfoUrl, map);
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            result = jsonObject.getInt("userId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }


    public static int uploadTestResult(int userId, int score) {
        int testId = -1;
        Map<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(userId));
        map.put("testPoint", String.valueOf(score));

        String jsonString = sendPostRequest(uploadTestResultUrl, map);
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            testId = jsonObject.getInt("testId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return testId;
    }


    public static String uploadUserImage(File file, int userId) {
        ArrayList<File> oneFile = new ArrayList<>();
        oneFile.add(file);
        return uploadImage(uploadUserImageUrl+"&userId="+String.valueOf(userId), oneFile);
    }


    private static PostData parseJsonToPost(JSONObject jsonObject) throws JSONException {
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


    private static TestData parseJsonToTest(JSONObject jsonObject) throws JSONException {
        String owner = jsonObject.getString("owner");
        String testType = jsonObject.getString("testType");
        int testId = jsonObject.getInt("testId");
        int testPoint = jsonObject.getInt("testPoint");
        String testDate = jsonObject.getString("testDate");

        TestData testData = new TestData(testId, owner, testType, testPoint, testDate);
        return testData;
    }

}
