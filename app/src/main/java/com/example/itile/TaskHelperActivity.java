package com.example.itile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itile.Adapter.ProjectHelperAdapter;
import com.example.itile.Adapter.ScheduleHelperAdapter;
import com.example.itile.Adapter.TaskHelperAdapter;
import com.example.itile.Util.HttpUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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

public class TaskHelperActivity extends AppCompatActivity {
    private RelativeLayout back;
    private ImageView pic1;
    private ImageView pic2;
    private ImageView all;
    private RecyclerView recyclerView;
    List<Map<String, Object>> list = new ArrayList<>();
    private boolean flag;          //判断选择的是未读还是已读，未读是true
    private int page;
    private TaskHelperAdapter mAdapter;
    private RefreshLayout refreshLayout;
    private boolean flag2;


    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_task_helper);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.hide();
        page = 1;
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout_task_helper);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_task_helper);
        LinearLayoutManager manager = new LinearLayoutManager(TaskHelperActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        mAdapter = new TaskHelperAdapter(TaskHelperActivity.this, list);
        recyclerView.setAdapter(mAdapter);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pic1 = (ImageView)findViewById(R.id.t_weidu);
        pic2 = (ImageView)findViewById(R.id.t_yidu);
        all = (ImageView)findViewById(R.id.task_helper_all);


        pic1.setImageResource(R.drawable.weidu1);
        pic1.invalidate();
        pic2.setImageResource(R.drawable.yidu0);
        pic2.invalidate();
        flag = true;
        postTaskHelper("http://118.190.245.170/worktile/taskhelper/","0",String.valueOf(page));
    }
    @Override
    protected void onResume(){
        super.onResume();


        pic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag){
                    list.clear();
                    flag = true;
                    page = 1;
                    pic1.setImageResource(R.drawable.weidu1);
                    pic1.invalidate();
                    pic2.setImageResource(R.drawable.yidu0);
                    pic2.invalidate();
                    postTaskHelper("http://118.190.245.170/worktile/taskhelper/","0",String.valueOf(page));
                }
            }
        });
        pic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    list.clear();
                    flag = false;
                    page = 1;
                    pic1.setImageResource(R.drawable.weidu0);
                    pic1.invalidate();
                    pic2.setImageResource(R.drawable.yidu1);
                    pic2.invalidate();
                    postTaskHelper("http://118.190.245.170/worktile/taskhelper/","1",String.valueOf(page));
                }
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postTaskHelper2("http://118.190.245.170/worktile/taskhelper/","1");
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                String choice;
                if(flag)
                    choice = "0";
                else
                    choice = "1";
                postTaskHelper("http://118.190.245.170/worktile/taskhelper/",choice,String.valueOf(page));
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                String choice;
                if(flag)
                    choice = "0";
                else
                    choice = "1";
                refreshLayout.finishRefresh(2000);
                page = 1;
                list.clear();
                postTaskHelper("http://118.190.245.170/worktile/taskhelper/",choice,String.valueOf(page));
            }
        });
    }
    public void postTaskHelper(String address,String choice,String page) {
        HttpUtil.postTaskHelper(address, choice,page,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TaskHelperActivity.this, "网络出现了问题...", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("任务助手页post", responseData);
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    String warning = jsonObject.getString("warning");
                    if(!warning.equals("1")){
                        flag2 = false;
                    }else {
                        flag2 = true;
                        JSONArray jsonArray = jsonObject.getJSONArray("task");
                        if(!String.valueOf(jsonArray).equals("[]")){
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject schedule = jsonArray.getJSONObject(i);
                                String pk = schedule.getString("pk");
                                JSONObject fields = schedule.getJSONObject("fields");
                                String name = fields.getString("name");
                                String p = fields.getString("project");

                                Map map = new HashMap();
                                map.put("pk", pk);
                                map.put("name", name);
                                map.put("type",1);
                                map.put("project",p);

                                list.add(map);
                            }
                        }else {
                            Map map1 = new HashMap();
                            map1.put("type",2);
                            list.add(map1);
                            Log.d("hahahahahhaha",String.valueOf(jsonArray));
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (flag2) {
                                refreshLayout.finishLoadMore(3000);
                                refreshLayout.setFooterHeight(200);
                                mAdapter.setData(list);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(TaskHelperActivity.this, warning, Toast.LENGTH_SHORT).show();
                                refreshLayout.finishLoadMore();
                            }
                        }
                    });
                } catch (JSONException e) {

                    e.printStackTrace();

                }
            }
        });
    }
    public void postTaskHelper2(String address,String read) {
        HttpUtil.postTaskHelper2(address, read,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TaskHelperActivity.this, "网络出现了问题...", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("任务助手页post2", responseData);
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    String warning = jsonObject.getString("warning");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(warning.equals("1")){
                                if(flag){
                                    list.clear();
                                    page = 1;
                                    Toast.makeText(TaskHelperActivity.this,"操作成功！",Toast.LENGTH_SHORT).show();
                                    postTaskHelper("http://118.190.245.170/worktile/taskhelper/","0",String.valueOf(page));
                                }else {
                                    Toast.makeText(TaskHelperActivity.this,"操作成功，请刷新后查看！",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(TaskHelperActivity.this,"操作失败！",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (JSONException e) {

                    e.printStackTrace();

                }
            }
        });
    }
}
