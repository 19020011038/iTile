package com.example.itile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itile.Adapter.ScheduleHelperAdapter;
import com.example.itile.Adapter.SearchAdapter;
import com.example.itile.Util.HttpUtil;

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

public class SearchActivity extends AppCompatActivity {
    private SearchView searchView;
    private RecyclerView recyclerView;
    List<Map<String, Object>> list = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search);
        searchView = (SearchView)findViewById(R.id.search);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_search);
        searchView.setSubmitButtonEnabled(true);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("搜索内容");
        searchView.requestFocus();
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.equals(""))
                    Toast.makeText(SearchActivity.this,"请您输入搜索内容",Toast.LENGTH_SHORT).show();
                else
                list.clear();
                postSearch("http://118.190.245.170/worktile/helper/",query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
    }
    public void postSearch(String address,String search) {
        HttpUtil.postSearch(address, search,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("搜索页Post", responseData);
                try {
                    Map map1 = new HashMap();
                    map1.put("type",0);
                    list.add(map1);
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONArray jsonArray = jsonObject.getJSONArray("project");
                    JSONArray jsonArray2 = jsonObject.getJSONArray("task");
                    if(String.valueOf(jsonArray).equals("[]") && String.valueOf(jsonArray2).equals("[]")){
                        Map map9 = new HashMap();
                        map9.put("type",3);
                        list.add(map9);
                    }else {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject schedule = jsonArray.getJSONObject(i);
                            String pk = schedule.getString("pk");
                            JSONObject fields = schedule.getJSONObject("fields");
                            String p_name = fields.getString("name");
                            String description = fields.getString("description");
                            String s_state = fields.getString("state");

                            Map map = new HashMap();
                            map.put("pk", pk);
                            map.put("name", p_name);
                            map.put("description",description);
                            map.put("state",s_state);
                            map.put("type",1);

                            list.add(map);
                        }
                        for (int i = 0; i < jsonArray2.length(); i++) {
                            JSONObject schedule = jsonArray.getJSONObject(i);
                            String pk = schedule.getString("pk");
                            JSONObject fields = schedule.getJSONObject("fields");
                            String starttime = fields.getString("starttime");
                            String endtime = fields.getString("endtime");
                            String p_name = fields.getString("name");
                            String description = fields.getString("description");
                            String s_state = fields.getString("state");

                            Map map = new HashMap();
                            map.put("pk", pk);
                            map.put("starttime", starttime);
                            map.put("endtime", endtime);
                            map.put("name", p_name);
                            map.put("description",description);
                            map.put("state",s_state);
                            map.put("type",2);

                            list.add(map);
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));//垂直排列 , Ctrl+P
                            recyclerView.setAdapter(new SearchAdapter(SearchActivity.this, list));//绑定适配器
                        }
                    });
                } catch (JSONException e) {

                    e.printStackTrace();

                }
            }
        });
    }
}
