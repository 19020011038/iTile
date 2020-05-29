package com.example.itile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.itile.ProjectActivity;
import com.example.itile.R;

import java.util.List;
import java.util.Map;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.ViewHolder>{

    private List<Map<String, Object>> list;
    private Context context;

    public FormAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FormAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_form, parent, false);
        return new FormAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull FormAdapter.ViewHolder holder, final int position) {
        String name = list.get(position).get("name").toString();
        String id = list.get(position).get("id").toString();
        String rate = list.get(position).get("rate").toString();
        String alltask = list.get(position).get("alltask").toString();
        String notstart = list.get(position).get("notstart").toString();
        String isgoing = list.get(position).get("isgoing").toString();
        String ended = list.get(position).get("ended").toString();
        int int_rate = Integer.valueOf(rate);


        holder.mName.setText(name);
        holder.textView1.setText(notstart);
        holder.textView2.setText(isgoing);
        holder.textView3.setText(ended);
        holder.rate.setText(rate+"%");

        holder.pbar.setMax(100);
        holder.pbar.setProgress(int_rate);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProjectActivity.class);
                intent.putExtra("project_id",id);
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
        private TextView textView1;
        private TextView textView2;
        private TextView textView3;
        private TextView rate;
        private ProgressBar pbar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.name);
            textView1 = itemView.findViewById(R.id.text1);
            textView2 = itemView.findViewById(R.id.text2);
            textView3 = itemView.findViewById(R.id.text3);
            rate = itemView.findViewById(R.id.rate);
            relativeLayout = itemView.findViewById(R.id.re);
            pbar = itemView.findViewById(R.id.pbar);

        }

    }
}


