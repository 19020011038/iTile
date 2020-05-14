package com.example.itile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itile.Adapter.ProjectHelperAdapter;
import com.example.itile.Adapter.ScheduleHelperAdapter;
import com.example.itile.Adapter.TaskHelperAdapter;
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

public class TaskHelperActivity extends AppCompatActivity {
    private ImageView back;
    private ImageView pic1;
    private ImageView pic2;
    private ImageView all;
    private RecyclerView recyclerView;
    List<Map<String, Object>> list = new ArrayList<>();
    private boolean flag;          //判断选择的是未读还是已读，已读是true


    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_task_helper);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.hide();
        back = (ImageView)findViewById(R.id.back_from_task_helper);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pic1 = (ImageView)findViewById(R.id.t_weidu);
        pic2 = (ImageView)findViewById(R.id.t_yidu);
        all = (ImageView)findViewById(R.id.task_helper_all);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_task_helper);

        pic1.setImageResource(R.drawable.weidu1);
        pic1.invalidate();
        pic2.setImageResource(R.drawable.yidu0);
        pic2.invalidate();
        flag = true;
    }
    @Override
    protected void onResume(){
        super.onResume();

        postTaskHelper("http://118.190.245.170/worktile/taskhelper/","0");
        pic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                flag = true;
                pic1.setImageResource(R.drawable.weidu1);
                pic1.invalidate();
                pic2.setImageResource(R.drawable.yidu0);
                pic2.invalidate();
                postTaskHelper("http://118.190.245.170/worktile/taskhelper/","0");
            }
        });
        pic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                flag = false;
                pic1.setImageResource(R.drawable.weidu0);
                pic1.invalidate();
                pic2.setImageResource(R.drawable.yidu1);
                pic2.invalidate();
                postTaskHelper("http://118.190.245.170/worktile/taskhelper/","1");
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                postTaskHelper2("http://118.190.245.170/worktile/projecthelper/","1");
            }
        });
    }
    public void postTaskHelper(String address,String choice) {
        HttpUtil.postTaskHelper(address, choice,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("任务助手页post", responseData);
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONArray jsonArray = jsonObject.getJSONArray("task");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject schedule = jsonArray.getJSONObject(i);
                        String pk = schedule.getString("pk");
                        JSONObject fields = schedule.getJSONObject("fields");
                        String name = fields.getString("name");

                        Map map = new HashMap();
                        map.put("pk", pk);
                        map.put("name", name);

                        list.add(map);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setLayoutManager(new LinearLayoutManager(TaskHelperActivity.this));//垂直排列 , Ctrl+P
                            recyclerView.setAdapter(new TaskHelperAdapter(TaskHelperActivity.this, list));//绑定适配器
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
                                    postTaskHelper("http://118.190.245.170/worktile/taskhelper/","0");
                                }else {
                                    list.clear();
                                    postTaskHelper("http://118.190.245.170/worktile/taskhelper/","1");
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
