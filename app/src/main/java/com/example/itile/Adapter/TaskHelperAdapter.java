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

import com.example.itile.ProjectActivity;
import com.example.itile.R;
import com.example.itile.ScheduleDetailActivity;
import com.example.itile.ScheduleHelperActivity;
import com.example.itile.TaskActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskHelperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Map<String, Object>> list = new ArrayList<>();
    private Context context;
    public final int KongBai_View = 2;
    public final int Item_View = 1;

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
}
