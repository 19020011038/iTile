package com.example.itile.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.itile.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChangeTaskOwnerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Map<String, Object>> list = new ArrayList<>();
    private Context context;
    public static final int ONE_ITEM = 1;
    public static final int TWO_ITEM = 2;
    private String choose;
    //解决holder复用问题
    private List<Integer> favorList=new ArrayList<>(); //用法：favorList.add(123);

    public ChangeTaskOwnerAdapter(Context context) {
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
            View view = LayoutInflater.from(context).inflate(R.layout.item_change_task_owner, parent, false);
            return new WhiteMyCommentViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_change_task_owner, parent, false);
            return new RecyclerViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RecyclerViewHolder) {
            final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
//            recyclerViewHolder.myshort_comment_bookstatus.setText(list.get(position).get("status").toString());//将子控件中的文本换为map中的文本
            String id = list.get(position).get("id").toString(); //这个非常重要
            String name = list.get(position).get("name").toString();
            String icon = list.get(position).get("icon").toString();
            recyclerViewHolder.nickname.setText(name);
            Glide.with(context).load("http://118.190.245.170/worktile/media/"+icon).into(recyclerViewHolder.icon);
            recyclerViewHolder.radio.setTag(new Integer(id)); //上tag是区分不同radio的关键
            if(favorList.contains(recyclerViewHolder.radio.getTag())) {
                recyclerViewHolder.radio.setChecked(true);
                choose = id;
            }
            else
                recyclerViewHolder.radio.setChecked(false);
            recyclerViewHolder.all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(favorList.contains(recyclerViewHolder.radio.getTag())) {
//                        recyclerViewHolder.cbox.setChecked(false);
//                        favorList.remove(new Integer(id));
                    }
                    else {
                        recyclerViewHolder.radio.setChecked(true);
                        favorList.clear();
                        favorList.add(new Integer(id));
                        choose = id;
                        notifyDataSetChanged();//有这句才能自由切换
                    }
                }
            });
//            recyclerViewHolder.cbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                    if (isChecked){
//                        if(!favorList.contains(recyclerViewHolder.cbox.getTag())){
//                            favorList.add(new Integer(id));
////                            Log.i("zyr", "能打印list吗："+favorList.toString());
//                        }
//                    }else{
//                        if(favorList.contains(recyclerViewHolder.cbox.getTag())){
//                            favorList.remove(new Integer(id));
//                        }
//                    }
//                }
//            });

        } else if (holder instanceof WhiteMyCommentViewHolder) {
            WhiteMyCommentViewHolder whiteStoreViewHolder = (WhiteMyCommentViewHolder) holder;
        }
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView nickname;
        private ImageView icon;
        private RadioButton radio;
        private RelativeLayout all;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            nickname = itemView.findViewById(R.id.friend_name);
            radio = itemView.findViewById(R.id.radio);
            all = itemView.findViewById(R.id.main_all);
            icon = itemView.findViewById(R.id.friend_icon);
        }
    }

    class WhiteMyCommentViewHolder extends RecyclerView.ViewHolder {
        private TextView white_store;

        WhiteMyCommentViewHolder(@NonNull View itemView) {
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

    public String getChoose(){
        return choose;
    }
}
