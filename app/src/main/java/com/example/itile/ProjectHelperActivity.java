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

public class ProjectHelperActivity extends AppCompatActivity {
    private RelativeLayout back;
    private ImageView pic1;
    private ImageView pic2;
    private ImageView all;
    private RecyclerView recyclerView;
    List<Map<String, Object>> list = new ArrayList<>();
    private boolean flag;          //判断选择的是未读还是已读，未读是true
    private int page;
    private ProjectHelperAdapter mAdapter;
    private RefreshLayout refreshLayout;
    private boolean flag2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_project_helper);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
        page = 1;
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout_project_helper);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_project_helper);
        LinearLayoutManager manager = new LinearLayoutManager(ProjectHelperActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        mAdapter = new ProjectHelperAdapter(ProjectHelperActivity.this, list);
        recyclerView.setAdapter(mAdapter);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pic1 = (ImageView) findViewById(R.id.weidu);
        pic2 = (ImageView) findViewById(R.id.yidu);
        all = (ImageView) findViewById(R.id.project_helper_all);

        pic1.setImageResource(R.drawable.weidu1);
        pic1.invalidate();
        pic2.setImageResource(R.drawable.yidu0);
        pic2.invalidate();
        flag = true;
        postProjectHelper("http://118.190.245.170/worktile/projecthelper/", "0", String.valueOf(page));
    }

    @Override
    protected void onResume() {
        super.onResume();

        pic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag) {
                    all.setVisibility(View.VISIBLE);
                    all.setClickable(true);
                    all.setImageResource(R.drawable.all);
                    all.invalidate();
                    list.clear();
                    flag = true;
                    page = 1;
                    pic1.setImageResource(R.drawable.weidu1);
                    pic1.invalidate();
                    pic2.setImageResource(R.drawable.yidu0);
                    pic2.invalidate();
                    postProjectHelper("http://118.190.245.170/worktile/projecthelper/", "0", String.valueOf(page));
                }
            }
        });
        pic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    all.setClickable(false);
                    all.setVisibility(View.INVISIBLE);
                    list.clear();
                    flag = false;
                    page = 1;
                    pic1.setImageResource(R.drawable.weidu0);
                    pic1.invalidate();
                    pic2.setImageResource(R.drawable.yidu1);
                    pic2.invalidate();
                    postProjectHelper("http://118.190.245.170/worktile/projecthelper/", "1", String.valueOf(page));
                }
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("[{type=2}]".equals(String.valueOf(list))) {
                    Toast.makeText(ProjectHelperActivity.this, "没有已读消息哦~", Toast.LENGTH_SHORT).show();
                } else {
                    postProjectHelper2("http://118.190.245.170/worktile/projecthelper/", "1");
                }
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                String choice;
                if (flag)
                    choice = "0";
                else
                    choice = "1";
                postProjectHelper("http://118.190.245.170/worktile/projecthelper/", choice, String.valueOf(page));
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                String choice;
                if (flag)
                    choice = "0";
                else
                    choice = "1";
                refreshLayout.finishRefresh(2000);
                page = 1;
                list.clear();
                postProjectHelper("http://118.190.245.170/worktile/projecthelper/", choice, String.valueOf(page));
            }
        });

    }

    public void postProjectHelper(String address, String choice, String page) {
        HttpUtil.postProjectHelper(address, choice, page, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ProjectHelperActivity.this, "网络出现了问题...", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("项目助手页post", responseData);
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    String warning = jsonObject.getString("warning");
                    if (!warning.equals("1")) {
                        flag2 = false;
                    } else {
                        flag2 = true;
                        JSONArray jsonArray = jsonObject.getJSONArray("project");
                        if (!String.valueOf(jsonArray).equals("[]")) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject schedule = jsonArray.getJSONObject(i);
                                String pk = schedule.getString("pk");
                                JSONObject fields = schedule.getJSONObject("fields");
                                String name = fields.getString("name");

                                Map map = new HashMap();
                                map.put("pk", pk);
                                map.put("name", name);
                                map.put("type", 1);

                                list.add(map);
                            }
                        } else {
                            Map map1 = new HashMap();
                            map1.put("type", 2);
                            list.add(map1);
                            Log.d("hahahahahhaha", String.valueOf(jsonArray));
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getProject("http://118.190.245.170/worktile/projecthelper/");
                            if (flag2) {
                                refreshLayout.finishLoadMore(3000);
                                refreshLayout.setFooterHeight(200);
                                mAdapter.setData(list);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(ProjectHelperActivity.this, warning, Toast.LENGTH_SHORT).show();
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

    public void postProjectHelper2(String address, String read) {
        HttpUtil.postProjectHelper2(address, read, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ProjectHelperActivity.this, "网络出现了问题...", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("项目助手页post2", responseData);
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    String warning = jsonObject.getString("warning");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (warning.equals("1")) {
                                if (flag) {
                                    list.clear();
                                    page = 1;
                                    Toast.makeText(ProjectHelperActivity.this, "操作成功！", Toast.LENGTH_SHORT).show();
                                    postProjectHelper("http://118.190.245.170/worktile/projecthelper/", "0", String.valueOf(page));
                                } else {
                                    Toast.makeText(ProjectHelperActivity.this, "操作成功，请刷新后查看！", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(ProjectHelperActivity.this, "操作失败！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (JSONException e) {

                    e.printStackTrace();

                }
            }
        });
    }

    public void getProject(String address) {
        HttpUtil.getProjectorTask(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("项目助手GET",responseData);
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                } catch (JSONException e) {

                    e.printStackTrace();

                }
            }
        });
    }

}
