package com.example.itile.Fragment.Guide;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.example.itile.Adapter.HomeAdapter;
import com.example.itile.Adapter.SearchAdapter;
import com.example.itile.LoginActivity;
import com.example.itile.ProjectHelperActivity;
import com.example.itile.R;
import com.example.itile.ScheduleHelperActivity;
import com.example.itile.SearchActivity;
import com.example.itile.SettingActivity;
import com.example.itile.TaskHelperActivity;
import com.example.itile.Util.HttpUtil;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeFragment extends Fragment {
    private TextView schedule_helper_content;
    private TextView project_helper_content;
    private TextView task_helper_content;
    private ImageView hongdian1;
    private ImageView hongdian2;
    private ImageView hongdian3;
    private String flag_schedule;
    private String flag_project;
    private String flag_task;
    private View re1;
    private View re2;
    private View re3;
    private ImageView search;
    private String description1;
    private String description2;
    private String description3;
    private TextView content1;
    private TextView content2;
    private TextView content3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d("test","test");
        content1 = getActivity().findViewById(R.id.schedule_content);
        content2 = getActivity().findViewById(R.id.project_content);
        content3 = getActivity().findViewById(R.id.task_content);
        schedule_helper_content = getActivity().findViewById(R.id.text1);
        project_helper_content = getActivity().findViewById(R.id.text2);
        task_helper_content = getActivity().findViewById(R.id.text3);
        hongdian1 = getActivity().findViewById(R.id.hongdian1);
        hongdian2 = getActivity().findViewById(R.id.hongdian2);
        hongdian3 = getActivity().findViewById(R.id.hongdian3);
        re1 = getActivity().findViewById(R.id.re1);
        re2 = getActivity().findViewById(R.id.re2);
        re3 = getActivity().findViewById(R.id.re3);
        search = getActivity().findViewById(R.id.home_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        re1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入日程助手
                Intent intent = new Intent(getActivity(), ScheduleHelperActivity.class);
                startActivity(intent);
            }
        });

        re2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入项目助手
                Intent intent = new Intent(getActivity(), ProjectHelperActivity.class);
                startActivity(intent);
            }
        });

        re3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入任务助手
                Intent intent = new Intent(getActivity(), TaskHelperActivity.class);
                startActivity(intent);
            }
        });
        getHelper("http://118.190.245.170/worktile/helper/");
    }

    public void getHelper(String address) {
        HttpUtil.getHelper(address, new Callback() {
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
                String responseData = response.body().string();
                Log.d("getHelper的返回内容", responseData);
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("state");
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("schedulestate");
                    flag_schedule = jsonObject2.getString("state");
                    description1 = jsonObject2.getString("description");
                    JSONObject jsonObject3 = jsonObject1.getJSONObject("projectstate");
                    flag_project = jsonObject3.getString("state");
                    description2 = jsonObject3.getString("description");
                    JSONObject jsonObject4 = jsonObject1.getJSONObject("taskstate");
                    flag_task = jsonObject4.getString("state");
                    description3 = jsonObject4.getString("description");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (flag_schedule.equals("0")){
                                hongdian1.setImageResource(R.drawable.hongdian);
                                hongdian1.invalidate();
                            }else {
                                hongdian1.setImageResource(R.drawable.hongdian);
                                hongdian1.invalidate();
                            }
                            content1.setText(description1);
                            if (flag_project.equals("0")){
                                hongdian2.setImageResource(R.drawable.hongdian);
                                hongdian2.invalidate();
                            }else {
                                Log.d("hongdian",flag_project);
                                hongdian2.setImageResource(R.drawable.hongdian);
                                Log.d("hongdian2243",flag_project);
                                hongdian2.invalidate();
                                Log.d("hongdian000000",flag_project);
                            }
                            content2.setText(description2);
                            if (flag_task.equals("0")){
                                hongdian3.setImageResource(R.drawable.hongdian);
                                hongdian3.invalidate();
                            }else {
                                hongdian3.setImageResource(R.drawable.hongdian);
                                hongdian3.invalidate();
                            }

                            content3.setText(description3);

                            Log.d("描述1",description1);
                            Log.d("描述2",description2);
                            Log.d("描述3",description3);

                        }
                    });
                } catch (JSONException e) {

                    e.printStackTrace();

                }
            }
        });
    }
}
