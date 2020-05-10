package com.example.itile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.itile.Adapter.FriendAddressAdapter;
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

public class AddressActivity extends AppCompatActivity {

    private ImageView imageView;
    private ImageView back;
    private RecyclerView recyclerView;
    private Map map;
    private FriendAddressAdapter mAdapter;
    private String friend_name;
    private String friend_icon;
    private String friend_id;

    List<Map<String, Object>> list = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_address);

        imageView = findViewById(R.id.add_friend);
        back = findViewById(R.id.back);
        recyclerView = findViewById(R.id.frind_recyclerView);

        homeNameOkHttp("http://118.190.245.170/worktile/friends");
//        homeNameOkHttp("http://175.24.47.150:8088/worktile/friendinfo/7/");

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressActivity.this, FindFriendActivity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        mAdapter = new FriendAddressAdapter(AddressActivity.this);
        recyclerView.setAdapter(mAdapter);
    }

        //获得头像昵称
    public void homeNameOkHttp(String address) {
        HttpUtil.homeNameOkHttp(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                Log.i("zyr", " name : error");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddressActivity.this, "网络访问失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                try {
                    list.clear();
//                    Log.i("zyr", "data个人中心：" + responseData);
                    JSONObject object = new JSONObject(responseData);
//                    JSONObject object1 = object.getJSONObject("friends_list");
//                    friend_name = object1.getString("username");
//                    friend_icon = object1.getString("avatar");
//                    friend_id = object1.getString("friend_id");
//                    Log.i("zyr", "AddressActivity.icon_url:" + friend_icon);
                    JSONArray jsonArray = object.getJSONArray("friends_list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                        JSONObject object3 = jsonObject1.getJSONObject("fields");
//                int news_id = jsonObject1.getInt("news_id");
//                        String status = jsonObject1.getString("status");
                        Log.i("zyr",jsonArray.toString());
                        String name = jsonObject1.getString("username");  //头像
                        String icon = jsonObject1.getString("avatar");
                        String id = jsonObject1.getString("friend");
                        String is_address = "1";

                        map = new HashMap();

                        map.put("username", name);
                        map.put("avatar", icon);
                        map.put("friend", id);
                        map.put("is_address", is_address);

                        list.add(map);
                        Log.i("zyr", "shortcomment:list.size1111:" + list.size());
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            recyclerView.setLayoutManager(new LinearLayoutManager(MyShortCommentsActivity.this));//纵向
////                            recyclerView.setAdapter(new MyShortCommentsAdapter(MyShortCommentsActivity.this, list));
                            mAdapter.setData(list);
                            mAdapter.notifyDataSetChanged();
//                            recyclerView.setNestedScrollingEnabled(false);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("zyr", "LLL" + responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddressActivity.this, "服务器访问失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }//标签页
        });
    }
}