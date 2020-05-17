package com.example.itile;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.itile.Adapter.AllProjectAdapter;
import com.example.itile.Adapter.FormAdapter;
import com.example.itile.Adapter.ScreenSlidePagerAdapter;
import com.example.itile.Util.HttpUtil;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FormActivity extends AppCompatActivity {

    private ImageView back;
    private String id;
    private String name;
    private String rate;
    private String alltask;
    private String notstart;
    private String isgoing;
    private String ended;
    private List<Map<String, Object>> list = new ArrayList<>();
    private RecyclerView recyclerView;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_form);

        back = findViewById(R.id.back);
        recyclerView = findViewById(R.id.recyclerView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FormWithOkHttp("http://118.190.245.170/worktile/all-project");


    }


    public void FormWithOkHttp(String address) {
        HttpUtil.ShowAllProjectWithOkHttp(address, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                //       Toast.makeText(getActivity(),"获取图书信息失败，请检查您的网络",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONArray jsonArray = jsonObject.getJSONArray("projects");

                    for (int i = 0; i < jsonArray.length(); i++) {




                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        name = jsonObject1.getString("name");
                        id = jsonObject1.getString("id");
                        rate = jsonObject1.getString("rate");
                        alltask = jsonObject1.getString("alltask");
                        notstart = jsonObject1.getString("notstart");
                        isgoing = jsonObject1.getString("isgoing");
                        ended = jsonObject1.getString("ended");

                        Map map = new HashMap();

                        map.put("name", name);
                        map.put("id",id);
                        map.put("rate",rate);
                        map.put("alltask",alltask);
                        map.put("notstart",notstart);
                        map.put("isgoing",isgoing);
                        map.put("ended",ended);


                        list.add(map);
                    }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                recyclerView.setLayoutManager(new LinearLayoutManager(FormActivity.this));//垂直排列 , Ctrl+P
                                recyclerView.setAdapter(new FormAdapter(FormActivity.this, list));//绑定适配器

                            }
                        });
//

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
