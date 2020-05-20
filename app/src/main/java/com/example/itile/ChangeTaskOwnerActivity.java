package com.example.itile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.itile.Adapter.ChangeMemberAdapter;
import com.example.itile.Adapter.ChangeTaskOwnerAdapter;
import com.example.itile.Util.HttpUtil;
import com.google.gson.Gson;

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

public class ChangeTaskOwnerActivity extends AppCompatActivity {

    private RelativeLayout back;
    private RelativeLayout finish;
    private RecyclerView recyclerView;
    private ChangeTaskOwnerAdapter mAdapter;
    private String project_id;
    private String task_id;
    private String owner_id;
    private Map map;
    List<Map<String, Object>> list = new ArrayList<>();
    //解决holder复用问题
    private List<Integer> favorList=new ArrayList<>(); //用法：favorList.add(123);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_change_task_owner);

        back = findViewById(R.id.back);
        finish = findViewById(R.id.finish);
        recyclerView = findViewById(R.id.recyclerView);

        Intent intent = getIntent();
        project_id = intent.getStringExtra("project_id");
        task_id = intent.getStringExtra("task_id");
        owner_id = intent.getStringExtra("owner_id");
        Log.i("zyr", "收到的："+owner_id);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        mAdapter = new ChangeTaskOwnerAdapter(ChangeTaskOwnerActivity.this);
        recyclerView.setAdapter(mAdapter);

        getProjectMemberWithOkHttp("http://118.190.245.170/worktile/project/"+project_id+"/task/"+task_id+"/manager");

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String choose = mAdapter.getChoose();
                changeMemberWithOkHttp(choose, "http://118.190.245.170/worktile/project/"+project_id+"/task/"+task_id+"/manager");
            }
        });
    }

    //获得头像昵称
    public void getProjectMemberWithOkHttp(String address) {
        HttpUtil.homeNameOkHttp(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                Log.i("zyr", " name : error");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChangeTaskOwnerActivity.this, "网络访问失败", Toast.LENGTH_SHORT).show();
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
                    JSONArray jsonArray = object.getJSONArray("friends_list");
                    favorList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String name = jsonObject1.getString("username");  //头像
                        String icon = jsonObject1.getString("avatar");
                        String id = jsonObject1.getString("friend");

                        map = new HashMap();

                        map.put("name", name);
                        map.put("icon", icon);
                        map.put("id", id);

                        list.add(map);

//                        if (list.size()==1)
//                            favorList.add(Integer.valueOf(id));
                        if (owner_id.equals(id))
                            favorList.add(Integer.valueOf(id));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.setData(list, favorList);
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
                            Toast.makeText(ChangeTaskOwnerActivity.this, "服务器访问失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }//标签页
        });
    }

    public void changeMemberWithOkHttp(final String list, String url){
        HttpUtil.changeTaskOwnerWithOkHttp( url,list, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                Log.i("zyr", " name : error");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChangeTaskOwnerActivity.this, "网络出现了问题...", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                try {
                    JSONObject object = new JSONObject(responseData);
                    String result = object.getString("warning");
//                    favorList.clear();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("1")){

                                Toast.makeText(ChangeTaskOwnerActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(ChangeTaskOwnerActivity.this, "修改失败，请稍后重试", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("zyr", "LLL" + responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChangeTaskOwnerActivity.this, "无法连接到服务器", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }//标签页
        });

    }
}
