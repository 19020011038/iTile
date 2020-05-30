package com.example.itile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.itile.Adapter.WorkAdapter;
import com.example.itile.Fragment.Guide.PersonFragment;
import com.example.itile.Fragment.Guide.WorkFragment;
import com.example.itile.Util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ScheduleDetailActivity extends AppCompatActivity {
    private ImageView back;
    private TextView show_status;
    private TextView change;
    private TextView show_detail;
    private TextView show_starttime;
    private TextView show_endtime;

    private String pk;
    private String detail;
    private String state;
    private String starttime;
    private String endtime;

    private ScheduleHelperActivity context1;
    private WorkFragment context2;
    private String position;

    private String from;
    private String change_to;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_schedule_detail);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Intent intent = getIntent();
        pk = intent.getStringExtra("pk");
        position = intent.getStringExtra("position");
        from = intent.getStringExtra("from");

        Log.d("这是详情页得到的pk",pk);
        getScheduleDetail("http://118.190.245.170/worktile/schedule/"+pk+"/");
        back = (ImageView)findViewById(R.id.back_from_schedule_detail);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        show_detail = (TextView)findViewById(R.id.schedule_detail_show_detail);
        show_starttime = (TextView)findViewById(R.id.schedule_detail_show_starttime);
        show_endtime = (TextView)findViewById(R.id.schedule_detail_show_endtime);
        show_status = (TextView)findViewById(R.id.schedule_detail_show_status);
        change = (TextView)findViewById(R.id.schedule_detail_change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state.equals("0")){
                    AlertDialog.Builder dialog = new AlertDialog.Builder (ScheduleDetailActivity.this);
                    dialog.setTitle("是否更日程状态？");
                    dialog.setMessage("若更改日程状态请点击确定");
                    dialog.setCancelable(false);
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            change_to = "1";
                            postScheduleDetail("http://118.190.245.170/worktile/schedule/"+pk+"/","1");
                        }
                    });
                    dialog.show();
                }else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder (ScheduleDetailActivity.this);
                    dialog.setTitle("是否更日程状态？");
                    dialog.setMessage("若更改日程状态请点击确定");
                    dialog.setCancelable(false);
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            change_to = "0";
                            postScheduleDetail("http://118.190.245.170/worktile/schedule/"+pk+"/","0");
                        }
                    });
                    dialog.show();
                }
            }
        });

    }
    public void getScheduleDetail(String address) {
        HttpUtil.getScheduleDetail(address,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("schedule");
                    detail = jsonObject1.getString("description");
                    starttime = jsonObject1.getString("starttime");
                    endtime = jsonObject1.getString("endtime");
                    state = jsonObject1.getString("state");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            show_starttime.setText(starttime.substring(5,7) + "月" + starttime.substring(8,10)+ "日" + " " + starttime.substring(11,13) + ":" + starttime.substring(14,16));
                            show_endtime.setText(endtime.substring(5,7) + "月" + endtime.substring(8,10)+ "日" + " " + endtime.substring(11,13) + ":" + endtime.substring(14,16));
                            show_detail.setText(detail);
                            if(state.equals("0"))
                                show_status.setText("未完成");
                            else
                                show_status.setText("已完成");
                        }
                    });
                } catch (JSONException e) {

                    e.printStackTrace();

                }
            }
        });
    }

    public void postScheduleDetail(String address,String state1) {
        HttpUtil.postScheduleDetail(address,state1,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ScheduleDetailActivity.this, "网络出现了问题...", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("schedule");
                    state = jsonObject1.getString("state");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(from.equals("helper")){
                                context1.change_state(Integer.valueOf(position),Integer.valueOf(change_to));
                            }else if(from.equals("work")){
                                WorkFragment workFragment = new WorkFragment();
                                workFragment.change_state2(Integer.valueOf(position),Integer.valueOf(change_to));
                                workFragment.show1();
                            }else {
                                PersonFragment personFragment = new PersonFragment();
                                personFragment.change_state3(Integer.valueOf(position),Integer.valueOf(change_to));
                            }
                            if(state.equals("0"))
                                show_status.setText("未完成");
                            else
                                show_status.setText("已完成");
                            Toast.makeText(ScheduleDetailActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {

                    e.printStackTrace();

                }
            }
        });
    }
}