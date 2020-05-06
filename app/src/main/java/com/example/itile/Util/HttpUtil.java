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

    //work
    public static void getWork(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie","sessionid=hmrb0q53hg3oze4qjivao42ywo6y3ijw;")
                .build();
        client.newCall(request).enqueue(callback);
    }

    //test
    public static void getTest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //子任务页面详情
    public static void getTask(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie","sessionid=hmrb0q53hg3oze4qjivao42ywo6y3ijw;")
                .build();
        client.newCall(request).enqueue(callback);
    }

    //任务详情
    public static void getProject(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie","sessionid=hmrb0q53hg3oze4qjivao42ywo6y3ijw;")
                .build();
        client.newCall(request).enqueue(callback);
    }

    //项目页面详情
    public static void jiekou4(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie","sessionid=hmrb0q53hg3oze4qjivao42ywo6y3ijw;")
                .build();
        client.newCall(request).enqueue(callback);
    }

    //新建项目页面
    public static void jiekou5(String address, String name, String description, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("name", name)
                .add("description", description)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie","sessionid=hmrb0q53hg3oze4qjivao42ywo6y3ijw;")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //新建任务
    public static void jiekou6(String address, String name, String description,String starttime,String endtime, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("name", name)
                .add("description", description)
                .add("starttime",starttime)
                .add("endtime",endtime)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie","sessionid=hmrb0q53hg3oze4qjivao42ywo6y3ijw;")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //统计报表
    public static void jiekou8(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie","sessionid=hmrb0q53hg3oze4qjivao42ywo6y3ijw;")
                .build();
        client.newCall(request).enqueue(callback);
    }
    //我的任务
    public static void jiekou9(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie","sessionid=hmrb0q53hg3oze4qjivao42ywo6y3ijw;")
                .build();
        client.newCall(request).enqueue(callback);
    }
    //修改项目描述
    public static void jiekou10(String address, String description, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("description", description)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie","sessionid=hmrb0q53hg3oze4qjivao42ywo6y3ijw;")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
    //修改任务负责人
    public static void jiekou13(String address, String manager_id, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("manager_id", manager_id)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie","sessionid=hmrb0q53hg3oze4qjivao42ywo6y3ijw;")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //修改项目状态
    public static void jiekou15(String address, String state, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("state", state)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie","sessionid=hmrb0q53hg3oze4qjivao42ywo6y3ijw;")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
    //查看项目成员
    public static void jiekou18(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie","sessionid=hmrb0q53hg3oze4qjivao42ywo6y3ijw;")
                .build();
        client.newCall(request).enqueue(callback);
    }
    //404
    public static void jiekou404(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .header("Cookie","sessionid=hmrb0q53hg3oze4qjivao42ywo6y3ijw;")
                .build();
        client.newCall(request).enqueue(callback);
    }
}
