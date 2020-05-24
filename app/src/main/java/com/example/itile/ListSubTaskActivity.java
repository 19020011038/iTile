package com.example.itile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itile.Adapter.SubTasklistAdapter;
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

public class ListSubTaskActivity extends AppCompatActivity {

    private String name;
    private String state;
    private TextView new_task;
    private String task_id;
    private String project_id;
    private String subtask_id;
    private List<Map<String, Object>> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageView back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_subtask_in_task);

        Intent intent = getIntent();
        task_id = intent.getStringExtra("task_id");
        project_id = intent.getStringExtra("project_id");
        new_task = findViewById(R.id.new_task);
        recyclerView =findViewById(R.id.recyclerView);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        new_task.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ListSubTaskActivity.this,NewSubTaskActivity.class);
//                intent.putExtra("project_id",project_id);
                intent.putExtra("task_id",task_id);
                intent.putExtra("project_id",project_id);
                startActivity(intent);


            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();

        list.clear();
        SubTaskWithOkHttp("http://118.190.245.170/worktile/project/"+project_id+"/task/"+task_id+"/all-subtasks");

    }

    public void SubTaskWithOkHttp(String address) {
        HttpUtil.TaskInProjectWithOkHttp(address, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                //       Toast.makeText(getActivity(),"获取图书信息失败，请检查您的网络",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONArray jsonArray = jsonObject.getJSONArray("subtasks");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        name = jsonObject1.getString("name");
                        subtask_id = jsonObject1.getString("id");
                        state = jsonObject1.getString("state");

                        Map map = new HashMap();
                        map.put("state", state);
                        map.put("name", name);
                        map.put("task_id",task_id);
                        map.put("project_id",project_id);
                        map.put("subtask_id",subtask_id);

                        list.add(map);
                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            recyclerView.setLayoutManager(new LinearLayoutManager(ListSubTaskActivity.this));//垂直排列 , Ctrl+P
                            recyclerView.setAdapter(new SubTasklistAdapter(ListSubTaskActivity.this, list));//绑定适配器



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







