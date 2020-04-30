package com.example.itile.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    private static final String TAG = "TAG";
    private static final String KET_LOGIN = "KEY_LOGIN";
    private static final String KEY_PICTURE = "KEY_PICTURE";
    private static final String KET_COOKIE = "KET_COOKIE";
    private static SharedPreferences mPreferences;
    private static SharedPreferences.Editor mEditor;
    private static SharedPreferencesUtil mSharedPreferencesUtil;
    private final Context contex;
    private String accountId;

    public SharedPreferencesUtil(Context context) {
        this.contex = context.getApplicationContext();
        mPreferences = this.contex.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public static SharedPreferencesUtil getInstance(Context context) {
        if (mSharedPreferencesUtil == null) {
            mSharedPreferencesUtil = new SharedPreferencesUtil(context);
        }
        return mSharedPreferencesUtil;
    }

    public boolean isLogin() {
        return getBoolean(KET_LOGIN, false);//检查登录状态true或false
    }

    public void setLogin(boolean value) {
        putBoolean(KET_LOGIN, value); //设置记住登录状态
    }

    public boolean isPicture() { return getBoolean(KEY_PICTURE, false); }

    public void setPicture(boolean value) {
        putBoolean(KEY_PICTURE, value);
    }

    private void put(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    private void putBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    private String get(String key) {
        return mPreferences.getString(key, "");
    }

    private boolean getBoolean(String key, boolean defaultValue) {
        return mPreferences.getBoolean(key, defaultValue);
    }
    //做了静态的getBoolean方法，用在静态的isCookie中
    private static boolean getBoolean_static(String key, boolean defaultValue) {
        return mPreferences.getBoolean(key, defaultValue);
    }

    public void setAccountId(String accountId) {
        mEditor.putString("accountId", accountId);
        mEditor.apply();
    }

    public String getAccountId() {
        return mPreferences.getString("accountId", "");
    }

    //存cookie
    public void saveCookie(String cookieId) {
        mEditor.putString("cookieId", cookieId);
        mEditor.apply();
    }
    //获取cookie
    public static String getCookie() {
        return mPreferences.getString("cookieId", "");
    }
    //判断是否有cookie
    public static boolean isCookie() {
        return getBoolean_static(KET_COOKIE, false);
    }
    //设置获得、未获得cookie
    public void setCookie(boolean value) {
        putBoolean(KET_COOKIE, value);
    }


}