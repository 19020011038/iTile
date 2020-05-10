package com.example.itile;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.itile.Util.HttpUtil;
import com.example.itile.Util.SharedPreferencesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NewProjectActivity extends AppCompatActivity {

    private EditText name;
    private EditText tip;
    private TextView finish;
    private String result;
    private String header;
    private SharedPreferencesUtil check;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_project);
        check = SharedPreferencesUtil.getInstance(getApplicationContext());
        finish = findViewById(R.id.finish);
        name = findViewById(R.id.name);
        tip = findViewById(R.id.tip);
        //输入框属性
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

        finish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String name1 = name.getText().toString();
                String tip1 = tip.getText().toString();
                if (TextUtils.isEmpty(name1))
                {
                    Toast.makeText(NewProjectActivity.this, "项目名称不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(tip1))
                {
                    Toast.makeText(NewProjectActivity.this, "项目描述不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {


                newProjectWithOkHttp("http://118.190.245.170/worktile/new-project",name1,tip1);

                finish();


                }

            }
        });


//        tip.setOnEditorActionListener(new TextView.OnEditorActionListener(){
//
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
//
//                return(event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
//
//            }
//
//        });



    }
    //修改昵称
    public void newProjectWithOkHttp(String address,String name, String description){
        HttpUtil.newProjectWithOkHttp(address, name,description, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(NewProjectActivity.this, "网络出现了问题...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(NewProjectActivity.this, "123123", Toast.LENGTH_SHORT).show();

                        }
                    });

            }//标签页
        });
    }
}
