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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itile.Adapter.ScheduleHelperAdapter;
import com.example.itile.Adapter.WorkAdapter;
import com.example.itile.AddressActivity;
import com.example.itile.FormActivity;
import com.example.itile.LoginActivity;
import com.example.itile.NewScheduleActivity;
import com.example.itile.Painter.InnerPainter2;
import com.example.itile.R;
import com.example.itile.ScheduleHelperActivity;
import com.example.itile.TaskActivity;
import com.example.itile.Util.HttpUtil;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.necer.calendar.BaseCalendar;
import com.necer.calendar.NCalendar;
import com.necer.enumeration.DateChangeBehavior;
import com.necer.listener.OnCalendarChangedListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.joda.time.LocalDate;
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
    static List<Map<String, Object>> list = new ArrayList<>();
    List<String> pointList = new ArrayList<>();
    static private WorkAdapter mAdapter;
    private RecyclerView recyclerView;
    private ImageView imageView;
    private String s = "hahahaha";
    private String yyyy;
    private String mm;
    private String dd;
    private String today;
    private String post_time;
    private ImageView jump_new_schedule;
    private int num;
    private int create;
    private NCalendar nCalendar;
    private TextView tv_year;
    private TextView tv_month;
    private RefreshLayout refreshLayout;


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
        recyclerView = root.findViewById(R.id.recyclerView_work);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        mAdapter = new WorkAdapter(getActivity(), list);
        recyclerView.setAdapter(mAdapter);
        return root;

    }

    @Override
    public void onResume() {
        super.onResume();
//        CalendarView calendarView = getActivity().findViewById(R.id.calendarview);
//        list.clear();

        nCalendar = getActivity().findViewById(R.id.ncalendar);
        tv_year = getActivity().findViewById(R.id.year);
        tv_month = getActivity().findViewById(R.id.month);
        refreshLayout = getActivity().findViewById(R.id.refreshLayout_work);
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

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), AddressActivity.class); //从前者跳到后者，特别注意的是，在fragment中，用getActivity()来获取当前的activity
                getActivity().startActivity(intent1);
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                NCalendarChangeMonthWithOkHttp("http://118.190.245.170/worktile/calendar/", post_time);
            }
        });
//        //日历的点击操作
//        Log.d("getDate", String.valueOf(calendarView.getDate()));
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//
//            @Override
//            public void onSelectedDayChange(CalendarView arg0, int arg1,
//                                            int arg2, int arg3) {
//                list.clear();
//                yyyy = String.valueOf(arg1);
//                String temp1 = String.valueOf(arg2 + 1);
//                if (arg2 + 1 < 10)
//                    mm = "0" + temp1;
//                else
//                    mm = temp1;
//                String temp2 = String.valueOf(arg3);
//                if (arg3 < 10)
//                    dd = "0" + temp2;
//                else
//                    dd = temp2;
//                post_time = yyyy + "-" + mm + "-" + dd + " 00-00";
//                Log.d("post_time", post_time);
//                create = 0;
//                postCalendar("http://118.190.245.170/worktile/calendar/", post_time);
//            }
//        });
//        postCalendar("http://118.190.245.170/worktile/calendar/", post_time);
//        Log.d("执行了onresume", s);

        nCalendar.setCalendarPainter(new InnerPainter2(getActivity(), nCalendar));

        nCalendar.setOnCalendarChangedListener(new OnCalendarChangedListener() {
            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int calendar_year, int calendar_month, LocalDate localDate, DateChangeBehavior dateChangeBehavior) {
//                Log.i("zyr", "新建厅啊啊啊"+calendar_year+calendar_month+localDate.toString()+dateChangeBehavior.toString());
//                Toast.makeText(getActivity(), "年："+calendar_year+"month:"+calendar_month+localDate.toString()+dateChangeBehavior.toString(), Toast.LENGTH_SHORT).show();
//                localDate.getDayOfYear();
                list.clear();

                post_time = localDate.toString();
                yyyy = post_time.substring(0,4);
                mm = post_time.substring(5,7);
                dd = post_time.substring(8, 10);

                Log.i("zyr", "dd:"+dd+"month"+mm+"year"+yyyy);
                String behavior = dateChangeBehavior.toString();
                if (behavior.equals("PAGE")|| behavior.equals("INITIALIZE")||behavior.equals("CLICK_PAGE")){
                    pointList.clear();
                    NCalendarChangeMonthWithOkHttp("http://118.190.245.170/worktile/calendar/", post_time);
                    tv_year.setText(yyyy);
                    tv_month.setText(mm);
                    Log.i("zyr", "切换月监听触发："+behavior);
                }else if (behavior.equals("CLICK")){
                    NCalendarChangeDateWithOkHttp("http://118.190.245.170/worktile/calendar/", post_time);
                }
            }
        });
        NCalendarChangeMonthWithOkHttp("http://118.190.245.170/worktile/calendar/", post_time);
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
//                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//垂直排列 , Ctrl+P
//                            recyclerView.setAdapter(new WorkAdapter(getActivity(), list));//绑定适配器
                            mAdapter.setData(list);
                            mAdapter.notifyDataSetChanged();
                            if (create != 0){
                                recyclerView.smoothScrollToPosition(num);
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

    public void NCalendarChangeMonthWithOkHttp(String address, String time) {
        HttpUtil.postCalendar(address, time, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "网络出现了问题...", Toast.LENGTH_SHORT).show();
                        refreshLayout.finishRefresh(false);
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("postCalendar", responseData);
                try {
                    list.clear();
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
                    JSONArray jsonArray1 = jsonObject.getJSONArray("daylist");
                    for (int i = 0;i < jsonArray1.length();i++){
                        String date1 = jsonArray1.getString(i);
                        pointList.add(date1);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.finishRefresh();
                            InnerPainter2 innerPainter = (InnerPainter2) nCalendar.getCalendarPainter();
                            innerPainter.setPointList(pointList);
//                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//垂直排列 , Ctrl+P
//                            recyclerView.setAdapter(new WorkAdapter(getActivity(), list));//绑定适配器
                            mAdapter.setData(list);
                            mAdapter.notifyDataSetChanged();
//                            if (create != 0){
//                                recyclerView.smoothScrollToPosition(num);
//                            }else {
//                                create++;
//                            }
                        }
                    });
                } catch (JSONException e) {

                    e.printStackTrace();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.finishRefresh(false);
                            Toast.makeText(getActivity(), "服务器连接失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void NCalendarChangeDateWithOkHttp(String address, String time) {
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
                    list.clear();
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
//                    JSONArray jsonArray1 = jsonObject.getJSONArray("daylist");
//                    for (int i = 0;i < jsonArray1.length();i++){
//                        String date1 = jsonArray1.getString(i);
//                        pointList.add(date1);
//                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            InnerPainter innerPainter2 = (InnerPainter) nCalendar.getCalendarPainter();
//                            innerPainter2.setPointList(pointList);
//                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//垂直排列 , Ctrl+P
//                            recyclerView.setAdapter(new WorkAdapter(getActivity(), list));//绑定适配器
                            mAdapter.setData(list);
                            mAdapter.notifyDataSetChanged();
//                            if (create != 0){
//                                recyclerView.smoothScrollToPosition(num);
//                            }else {
//                                create++;
//                            }
//                            recyclerView.smoothScrollToPosition(num);
                        }
                    });
                } catch (JSONException e) {

                    e.printStackTrace();

                }
            }
        });
    }
    public static void change_state2(int position,int state){
        Log.d("qshnb6666","qshnb666");
        String pk = list.get(position).get("pk").toString();
        String starttime = list.get(position).get("starttime").toString();
        String endtime = list.get(position).get("endtime").toString();
        String description = list.get(position).get("description").toString();

        Map map = new HashMap();
        map.put("pk", pk);
        map.put("starttime", starttime);
        map.put("endtime", endtime);
        map.put("state", state);
        map.put("description", description);
        map.put("type",1);

        list.remove(position);
        list.add(position,map);
        Log.d("list",String.valueOf(list));

        mAdapter.notifyDataSetChanged();
    }

    public static void show1(){
        Log.d("hahahah","hadsjaodi");
    }
}
