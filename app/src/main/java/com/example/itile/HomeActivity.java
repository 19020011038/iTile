package com.example.itile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.itile.Adapter.HomeAdapter;
import com.example.itile.Util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private RecyclerView recyclerView;
    private Map map;
    private HomeAdapter mAdapter;

    List<Map<String, Object>> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerView = findViewById(R.id.myhome_recyclerview);

        home_icon = findViewById(R.id.home_icon);
        home_nickname = findViewById(R.id.home_nickname);
        home_setting = findViewById(R.id.home_setting);
        home_telephone = findViewById(R.id.home_telephone);
        home_email = findViewById(R.id.home_email);
        //获取用户名
        //username = check.getAccountId();
        nameAddress = "http://118.190.245.170/worktile/center/";
//        nameAddress = "http://175.24.47.150:8088/worktile/center/";

        homeNameOkHttp(nameAddress);

        home_setting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        mAdapter = new HomeAdapter(HomeActivity.this);
        recyclerView.setAdapter(mAdapter);
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
                    list.clear();
                    Log.i("zyr","data个人中心："+responseData);
                    JSONObject object = new JSONObject(responseData);
                    JSONObject object1 = object.getJSONObject("user");
                    nickname = object1.getString("user_name");
                    icon = object1.getString("avatar");
                    telephone = object1.getString("telephone");
                    email = object1.getString("email");
                    Log.i("zyr", "HomeActivity.icon_url:"+icon);
                    JSONArray jsonArray = object.getJSONArray("schedule");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject object3 = jsonObject1.getJSONObject("fields");
//                int news_id = jsonObject1.getInt("news_id");
//                        String status = jsonObject1.getString("status");
                        String time1 = object3.getString("starttime");  //头像
                        String time2 = object3.getString("endtime");
                        String d = object3.getString("description");
                        String state = object3.getString("state");
//                        String book_name = jsonObject1.getString("title");
//                        String book_photo = jsonObject1.getString("image");
//                        String comment_id = jsonObject1.getString("id");
                        map = new HashMap();

//                        map.put("status", status);
                        map.put("time1", time1);
                        map.put("time2", time2);
                        map.put("d", d);
                        map.put("state", state);
//                        map.put("book_num", book_num);
//                        map.put("book_photo", book_photo);
//                        map.put("comment_id", comment_id);

                        list.add(map);
                        Log.i("zyr", "shortcomment:list.size1111:"+list.size());
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            home_nickname.setText(nickname);
                            home_telephone.setText(telephone);
                            home_email.setText(email);
//                            Glide.with(HomeActivity.this).load("http://175.24.47.150:8088/worktile/static/"+icon).into(home_icon);
                            Glide.with(HomeActivity.this).load("http://118.190.245.170/worktile/static/"+icon).into(home_icon);
                            Log.i("zyr", "shortcomment:list.size:"+list.size());
//                            recyclerView.setLayoutManager(new LinearLayoutManager(MyShortCommentsActivity.this));//纵向
////                            recyclerView.setAdapter(new MyShortCommentsAdapter(MyShortCommentsActivity.this, list));
                            mAdapter.setData(list);
                            mAdapter.notifyDataSetChanged();
//                            recyclerView.setNestedScrollingEnabled(false);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i( "zyr", "LLL"+responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        Toast.makeText(HomeActivity.this,"服务器访问失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }//标签页
        });
    }
}
