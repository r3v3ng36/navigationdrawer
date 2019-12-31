package com.example.navigationdrawer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawer.Model.ProcessTaskModel;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.web_activity;

import java.util.List;

public class ProcessTaskAdapter extends RecyclerView.Adapter<ProcessTaskAdapter.MyViewHolder>{
    Context context;
    private List<ProcessTaskModel> processTaskModels;

    public ProcessTaskAdapter(Context context, List<ProcessTaskModel> processTaskModels) {
        this.context = context;
        this.processTaskModels = processTaskModels;
    }

    @Override
    public ProcessTaskAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(context)
                .inflate(R.layout.completed_task_row, null,false);
        return new ProcessTaskAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ProcessTaskAdapter.MyViewHolder myViewHolder,final int i) {
        myViewHolder.tv_employees.setText(processTaskModels.get(i).getEmployee());
        myViewHolder.tv_task_detail.setText(processTaskModels.get(i).getTask_detail());
        myViewHolder.tv_recording.setText(processTaskModels.get(i).getRecording());
        myViewHolder.tv_assign_date.setText(processTaskModels.get(i).getAssign_date());
        myViewHolder.tv_completion_date.setText(processTaskModels.get(i).getInprogress_date());
        myViewHolder.tv_status.setText(processTaskModels.get(i).getStatus());

        myViewHolder.tv_recording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), web_activity.class);
                intent.putExtra("url", "https://step.focusspoint.com/"+processTaskModels.get(i).getRecording());
                context.startActivity(intent);
            }
        });



    }
    @Override
    public int getItemCount() {
        return processTaskModels.size();
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
