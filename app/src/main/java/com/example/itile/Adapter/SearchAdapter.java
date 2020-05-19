package com.example.itile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.itile.R;

import java.util.List;
import java.util.Map;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.YELLOW;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Map<String, Object>> list;
    private Context context;
    public final int Task_View = 2;
    public final int Project_View = 1;
    public final int Title_View = 0;
    public final int KongBai_View = 3;


    public SearchAdapter(Context context, List<Map<String, Object>> list) {
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
        if (viewType == Project_View) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_project, parent, false);
            return new SearchProjectViewHolder(view);
        } else if(viewType == Task_View){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_task, parent, false);
            return new SearchTaskViewHolder(view);
        }else if(viewType == Title_View){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_search, parent, false);
            return new TitleViewHolder(view);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kongbai, parent, false);
            return new KongBaiViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof SearchProjectViewHolder){
            SearchProjectViewHolder viewHolder = (SearchProjectViewHolder) holder;
            viewHolder.p_name.setText(list.get(position).get("name").toString());
            viewHolder.p_description.setText(list.get(position).get("description").toString());
            viewHolder.p_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(SearchAdapter.this.context,详情.class);
//                    intent.putExtra(字段名,list.get(position).get("pk").toString());
//                    context.startActivity(intent);
                }
            });
            if(list.get(position).get("state").toString().equals("0")){
                viewHolder.p_state.setText("未完成");
                viewHolder.p_state.setTextColor(RED);
            }else {
                viewHolder.p_state.setText("已完成");
                viewHolder.p_state.setTextColor(GREEN);
            }
        }else if(holder instanceof  SearchTaskViewHolder){
            SearchTaskViewHolder viewHolder = (SearchTaskViewHolder) holder;
            viewHolder.t_name.setText(list.get(position).get("name").toString());
            viewHolder.t_description.setText(list.get(position).get("description").toString());
            viewHolder.t_time.setText(list.get(position).get("starttime").toString().substring(0,4)+"."+list.get(position).get("starttime").toString().substring(5,7)+"."+list.get(position).get("starttime").toString().substring(8,10)+" "+list.get(position).get("starttime").toString().substring(11,13)+"："+list.get(position).get("starttime").toString().substring(14,16)+"~"+list.get(position).get("endtime").toString().substring(0,4)+"."+list.get(position).get("endtime").toString().substring(5,7)+"."+list.get(position).get("endtime").toString().substring(8,10)+" "+list.get(position).get("endtime").toString().substring(11,13)+"："+list.get(position).get("endtime").toString().substring(14,16));
            viewHolder.t_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(SearchAdapter.this.context,详情.class);
//                    intent.putExtra(字段名,list.get(position).get("pk").toString());
//                    context.startActivity(intent);
                }
            });
            if(list.get(position).get("state").toString().equals("0")){
                viewHolder.t_state.setText("未开始");
                viewHolder.t_state.setTextColor(RED);
            }else if(list.get(position).get("state").toString().equals("1")){
                viewHolder.t_state.setText("进行中");
                viewHolder.t_state.setTextColor(YELLOW);
            }else {
                viewHolder.t_state.setText("已完成");
                viewHolder.t_state.setTextColor(GREEN);
            }
        }else if(holder instanceof TitleViewHolder){
            TitleViewHolder viewHolder = (TitleViewHolder) holder;
        }else {
            KongBaiViewHolder viewHolder = (KongBaiViewHolder)holder;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.search_title);
        }
    }


    class SearchProjectViewHolder extends RecyclerView.ViewHolder {
        public TextView p_name;
        public  TextView p_description;
        public View p_view;
        public TextView p_state;

        SearchProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            p_name = itemView.findViewById(R.id.search_project_name);
            p_description = itemView.findViewById(R.id.search_project_description);
            p_view = itemView.findViewById(R.id.a_search_project);
            p_state = itemView.findViewById(R.id.search_project_state);
        }

    }

    class SearchTaskViewHolder extends RecyclerView.ViewHolder {
        public TextView t_name;
        public  TextView t_time;
        public  TextView t_description;
        public View t_view;
        public TextView t_state;

        SearchTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            t_name = itemView.findViewById(R.id.search_task_name);
            t_time = itemView.findViewById(R.id.search_task_time);
            t_description = itemView.findViewById(R.id.search_task_description);
            t_view = itemView.findViewById(R.id.a_search_task);
            t_state = itemView.findViewById(R.id.search_task_state);
        }

    }

    class KongBaiViewHolder extends RecyclerView.ViewHolder {

        KongBaiViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }


}
