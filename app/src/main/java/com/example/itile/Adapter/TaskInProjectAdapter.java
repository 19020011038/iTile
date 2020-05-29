package com.example.itile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

public class TaskInProjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Map<String, Object>> list;
    private Context context;
    private int flag;

    public TaskInProjectAdapter(Context context, List<Map<String, Object>> list,int flag) {
        this.context = context;
        this.list = list;
        this.flag = flag;


        Log.d("52911",flag+"");
    }


    @Override
    public int getItemViewType(int position) {
        return Integer.valueOf(list.get(position).get("flag").toString());
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("52922",viewType+"");
        if (viewType == 0)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nothing, parent, false);

            return new TaskInProjectAdapter.NothingViewHolder(view);
        }

        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);

            return new TaskInProjectAdapter.ViewHolder(view);
        }

    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof TaskInProjectAdapter.NothingViewHolder){
            TaskInProjectAdapter.NothingViewHolder viewHolder = (TaskInProjectAdapter.NothingViewHolder) holder;

        }
        else {
            TaskInProjectAdapter.ViewHolder viewHolder = (TaskInProjectAdapter.ViewHolder) holder;
            String name = list.get(position).get("name").toString();
            String task_id = list.get(position).get("task_id").toString();
            String project_id = list.get(position).get("project_id").toString();

            viewHolder.mName.setText(name);

            viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TaskActivity.class);
                    intent.putExtra("project_id",project_id);
                    intent.putExtra("task_id",task_id);
                    context.startActivity(intent);
                }
            });
        }



    }



    class NothingViewHolder extends RecyclerView.ViewHolder {

        NothingViewHolder(@NonNull View itemView) {
            super(itemView);

        }
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


