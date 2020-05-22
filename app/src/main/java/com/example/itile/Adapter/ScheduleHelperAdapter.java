package com.example.itile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itile.R;
import com.example.itile.ScheduleDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScheduleHelperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public final int KongBai_View = 0;
    public final int Item_View = 1;
    List<Map<String, Object>> list = new ArrayList<>();
    private Context context;

    public List<Map<String, Object>> getData() {
        return list;
    }

    public void setData(List<Map<String, Object>> list) {
        this.list = list;
    }


    public ScheduleHelperAdapter(Context context, List<Map<String, Object>> list) {
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
            return new KongBaiViewHolder(view);
        } else  {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ScheduleHelperAdapter.KongBaiViewHolder){
            KongBaiViewHolder viewHolder = (KongBaiViewHolder)holder;
        }else {
            ItemViewHolder viewHolder = (ItemViewHolder)holder;
            if(list.get(position).get("state").toString().equals("1")){
                viewHolder.point.setImageResource(R.drawable.point2);
                viewHolder.point.invalidate();
            }else {
                viewHolder.point.setImageResource(R.drawable.point1);
                viewHolder.point.invalidate();
            }
            viewHolder.time1.setText(list.get(position).get("starttime").toString().substring(11,16));
            viewHolder.time2.setText(list.get(position).get("endtime").toString().substring(11,16));
            viewHolder.description.setText(list.get(position).get("description").toString());
            viewHolder.a_schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ScheduleHelperAdapter.this.context, ScheduleDetailActivity.class);
                    intent.putExtra("pk",list.get(position).get("pk").toString());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView point;
        private TextView time1;
        private TextView time2;
        private TextView description;
        private View a_schedule;


        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            point = itemView.findViewById(R.id.work_point);
            time1 = itemView.findViewById(R.id.work_time1);
            time2 = itemView.findViewById(R.id.work_time2);
            description = itemView.findViewById(R.id.work_description);
            a_schedule = itemView.findViewById(R.id.a_schedule);
        }
    }

    class KongBaiViewHolder extends RecyclerView.ViewHolder {

        KongBaiViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

}
