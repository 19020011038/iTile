package com.example.itile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itile.Util.HttpUtil;
import com.example.itile.Util.SharedPreferencesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText login_telephone;
    private EditText login_password;
    private TextView login_findpassword;
    private TextView login_register;
    private ImageButton login_finish;
    private SharedPreferencesUtil check;
    private String result;
    private String username;
    private String code;
    private String header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        //别忘了这句！！！！
        check = SharedPreferencesUtil.getInstance(getApplicationContext());
        login_telephone = findViewById(R.id.login_telephone);
        login_password = findViewById(R.id.login_password);
        login_finish = findViewById(R.id.login_finish);
        login_findpassword = findViewById(R.id.login_findpassword);
        login_register = findViewById(R.id.login_register);

        if (check.isLogin()){
            Intent intent2 = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent2);
            finish();
        }

        login_finish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String loginAddress="http://118.190.245.170/worktile/login/";
//                String loginAddress="http://175.24.47.150:8088/worktile/login/";
                String loginAccount = login_telephone.getText().toString();
                String loginPassword = login_password.getText().toString();
                Log.i("zyr","密码："+loginPassword);
                if (TextUtils.isEmpty(loginAccount)){
                    Toast.makeText(LoginActivity.this,"请输入手机号", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(loginPassword)){
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }else if (!(loginAccount.length()==11)){
                    Toast.makeText(LoginActivity.this, "手机号码应为11位", Toast.LENGTH_SHORT).show();
                }else {
                    loginWithOkHttp(loginAddress, loginAccount, loginPassword);
                }
            }
        });

        login_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        login_findpassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FindPasswordActivity.class);
                startActivity(intent);
            }
        });

        //防空格
        setEditTextInputSpace(login_telephone);
        setEditTextInputSpace(login_password);

        //控制最大长度
        int maxLengthUserName =11;
        int maxLengthPassword = 18;
        InputFilter[] fArray =new InputFilter[1];
        fArray[0]=new  InputFilter.LengthFilter(maxLengthUserName);
        login_telephone.setFilters(fArray);
        InputFilter[] fArray1 =new InputFilter[1];
        fArray1[0]=new  InputFilter.LengthFilter(maxLengthPassword);
        login_password.setFilters(fArray1);

    }

    //实现登录
    public void loginWithOkHttp(String address, final String account, final String password){
        HttpUtil.loginWithOkHttp(address,account,password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                Log.i( "zyr", " name : error");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "网络出现了问题...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                header = response.header("set-cookie");
//
                try{
                    JSONObject object = new JSONObject(responseData);
                    result = object.getString("warning");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i( "zyr", "LLL"+responseData);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.equals("1")){
                            String JSESSIONID=header.substring(0, 43);
                            Log.i("zyr","0");
                            Log.i("zyr","login_jsessionid:"+JSESSIONID);
                            check.setCookie(true);//设置已获得cookie
                            check.saveCookie(JSESSIONID);//保存获得的cookie
                            check.setLogin(true);  //设置登录状态为已登录
                            check.setAccountId(account);  //添加账户信息
                            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
//                        }else if (result.equals("用户名不存在")){
//                            Toast.makeText(LoginActivity.this,"该用户不存在",Toast.LENGTH_SHORT).show();
//                        }else if (result.equals("用户名或者密码错误")){
//                            Toast.makeText(LoginActivity.this,"用户名或者密码错误",Toast.LENGTH_SHORT).show();
//                        }else if (result.equals("该用户已经被冻结")){
//                            Toast.makeText(LoginActivity.this,"该用户尚未完成注册环节或找回密码，处于冻结状态",Toast.LENGTH_SHORT).show();
//                        }else if (result.equals("未提交全部参数")){
//                            Toast.makeText(LoginActivity.this,"用户名或密码为空",Toast.LENGTH_SHORT).show();
//                        }else if (result.equals("未提交POST请求")){
//                            Toast.makeText(LoginActivity.this,"提交请求失败",Toast.LENGTH_SHORT).show();
                        }else if (result!=null){
                            Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                            Log.i("zyr", "data信息："+responseData);
                        } else {
                            Toast.makeText(LoginActivity.this,"登录失败，请稍后重试",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }//标签页
        });
    }

    //防止空格回车
    public static void setEditTextInputSpace(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
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
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
