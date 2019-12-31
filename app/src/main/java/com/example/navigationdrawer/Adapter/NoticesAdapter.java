package com.example.navigationdrawer.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.navigationdrawer.Model.noticefeedModel;
import com.example.navigationdrawer.R;
import java.util.List;


public class NoticesAdapter   extends RecyclerView.Adapter<NoticesAdapter.MyViewHolder>  {

    Context context;
    private List<noticefeedModel> noticefeedModelList;

    public NoticesAdapter(Context context, List<noticefeedModel> noticefeedModelList) {
        this.context = context;
        this.noticefeedModelList = noticefeedModelList;
    }

    @Override
    public NoticesAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(context)
                .inflate(R.layout.notices_single_row, null,false);
        return new NoticesAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticesAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_notices.setText((Html.fromHtml(Html.fromHtml(noticefeedModelList.get(i).getNotice()).toString())));



    }
    @Override
    public int getItemCount() {
        return noticefeedModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_notices,tv_time;
        public MyViewHolder(final View itemView) {
            super(itemView);
            tv_notices = itemView.findViewById(R.id.tv_employee);


        }
    }


}

