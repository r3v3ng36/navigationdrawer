package com.example.navigationdrawer.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.navigationdrawer.Model.notificationfeedModel;
import com.example.navigationdrawer.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder>  {

    Context context;
    private List<notificationfeedModel> notificationfeedModelList;


    public NotificationAdapter(Context context, List<notificationfeedModel> notificationfeedModelList) {
        this.context = context;
        this.notificationfeedModelList = notificationfeedModelList;
    }

    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(context)
                .inflate(R.layout.notification_single_row, null,false);
        return new NotificationAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_time.setText(notificationfeedModelList.get(i).getN_time());
        myViewHolder.tv_notification.setText((Html.fromHtml(Html.fromHtml(notificationfeedModelList.get(i).getN_message()).toString())));
    }
    @Override
    public int getItemCount() {
        return notificationfeedModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_notification,tv_time;
        public MyViewHolder(final View itemView) {
            super(itemView);
            tv_notification = itemView.findViewById(R.id.tv_notification);
            tv_time = itemView.findViewById(R.id.tv_time);

        }
    }
}


