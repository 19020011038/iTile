package com.example.itile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.itile.ProjectActivity;
import com.example.itile.R;
import com.example.itile.TaskActivity;

import java.util.List;
import java.util.Map;

public class TaskInProjectAdapter extends RecyclerView.Adapter<TaskInProjectAdapter.ViewHolder>{

    private List<Map<String, Object>> list;
    private Context context;

    public TaskInProjectAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TaskInProjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_project, parent, false);
        return new TaskInProjectAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TaskInProjectAdapter.ViewHolder holder, final int position) {
        String name = list.get(position).get("name").toString();
        String task_id = list.get(position).get("task_id").toString();
        String project_id = list.get(position).get("project_id").toString();

        holder.mName.setText(name);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TaskActivity.class);
                intent.putExtra("project_id",project_id);
                intent.putExtra("task_id",task_id);
                context.startActivity(intent);
            }
        });


    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private RelativeLayout relativeLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.name);
            relativeLayout = itemView.findViewById(R.id.re);

        }

    }
}


