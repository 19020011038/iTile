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

import com.example.itile.Adapter.ScheduleHelperAdapter;
import com.example.itile.Adapter.WorkAdapter;
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

public class ScheduleHelperActivity extends AppCompatActivity {
    private ImageView back;
    private RecyclerView recyclerView;
    List<Map<String, Object>> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_schedule_helper);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_schedule_helper);
        back = (ImageView) findViewById(R.id.back_from_schedule_helper);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getScheduleHelper("http://118.190.245.170/worktile/schedulehelper/");
    }

    public void getScheduleHelper(String address) {
        HttpUtil.getScheduleHelper(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ScheduleHelperActivity.this, "网络出现了问题...", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("日程助手页get", responseData);
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONArray jsonArray = jsonObject.getJSONArray("schedule");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject schedule = jsonArray.getJSONObject(i);
                        String pk = schedule.getString("pk");
                        JSONObject fields = schedule.getJSONObject("fields");
                        String starttime = fields.getString("starttime");
                        String endtime = fields.getString("endtime");
                        String state = fields.getString("state");
                        String description = fields.getString("description");

                        Map map = new HashMap();
                        map.put("pk", pk);
                        map.put("starttime", starttime);
                        map.put("endtime", endtime);
                        map.put("state", state);
                        map.put("description", description);

                        list.add(map);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setLayoutManager(new LinearLayoutManager(ScheduleHelperActivity.this));//垂直排列 , Ctrl+P
                            recyclerView.setAdapter(new ScheduleHelperAdapter(ScheduleHelperActivity.this, list));//绑定适配器
                        }
                    });
                } catch (JSONException e) {

                    e.printStackTrace();

                }
            }
        });
    }

}
