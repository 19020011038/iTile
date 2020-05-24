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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.YELLOW;

public class ProjectHelperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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


    public ProjectHelperAdapter(Context context, List<Map<String, Object>> list) {
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
            return new ProjectHelperAdapter.KongBaiViewHolder(view);
        } else  {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project_helper, parent, false);
            return new ProjectHelperAdapter.ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ProjectHelperAdapter.KongBaiViewHolder){
            ProjectHelperAdapter.KongBaiViewHolder viewHolder = (ProjectHelperAdapter.KongBaiViewHolder) holder;

        }else {
            ProjectHelperAdapter.ItemViewHolder viewHolder = (ProjectHelperAdapter.ItemViewHolder) holder;
            viewHolder.p_name.setText(list.get(position).get("name").toString());
            viewHolder.a_project.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProjectHelperAdapter.this.context, ProjectActivity.class);
                    intent.putExtra("project_id",list.get(position).get("pk").toString());
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
        public TextView p_name;
        public View a_project;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            p_name = itemView.findViewById(R.id.project_helper_name);
            a_project = itemView.findViewById(R.id.a_project);
        }
    }
}
