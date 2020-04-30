package com.example.itile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.itile.Util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {

    private ImageView home_icon;
    private TextView home_nickname;
    private TextView home_telephone;
    private TextView home_email;
    private String nameAddress;
    private ImageView home_setting;
    private String nickname;
    private String icon;
    private String telephone;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

    }

    @Override
    protected void onResume() {
        super.onResume();

        home_icon = findViewById(R.id.home_icon);
        home_nickname = findViewById(R.id.home_nickname);
        home_setting = findViewById(R.id.home_setting);
        home_telephone = findViewById(R.id.home_telephone);
        home_email = findViewById(R.id.home_email);
        //获取用户名
        //username = check.getAccountId();
        nameAddress = "http://118.190.245.170/worktile/userinfo";
        homeNameOkHttp(nameAddress);

    }

    //获得头像昵称
    public void homeNameOkHttp(String address){
        HttpUtil.homeNameOkHttp(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                Log.i( "zyr", " name : error");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                try{
                    JSONObject object = new JSONObject(responseData);
                    JSONObject object1 = object.getJSONObject("user");
                    nickname = object1.getString("username");
                    icon = object1.getString("avatar");
                    telephone = object1.getString("telephone");
                    email = object1.getString("email");
                    Log.i("zyr", "HomeActivity.icon_url:"+icon);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i( "zyr", "LLL"+responseData);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        home_nickname.setText(nickname);
                        home_telephone.setText(telephone);
                        home_email.setText(email);
                        Glide.with(HomeActivity.this).load(icon).into(home_icon);
//                        Toast.makeText(HomeActivity.this,"显示头像",Toast.LENGTH_SHORT).show();
                    }
                });
            }//标签页
        });
    }
}
