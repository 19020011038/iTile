
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

public class TaskActivity extends AppCompatActivity {

    private ImageView back;
    private String task_id;
    private String project_id;
    private RelativeLayout relativeLayout;
    private String name;
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

    private TextView Aname;
    private TextView Adescription;
    private TextView Astarttime;
    private TextView Aendtime;
    private TextView Amanager;
    private TextView Anumber;
    private TextView Aproject;

    private RelativeLayout relativeLayout1;

    private TextView change;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_task);

        Intent intent = getIntent();
        task_id = intent.getStringExtra("task_id");
        project_id = intent.getStringExtra("project_id");


        relativeLayout = findViewById(R.id.all_task);
        Aname = findViewById(R.id.name);
        Astarttime = findViewById(R.id.time1);
        Aendtime = findViewById(R.id.time2);
        Adescription = findViewById(R.id.description);
        Amanager = findViewById(R.id.manager);
        Anumber = findViewById(R.id.number);
        Aproject = findViewById(R.id.project);

        relativeLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TaskActivity.this, ListSubTaskActivity.class);
                intent.putExtra("project_id",project_id);
                intent.putExtra("task_id",task_id);
                startActivity(intent);


            }
        });


        DetailWithOkHttp("http://118.190.245.170/worktile/project/"+project_id+"/task/"+task_id);

        relativeLayout1 = findViewById(R.id.re);

        relativeLayout1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(TaskActivity.this,      .class);
//                intent.putExtra("project_id",project_id);
//                intent.putExtra("task_id",task_id);
//                startActivity(intent);


            }
        });

                change = findViewById(R.id.new_task);
        change.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(TaskActivity.this,.class);
//                intent.putExtra("project_id",project_id);
//                intent.putExtra("task_id",task_id);
//                intent.putExtra("state",state);
//                startActivity(intent);


            }
        });


        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




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

                        name = jsonObject1.getString("task_name");
                        state = jsonObject1.getString("state");
                        description = jsonObject1.getString("description");
                        starttime = jsonObject1.getString("starttime");
                        endtime = jsonObject1.getString("endtime");
                        manager_id = jsonObject1.getString("manager_id");
                        manager_name = jsonObject1.getString("manager_name");
                        right = jsonObject1.getString("right");
                        project_id_belong = jsonObject1.getString("project_id");
                        project_name = jsonObject1.getString("project_name");
                        subtask_num = jsonObject1.getString("subtask_num");

                    String time1 = starttime.replace("T", " ");
                    String time2 = endtime.replace("T", " ");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Aname.setText(name);
                            Adescription.setText(description);
                            Astarttime.setText(time1);
                            Aendtime.setText(time2);
                            Amanager.setText(manager_name);
                            Anumber.setText(subtask_num);
                            Aproject.setText(project_name);

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}