package com.example.itile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itile.Adapter.ScheduleHelperAdapter;
import com.example.itile.Adapter.WorkAdapter;
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

public class ScheduleHelperActivity extends AppCompatActivity {
    private ImageView back;
    private RecyclerView recyclerView;
    List<Map<String, Object>> list = new ArrayList<>();
    private RefreshLayout refreshLayout;
    private int page = 1;
    private ScheduleHelperAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_schedule_helper);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout_schedule_helper);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_schedule_helper);
        LinearLayoutManager manager = new LinearLayoutManager(ScheduleHelperActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        mAdapter = new ScheduleHelperAdapter(ScheduleHelperActivity.this, list);
        recyclerView.setAdapter(mAdapter);

        back = (ImageView) findViewById(R.id.back_from_schedule_helper);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getScheduleHelper("http://118.190.245.170/worktile/schedulehelper/");

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                postScheduleHelper("http://118.190.245.170/worktile/schedulehelper/", String.valueOf(page));
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(2000);
                page = 1;
                list.clear();
                getScheduleHelper("http://118.190.245.170/worktile/schedulehelper/");
            }
        });
    }

    public void postScheduleHelper(String address, String page) {
        HttpUtil.postScheduleHelper(address, page, new Callback() {
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
                boolean flag;
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("日程助手页post", responseData);
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    String warning = jsonObject.getString("warning");
                    if (!warning.equals("1")) {
                        flag = false;
                    } else {
                        flag = true;
                        JSONArray jsonArray = jsonObject.getJSONArray("schedule");
                        if (String.valueOf(jsonArray).equals("[]")) {
                            Map map1 = new HashMap();
                            map1.put("type", 0);
                            list.add(map1);
                        } else {
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
                                map.put("type",1);

                                list.add(map);
                            }
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (flag) {
                                refreshLayout.finishLoadMore(2000);
                                refreshLayout.setFooterHeight(25);
                                mAdapter.setData(list);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(ScheduleHelperActivity.this, warning, Toast.LENGTH_SHORT).show();
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


    public void getScheduleHelper(String address) {
        HttpUtil.getScheduleHelper(address,  new Callback() {
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
                boolean flag;
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("日程助手页get", responseData);
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    String warning = jsonObject.getString("warning");
                    if (!warning.equals("1")) {
                        flag = false;
                    } else {
                        flag = true;
                        JSONArray jsonArray = jsonObject.getJSONArray("schedule");
                        if (String.valueOf(jsonArray).equals("[]")) {
                            Map map1 = new HashMap();
                            map1.put("type", 0);
                            list.add(map1);
                        } else {
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
                                map.put("type",1);

                                list.add(map);
                            }
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (flag) {
                                refreshLayout.finishLoadMore(2000);
                                refreshLayout.setFooterHeight(25);
                                mAdapter.setData(list);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(ScheduleHelperActivity.this, warning, Toast.LENGTH_SHORT).show();
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


}
