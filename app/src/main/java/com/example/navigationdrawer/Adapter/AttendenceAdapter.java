package com.example.navigationdrawer.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputEditText;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.navigationdrawer.Model.AttendeceModel;
import com.example.navigationdrawer.R;

import java.util.List;

public class AttendenceAdapter extends RecyclerView.Adapter<AttendenceAdapter.MyViewHolder>  {

    Context context;
    private List<AttendeceModel> attendeceModelList;

    public AttendenceAdapter(Context context, List<AttendeceModel> attendeceModelList) {
        this.context = context;
        this.attendeceModelList = attendeceModelList;
    }

    @Override
    public AttendenceAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(context)
                .inflate(R.layout.attendence_single_row, null,false);
        return new AttendenceAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendenceAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.attendencedate.setText(attendeceModelList.get(i).getSaDate());
        myViewHolder.sno.setText(attendeceModelList.get(i).getSaId());
        myViewHolder.attendencecheckin.setText(attendeceModelList.get(i).getSaCheckin());
        myViewHolder.attendencecheckout.setText(attendeceModelList.get(i).getSaCheckout());
        myViewHolder.attendencecheckontime.setText(attendeceModelList.get(i).getSaOntime());
        myViewHolder.attendencecheckofftime.setText(attendeceModelList.get(i).getSaOfftime());


    }
    @Override
    public int getItemCount() {
        return attendeceModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView attendencedate,attendencestatus,sno;
        TextInputEditText attendencecheckin,attendencecheckout,attendencecheckontime,attendencecheckofftime;
        public MyViewHolder(final View itemView) {
            super(itemView);
            attendencedate = itemView.findViewById(R.id.attendencedate);
            sno = itemView.findViewById(R.id.sno);
            attendencecheckin = itemView.findViewById(R.id.attendencecheckin);
            attendencecheckout = itemView.findViewById(R.id.attendencecheckout);
            attendencecheckontime = itemView.findViewById(R.id.attendencecheckontime);
            attendencecheckofftime = itemView.findViewById(R.id.attendencecheckofftime);

        }
    }
}

