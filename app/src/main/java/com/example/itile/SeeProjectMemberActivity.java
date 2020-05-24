package com.example.itile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.itile.Adapter.FriendAddressAdapter;
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

public class SeeProjectMemberActivity extends AppCompatActivity {

    private RelativeLayout change_member;
    private RelativeLayout back;
    private RecyclerView recyclerView;
    private Map map;
    private FriendAddressAdapter mAdapter;
    private String project_id;

    List<Map<String, Object>> list = new ArrayList<>();
    private String ifcreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_see_project_member);

        Intent intent = getIntent();
        project_id = intent.getStringExtra("project_id");
        ifcreator = intent.getStringExtra("ifcreator");


    }

    @Override
    protected void onResume() {
        super.onResume();

        if(ifcreator =="0")
        {
            change_member.setVisibility(View.INVISIBLE);
        }

        change_member = findViewById(R.id.change_member);
        back = findViewById(R.id.back);
        recyclerView = findViewById(R.id.see_projectmember_recyclerView);

        homeNameOkHttp("http://118.190.245.170/worktile/project/"+project_id+"/members");
//        homeNameOkHttp("http://175.24.47.150:8088/worktile/friendinfo/7/");

        change_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeeProjectMemberActivity.this, ChangeProjectMemberActivity.class);
                intent.putExtra("project_id", project_id);
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
        mAdapter = new FriendAddressAdapter(SeeProjectMemberActivity.this);
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
                        Toast.makeText(SeeProjectMemberActivity.this, "网络访问失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                try {
                    list.clear();
                    JSONObject object = new JSONObject(responseData);
                    JSONArray jsonArray = object.getJSONArray("members");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String name = jsonObject1.getString("username");  //头像
                        String icon = jsonObject1.getString("avatar");
                        String id = jsonObject1.getString("id");
                        String is_address = "1";

                        map = new HashMap();

                        map.put("username", name);
                        map.put("avatar", icon);
                        map.put("friend", id);
                        map.put("is_address", is_address);

                        list.add(map);
//                        Log.i("zyr", "shortcomment:list.size1111:" + list.size());
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
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
                            Toast.makeText(SeeProjectMemberActivity.this, "服务器访问失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }//标签页
        });
    }
}
