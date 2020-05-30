package com.example.itile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itile.Adapter.WorkAdapter;
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

public class NewScheduleActivity extends AppCompatActivity {
    private TextView sure;
    private EditText editText;
    private ImageView back;
    private Spinner mspinner_nian;
    private ArrayAdapter<String> adapter_nian;
    private String yyyy="2020";
    private String MM="05";
    private String dd="09";
    private String HH1="23";
    private String mm1="29";
    private String HH2="23";
    private String mm2="59";
    private String description;
    private String starttime;
    private String endtime;
    private String warning;

    private Spinner mspinner_yue;
    private ArrayAdapter<String> adapter_yue;
    private Spinner mspinner_ri;
    private ArrayAdapter<String> adapter_ri;
    private Spinner mspinner_shi1;
    private ArrayAdapter<String> adapter_shi1;
    private Spinner mspinner_fen1;
    private ArrayAdapter<String> adapter_fen1;
    private Spinner mspinner_shi2;
    private ArrayAdapter<String> adapter_shi2;
    private Spinner mspinner_fen2;
    private ArrayAdapter<String> adapter_fen2;

    private boolean flag1;
    private boolean flag2;
    private boolean flag3;
    private boolean flag4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_schedule);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Bundle bundle = getIntent().getExtras();
        yyyy = bundle.getString("year");
        MM = bundle.getString("month");
        dd = bundle.getString("day");
        back = (ImageView)findViewById(R.id.back_from_new_schedule);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        editText = (EditText)findViewById(R.id.new_schedule_description);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                for(int i=s.length();i>0;i--){
                    if(s.subSequence(i-1,i).toString().equals("\n"))
                        s.replace(i-1,i,"");
                }
            }
        });
        sure = (TextView)findViewById(R.id.new_schedule_sure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description = editText.getText().toString();
                if("".equals(description))
                    Toast.makeText(NewScheduleActivity.this,"请您输入日程详情",Toast.LENGTH_SHORT).show();
                else {
                    if (description.trim().isEmpty())
                    {
                        Toast.makeText(NewScheduleActivity.this, "日程详情不能全为空格！", Toast.LENGTH_SHORT).show();
                    }else {
                        if(!flag1 || !flag2)
                            Toast.makeText(NewScheduleActivity.this,"请选择开始时间",Toast.LENGTH_SHORT).show();
                        else if(!flag3 || !flag4)
                            Toast.makeText(NewScheduleActivity.this,"请选择结束时间",Toast.LENGTH_SHORT).show();
                        else
                            postNewSchedule("http://118.190.245.170/worktile/newschedule/",starttime,endtime,description);
                    }
                }
            }
        });
        starttime = yyyy+"-"+MM+"-"+dd+" "+HH1+"-"+mm1;
        endtime = yyyy+"-"+MM+"-"+dd+" "+HH2+"-"+mm2;

        //选择开始时
        String[] shi1 = new String[]{"时","00", "01", "02", "03", "04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
        //创建一个数组适配器
        adapter_shi1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, shi1);
        adapter_shi1.setDropDownViewResource(R.layout.spinner_drop);     //设置下拉列表框的下拉选项样式

        mspinner_shi1 = findViewById(R.id.spinner_HH1);
        mspinner_shi1.setAdapter(adapter_shi1);
        //点击事件
        mspinner_shi1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {



            private String positions;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                closeKeyBoard();
                positions = adapter_shi1.getItem(position);
                if (positions.equals("时")){
                    flag1 = false;
                }else{
                    flag1 = true;
                    HH1 = positions;
                    starttime = yyyy+"-"+MM+"-"+dd+" "+HH1+"-"+mm1;
                    endtime = yyyy+"-"+MM+"-"+dd+" "+HH2+"-"+mm2;

                }
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setVisibility(View.VISIBLE);
            }
        });

        //选择开始分
        String[] fen1 = new String[]{"分", "00","01", "02", "03", "04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59"};
        //创建一个数组适配器
        adapter_fen1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fen1);
        adapter_fen1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式

        mspinner_fen1 = findViewById(R.id.spinner_mm1);
        mspinner_fen1.setAdapter(adapter_fen1);
        //点击事件
        mspinner_fen1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            private String positions;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positions = adapter_fen1.getItem(position);
                closeKeyBoard();
                if (positions.equals("分")){
                    flag2 = false;
                }else{
                    flag2 = true;
                    mm1 = positions;
                    starttime = yyyy+"-"+MM+"-"+dd+" "+HH1+"-"+mm1;
                    endtime = yyyy+"-"+MM+"-"+dd+" "+HH2+"-"+mm2;

                }
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setVisibility(View.VISIBLE);
            }
        });

        //选择结束时
        String[] shi2 = new String[]{"时", "00","01", "02", "03", "04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
        //创建一个数组适配器
        adapter_shi2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, shi2);
        adapter_shi2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式

        mspinner_shi2 = findViewById(R.id.spinner_HH2);
        mspinner_shi2.setAdapter(adapter_shi2);
        //点击事件
        mspinner_shi2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            private String positions;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positions = adapter_shi2.getItem(position);
                closeKeyBoard();
                if (positions.equals("时")){
                    flag3 = false;
                }else{
                    flag3 = true;
                    HH2 = positions;
                    starttime = yyyy+"-"+MM+"-"+dd+" "+HH1+"-"+mm1;
                    endtime = yyyy+"-"+MM+"-"+dd+" "+HH2+"-"+mm2;

                }
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setVisibility(View.VISIBLE);
            }
        });

        //选择开始分
        String[] fen2 = new String[]{"分", "00","01", "02", "03", "04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59"};
        //创建一个数组适配器
        adapter_fen2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fen2);
        adapter_fen2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式

        mspinner_fen2 = findViewById(R.id.spinner_mm2);
        mspinner_fen2.setAdapter(adapter_fen2);
        //点击事件
        mspinner_fen2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            private String positions;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positions = adapter_fen2.getItem(position);
                closeKeyBoard();
                if (positions.equals("分")){
                    flag4 = false;
                }else{
                    flag4 = true;
                    mm2 = positions;
                    starttime = yyyy+"-"+MM+"-"+dd+" "+HH1+"-"+mm1;
                    endtime = yyyy+"-"+MM+"-"+dd+" "+HH2+"-"+mm2;

                }
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setVisibility(View.VISIBLE);
            }
        });
    }

    public void postNewSchedule(String address,String starttime,String endtime,String description) {
        HttpUtil.postNewSchedule(address,starttime,endtime,description, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(NewScheduleActivity.this, "网络出现了问题...", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("postNewCalendar",responseData);
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    warning = jsonObject.getString("warning");

                   runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(warning.equals("1")){
                                Toast.makeText(NewScheduleActivity.this,"新建日程成功",Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(NewScheduleActivity.this,warning,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (JSONException e) {

                    e.printStackTrace();

                }
            }
        });
    }


//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        closeKeyBoard();
//        return super.onTouchEvent(event);
//    }

    public void closeKeyBoard() {
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            View v = getCurrentFocus();
            closeSoftInput(this, v);
        }
    }
    // 关闭键盘输入法
    public static void closeSoftInput(Context context, View v) {
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

}
