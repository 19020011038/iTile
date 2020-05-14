package com.example.itile.Fragment.Task;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.example.itile.TaskActivity;
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

public class FinishFragment extends Fragment {
    private List<Map<String, Object>> list = new ArrayList<>();
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

    public static FinishFragment newInstance(int index) {
        FinishFragment fragment = new FinishFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt(ARG_SECTION_NUMBER, index);
//        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_task_finish, container, false);

        return root;

    }

    @Override
    public void onResume() {
        super.onResume();

        recyclerView = getActivity().findViewById(R.id.recyclerView);


        ShowAllTaskWithOkHttp("http://118.190.245.170/worktile/all-tasks");


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

                    JSONArray jsonArray = jsonObject2.getJSONArray("notstart");

                    JSONObject jsonObject3 = jsonObject.getJSONObject("notstart");

                    JSONArray jsonArray1 = jsonObject3.getJSONArray("fields");



                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject_1 = jsonArray.getJSONObject(i);
                        JSONObject jsonObject_2 = jsonArray1.getJSONObject(i);

                        model_name = jsonObject_1.getString("model");
                        task_id = jsonObject_1.getString("id");

                        name = jsonObject_2.getString("name");
                        state = jsonObject_2.getString("state");
                        ifread = jsonObject_2.getString("ifread");
                        description = jsonObject_2.getString("description");
                        project = jsonObject_2.getString("project");

                        starttime = jsonObject_2.getString("starttime");
                        endtime = jsonObject_2.getString("endtime");
                        manager = jsonObject_2.getString("manager");
                        user = jsonObject_2.getString("user");



                        Map map = new HashMap();

                        map.put("name", model_name);
                        map.put("id",task_id);

                        map.put("name",name);
                        map.put("state",state);
                        map.put("ifread",ifread);
                        map.put("description",description);

                        map.put("project",project);
                        map.put("starttime",starttime);
                        map.put("endtime",endtime);
                        map.put("manager",manager);
                        map.put("user",user);
                        list.add(map);

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


