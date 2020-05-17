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

public class ProjectHelperAdapter extends RecyclerView.Adapter<ProjectHelperAdapter.ViewHolder>{
    private List<Map<String, Object>> list;
    private Context context;

    public ProjectHelperAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProjectHelperAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_project_helper, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectHelperAdapter.ViewHolder holder, final int position) {
        holder.project_name.setText(list.get(position).get("name").toString());
        holder.a_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ProjectHelperAdapter.this.context,项目详情页.this);
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
        private  TextView project_name;
        private View a_project;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            project_name = itemView.findViewById(R.id.project_helper_name);
            a_project = itemView.findViewById(R.id.a_project);
        }
    }
}
