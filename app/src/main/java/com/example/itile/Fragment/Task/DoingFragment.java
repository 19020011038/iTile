package com.example.itile.Fragment.Task;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itile.Adapter.DoingAllTaskAdapter;
import com.example.itile.Adapter.UpcomingAllTaskAdapter;
import com.example.itile.R;
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

public class DoingFragment extends Fragment {
    private List<Map<String, Object>> list2 = new ArrayList<>();
    private RelativeLayout task;
    private RelativeLayout form;
    private ImageView imageView;
    private String model_name;
    private String task_id;
    private RecyclerView recyclerView;

    private String name;
    private String state;
    private String ifread;
    private String description;
    private String project;

    private String starttime;
    private String endtime;
    private String manager;
    private String user;

    public static DoingFragment newInstance(int index) {
        DoingFragment fragment = new DoingFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt(ARG_SECTION_NUMBER, index);
//        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_task_doing, container, false);
        recyclerView = (RecyclerView)root.findViewById(R.id.recyclerView);

        list2.clear();
        ShowAllTaskWithOkHttp("http://118.190.245.170/worktile/all-tasks");


        return root;

    }

    @Override
    public void onResume() {
        super.onResume();




    }

    public void ShowAllTaskWithOkHttp(String address) {
        HttpUtil.ShowAllTaskWithOkHttp(address, new Callback() {



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

                    JSONObject jsonObject2 = jsonObject.getJSONObject("data");

                    JSONArray jsonArray = jsonObject2.getJSONArray("isgoing");

                    Log.d("22222",responseData);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject schedule = jsonArray.getJSONObject(i);
                        String model = schedule.getString("model");
                        String id = schedule.getString("pk");
                        JSONObject fields = schedule.getJSONObject("fields");

                        name = fields.getString("name");
                        state = fields.getString("state");
                        ifread = fields.getString("ifread");
                        description = fields.getString("description");
                        project = fields.getString("project");
                        starttime = fields.getString("starttime");
                        endtime = fields.getString("endtime");
                        manager = fields.getString("manager");
                        user = fields.getString("user");



                        Map map = new HashMap();

                        map.put("model", model);
                        map.put("id",id);
                        map.put("name",name);
                        map.put("state",state);
                        map.put("ifread",ifread);
                        map.put("description",description);
                        map.put("project",project);
                        map.put("starttime",starttime);
                        map.put("endtime",endtime);
                        map.put("manager",manager);
                        map.put("user",user);
                        list2.add(map);
                    }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//垂直排列 , Ctrl+P
                                recyclerView.setAdapter(new DoingAllTaskAdapter(getActivity(), list2));//绑定适配器

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

