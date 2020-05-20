package com.example.itile.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.itile.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Map<String, Object>> list = new ArrayList<>();
    private Context context;
    private String result;
    private Handler mHandler;
    private Handler mHandler_f;
    public static final int ONE_ITEM = 1;
    public static final int TWO_ITEM = 2;

    public HomeAdapter(Context context) {
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
            View view = LayoutInflater.from(context).inflate(R.layout.item_home_white, parent, false);
            return new HomeWhiteViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_home_today, parent, false);
            return new RecyclerViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RecyclerViewHolder) {
            RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
//            recyclerViewHolder.myshort_comment_bookstatus.setText(list.get(position).get("status").toString());//将子控件中的文本换为map中的文本
//        holder.main_image.setImageURI((Uri) list.get(position).get("images"));
            final String time1 = list.get(position).get("time1").toString(); //这个非常重要
            final String time2 = list.get(position).get("time2").toString(); //这个非常重要
            String time11 = time1.substring(11, 16);
            String time22 = time2.substring(11, 16);
            recyclerViewHolder.time1.setText(time11);
            recyclerViewHolder.time2.setText(time22);
            recyclerViewHolder.d.setText(list.get(position).get("d").toString());
//            recyclerViewHolder.state.setText(list.get(position).get("state").toString());
            final String state = list.get(position).get("state").toString(); //这个非常重要
            if (state.equals("0")) {
                recyclerViewHolder.point.setBackgroundResource(R.drawable.point1);
            }
//            recyclerViewHolder.mybook_comment_name.setText(list.get(position).get("还没给的书评名字").toString());
//            final String score = list.get(position).get("score").toString(); //这个非常重要
//            final String book_id = list.get(position).get("book_num").toString(); //这个非常重要
//            final String comment_id = list.get(position).get("comment_id").toString();
//            Glide.with(context).load(bookphoto_url).into(recyclerViewHolder.myshort_comment_bookphoto);
        } else if (holder instanceof HomeWhiteViewHolder) {
            HomeWhiteViewHolder homeWhiteViewHolder = (HomeWhiteViewHolder) holder;
        }
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView time1;
        private TextView time2;
        private TextView d;
        private ImageView point;
        //        private TextView mybook_comment_bookinfo;
//        private ImageView myshort_comment_bookphoto;
        private TextView state;
//        private TextView myshort_comment_time;
//        private ImageButton myshort_comment_del;
//        private RelativeLayout myshort_all;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

//            myshort_comment_name = itemView.findViewById(R.id.myshort_comment_name);
            time1 = itemView.findViewById(R.id.time1);
            time2 = itemView.findViewById(R.id.time2);
//            mybook_comment_bookinfo = itemView.findViewById(R.id.mybook_comment_bookinfo);
//            state = itemView.findViewById(R.id.state);
//            myshort_comment_bookstatus = itemView.findViewById(R.id.myshort_comment_bookstatus);
            d = itemView.findViewById(R.id.d);
            point = itemView.findViewById(R.id.point);
//            myshort_comment_del = itemView.findViewById(R.id.myshort_comment_del);
//            myshort_all = itemView.findViewById(R.id.myshort_all);
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
