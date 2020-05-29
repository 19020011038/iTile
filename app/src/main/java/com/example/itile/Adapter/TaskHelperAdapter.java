package com.example.itile.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itile.ProjectActivity;
import com.example.itile.R;
import com.example.itile.ScheduleDetailActivity;
import com.example.itile.ScheduleHelperActivity;
import com.example.itile.TaskActivity;
import com.example.itile.Util.HttpUtil;

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

public class TaskHelperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Map<String, Object>> list = new ArrayList<>();
    private Context context;
    public final int KongBai_View = 2;
    public final int Item_View = 1;
    private int delete_position;
    private Handler handler;

    public List<Map<String, Object>> getData() {
        return list;
    }

    public void setData(List<Map<String, Object>> list) {
        this.list = list;
    }

    public TaskHelperAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.valueOf(list.get(position).get("type").toString());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == KongBai_View) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kongbai, parent, false);
            return new TaskHelperAdapter.KongBaiViewHolder(view);
        } else  {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_helper, parent, false);
            return new TaskHelperAdapter.ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof TaskHelperAdapter.KongBaiViewHolder){
            TaskHelperAdapter.KongBaiViewHolder viewHolder = (TaskHelperAdapter.KongBaiViewHolder) holder;

        }else {
            TaskHelperAdapter.ItemViewHolder viewHolder = (TaskHelperAdapter.ItemViewHolder) holder;
            viewHolder.t_name.setText(list.get(position).get("name").toString());
            viewHolder.a_task.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TaskHelperAdapter.this.context, TaskActivity.class);
                    intent.putExtra("task_id",list.get(position).get("pk").toString());
                    intent.putExtra("project_id",list.get(position).get("project").toString());
                    context.startActivity(intent);
                }
            });
            viewHolder.a_task.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(TaskHelperAdapter.this.context);
                    dialog.setTitle("是否删除该任务？");
                    dialog.setMessage("若删除任务请点击确定（只会在消息模块中删除）");
                    dialog.setCancelable(false);
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            delete_position = position;
                            postDelete("http://118.190.245.170/worktile/taskhelper/", list.get(position).get("pk").toString());
                        }
                    });
                    dialog.show();
                    return true;
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class KongBaiViewHolder extends RecyclerView.ViewHolder {

        KongBaiViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView t_name;
        public View a_task;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            t_name = itemView.findViewById(R.id.task_helper_name);
            a_task = itemView.findViewById(R.id.a_task);
        }
    }

    public void postDelete(String address, String pk) {
        HttpUtil.postDelete(address, pk, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("项目助手页删除post", responseData);
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    String warning = jsonObject.getString("warning");
                    if (warning.equals("1")) {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        });
        handler = new Handler(context.getMainLooper()) {
            public void handleMessage(Message message) {
                super.handleMessage(message);
                if (true) {
                    list.remove(delete_position);
                    if(list.size() == 0){
                        Map map = new HashMap();
                        map.put("type",2);
                        list.add(map);
                    }
                    notifyDataSetChanged();
                    Toast.makeText(TaskHelperAdapter.this.context, "删除成功！", Toast.LENGTH_SHORT).show();

                }
            }
        };
    }
}
