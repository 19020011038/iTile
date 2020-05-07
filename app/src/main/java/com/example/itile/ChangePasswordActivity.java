package com.example.itile;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText change_oldpassword;
    private EditText change_newpassword;
    private EditText change_repassword;
    private Button changepassword_finish;
    private RelativeLayout change_back;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_change_password);

        change_oldpassword = findViewById(R.id.password_old);
        change_newpassword = findViewById(R.id.password_new);
        change_repassword = findViewById(R.id.password_renew);
        changepassword_finish = findViewById(R.id.password_finish);
        change_back = findViewById(R.id.password_back);

        //控制最大长度
        int maxLengthPassword = 15;
        InputFilter[] fArray =new InputFilter[1];
        fArray[0]=new  InputFilter.LengthFilter(maxLengthPassword);
        change_oldpassword.setFilters(fArray);
        change_newpassword.setFilters(fArray);
        change_repassword.setFilters(fArray);

        setEditTextInputSpace(change_oldpassword);
        setEditTextInputSpace(change_newpassword);
        setEditTextInputSpace(change_repassword);

        change_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        changepassword_finish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                String passwordAddress="http://175.24.47.150:8088/worktile/changepassword/";
                String passwordAddress="http://118.190.245.170/worktile/changepassword/";
                String old_password = change_oldpassword.getText().toString();
                String new_password = change_newpassword.getText().toString();
                String renew_password = change_repassword.getText().toString();
                if (TextUtils.isEmpty(old_password)){
                    Toast.makeText(ChangePasswordActivity.this,"请输入原密码", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(new_password)){
                    Toast.makeText(ChangePasswordActivity.this, "请输入新密码", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(renew_password)){
                    Toast.makeText(ChangePasswordActivity.this, "请确认新密码", Toast.LENGTH_SHORT).show();
                }else {
                    if (!new_password.equals(renew_password)){
                        Toast.makeText(ChangePasswordActivity.this,"新密码与确认密码不相符", Toast.LENGTH_SHORT).show();
                    } else {
                        if (new_password.length() < 6) {
                            Toast.makeText(ChangePasswordActivity.this,"新密码长度不能小于6位", Toast.LENGTH_SHORT).show();
                        } else {
                            ChangePasswordWithOkHttp(passwordAddress,old_password,new_password,renew_password);
                        }
                    }
                }
            }
        });
    }

    //更改密码
    public void ChangePasswordWithOkHttp(String address, final String old_password, final String new_password, final String renew_password){
        HttpUtil.ChangePasswordWithOkHttp(address,old_password,new_password,renew_password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                Log.i( "zyr", " error : ChangePasswordError");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChangePasswordActivity.this, "网络出现了问题...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                try{
                    JSONObject object = new JSONObject(responseData);
                    result = object.getString("warning");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("1")) {
                                Toast.makeText(ChangePasswordActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else if (result != null) {
                                Toast.makeText(ChangePasswordActivity.this, result, Toast.LENGTH_SHORT).show();
                                Log.i("zyr", "error:ChangePasswordActivity post请求提交失败");
                            } else {
                                Toast.makeText(ChangePasswordActivity.this, "发送失败1", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i( "zyr", "LLL"+responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChangePasswordActivity.this, "发送失败2", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
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
