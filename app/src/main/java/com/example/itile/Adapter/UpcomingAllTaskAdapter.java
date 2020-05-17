package com.example.itile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itile.R;

import java.util.List;
import java.util.Map;

public class UpcomingAllTaskAdapter extends RecyclerView.Adapter<UpcomingAllTaskAdapter.ViewHolder>{

    private List<Map<String, Object>> list;
    private Context context;

    public UpcomingAllTaskAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UpcomingAllTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_all_task_upcoming, parent, false);
        return new UpcomingAllTaskAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull UpcomingAllTaskAdapter.ViewHolder holder, final int position) {
        String name = list.get(position).get("name").toString();
        String id = list.get(position).get("id").toString();

        holder.mName.setText(name);

//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, TaskActivity.class);
//                intent.putExtra("task_id",id);
//                context.startActivity(intent);
//            }
//        });


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
            mName = itemView.findViewById(R.id.task_name);
            relativeLayout = itemView.findViewById(R.id.re);




        }

    }
}


