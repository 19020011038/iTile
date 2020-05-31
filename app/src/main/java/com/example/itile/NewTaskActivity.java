package com.example.itile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.itile.Adapter.TaskInProjectAdapter;
import com.example.itile.Util.HttpUtil;
import com.example.itile.Util.SharedPreferencesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NewTaskActivity extends AppCompatActivity {

    private EditText name;
    private EditText tip;
    private SharedPreferencesUtil check;

    private String project_id;

    private String yyyy1="a";
    private String MM1="a";
    private String dd1="a";
    private String HH1="a";
    private String mm1="a";

    private String yyyy2="a";
    private String MM2="a";
    private String dd2="a";
    private String HH2="a";
    private String mm2="a";
    private String description;
    private String starttime;
    private String endtime;
    private RelativeLayout start;
    private RelativeLayout end;
    private ImageView back;
    private TextView finish;
    private AlertDialog.Builder dialog;
    private NumberPicker numberpicker0;
    private NumberPicker numberpicker2;
    private NumberPicker numberpicker3;
    private NumberPicker numberpicker4;
    private NumberPicker numberpicker5;

    private TextView textView1;
    private TextView textView2;

    int year;
    int month;
    int day;


    int year1;
    int month1;
    int day1;

    int now_year ;
    int now_month ;
    int now_day ;

    int a;
    int flag1=0;
    int flag2=0;
    int fen = 0;
    int miao = 0;

    private String today;
    private String post_time;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_task);

        //获取系统时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm");
        Date date = new Date(System.currentTimeMillis());
        Log.d("当前时间", simpleDateFormat.format(date));
        today = simpleDateFormat.format(date);
        post_time = today;
        now_year = Integer.parseInt(today.substring(0, 4));
        now_month = Integer.parseInt(today.substring(5, 7));
        now_day = Integer.parseInt(today.substring(8, 10));

        year1=now_year;
        month1=now_month;
        day1=now_day;

        year=now_year;
        month=now_month;
        day=now_day;




        Intent intent = getIntent();
        project_id = intent.getStringExtra("project_id");

        check = SharedPreferencesUtil.getInstance(getApplicationContext());
        finish = findViewById(R.id.finish);
        name = findViewById(R.id.name);
        tip = findViewById(R.id.tip);
        start = findViewById(R.id.start);
        end = findViewById(R.id.end);

        textView1 = findViewById(R.id.tvstart);
        textView2 = findViewById(R.id.tvend);


        tip.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
                for (int i = s.length(); i > 0; i--) {

                    if (s.subSequence(i - 1, i).toString().equals("\n"))
                        s.replace(i - 1, i, "");
                }
            }
        });

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=1;
                flag1=1;
                dialog = createLoadingDialog(NewTaskActivity.this, "1");
                dialog.create().show();


            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=2;
                flag2=1;
                dialog = createLoadingDialog(NewTaskActivity.this, "2");
                dialog.create().show();



            }
        });


        finish = (TextView) findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                starttime = yyyy1 + "-" + MM1 + "-" + dd1 + " " + HH1 + ":" + mm1;
                endtime = yyyy2 + "-" + MM2 + "-" + dd2 + " " + HH2 + ":" + mm2;
                Date a = new Date(Integer.valueOf(yyyy1),Integer.valueOf(MM1),Integer.valueOf(dd1),Integer.valueOf(HH1),Integer.valueOf(mm1));
                Date b = new Date(Integer.valueOf(yyyy2),Integer.valueOf(MM2),Integer.valueOf(dd2),Integer.valueOf(HH2),Integer.valueOf(mm2));


                Log.d("showstr", starttime);
                Log.d("showstr", endtime);


                String name1 = name.getText().toString();
                String tip1 = tip.getText().toString();

                if (name1.trim().isEmpty())
                {
                    Toast.makeText(NewTaskActivity.this, "任务名称不能全为空格！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (tip1.trim().isEmpty())
                {
                    Toast.makeText(NewTaskActivity.this, "任务描述不能全为空格！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(name1)) {
                    Toast.makeText(NewTaskActivity.this, "任务名称不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(tip1)) {
                    Toast.makeText(NewTaskActivity.this, "任务描述不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (flag1==0)
                    Toast.makeText(NewTaskActivity.this, "请选择开始时间", Toast.LENGTH_SHORT).show();
                else if (flag2==0)
                    Toast.makeText(NewTaskActivity.this, "请选择结束时间", Toast.LENGTH_SHORT).show();
                else {
                    if(a.after(b)){
                        Toast.makeText(NewTaskActivity.this,"结束时间应晚于开始时间",Toast.LENGTH_SHORT).show();
                    }else {
                        newTaskWithOkHttp("http://118.190.245.170/worktile/project/" + project_id + "/new-task", name1, tip1, starttime, endtime);
                        Toast.makeText(NewTaskActivity.this, "新建任务成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }

    public AlertDialog.Builder createLoadingDialog(Context context, String a) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view

        numberpicker0 = (NumberPicker) v.findViewById(R.id.numberpicker1);
        numberpicker0.setMaxValue(2025);
        numberpicker0.setMinValue(now_year);
        numberpicker0.setValue(now_year);
        numberpicker0.setFocusable(true);
        numberpicker0.setFocusableInTouchMode(true);
        numberpicker0.setOnValueChangedListener(yearChangedListener);

        numberpicker2 = (NumberPicker) v.findViewById(R.id.numberpicker2);
        numberpicker2.setMaxValue(12);
        numberpicker2.setMinValue(1);
        numberpicker2.setValue(now_month);
        numberpicker2.setFocusable(true);
        numberpicker2.setFocusableInTouchMode(true);
        numberpicker2.setOnValueChangedListener(monthChangedListener);

        /*
         * / setMaxValue根据每月的天数不一样，使用switch()进行分别判断
         */
        numberpicker3 = (NumberPicker) v.findViewById(R.id.numberpicker3);
        numberpicker3.setMinValue(1);
        numberpicker3.setMaxValue(31);
        numberpicker3.setValue(now_day);
        numberpicker3.setFocusable(true);
        numberpicker3.setFocusableInTouchMode(true);
        numberpicker3.setOnValueChangedListener(dayChangedListener);

        numberpicker4 = (NumberPicker) v.findViewById(R.id.numberpicker4);
        numberpicker4.setMaxValue(23);
        numberpicker4.setMinValue(0);
        numberpicker4.setValue(0);
        numberpicker4.setFocusable(true);
        numberpicker4.setFocusableInTouchMode(true);
//        numberpicker4.setOnValueChangedListener(yearChangedListener);

        numberpicker5 = (NumberPicker) v.findViewById(R.id.numberpicker5);
        numberpicker5.setMaxValue(59);
        numberpicker5.setMinValue(0);
        numberpicker5.setValue(0);
        numberpicker5.setFocusable(true);
        numberpicker5.setFocusableInTouchMode(true);
//        numberpicker5.setOnValueChangedListener(yearChangedListener);

        AlertDialog.Builder loadingDialog = new AlertDialog.Builder(context);
        loadingDialog.setMessage("选择开始时间");
        loadingDialog.setView(v);
        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        fen = numberpicker4.getValue();
                        miao = numberpicker5.getValue();

                        fen = numberpicker4.getValue();
                        miao = numberpicker5.getValue();

                        if (a.equals("1"))

                        {

                            yyyy1 = year+"";
                            MM1 = month+"";
                            dd1 = day+"";
                            HH1 = fen+"";
                            mm1 = miao+"";
                            textView1.setText(year+ "年" +month + "月" + day + "日" + fen + "分" + miao + "秒");
                        }
                        else
                        {
                            yyyy2 = year1+"";
                            MM2 = month1+"";
                            dd2 = day1+"";
                            HH2 = fen+"";
                            mm2 = miao+"";
                            textView2.setText(year1+ "年" +month1 + "月" + day1 + "日" + fen + "分" + miao + "秒");
                        }

                    }
                });
        loadingDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
        return loadingDialog;

    }


    public void newTaskWithOkHttp (String address, String name, String description, String
            starttime, String endtime){
        HttpUtil.newTaskWithOkHttp(address, name, description, starttime, endtime, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理

                Log.d("hhh", "111");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("hhh", "222");

                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                Log.d("qqqq", responseData);

                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    String result = jsonObject.getString("warning");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(NewTaskActivity.this, result, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private NumberPicker.OnValueChangeListener yearChangedListener = new NumberPicker.OnValueChangeListener() {

        @Override
        public void onValueChange(NumberPicker arg0, int arg1, int arg2) {

            year = numberpicker0.getValue();
            month = numberpicker2.getValue();

            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    numberpicker3.setMaxValue(31);
                    break;
                case 2:
                    if ((year%4==0&&year%100!=0)|| year%400==0)
                        numberpicker3.setMaxValue(29);
                    else
                        numberpicker3.setMaxValue(28);
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    numberpicker3.setMaxValue(30);
                    break;

                default:
                    break;
            }
        }

    };


    private NumberPicker.OnValueChangeListener monthChangedListener = new NumberPicker.OnValueChangeListener() {

        @Override
        public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
            // TODO Auto-generated method stub
            year = numberpicker0.getValue();
            month = numberpicker2.getValue();

            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    numberpicker3.setMaxValue(31);
                    break;
                case 2:
                    if ((year%4==0 && year%100!=0)|| year%400==0)
                        numberpicker3.setMaxValue(29);
                    else
                        numberpicker3.setMaxValue(28);
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    numberpicker3.setMaxValue(30);
                    break;

                default:
                    break;
            }
        }

    };

    private NumberPicker.OnValueChangeListener dayChangedListener = new NumberPicker.OnValueChangeListener() {

        @Override
        public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
            // TODO Auto-generated method stub
            if (a==1)
            {
                year = numberpicker0.getValue();
                month = numberpicker2.getValue();
                day = numberpicker3.getValue();
            }
            else
            {
                year1 = numberpicker0.getValue();
                month1 = numberpicker2.getValue();
                day1 = numberpicker3.getValue();
            }


        }

    };

    }

