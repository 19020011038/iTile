package com.example.itile.Fragment.Guide;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itile.Adapter.WorkAdapter;
import com.example.itile.AddressActivity;
import com.example.itile.FormActivity;
import com.example.itile.LoginActivity;
import com.example.itile.NewScheduleActivity;
import com.example.itile.R;
import com.example.itile.TaskActivity;
import com.example.itile.Util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WorkFragment extends Fragment {
    List<Map<String, Object>> list = new ArrayList<>();

    private RecyclerView recyclerView;
    private ImageView imageView;
    private String s = "hahahaha";
    private String yyyy;
    private String mm;
    private String dd;
    private String today;
    private String post_time;
    private ImageView jump_new_schedule;
    private int num = 0;
    private int create;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_work, container, false);
        Log.d("执行了oncreate", s);
        create = 0;
        //获取系统时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm");
        Date date = new Date(System.currentTimeMillis());
        Log.d("当前时间", simpleDateFormat.format(date));
        today = simpleDateFormat.format(date);
        post_time = today;
        yyyy = today.substring(0, 4);
        Log.d("yyyy", yyyy);
        mm = today.substring(5, 7);
        Log.d("mm", mm);
        dd = today.substring(8, 10);
        Log.d("dd", dd);
        return root;

    }

    @Override
    public void onResume() {
        super.onResume();
        CalendarView calendarView = getActivity().findViewById(R.id.calendarview);
        list.clear();
        //新建日程
        jump_new_schedule = getActivity().findViewById(R.id.work_new_schedule);
        jump_new_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewScheduleActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("year", yyyy);
                bundle.putString("month", mm);
                bundle.putString("day", dd);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        imageView = getActivity().findViewById(R.id.tongxunlu);
        recyclerView = getActivity().findViewById(R.id.recyclerView_work);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), AddressActivity.class); //从前者跳到后者，特别注意的是，在fragment中，用getActivity()来获取当前的activity
                getActivity().startActivity(intent1);
            }
        });

        //日历的点击操作
        Log.d("getDate", String.valueOf(calendarView.getDate()));
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView arg0, int arg1,
                                            int arg2, int arg3) {
                list.clear();
                yyyy = String.valueOf(arg1);
                String temp1 = String.valueOf(arg2 + 1);
                if (arg2 + 1 < 10)
                    mm = "0" + temp1;
                else
                    mm = temp1;
                String temp2 = String.valueOf(arg3);
                if (arg3 < 10)
                    dd = "0" + temp2;
                else
                    dd = temp2;
                post_time = yyyy + "-" + mm + "-" + dd + " 00-00";
                Log.d("post_time", post_time);
                create = 0;
                postCalendar("http://118.190.245.170/worktile/calendar/", post_time);
            }
        });
        postCalendar("http://118.190.245.170/worktile/calendar/", post_time);
        Log.d("执行了onresume", s);
    }

    public void postCalendar(String address, String time) {
        HttpUtil.postCalendar(address, time, new Callback() {
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
                Log.d("postCalendar", responseData);
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONArray jsonArray = jsonObject.getJSONArray("schedule");
                    num = jsonArray.length();
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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//垂直排列 , Ctrl+P
                            recyclerView.setAdapter(new WorkAdapter(getActivity(), list));//绑定适配器
                            if (create != 0){
                                recyclerView.smoothScrollToPosition(num - 1);
                            }else {
                                create++;
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
