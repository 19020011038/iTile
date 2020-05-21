package com.example.itile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itile.Util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChangeSonTaskInfoActivity extends AppCompatActivity {

    private String[] areas = new String[]{"未开始","进行中", "已完成"};
    private RadioOnClick radioOnClick = new RadioOnClick(1);
    private ListView areaRadioListView;
    private Button radioButton;
    private TextView state;
    private int lastChoose;
    private String string_choose;
    private RelativeLayout change_state;
    private TextView change_info;
    private EditText info;
    private RelativeLayout back;
    private String sontask_id;
    private String task_id;
    private String result2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_change_son_task_info);

        change_state = findViewById(R.id.change_state);
        state = findViewById(R.id.state);
        change_info = findViewById(R.id.finish_info);
        info = findViewById(R.id.info);
        back = findViewById(R.id.back);

        setEditTextInputSpace(info);

        //控制最大长度
        int maxLength = 100;
        InputFilter[] fArray =new InputFilter[1];
        fArray[0]=new  InputFilter.LengthFilter(maxLength);
        info.setFilters(fArray);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        change_state.setOnClickListener(new RadioClickListener());

        Intent intent = getIntent();
        string_choose = intent.getStringExtra("state");
        sontask_id = intent.getStringExtra("subtask_id");
        task_id = intent.getStringExtra("task_id");

        lastChoose = Integer.valueOf(string_choose);
        radioOnClick.setIndex(lastChoose);

        if (lastChoose==0)
            state.setText("未开始");
        else if(lastChoose == 1)
            state.setText("进行中");
        else
            state.setText("已完成");

        change_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Address="http://118.190.245.170/worktile/task/"+task_id+"/subtask/"+sontask_id+"/description";
                String getInfo = info.getText().toString();
                if (TextUtils.isEmpty(getInfo)){
                    Toast.makeText(ChangeSonTaskInfoActivity.this,"介绍不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    changeProjectInfoWithOkHttp(Address, getInfo);
                }
            }
        });
    }

    class RadioClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            AlertDialog ad =new AlertDialog.Builder(ChangeSonTaskInfoActivity.this).setTitle("修改状态").setSingleChoiceItems(areas,lastChoose,radioOnClick).create();
            areaRadioListView=ad.getListView();
            ad.show();
        }
    }
    /**
     * 点击单选框事件
     * @author xmz
     *
     */
    class RadioOnClick implements DialogInterface.OnClickListener{
        private int index;

        public RadioOnClick(int index){
            this.index = index;
        }
        public void setIndex(int index){
            this.index=index;
        }
        public int getIndex(){
            return index;
        }
        public String getStringIndex(){
            return String.valueOf(index);
        }
        public void onClick(DialogInterface dialog, int whichButton){
            setIndex(whichButton);
//            if (aaa%2==0) {
//                lastChoose = radioOnClick.getIndex();
//                Toast.makeText(MainActivity.this, "修改成功！您已经选择了： " + lastChoose + ":" + areas[lastChoose], Toast.LENGTH_SHORT).show();
//            }else{
//                Toast.makeText(MainActivity.this, "修改失败，请稍后重试 " + lastChoose + ":" + areas[lastChoose], Toast.LENGTH_SHORT).show();
//            }
//            aaa++;
//            if (lastChoose==0)
//                state.setText("未完成");
//            else if (lastChoose == 1)
//                state.setText("进行中");
//            else
//                state.setText("已完成");
//            Toast.makeText(MainActivity.this, "您已经选择了： " + index + ":" + areas[index], Toast.LENGTH_LONG).show();
            ChangeProjectStateWithOkhttp("http://118.190.245.170/worktile/task/"+task_id+"/subtask/"+sontask_id+"/state", radioOnClick.getStringIndex());
            dialog.dismiss();
        }
    }

    public void ChangeProjectStateWithOkhttp(final String url, String string_state){
        HttpUtil.changeProjectStateWithOkhttp( url,string_state, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                Log.i("zyr", " name : error");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChangeSonTaskInfoActivity.this, "网络出现了问题...", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                try {
                    JSONObject object = new JSONObject(responseData);
                    String result = object.getString("warning");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("POST成功")){
                                lastChoose = radioOnClick.getIndex();
                                if (lastChoose==0)
                                    state.setText("未开始");
                                else if(lastChoose == 1)
                                    state.setText("进行中");
                                else
                                    state.setText("已完成");
                                Toast.makeText(ChangeSonTaskInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(ChangeSonTaskInfoActivity.this, "修改失败，请稍后重试", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("zyr", "LLL" + responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChangeSonTaskInfoActivity.this, "无法连接到服务器", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }//标签页
        });
    }

    public void changeProjectInfoWithOkHttp(String address, final String getInfo){
        HttpUtil.changeProjectInfoWithOkhttp(address, getInfo, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                Log.i( "zyr", " nicknameActivity : error");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChangeSonTaskInfoActivity.this, "网络出现了问题...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                try{
                    JSONObject object = new JSONObject(responseData);
                    result2 = object.getString("warning");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result2.equals("POST成功")) {
                                Toast.makeText(ChangeSonTaskInfoActivity.this, "简介修改成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ChangeSonTaskInfoActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i( "zyr", "LLL"+responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChangeSonTaskInfoActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
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
}
