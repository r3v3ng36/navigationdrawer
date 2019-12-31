package com.example.navigationdrawer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawer.Model.FilterModel;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.web_activity;

import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder>{
    Context context;
    private List<FilterModel> filterModels;

    public FilterAdapter(Context context, List<FilterModel> filterModels) {
        this.context = context;
        this.filterModels = filterModels;
    }

    @Override
    public FilterAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(context)
                .inflate(R.layout.completed_task_row, null,false);
        return new FilterAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterAdapter.MyViewHolder myViewHolder,final int i) {
        myViewHolder.tv_employees.setText(filterModels.get(i).getEmployee());
        myViewHolder.tv_task_detail.setText(filterModels.get(i).getTask_detail());
        myViewHolder.tv_recording.setText(filterModels.get(i).getRecording());
        myViewHolder.tv_assign_date.setText(filterModels.get(i).getAssign_date());
        myViewHolder.tv_completion_date.setText(filterModels.get(i).getAction_date());
        myViewHolder.tv_status.setText(filterModels.get(i).getT_status());

        myViewHolder.tv_recording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), web_activity.class);
                intent.putExtra("url", "https://step.focusspoint.com/"+filterModels.get(i).getRecording());
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return filterModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_recording,tv_status,tv_employees,tv_assign_date,tv_completion_date,tv_task_detail;
        public MyViewHolder(final View itemView) {
            super(itemView);
            tv_employees= itemView.findViewById(R.id.tv_employee);
            tv_task_detail = itemView.findViewById(R.id.tv_task_detail);
            tv_recording = itemView.findViewById(R.id.tv_recording);
            tv_assign_date = itemView.findViewById(R.id.tv_assign_date);
            tv_completion_date = itemView.findViewById(R.id.tv_completion_date);
            tv_status = itemView.findViewById(R.id.tv_status);
        }
    }
}
