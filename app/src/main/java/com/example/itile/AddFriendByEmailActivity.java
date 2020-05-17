package com.example.itile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.itile.Adapter.AddFriendByTelAdapter;
import com.example.itile.Adapter.FriendAddressAdapter;
import com.example.itile.Util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddFriendByEmailActivity extends AppCompatActivity {

    private RelativeLayout tel_back;
    private RecyclerView recyclerView;
    private Map map;
    private AddFriendByTelAdapter mAdapter;
    private ImageButton tel_find;
    private EditText tel;
    private String telephone;
    private ProgressDialog progressDialog;

    List<Map<String, Object>> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_friend_by_email);

        tel_back = findViewById(R.id.addby_tel_back);
        recyclerView = findViewById(R.id.findby_tel_recyclerView);
        tel_find = findViewById(R.id.addby_tel_find);
        tel = findViewById(R.id.addby_tel);

        setEditTextInputSpace(tel);

        //控制最大长度
        int maxLengthTelephone =30;
        InputFilter[] fArray =new InputFilter[1];
        fArray[0]=new  InputFilter.LengthFilter(maxLengthTelephone);
        tel.setFilters(fArray);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        mAdapter = new AddFriendByTelAdapter(AddFriendByEmailActivity.this);
        recyclerView.setAdapter(mAdapter);

        tel_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tel_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telephone = tel.getText().toString();
                Log.i("zyr","telephone:"+telephone);
                if (TextUtils.isEmpty(telephone)) {
                    Toast.makeText(AddFriendByEmailActivity.this, "请输入邮箱~", Toast.LENGTH_SHORT).show();
                } else {
                    addFriendByEmailWithOkhttp("http://118.190.245.170/worktile/new-friend/email", telephone);
                    progressDialog = new ProgressDialog(AddFriendByEmailActivity.this);
//                    progressDialog.setTitle("正在搜索...");
                    progressDialog.setMessage("正在搜索......");
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                }
            }
        });
    }

    //获得头像昵称
    public void addFriendByEmailWithOkhttp(String address, String telephone) {
        HttpUtil.addFriendByEmailWithOkhttp(address, telephone, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                Log.i("zyr", " name : error");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(AddFriendByEmailActivity.this, "网络访问失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                try {
                    list.clear();
                    JSONObject object = new JSONObject(responseData);
                    Log.i("zyr","Tel"+responseData);
                    JSONArray jsonArray = object.getJSONArray("user_list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String name = jsonObject1.getString("username");  //头像
                        String icon = jsonObject1.getString("avatar");
                        String id = jsonObject1.getString("id");
                        String telephone = jsonObject1.getString("email");

                        map = new HashMap();

                        map.put("username", name);
                        map.put("avatar", icon);
                        map.put("id", id);
                        map.put("tel_or_email", telephone);

                        list.add(map);
                        Log.i("zyr", "shortcomment:list.size1111:" + list.size());
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
//                            recyclerView.setLayoutManager(new LinearLayoutManager(MyShortCommentsActivity.this));//纵向
////                            recyclerView.setAdapter(new MyShortCommentsAdapter(MyShortCommentsActivity.this, list));
                            mAdapter.setData(list);
                            mAdapter.notifyDataSetChanged();
//                            recyclerView.setNestedScrollingEnabled(false);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("zyr", "LLL" + responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            Toast.makeText(AddFriendByEmailActivity.this, "服务器访问失败", Toast.LENGTH_SHORT).show();
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

    //判断email格式是否正确
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
