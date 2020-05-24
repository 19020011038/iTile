package com.example.itile;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NewTaskActivity extends AppCompatActivity {

    private EditText name;
    private EditText tip;
    private SharedPreferencesUtil check;

    private String project_id;
    private Spinner mspinner_nian1;
    private Spinner mspinner_nian2;
    private ArrayAdapter<String> adapter_nian;
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
private ImageView back;
    private TextView finish;

    private Spinner mspinner_yue1;
    private ArrayAdapter<String> adapter_yue1;
    private Spinner mspinner_ri1;
    private ArrayAdapter<String> adapter_ri1;
    private Spinner mspinner_shi1;
    private ArrayAdapter<String> adapter_shi1;
    private Spinner mspinner_fen1;
    private ArrayAdapter<String> adapter_fen1;

    private Spinner mspinner_yue2;
    private ArrayAdapter<String> adapter_yue2;
    private Spinner mspinner_ri2;
    private ArrayAdapter<String> adapter_ri2;
    private Spinner mspinner_shi2;
    private ArrayAdapter<String> adapter_shi2;
    private Spinner mspinner_fen2;
    private ArrayAdapter<String> adapter_fen2;
    String[] nian = new String[]{"年", "2018", "2019", "2020", "2021","2022","2023"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_task);

        Intent intent = getIntent();
        project_id = intent.getStringExtra("project_id");

        check = SharedPreferencesUtil.getInstance(getApplicationContext());
        finish = findViewById(R.id.finish);
        name = findViewById(R.id.name);
        tip = findViewById(R.id.tip);

        tip.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
                for(int i = s.length(); i > 0; i--){

                    if(s.subSequence(i-1, i).toString().equals("\n"))
                        s.replace(i-1, i, "");
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

        finish = (TextView) findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                starttime = yyyy1 + "-" + MM1 + "-" + dd1 + " " + HH1 + ":" + mm1 ;
                endtime = yyyy2 + "-" + MM2 + "-" + dd2 + " " + HH2 + ":" + mm2 ;


                Log.d("showstr",starttime);
                Log.d("showstr",endtime);



                String name1 = name.getText().toString();
                String tip1 = tip.getText().toString();
                if (TextUtils.isEmpty(name1))
                {
                    Toast.makeText(NewTaskActivity.this, "任务名称不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(tip1))
                {
                    Toast.makeText(NewTaskActivity.this, "任务描述不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(yyyy1=="a" || MM1=="a" ||dd1=="a" ||HH1=="a" ||mm1=="a" )
                    Toast.makeText(NewTaskActivity.this,"请选择开始时间",Toast.LENGTH_SHORT).show();
                else if(yyyy2=="a" ||MM2=="a" ||dd2=="a" ||HH2=="a" ||mm2=="a" )
                    Toast.makeText(NewTaskActivity.this,"请选择结束时间",Toast.LENGTH_SHORT).show();
                else {

                    newTaskWithOkHttp("http://118.190.245.170/worktile/project/"+project_id+"/new-task",name1,tip1,starttime,endtime);
                    Log.d("time",starttime);
                    Log.d("time",endtime);
                    Toast.makeText(NewTaskActivity.this, "开始"+starttime+"\n"+"结束"+endtime, Toast.LENGTH_SHORT).show();
                    finish();

                }
            }
        });



        //创建一个数组适配器
        adapter_nian = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nian);
        adapter_nian.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式

        mspinner_nian1 = findViewById(R.id.spinner_nian1);
        mspinner_nian1.setAdapter(adapter_nian);
        //点击事件
        mspinner_nian1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            private String positions;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positions = adapter_nian.getItem(position);
                if (positions.equals("年")){
                    //
                }else{
                    yyyy1 = positions;

                    Toast.makeText(NewTaskActivity.this,yyyy1,Toast.LENGTH_SHORT).show();
                }
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setVisibility(View.VISIBLE);
            }
        });

        mspinner_nian2 = findViewById(R.id.spinner_nian2);
        mspinner_nian2.setAdapter(adapter_nian);
        //点击事件
        mspinner_nian2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            private String positions;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positions = adapter_nian.getItem(position);
                if (positions.equals("年")){
                }else{
                    yyyy2 = positions;

                    Toast.makeText(NewTaskActivity.this,yyyy2,Toast.LENGTH_SHORT).show();
                }
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setVisibility(View.VISIBLE);
            }
        });



        //选择月
        String[] yue = new String[]{"月", "01", "02", "03", "04","05","06","07","08","09","10","11","12"};
        //创建一个数组适配器
        adapter_yue1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yue);
        adapter_yue1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式

        mspinner_yue1 = findViewById(R.id.spinner_yue1);
        mspinner_yue1.setAdapter(adapter_yue1);
        //点击事件
        mspinner_yue1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            private String positions;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positions = adapter_yue1.getItem(position);
                if (positions.equals("月")){
                    //
                }else{
                    MM1 = positions;

                    Toast.makeText(NewTaskActivity.this,MM1,Toast.LENGTH_SHORT).show();
                }
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setVisibility(View.VISIBLE);
            }
        });

        adapter_yue2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yue);
        adapter_yue2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式

        mspinner_yue2 = findViewById(R.id.spinner_yue2);
        mspinner_yue2.setAdapter(adapter_yue2);
        //点击事件
        mspinner_yue2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            private String positions;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positions = adapter_yue2.getItem(position);
                if (positions.equals("月")){
                    //
                }else{
                    MM2 = positions;

                    Toast.makeText(NewTaskActivity.this,MM2,Toast.LENGTH_SHORT).show();
                }
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setVisibility(View.VISIBLE);
            }
        });

        //选择开始日
        String[] ri = new String[]{"日", "01", "02", "03", "04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
        //创建一个数组适配器
        adapter_ri1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ri);
        adapter_ri1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式

        mspinner_ri1 = findViewById(R.id.spinner_ri1);
        mspinner_ri1.setAdapter(adapter_ri1);
        //点击事件
        mspinner_ri1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            private String positions;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positions = adapter_ri1.getItem(position);
                if (positions.equals("日")){
                    //
                }else{
                    dd1 = positions;

                    Toast.makeText(NewTaskActivity.this,dd1,Toast.LENGTH_SHORT).show();
                }
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setVisibility(View.VISIBLE);
            }
        });

        //结束日
        adapter_ri2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ri);
        adapter_ri2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式

        mspinner_ri2 = findViewById(R.id.spinner_ri2);
        mspinner_ri2.setAdapter(adapter_ri2);
        //点击事件
        mspinner_ri2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            private String positions;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positions = adapter_ri2.getItem(position);
                if (positions.equals("日")){
                    //
                }else{
                    dd2 = positions;

                    Toast.makeText(NewTaskActivity.this,dd2,Toast.LENGTH_SHORT).show();
                }
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setVisibility(View.VISIBLE);
            }
        });


        //选择开始时
        String[] shi1 = new String[]{"时", "01", "02", "03", "04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24"};
        //创建一个数组适配器
        adapter_shi1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, shi1);
        adapter_shi1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式

        mspinner_shi1 = findViewById(R.id.spinner_HH1);
        mspinner_shi1.setAdapter(adapter_shi1);
        //点击事件
        mspinner_shi1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            private String positions;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positions = adapter_shi1.getItem(position);
                if (positions.equals("时")){
                    //
                }else{
                    HH1 = positions;

                    Toast.makeText(NewTaskActivity.this,HH1,Toast.LENGTH_SHORT).show();
                }
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setVisibility(View.VISIBLE);
            }
        });




        //选择结束时
        String[] shi2 = new String[]{"时", "01", "02", "03", "04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24"};
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
                if (positions.equals("时")){
                    //
                }else{
                    HH2 = positions;

                    Toast.makeText(NewTaskActivity.this,HH2,Toast.LENGTH_SHORT).show();
                }
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setVisibility(View.VISIBLE);
            }
        });

         //选择开始分
        String[] fen1 = new String[]{"分", "01", "02", "03", "04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60"};
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
                if (positions.equals("分")){
                    //
                }else{
                    mm1 = positions;

                    Toast.makeText(NewTaskActivity.this,mm1,Toast.LENGTH_SHORT).show();
                }
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setVisibility(View.VISIBLE);
            }
        });


        //选择结束分
        String[] fen2 = new String[]{"分", "01", "02", "03", "04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60"};
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
                if (positions.equals("分")){
                    //
                }else{
                    mm2 = positions;

                    Toast.makeText(NewTaskActivity.this,mm2,Toast.LENGTH_SHORT).show();
                }
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setVisibility(View.VISIBLE);
            }
        });
    }
    public void newTaskWithOkHttp(String address, String name, String description,String starttime,String endtime){
        HttpUtil.newTaskWithOkHttp(address, name,description, starttime,endtime,new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        //在这里对异常情况进行处理

                        Log.d("hhh","111");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("hhh","222");

                        //得到服务器返回的具体内容
                        final String responseData = response.body().string();
                        Log.d("qqqq",responseData);

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

    }

