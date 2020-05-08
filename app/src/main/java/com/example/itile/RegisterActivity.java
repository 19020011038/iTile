package com.example.itile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.itile.Util.HttpUtil;
import com.example.itile.Util.SharedPreferencesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText register_nickname;
    private EditText register_password;
    private EditText register_email;
    private EditText register_telephone;
    private EditText register_repassword;
    private EditText register_emailnum;
    private Button register_get_emailnum;
    private Button register_finish;
    private SharedPreferencesUtil check;
    private RelativeLayout register_back;
    private String header;
    private String result;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);

        register_nickname = findViewById(R.id.register_nickname);
        register_email = findViewById(R.id.register_email);
        register_password = findViewById(R.id.register_password);
        register_repassword = findViewById(R.id.register_repassword);
        register_telephone = findViewById(R.id.register_telephone);
        register_emailnum = findViewById(R.id.register_email_num);
        register_get_emailnum = findViewById(R.id.register_get_emailnum);
        register_finish = findViewById(R.id.register_finish);
        check = SharedPreferencesUtil.getInstance(getApplicationContext());
        register_back = findViewById(R.id.register_back);

        setEditTextInputSpace(register_nickname);
        setEditTextInputSpace(register_email);
        setEditTextInputSpace(register_password);
        setEditTextInputSpace(register_repassword);
        setEditTextInputSpace(register_telephone);
        setEditTextInputSpace(register_emailnum);

        //控制最大长度
        int maxLengthUserName =10;
        int maxLengthPassword = 15;
        int maxLengthEmailNum = 4;
        int maxLengthEmail = 25;
        int maxLengthTelephone = 11;
        InputFilter[] fArray =new InputFilter[1];
        fArray[0]=new  InputFilter.LengthFilter(maxLengthUserName);
        register_nickname.setFilters(fArray);
        InputFilter[] fArray1 =new InputFilter[1];
        fArray1[0]=new  InputFilter.LengthFilter(maxLengthPassword);
        InputFilter[] fArray2 =new InputFilter[1];
        fArray2[0]=new  InputFilter.LengthFilter(maxLengthEmailNum);
        InputFilter[] fArray3 =new InputFilter[1];
        fArray3[0]=new  InputFilter.LengthFilter(maxLengthTelephone);
        InputFilter[] fArray4 =new InputFilter[1];
        fArray4[0]=new  InputFilter.LengthFilter(maxLengthEmail);
        register_password.setFilters(fArray1);
        register_repassword.setFilters(fArray1);
        register_email.setFilters(fArray4);
        register_emailnum.setFilters(fArray2);
        register_telephone.setFilters(fArray3);

        register_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        register_get_emailnum.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String registerAddress="http://118.190.245.170/worktile/register/";
//                String registerAddress="http://175.24.47.150:8088/worktile/register/";
                String registerAccount = register_nickname.getText().toString();
                String registerEmail = register_email.getText().toString();
                String registerPassword = register_password.getText().toString();
                String registerRepassword = register_repassword.getText().toString();
                String registerTelephone = register_telephone.getText().toString();
                if (TextUtils.isEmpty(registerAccount)){
                    Toast.makeText(RegisterActivity.this,"请输入昵称", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(registerEmail)){
                    Toast.makeText(RegisterActivity.this, "请输入邮箱", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(registerPassword)){
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(registerRepassword)){
                    Toast.makeText(RegisterActivity.this, "请确认密码", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(registerTelephone)){
                    Toast.makeText(RegisterActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                }else if (!(registerTelephone.length()==11)){
                    Toast.makeText(RegisterActivity.this, "手机号应为11位", Toast.LENGTH_SHORT).show();
//                }else if (!(isMobileNO(registerTelephone))){
//                    Toast.makeText(RegisterActivity.this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
                }else if (!(isEmail(registerEmail))){
                    Toast.makeText(RegisterActivity.this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
                }else {
                    if (!registerPassword.equals(registerRepassword)){
                        Toast.makeText(RegisterActivity.this,"密码与确认密码不相符", Toast.LENGTH_SHORT).show();
                    } else {
                        if (registerPassword.length() < 6) {
                            Toast.makeText(RegisterActivity.this,"密码长度不能小于6位", Toast.LENGTH_SHORT).show();
                        } else {
                            registerWithOkHttp1(registerAddress, registerAccount, registerEmail, registerPassword, registerRepassword, registerTelephone);
                            progressDialog = new ProgressDialog(RegisterActivity.this);
                            progressDialog.setTitle("加载中");
                            progressDialog.setMessage("正在加载中......");
                            progressDialog.setCancelable(true);
                            progressDialog.show();
                        }}
                }
            }
        });

        register_finish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String registerAddress="http://118.190.245.170/worktile/register/";
//                String registerAddress="http://175.24.47.150:8088/worktile/register/";
                String registerAccount = register_nickname.getText().toString();
                String registerEmail = register_email.getText().toString();
                String registerPassword = register_password.getText().toString();
                String registerRepassword = register_repassword.getText().toString();
                String registerTelephone = register_telephone.getText().toString();
                String registerEmailNum = register_emailnum.getText().toString();
                if (TextUtils.isEmpty(registerAccount)){
                    Toast.makeText(RegisterActivity.this,"请输入昵称", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(registerEmail)){
                    Toast.makeText(RegisterActivity.this, "请输入邮箱", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(registerPassword)){
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(registerRepassword)){
                    Toast.makeText(RegisterActivity.this, "请确认密码", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(registerTelephone)){
                    Toast.makeText(RegisterActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                }else if (!(registerTelephone.length()==11)){
                    Toast.makeText(RegisterActivity.this, "手机号应为11位", Toast.LENGTH_SHORT).show();
                }else if (!(isEmail(registerEmail))){
                    Toast.makeText(RegisterActivity.this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(registerEmailNum)){
                    Toast.makeText(RegisterActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                } else {
                    if (!registerPassword.equals(registerRepassword)){
                        Toast.makeText(RegisterActivity.this,"密码与确认密码不相符", Toast.LENGTH_SHORT).show();
                    } else {
                        if (registerPassword.length() < 6) {
                            Toast.makeText(RegisterActivity.this, "密码长度不能小于6位", Toast.LENGTH_SHORT).show();
                        } else {
                            registerWithOkHttp2(registerAddress, registerAccount, registerEmail, registerPassword, registerRepassword, registerTelephone, registerEmailNum);
                            progressDialog = new ProgressDialog(RegisterActivity.this);
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

    //实现注册1
    public void registerWithOkHttp1(String address,String account,String email, String password, String repassword, String telephone){
        HttpUtil.registerWithOkHttp1(address, account, email, password, repassword, telephone,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                Log.i("zyr","regiser.error");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "网络出现了问题...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                header = response.header("set-cookie");
                try{
                    JSONObject object = new JSONObject(responseData);
//                    result = object.getString("result");
                    result = object.getString("warning");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            if (result.equals("1")) {
                            if (result.equals("验证码已发送")) {
                                String JSESSIONID = header.substring(0, 43);
                                Log.i("zyr", "0");
                                Log.i("zyr", "register_jsessionid:" + JSESSIONID);
                                check.setCookie(true);//设置已获得cookie
                                check.saveCookie(JSESSIONID);//保存获得的cookie
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
//                            }else if (result.equals("邮箱格式不正确")) {
//                                progressDialog.dismiss();
//                                Toast.makeText(RegisterActivity.this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
//                            }else if (result.equals("密码格式不正确")) {
//                                progressDialog.dismiss();
//                                Toast.makeText(RegisterActivity.this, "密码格式不正确", Toast.LENGTH_SHORT).show();
//                            }else if (result.equals("用户名格式不正确")) {
//                                progressDialog.dismiss();
//                                Toast.makeText(RegisterActivity.this, "用户名格式不正确", Toast.LENGTH_SHORT).show();
//                            }else if (result.equals("用户名已经存在")) {
//                                progressDialog.dismiss();
//                                Toast.makeText(RegisterActivity.this, "用户名已经存在", Toast.LENGTH_SHORT).show();
//                            }else if (result.equals("未提交全部参数")) {
//                                progressDialog.dismiss();
//                                Toast.makeText(RegisterActivity.this, "信息提交不全", Toast.LENGTH_SHORT).show();
//                                Log.i("zyr","register.error:"+result);
//                            }else if (result.equals("两次密码不一致")) {
//                                progressDialog.dismiss();
//                                Toast.makeText(RegisterActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
//                            }else if (result.equals("未提交POST请求")) {
//                                progressDialog.dismiss();
//                                Toast.makeText(RegisterActivity.this, "提交请求失败", Toast.LENGTH_SHORT).show();
//                            }else if (result.equals("邮箱发送失败")) {
//                                progressDialog.dismiss();
//                                Toast.makeText(RegisterActivity.this, "邮箱发送失败", Toast.LENGTH_SHORT).show();
                            }else if (result!=null){
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_SHORT).show();
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i( "zyr", "register.error2okhttp:"+responseData);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "1服务器连接失败", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }

    //实现注册2
    public void registerWithOkHttp2(String address,String account,String email, String password, String repassword, String telephone, String emailnum){
        HttpUtil.registerWithOkHttp2(address, account, email, password, repassword, telephone,emailnum,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                Log.i("zyr","regiser.error");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "网络出现了问题...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
//                header = response.header("set-cookie");
                try{
                    JSONObject object = new JSONObject(responseData);
                    result = object.getString("warning");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("1")) {
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }else if (result!=null){
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_SHORT).show();
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i( "zyr", "register.error2okhttp:"+responseData);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "2服务器连接失败", Toast.LENGTH_SHORT).show();

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

    //判断手机格式是否正确
    public boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }
    //判断email格式是否正确
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
