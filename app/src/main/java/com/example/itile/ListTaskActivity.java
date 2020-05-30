package com.example.itile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itile.Adapter.TaskInProjectAdapter;
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

public class ListTaskActivity extends AppCompatActivity {

    private String project_id;
    private String task_id;
    private String name;
    private String state;
    private TextView new_task;
    private List<Map<String, Object>> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private RelativeLayout back;
    private int flag = 1;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_task_in_project);

        Intent intent = getIntent();
        project_id = intent.getStringExtra("project_id");


        recyclerView =findViewById(R.id.recyclerView);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new_task = findViewById(R.id.new_task);
        new_task.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ListTaskActivity.this,NewTaskActivity.class);
                intent.putExtra("project_id",project_id);
                startActivity(intent);


            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();

        list.clear();

        TaskInProjectWithOkHttp("http://118.190.245.170/worktile/project/"+project_id+"/all-tasks");

    }

    public void TaskInProjectWithOkHttp(String address) {
        HttpUtil.TaskInProjectWithOkHttp(address, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ListTaskActivity.this, "网络出现了问题...", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONArray jsonArray = jsonObject.getJSONArray("tasks");

                    if (jsonArray.length()==0)
                    {

                        flag = 0;

                    }
                    else flag=1;

                    if (flag!=0)
                    {
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            name = jsonObject1.getString("name");
                            task_id = jsonObject1.getString("id");
                            state = jsonObject1.getString("state");

                            Map map = new HashMap();
                            map.put("state", state);
                            map.put("name", name);
                            map.put("task_id",task_id);
                            map.put("project_id",project_id);
                            map.put("flag",1);
                            list.add(map);
                        }
                    }

                    else
                    {
                        Map map1 = new HashMap();
                        map1.put("flag",0);
                        list.add(map1);
                    }




                    runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                recyclerView.setLayoutManager(new LinearLayoutManager(ListTaskActivity.this));//垂直排列 , Ctrl+P
                                recyclerView.setAdapter(new TaskInProjectAdapter(ListTaskActivity.this, list,flag));//绑定适配器


                            }
                        });
//

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

