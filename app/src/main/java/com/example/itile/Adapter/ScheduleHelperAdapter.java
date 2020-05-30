package com.example.itile.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itile.R;
import com.example.itile.ScheduleDetailActivity;
import com.example.itile.Util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static androidx.recyclerview.widget.RecyclerView.NO_POSITION;

public class ScheduleHelperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final int KongBai_View = 0;
    public final int Item_View = 1;
    List<Map<String, Object>> list = new ArrayList<>();
    private Context context;
    private Handler handler;
    private int delete_position;
    private Handler handler_img;

    public List<Map<String, Object>> getData() {
        return list;
    }

    public void setData(List<Map<String, Object>> list) {
        this.list = list;
    }


    public ScheduleHelperAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
        this.handler_img = handler_img;
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
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ScheduleHelperAdapter.KongBaiViewHolder) {
            KongBaiViewHolder viewHolder = (KongBaiViewHolder) holder;
        } else {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            if (list.get(position).get("state").toString().equals("1")) {
                viewHolder.point.setImageResource(R.drawable.point2);
                viewHolder.point.invalidate();

            } else {
                viewHolder.point.setImageResource(R.drawable.point1);
                viewHolder.point.invalidate();

            }
            handler_img = new Handler(context.getMainLooper()) {
                public void handleMessage(Message message) {
                    super.handleMessage(message);
                    if (message.what == 1) {
                        viewHolder.point.setImageResource(R.drawable.point2);
                        viewHolder.point.invalidate();
                    }else {
                        viewHolder.point.setImageResource(R.drawable.point1);
                        viewHolder.point.invalidate();
                    }
                }
            };
            viewHolder.time1.setText(list.get(position).get("starttime").toString().substring(11, 16));
            viewHolder.time2.setText(list.get(position).get("endtime").toString().substring(11, 16));
            viewHolder.description.setText(list.get(position).get("description").toString());
            viewHolder.a_schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ScheduleHelperAdapter.this.context, ScheduleDetailActivity.class);
                    intent.putExtra("pk", list.get(position).get("pk").toString());
                    intent.putExtra("position",String.valueOf(position));
                    intent.putExtra("from","helper");
                    context.startActivity(intent);
                }
            });
            viewHolder.a_schedule.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ScheduleHelperAdapter.this.context);
                    dialog.setTitle("是否删除该日程？");
                    dialog.setMessage("若删除日程请点击确定");
                    dialog.setCancelable(false);
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            delete_position = position;
                            postDeleteSchedule("http://118.190.245.170/worktile/schedulehelper/", list.get(position).get("pk").toString());
                        }
                    });
                    dialog.show();
                    return true;
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

    public void postDeleteSchedule(String address, String pk) {
        HttpUtil.postDeleteSchedule(address, pk, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseData = response.body().string();
                Log.d("日程助手页删除post", responseData);
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    String warning = jsonObject.getString("warning");
                    if (warning.equals("1")) {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        });
        handler = new Handler(context.getMainLooper()) {
            public void handleMessage(Message message) {
                super.handleMessage(message);
                if (true) {
                    list.remove(delete_position);
                    if(list.size() == 0){
                        Map map = new HashMap();
                        map.put("type",0);
                        list.add(map);
                    }
                    notifyDataSetChanged();
                    Toast.makeText(ScheduleHelperAdapter.this.context, "删除成功！", Toast.LENGTH_SHORT).show();

                }
            }
        };
    }



}
