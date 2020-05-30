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

import java.util.List;
import java.util.Map;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.ViewHolder>{

    private List<Map<String, Object>> list;
    private Context context;

    public List<Map<String, Object>> getData() {
        return list;
    }

    public void setData(List<Map<String, Object>> list) {
        this.list = list;
    }

    public WorkAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public WorkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkAdapter.ViewHolder holder, final int position) {
        if(list.get(position).get("state").toString().equals("1")){
            holder.point.setImageResource(R.drawable.point2);
            holder.point.invalidate();
        }else {
            holder.point.setImageResource(R.drawable.point1);
            holder.point.invalidate();
        }
        holder.time1.setText(list.get(position).get("starttime").toString().substring(11,16));
        holder.time2.setText(list.get(position).get("endtime").toString().substring(11,16));
        holder.description.setText(list.get(position).get("description").toString());
        holder.a_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkAdapter.this.context, ScheduleDetailActivity.class);
                intent.putExtra("pk",list.get(position).get("pk").toString());
                intent.putExtra("position",String.valueOf(position));
                intent.putExtra("from","work");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView point;
        private TextView time1;
        private TextView time2;
        private TextView description;
        private View a_schedule;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            point = itemView.findViewById(R.id.work_point);
            time1 = itemView.findViewById(R.id.work_time1);
            time2 = itemView.findViewById(R.id.work_time2);
            description = itemView.findViewById(R.id.work_description);
            a_schedule = itemView.findViewById(R.id.a_schedule);
        }
    }
}
