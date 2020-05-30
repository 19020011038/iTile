package com.example.itile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.itile.Util.HttpUtil;
import com.example.itile.Util.SharedPreferencesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FindPasswordActivity extends AppCompatActivity {

    private EditText find_telephone;
    private EditText find_email_num;
    private EditText find_newpassword;
    private EditText find_repassword;
    private Button find_get_emailnum;
    private Button find_finish;
    private RelativeLayout find_back;
    private SharedPreferencesUtil check;
    private ProgressDialog progressDialog;
    private String result;
    private String header;
    private TimeCount time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_find_password);

        //别忘了这句！！！！
        check = SharedPreferencesUtil.getInstance(getApplicationContext());
        find_telephone = findViewById(R.id.find_telephone);
        find_email_num = findViewById(R.id.find_email_num);
        find_newpassword = findViewById(R.id.find_newpassword);
        find_repassword = findViewById(R.id.find_repassword);
        find_get_emailnum = findViewById(R.id.find_get_emailnum);
        find_finish = findViewById(R.id.find_finish);
        find_back = findViewById(R.id.find_back);

        setEditTextInputSpace(find_telephone);
        setEditTextInputSpace(find_email_num);
        setEditTextInputSpace(find_newpassword);
        setEditTextInputSpace(find_repassword);

        //控制最大长度
        int maxLengthPassword = 15;
        int maxLengthEmailNum = 4;
        int maxLengthTelephone = 11;
        InputFilter[] fArray =new InputFilter[1];
        fArray[0]=new  InputFilter.LengthFilter(maxLengthPassword);
        find_newpassword.setFilters(fArray);
        find_repassword.setFilters(fArray);
        InputFilter[] fArray1 =new InputFilter[1];
        fArray1[0]=new  InputFilter.LengthFilter(maxLengthEmailNum);
        find_email_num.setFilters(fArray1);
        InputFilter[] fArray2 =new InputFilter[1];
        fArray2[0]=new  InputFilter.LengthFilter(maxLengthTelephone);
        find_telephone.setFilters(fArray2);

        find_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        time = new TimeCount(60000, 1000);
        find_get_emailnum.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String registerAddress="http://118.190.245.170/worktile/findback/";
//                String registerAddress="http://175.24.47.150:8088/worktile/findback/";
                String registerTelephone = find_telephone.getText().toString();
                if (TextUtils.isEmpty(registerTelephone)){
                    Toast.makeText(FindPasswordActivity.this,"请输入手机号", Toast.LENGTH_SHORT).show();
                }else if (!(registerTelephone.length()==11)){
                    Toast.makeText(FindPasswordActivity.this, "手机号应为11位", Toast.LENGTH_SHORT).show();
                }else {
                    findPasswordWithOkHttp1(registerAddress, registerTelephone);
                    progressDialog = new ProgressDialog(FindPasswordActivity.this);
                    progressDialog.setTitle("加载中");
                    progressDialog.setMessage("正在加载中......");
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                }
            }
        });

        find_finish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String registerAddress="http://118.190.245.170/worktile/findback/";
//                String registerAddress="http://175.24.47.150:8088/worktile/findback/";
//                String registerAccount = register_nickname.getText().toString();
//                String registerEmail = register_email.getText().toString();
                String registerPassword = find_newpassword.getText().toString();
                String registerRepassword = find_repassword.getText().toString();
                String registerTelephone = find_telephone.getText().toString();
                String registerEmailNum = find_email_num.getText().toString();
//                if (TextUtils.isEmpty(registerAccount)){
//                    Toast.makeText(RegisterActivity.this,"请输入昵称", Toast.LENGTH_SHORT).show();
//                }else if (TextUtils.isEmpty(registerEmail)){
//                    Toast.makeText(RegisterActivity.this, "请输入邮箱", Toast.LENGTH_SHORT).show();
//                }else
                if (TextUtils.isEmpty(registerPassword)){
                    Toast.makeText(FindPasswordActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(registerRepassword)){
                    Toast.makeText(FindPasswordActivity.this, "请确认密码", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(registerTelephone)){
                    Toast.makeText(FindPasswordActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                }else if (!(registerTelephone.length()==11)){
                    Toast.makeText(FindPasswordActivity.this, "手机号应为11位", Toast.LENGTH_SHORT).show();
//                }else if (!(isEmail(registerEmail))){
//                    Toast.makeText(FindPasswordActivity.this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(registerEmailNum)){
                    Toast.makeText(FindPasswordActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                } else {
                    if (!registerPassword.equals(registerRepassword)){
                        Toast.makeText(FindPasswordActivity.this,"密码与确认密码不相符", Toast.LENGTH_SHORT).show();
                    } else {
                        if (registerPassword.length() < 6) {
                            Toast.makeText(FindPasswordActivity.this, "密码长度不能小于6位", Toast.LENGTH_SHORT).show();
                        } else {
                            findPasswordWithOkHttp2(registerAddress, registerTelephone, registerPassword, registerRepassword, registerEmailNum);
                            progressDialog = new ProgressDialog(FindPasswordActivity.this);
                            progressDialog.setTitle("加载中");
                            progressDialog.setMessage("正在加载中......");
                            progressDialog.setCancelable(true);
                            progressDialog.show();
                        }
                    }
                }
            }
        });
    }

    //忘记密码1
    public void findPasswordWithOkHttp1(String address, String telephone){
        HttpUtil.findPasswordWithOkHttp1(address, telephone, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                Log.i("zyr","regiser.error");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(FindPasswordActivity.this, "网络出现了问题...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                header = response.header("set-cookie");
                try{
                    JSONObject object = new JSONObject(responseData);
                    result = object.getString("warning");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        if (result.equals("验证码已发送")) {
                            String JSESSIONID = header.substring(0, 43);
                            Log.i("zyr", "0");
                            Log.i("zyr", "register_jsessionid:" + JSESSIONID);
                            check.setCookie(true);//设置已获得cookie
                            check.saveCookie(JSESSIONID);//保存获得的cookie
                            time.start();
                            Toast.makeText(FindPasswordActivity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
                        }else if (result!=null){
                            Toast.makeText(FindPasswordActivity.this, result, Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(FindPasswordActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i( "zyr", "find.error2okhttp:"+responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            Toast.makeText(FindPasswordActivity.this, "1服务器连接失败", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }

    //忘记密码2
    public void findPasswordWithOkHttp2(String address, String telephone, String password, String repassword, String email_num){
        HttpUtil.findPasswordWithOkHttp2(address, telephone,password, repassword, email_num, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                Log.i("zyr","regiser.error");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(FindPasswordActivity.this, "网络出现了问题...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                try{
                    JSONObject object = new JSONObject(responseData);
                    result = object.getString("warning");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            if (result.equals("1")) {
                                Toast.makeText(FindPasswordActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(FindPasswordActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }else if (result!=null){
                                Toast.makeText(FindPasswordActivity.this, result, Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(FindPasswordActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i( "zyr", "find.error2okhttp:"+responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            Toast.makeText(FindPasswordActivity.this, "2服务器连接失败", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }

    //防止空格回车
    public static void setEditTextInputSpace(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(" ")) {
                    String[] str = s.toString().split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    editText.setText(str1);
                    editText.setSelection(start);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
//            btnGetcode.setBackgroundColor(Color.parseColor("#B6B6D8"));
            find_get_emailnum.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_shape2));
            find_get_emailnum.setClickable(false);
            find_get_emailnum.setText("("+millisUntilFinished / 1000 +") 秒后可重新发送");
        }

        @Override
        public void onFinish() {
            find_get_emailnum.setText("重新获取验证码");
            find_get_emailnum.setClickable(true);
            find_get_emailnum.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_shape));

        }
    }
}
