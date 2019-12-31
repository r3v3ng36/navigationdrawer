package com.example.navigationdrawer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawer.Model.CourseFeedModel;
import com.example.navigationdrawer.Model.DaysModel;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.interfaces.ClickListener;

import java.lang.ref.WeakReference;
import java.util.List;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.MyViewHolder>  {

    Context context;
    private List<DaysModel> daysModels;



    public DaysAdapter(Context context, List<DaysModel> daysModels) {
        this.context = context;;
        this.daysModels = daysModels;
    }

    @Override
    public DaysAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(context)
                .inflate(R.layout.days_single_row, null,false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull DaysAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_days.setText(daysModels.get(i).getCdDays());
        myViewHolder.tv_check_in.setText(daysModels.get(i).getCdCheckin());
        myViewHolder.tv_checkout.setText(daysModels.get(i).getCdCheckout());
    }

    @Override
    public int getItemCount() {
        return daysModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_days,tv_check_in,tv_checkout;


        public MyViewHolder(final View itemView) {
            super(itemView);

            tv_days = itemView.findViewById(R.id.tv_day);
            tv_check_in = itemView.findViewById(R.id.tv_checkin);
            tv_checkout = itemView.findViewById(R.id.tv_check_out);


        }
    }
}
