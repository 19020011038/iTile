package com.example.itile.Util;

import java.io.File;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {

    //所有任务
    public static void ShowAllTaskWithOkHttp(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }


    //新建项目
    public static void newProjectWithOkHttp(String address, String name, String description, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("name",name)
                .add("description",description)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie",SharedPreferencesUtil.getCookie())
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //项目页面 所有项目
    public static void ShowAllProjectWithOkHttp(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie",SharedPreferencesUtil.getCookie())
                .build();
        client.newCall(request).enqueue(callback);
    }


    //登录
    public static void loginWithOkHttp(String address, String account, String password, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("telephone",account)
//                .add("username",account)
                .add("password",password)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
    //注册1
    public static void registerWithOkHttp1(String address, String account, String email, String password, String repassword, String telephone, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("name", account)
                .add("password1", password)
                .add("password2", repassword)
                .add("telephone", telephone)
                .add("email", email)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //注册2
    public static void registerWithOkHttp2(String address, String account, String email, String password, String repassword, String telephone, String email_num, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("name", account)
                .add("password1", password)
                .add("password2", repassword)
                .add("telephone", telephone)
                .add("email", email)
                .add("check", email_num)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie",SharedPreferencesUtil.getCookie())
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //找回密码1
    public static void findPasswordWithOkHttp1(String address, String telephone, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("telephone",telephone)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //找回密码2
    public static void findPasswordWithOkHttp2(String address, String telephone, String password, String repassword, String email_num, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("telephone", telephone)
                .add("password1", password)
                .add("password2", repassword)
                .add("check", email_num)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie",SharedPreferencesUtil.getCookie())
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //home页get头像昵称
    public static void homeNameOkHttp(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie",SharedPreferencesUtil.getCookie())
                .build();
        client.newCall(request).enqueue(callback);
    }

    //上传头像获得url   POST
    public static void userIconWithOkHttp(String address, File file, okhttp3.Callback callback)
    {
        OkHttpClient client = new OkHttpClient();
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("newavatar", file.getName(), RequestBody.create(MediaType.parse("image/jpeg"), file))
                .build();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie",SharedPreferencesUtil.getCookie())
                .post(multipartBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //找回密码1
    public static void signoutWithOkhttp(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("quit","1")
                .build();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie",SharedPreferencesUtil.getCookie())
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //修改密码post请求
    public static void ChangePasswordWithOkHttp(String address, String old_password, String new_password, String renew_password, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("password", old_password)
                .add("password1", new_password)
                .add("password2", renew_password)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie",SharedPreferencesUtil.getCookie())
                .post(body)
                .build();
//        client.newCall(request).enqueue(callback);
        Call call = client.newCall(request);
        //5.请求加入调度,重写回调方法
        call.enqueue(callback);
    }

    //修改昵称
    public static void nicknameWithOkHttp(String address, String new_nickname, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("newname",new_nickname)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie",SharedPreferencesUtil.getCookie())
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

//    personInfoWithOkhttp个人页添加好友
//修改昵称
    public static void addFriendWithOkHttp(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("make_friend", "1")
                .build();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie", SharedPreferencesUtil.getCookie())
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void addFriendByTelWithOkhttp(String address, String telephone, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("telephone", telephone)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie", SharedPreferencesUtil.getCookie())
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void addFriendByNicknameWithOkhttp(String address, String nickname, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("username", nickname)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie", SharedPreferencesUtil.getCookie())
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void addFriendByEmailWithOkhttp(String address, String email, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie", SharedPreferencesUtil.getCookie())
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
    //消息页GET
    public static void getHelper(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie",SharedPreferencesUtil.getCookie())
                .build();
        client.newCall(request).enqueue(callback);
    }
    //日历页POST
    public static void postCalendar(String address, String time, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("time",time)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie",SharedPreferencesUtil.getCookie())
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
    //新建日程post
    public static void postNewSchedule(String address, String starttime,String endtime,String description, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("starttime",starttime)
                .add("endtime",endtime)
                .add("description",description)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie",SharedPreferencesUtil.getCookie())
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
    //日程详情GET
    public static void getScheduleDetail(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie",SharedPreferencesUtil.getCookie())
                .build();
        client.newCall(request).enqueue(callback);
    }
    //日程详情post
    public static void postScheduleDetail(String address, String state, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("state",state)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie",SharedPreferencesUtil.getCookie())
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
    //日程助手页GET
    public static void getScheduleHelper(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie",SharedPreferencesUtil.getCookie())
                .build();
        client.newCall(request).enqueue(callback);
    }
    //项目助手页POST choice
    public static void postProjectHelper(String address, String choice, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("choice",choice)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie",SharedPreferencesUtil.getCookie())
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
    //项目助手页POST read
    public static void postProjectHelper2(String address, String read, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("read",read)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie",SharedPreferencesUtil.getCookie())
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
    //任务助手页POST choice
    public static void postTaskHelper(String address, String choice, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("choice",choice)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie",SharedPreferencesUtil.getCookie())
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
    //任务助手页POST read
    public static void postTaskHelper2(String address, String read, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("read",read)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie",SharedPreferencesUtil.getCookie())
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
