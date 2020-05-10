package com.example.itile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.itile.PersonInfoActivity;
import com.example.itile.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddFriendByTelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Map<String, Object>> list = new ArrayList<>();
    private Context context;
    private String result;
    private Handler mHandler;
    private Handler mHandler_f;
    public static final int ONE_ITEM = 1;
    public static final int TWO_ITEM = 2;

    public AddFriendByTelAdapter(Context context) {
        this.context = context;

    }

    public void setData(List<Map<String, Object>> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.size() == 0) {
            return TWO_ITEM;
        } else {
            return ONE_ITEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TWO_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_addfriend_white, parent, false);
            return new HomeWhiteViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_addfriend_tel, parent, false);
            return new RecyclerViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RecyclerViewHolder) {
            RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
            recyclerViewHolder.name.setText(list.get(position).get("username").toString());
            final String icon = list.get(position).get("avatar").toString();
            Glide.with(context).load("http://118.190.245.170/worktile/static/"+icon).into(recyclerViewHolder.icon);
            final String friend_id = list.get(position).get("id").toString();
            recyclerViewHolder.tel.setText(list.get(position).get("tel_or_email").toString());
            recyclerViewHolder.all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PersonInfoActivity.class);
                    intent.putExtra("friend_id", friend_id);
                    context.startActivity(intent);
                }
            });
        } else if (holder instanceof HomeWhiteViewHolder) {
            HomeWhiteViewHolder homeWhiteViewHolder = (HomeWhiteViewHolder) holder;
        }
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView tel;
        private TextView name;
        private ImageView icon;
        private RelativeLayout all;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tel = itemView.findViewById(R.id.addby_tel);
            name = itemView.findViewById(R.id.addby_tel_name);
            icon = itemView.findViewById(R.id.addby_tel_icon);
            all = itemView.findViewById(R.id.addby_tel_all);
        }
    }

    class HomeWhiteViewHolder extends RecyclerView.ViewHolder {
        private TextView white_store;

        HomeWhiteViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

    @Override
    public int getItemCount() {
        if (list.size() > 0) {
            return list.size();
        } else {
            return 1;
        }
    }


}
