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

public class ChangeNicknameActivity extends AppCompatActivity {

    private EditText change_nickname;
    private Button change_finish;
    private RelativeLayout change_back;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_change_nickname);

        change_nickname = findViewById(R.id.change_nickname);
        change_finish = findViewById(R.id.change_finish);
        change_back = findViewById(R.id.change_back);

        setEditTextInputSpace(change_nickname);
        //控制最大长度
        int maxLengthPassword = 10;
        InputFilter[] fArray =new InputFilter[1];
        fArray[0]=new  InputFilter.LengthFilter(maxLengthPassword);
        change_nickname.setFilters(fArray);

        change_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        change_finish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                String nicknameAddress="http://175.24.47.150:8088/worktile/userinfo/";
                String nicknameAddress="http://118.190.245.170/worktile/userinfo/";
                String new_nickname = change_nickname.getText().toString();
                if (TextUtils.isEmpty(new_nickname)){
                    Toast.makeText(ChangeNicknameActivity.this,"新昵称不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    Log.i("zyr","new_nickname:"+new_nickname);
                    nicknameWithOkHttp(nicknameAddress, new_nickname);
                }
            }
        });
    }

    //修改昵称
    public void nicknameWithOkHttp(String address, final String new_nickname){
        HttpUtil.nicknameWithOkHttp(address, new_nickname, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                Log.i( "zyr", " nicknameActivity : error");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChangeNicknameActivity.this, "网络出现了问题...", Toast.LENGTH_SHORT).show();
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
                            if (result.equals("1")) {
                                Toast.makeText(ChangeNicknameActivity.this, "昵称修改成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else if (result != null) {
                                Toast.makeText(ChangeNicknameActivity.this, result, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ChangeNicknameActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i( "zyr", "LLL"+responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChangeNicknameActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
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
