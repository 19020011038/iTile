package com.example.itile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.itile.Util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PersonInfoActivity extends AppCompatActivity {

    private Button personinfo_add_frind;
    private TextView personinfo_name;
    private ImageView personinfo_icon;
    private TextView personinfo_tel;
    private TextView personinfo_email;
    private TextView personinfo_work;
    private TextView personinfo_age;
    private TextView personinfo_birthday;
    private TextView personinfo_constell;
    private TextView personinfo_intro;
    private ImageView personinfo_sex;
    private String person_id;
    private String result;
    private RelativeLayout personinfo_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_person_info);

        personinfo_add_frind = findViewById(R.id.personinfo_add_friend);
//        personinfo_add_frind.setVisibility(View.GONE);
        personinfo_name = findViewById(R.id.personinfo_name);
        personinfo_icon = findViewById(R.id.personinfo_icon);
        personinfo_tel = findViewById(R.id.personinfo_tel);
        personinfo_email = findViewById(R.id.personinfo_email);
        personinfo_back = findViewById(R.id.personinfo_back);
        personinfo_sex = findViewById(R.id.sex);
        personinfo_age = findViewById(R.id.personinfo_age);
        personinfo_work = findViewById(R.id.personinfo_work);
        personinfo_birthday = findViewById(R.id.personinfo_birthday);
        personinfo_constell = findViewById(R.id.personinfo_constell);
        personinfo_intro = findViewById(R.id.personinfo_intro);

        Intent intent = getIntent();
        person_id = intent.getStringExtra("friend_id");
        Log.i("zyr", "个人页接受personid="+person_id);

        personinfo_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        personinfo_add_frind.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addFriendWithOkhttp("http://118.190.245.170/worktile/friendinfo/"+ person_id+"/");
//                addFriendWithOkhttp("http://175.24.47.150:8088/worktile/friendinfo/"+ "7"+"/");
            }
        });
//        personInfoWithOkhttp("http://175.24.47.150:8088/worktile/friendinfo/"+ "7"+"/");
        personInfoWithOkhttp("http://118.190.245.170/worktile/friendinfo/"+ person_id+"/");
    }

    //实现登录
    public void addFriendWithOkhttp(String address){
        HttpUtil.addFriendWithOkHttp(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                Log.i( "zyr", " name : error");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PersonInfoActivity.this, "网络出现了问题...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                try{
                    JSONObject object = new JSONObject(responseData);
                    result = object.getString("warning");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.equals("1")){
                            Toast.makeText(PersonInfoActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                            personinfo_add_frind.setVisibility(View.GONE);
                        }else if (result!=null){
                            Toast.makeText(PersonInfoActivity.this, result, Toast.LENGTH_SHORT).show();
                            Log.i("zyr", "data信息："+responseData);
                        } else {
                            Toast.makeText(PersonInfoActivity.this,"添加失败，请稍后重试",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i( "zyr", "LLL"+responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PersonInfoActivity.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }//标签页
        });
    }

    //获得头像昵称
    public void personInfoWithOkhttp(String address){
        HttpUtil.homeNameOkHttp(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                Log.i( "zyr", " name : error");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PersonInfoActivity.this,"网络出现了问题...",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                try{
                    Log.i("zyr","data个人页："+responseData);
                    JSONObject object = new JSONObject(responseData);
                    JSONObject object1 = object.getJSONObject("friend");
                    String nickname = object1.getString("user_name");
                    String icon = object1.getString("avatar");
                    String telephone = object1.getString("telephone");
                    String email = object1.getString("email");
                    String is_friend = object.getString("isfriend");
                    String age_s = object1.getString("age");
                    String constell_s = object1.getString("constellation");
                    String work_s = object1.getString("profession");
                    String sex_s = object1.getString("gender");
                    String intro_s = object1.getString("introduction");
                    JSONObject birthday = object1.getJSONObject("birthday");
                    String month_s = birthday.getString("month");
                    String day_s = birthday.getString("day");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            personinfo_name.setText(nickname);
                            personinfo_tel.setText(telephone);
                            personinfo_email.setText(email);
                            if(is_friend.equals("1")){
                                personinfo_add_frind.setVisibility(View.GONE);
                            }
                            String birthday_all = month_s+"-"+day_s;
                            if (sex_s.equals("女"))
                                personinfo_sex.setBackgroundResource(R.drawable.womanpicture);
                            if (!work_s.equals("null"))
                                personinfo_work.setText(work_s);
                            else
                                personinfo_work.setText("未填写");
                            if (!month_s.equals("null"))
                                personinfo_birthday.setText(birthday_all);
                            else
                                personinfo_birthday.setText("未填写");
                            if (!intro_s.equals("null"))
                                personinfo_intro.setText(intro_s);
                            else
                                personinfo_intro.setText("未填写");
                            if (!constell_s.equals("null"))
                                personinfo_constell.setText(constell_s);
                            else
                                personinfo_constell.setText("");
                            if (!age_s.equals("null"))
                                personinfo_age.setText(age_s+"岁");
                            else
                                personinfo_age.setText("");
//                            Glide.with(PersonInfoActivity.this).load("http://175.24.47.150:8088/worktile/static/"+icon).into(personinfo_icon);
                            Glide.with(PersonInfoActivity.this).load("http://118.190.245.170/worktile/media/"+icon).into(personinfo_icon);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i( "zyr", "LLL"+responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PersonInfoActivity.this,"服务器访问失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }//标签页
        });
    }
}
