package com.example.itile.Util;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {

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
}
