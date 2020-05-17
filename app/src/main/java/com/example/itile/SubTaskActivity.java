
package com.example.itile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.itile.Util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SubTaskActivity extends AppCompatActivity {

    private ImageView back;
    private String task_id;
    private String project_id;
    private RelativeLayout relativeLayout;
    private String subtaskname;
    private String description;
    private String starttime;
    private String endtime;
    private String state;
    private String manager_id;
    private String manager_name;
    private String subtask_num;
    private String right;
    private String project_id_belong;
    private String project_name;
    private String task_id_belong;
    private String task_name;
    private String subtask_id;

    private TextView Aname;
    private TextView Adescription;
    private TextView Astarttime;
    private TextView Aendtime;
    private TextView Amanager;
    private TextView Anumber;
    private TextView Aproject;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_subtask);

        Intent intent = getIntent();
        task_id = intent.getStringExtra("task_id");
        project_id = intent.getStringExtra("project_id");
        subtask_id = intent.getStringExtra("subtask_id");


        Aname = findViewById(R.id.name);
        Astarttime = findViewById(R.id.time1);
        Aendtime = findViewById(R.id.time2);
        Adescription = findViewById(R.id.description);
        Amanager = findViewById(R.id.manager);
        Anumber = findViewById(R.id.number);
        Aproject = findViewById(R.id.project);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        DetailWithOkHttp("http://118.190.245.170/worktile/task/"+task_id+"/subtask/"+subtask_id);

    }
    public void DetailWithOkHttp(String address) {
        HttpUtil.ShowAllProjectWithOkHttp(address, new Callback() {

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
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");


                        state = jsonObject1.getString("state");
                        description = jsonObject1.getString("description");
                        starttime = jsonObject1.getString("starttime");
                        endtime = jsonObject1.getString("endtime");
                        manager_id = jsonObject1.getString("manager_id");
                        manager_name = jsonObject1.getString("manager_name");
                        task_id_belong = jsonObject1.getString("task_id");
                        task_name = jsonObject1.getString("task_name");
                        task_id = jsonObject1.getString("task_id");
                        project_id = jsonObject1.getString("project_id");
                        project_name = jsonObject1.getString("project_name");
                   subtaskname = jsonObject1.getString("name");
                    String time1 = starttime.replace("T", " ");
                    String time2 = endtime.replace("T", " ");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Aname.setText(subtaskname);
                            Adescription.setText(description);
                            Astarttime.setText(time1);
                            Aendtime.setText(time2);
                            Amanager.setText(manager_name);
                            Aproject.setText(task_name);

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}