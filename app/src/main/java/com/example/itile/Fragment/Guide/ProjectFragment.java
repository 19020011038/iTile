package com.example.itile.Fragment.Guide;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itile.Adapter.AllProjectAdapter;
import com.example.itile.FormActivity;
import com.example.itile.NewProjectActivity;
import com.example.itile.R;
import com.example.itile.AllTaskActivity;
import com.example.itile.Util.HttpUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
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

public class ProjectFragment extends Fragment {
    private List<Map<String, Object>> list = new ArrayList<>();
    private RelativeLayout task;
    private RelativeLayout form;
    private ImageView imageView;
    private String name;
    private String project_id;
    private RecyclerView recyclerView;
    private int flag = 1;
    private RefreshLayout refreshLayout;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_project, container, false);

        return root;

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshLayout = getActivity().findViewById(R.id.refreshLayout_project);
        task = getActivity().findViewById(R.id.my_task);
        form = getActivity().findViewById(R.id.my_form);
        imageView = getActivity().findViewById(R.id.add_new);
        recyclerView = getActivity().findViewById(R.id.recyclerView);

        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AllTaskActivity.class); //从前者跳到后者，特别注意的是，在fragment中，用getActivity()来获取当前的activity
                getActivity().startActivity(intent);
            }
        });
        form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), FormActivity.class); //从前者跳到后者，特别注意的是，在fragment中，用getActivity()来获取当前的activity
                getActivity().startActivity(intent1);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), NewProjectActivity.class); //从前者跳到后者，特别注意的是，在fragment中，用getActivity()来获取当前的activity
                getActivity().startActivity(intent1);
            }
        });

        list.clear();

        ShowAllProjectWithOkHttp("http://118.190.245.170/worktile/work");

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                list.clear();
                ShowAllProjectWithOkHttp("http://118.190.245.170/worktile/work");
                Log.d("hahahshuaxin","shuaxin");
                refreshLayout.finishRefresh(2500);
            }
        });

    }

    public void ShowAllProjectWithOkHttp(String address) {
        HttpUtil.ShowAllProjectWithOkHttp(address, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "网络出现了问题...", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONArray jsonArray = jsonObject.getJSONArray("project");
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
                        project_id = jsonObject1.getString("id");

                        Map map = new HashMap();

                        map.put("name", name);
                        map.put("project_id",project_id);
                        map.put("flag",1);
                        list.add(map);
                    }}

                    else
                    {
                        Map map1 = new HashMap();
                        map1.put("flag",0);
                        list.add(map1);
                    }

                    if (!getActivity().equals(null))
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//垂直排列 , Ctrl+P
                                recyclerView.setAdapter(new AllProjectAdapter(getActivity(), list));//绑定适配器


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
