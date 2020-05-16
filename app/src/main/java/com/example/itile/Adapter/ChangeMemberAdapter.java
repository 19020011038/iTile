package com.example.itile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
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

public class ChangeMemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Map<String, Object>> list = new ArrayList<>();
    private Context context;
    private String result;
    private Handler mHandler;
    private Handler mHandler_f;
    public static final int ONE_ITEM = 1;
    public static final int TWO_ITEM = 2;
    public static final int THREE_ITEM = 3;
    public static final int FOUR_ITEM = 4;
    //解决holder复用问题
    private List<Integer> favorList=new ArrayList<>(); //用法：favorList.add(123);

    public ChangeMemberAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Map<String, Object>> list, List<Integer> favorList) {
        this.list = list;
        this.favorList = favorList;
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
            View view = LayoutInflater.from(context).inflate(R.layout.item_nofriend, parent, false);
            return new HomeWhiteViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_change_member, parent, false);
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
            final String friend_id = list.get(position).get("friend_id").toString();
            recyclerViewHolder.cbox.setTag(new Integer(friend_id));
            if(favorList.contains(recyclerViewHolder.cbox.getTag()))
                recyclerViewHolder.cbox.setChecked(true);
            else
                recyclerViewHolder.cbox.setChecked(false);

            recyclerViewHolder.all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(favorList.contains(recyclerViewHolder.cbox.getTag())) {
                        recyclerViewHolder.cbox.setChecked(false);
                        favorList.remove(new Integer(friend_id));
                        Log.i("zyr", "新减少了："+friend_id);
                    }
                    else {
                        recyclerViewHolder.cbox.setChecked(true);
                        favorList.add(new Integer(friend_id));
                        Log.i("zyr", "新增加了："+friend_id);
                    }
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
        private CheckBox cbox;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
//            tel = itemView.findViewById(R.id.friend_tel);
            name = itemView.findViewById(R.id.friend_name);
            icon = itemView.findViewById(R.id.friend_icon);
            all = itemView.findViewById(R.id.friend_all);
            cbox = itemView.findViewById(R.id.change_cbox);

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

    public List<Integer> getList(){
        return favorList;
    }
}
