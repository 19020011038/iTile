package com.example.itile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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

        find_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        find_get_emailnum.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                String registerAddress="http://118.190.245.170/worktile/register";
                String registerAddress="http://175.24.47.150:8088/worktile/findback";
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


    }

    //忘记密码
    public void findPasswordWithOkHttp1(String address, String telephone){
        HttpUtil.findPasswordWithOkHttp1(address, telephone, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                Log.i("zyr","regiser.error");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                header = response.header("set-cookie");
                try{
                    JSONObject object = new JSONObject(responseData);
                    result = object.getString("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i( "zyr", "find.error2okhttp:"+responseData);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.equals("1")) {
                            String JSESSIONID = header.substring(0, 43);
                            Log.i("zyr", "0");
                            Log.i("zyr", "register_jsessionid:" + JSESSIONID);
                            check.setCookie(true);//设置已获得cookie
                            check.saveCookie(JSESSIONID);//保存获得的cookie
                            Toast.makeText(FindPasswordActivity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
                        }else if (result.equals("该用户已经被冻结")) {
                            Toast.makeText(FindPasswordActivity.this, "该用户已经被冻结", Toast.LENGTH_SHORT).show();
                        }else if (result.equals("两次密码不一致'")) {
                            Toast.makeText(FindPasswordActivity.this, "两次密码不一致'", Toast.LENGTH_SHORT).show();
                        }else if (result.equals("新密码格式不正确")) {
                            Toast.makeText(FindPasswordActivity.this, "新密码格式不正确", Toast.LENGTH_SHORT).show();
                        }else if (result.equals("用户名与邮箱不匹配")) {
                            Toast.makeText(FindPasswordActivity.this, "用户名与邮箱不匹配", Toast.LENGTH_SHORT).show();
                        }else if (result.equals("用户名不存在")) {
                            Toast.makeText(FindPasswordActivity.this, "用户名不存在", Toast.LENGTH_SHORT).show();
                            Log.i("zyr","register.error:"+result);
                        }else if (result.equals("未提交全部参数")) {
                            Toast.makeText(FindPasswordActivity.this, "未提交全部参数", Toast.LENGTH_SHORT).show();
                        }else if (result.equals("未提交POST请求")) {
                            Toast.makeText(FindPasswordActivity.this, "提交请求失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
