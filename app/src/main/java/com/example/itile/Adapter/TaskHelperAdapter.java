package com.example.itile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itile.R;
import com.example.itile.ScheduleDetailActivity;
import com.example.itile.ScheduleHelperActivity;

import java.util.List;
import java.util.Map;

public class TaskHelperAdapter extends RecyclerView.Adapter<TaskHelperAdapter.ViewHolder>{
    private List<Map<String, Object>> list;
    private Context context;

    public TaskHelperAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TaskHelperAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task_helper, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHelperAdapter.ViewHolder holder, final int position) {
        holder.task_name.setText(list.get(position).get("name").toString());
        holder.a_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ProjectHelperAdapter.this.context,任务详情页.this);
//                intent.putExtra("这里写你用的字段名",list.get(position).get("pk").toString());
//                context.startActivity(intent);
//                在这里写跳转，我已经写了一部分了
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private  TextView task_name;
        private View a_task;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            task_name = itemView.findViewById(R.id.task_helper_name);
            a_task = itemView.findViewById(R.id.a_task);
        }
    }
}
