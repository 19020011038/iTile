package com.example.itile.Fragment.Guide;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.itile.Adapter.HomeAdapter;
import com.example.itile.R;
import com.example.itile.SettingActivity;
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

public class PersonFragment extends Fragment {

    private ImageView setting;
    private ImageView home_icon;
    private TextView home_nickname;
    private TextView home_telephone;
    private TextView home_email;
    private TextView home_work;
    private TextView home_birthday;
    private TextView home_constell;
    private TextView home_intro;
    private TextView home_age;
    private String nameAddress;
    private ImageView home_setting;
    private ImageView sex;
    private String nickname;
    private String icon;
    private String telephone;
    private String email;

    private RecyclerView recyclerView;
    private Map map;
    private HomeAdapter mAdapter;

    List<Map<String, Object>> list = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_person, container, false);


        return root;

    }

    @Override
    public void onResume() {
        super.onResume();

        setting = getActivity().findViewById(R.id.home_setting);
        recyclerView = getActivity().findViewById(R.id.myhome_recyclerview);

        home_icon = getActivity().findViewById(R.id.home_icon);
        home_nickname = getActivity().findViewById(R.id.home_nickname);
        home_telephone = getActivity().findViewById(R.id.home_telephone);
        home_email = getActivity().findViewById(R.id.home_email);
        home_work = getActivity().findViewById(R.id.home_work);
        home_birthday = getActivity().findViewById(R.id.home_brithday);
        home_constell = getActivity().findViewById(R.id.home_constell);
        home_intro = getActivity().findViewById(R.id.home_intro);
        sex = getActivity().findViewById(R.id.sex);
        home_age = getActivity().findViewById(R.id.home_age);
        //获取用户名
        //username = check.getAccountId();
        nameAddress = "http://118.190.245.170/worktile/center/";
//        nameAddress = "http://175.24.47.150:8088/worktile/center/";

        homeNameOkHttp(nameAddress);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), SettingActivity.class); //从前者跳到后者，特别注意的是，在fragment中，用getActivity()来获取当前的activity
                getActivity().startActivity(intent);
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        mAdapter = new HomeAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);
    }

    //获得头像昵称
    public void homeNameOkHttp(String address){
        HttpUtil.homeNameOkHttp(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                Log.i( "zyr", " name : error");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"网络出现了问题...",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                try{
                    list.clear();
                    Log.i("zyr","data个人中心："+responseData);
                    JSONObject object = new JSONObject(responseData);
                    JSONObject object1 = object.getJSONObject("user");
                    nickname = object1.getString("user_name");
                    icon = object1.getString("avatar");
                    telephone = object1.getString("telephone");
                    email = object1.getString("email");
                    String work_s = object1.getString("profession");
                    String contell_s = object1.getString("constellation");
                    String sex_s = object1.getString("gender");
                    String intro_s = object1.getString("introduction");
                    String age_s = object1.getString("age");
                    Log.i("zyr", "HomeActivity.icon_url:"+icon);
                    JSONObject birthday = object1.getJSONObject("birthday");
                    String year_s = birthday.getString("year");
                    String month_s = birthday.getString("month");
                    String day_s = birthday.getString("day");
                    JSONArray jsonArray = object.getJSONArray("schedule");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject object3 = jsonObject1.getJSONObject("fields");
                        String task_id = jsonObject1.getString("pk");
//                int news_id = jsonObject1.getInt("news_id");
//                        String status = jsonObject1.getString("status");
                        String time1 = object3.getString("starttime");  //头像
                        String time2 = object3.getString("endtime");
                        String d = object3.getString("description");
                        String state = object3.getString("state");
//                        String book_name = jsonObject1.getString("title");
//                        String book_photo = jsonObject1.getString("image");
//                        String comment_id = jsonObject1.getString("id");
                        map = new HashMap();

//                        map.put("status", status);
                        map.put("time1", time1);
                        map.put("time2", time2);
                        map.put("d", d);
                        map.put("state", state);
                        map.put("task_id", task_id);
//                        map.put("book_num", book_num);
//                        map.put("book_photo", book_photo);
//                        map.put("comment_id", comment_id);

                        list.add(map);
                        Log.i("zyr", "shortcomment:list.size1111:"+list.size());
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            home_nickname.setText(nickname);
                            home_telephone.setText(telephone);
                            home_email.setText(email);
                            String birthday_all = year_s+"-"+month_s+"-"+day_s;
                            if (sex_s.equals("女"))
                                sex.setBackgroundResource(R.drawable.womanpicture);
                            else
                                sex.setBackgroundResource(R.drawable.manpicture);
                            if (!work_s.equals("null"))
                                home_work.setText(work_s);
                            else
                                home_work.setText("未填写");
                            if (!year_s.equals("null"))
                                home_birthday.setText(birthday_all);
                            else
                                home_birthday.setText("未填写");
                            if (!intro_s.equals("null"))
                                home_intro.setText(intro_s);
                            else
                                home_intro.setText("未填写");
                            if (!contell_s.equals("null"))
                                home_constell.setText(contell_s);
                            else
                                home_constell.setText("");
                            if (!age_s.equals("null"))
                                home_age.setText(age_s+"岁");
                            else
                                home_age.setText("");
//                            Glide.with(HomeActivity.this).load("http://175.24.47.150:8088/worktile/static/"+icon).into(home_icon);
                            Glide.with(getActivity()).load("http://118.190.245.170/worktile/media/"+icon).into(home_icon);
                            Log.i("zyr", "shortcomment:list.size:"+list.size());
//                            recyclerView.setLayoutManager(new LinearLayoutManager(MyShortCommentsActivity.this));//纵向
////                            recyclerView.setAdapter(new MyShortCommentsAdapter(MyShortCommentsActivity.this, list));
                            mAdapter.setData(list);
                            mAdapter.notifyDataSetChanged();
                            recyclerView.setNestedScrollingEnabled(false);  //防止两层滑动冲突
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i( "zyr", "LLL"+responseData);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"服务器访问失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }//标签页
        });
    }
}
