package com.example.navigationdrawer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawer.Model.CompletedTaskModel;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.recordview;
import com.example.navigationdrawer.web_activity;

import java.util.List;

public class CompletedtaskAdapter extends RecyclerView.Adapter<CompletedtaskAdapter.MyViewHolder>{
    Context context;
    private List<CompletedTaskModel> completedTaskModels;

    public CompletedtaskAdapter(Context context, List<CompletedTaskModel> completedTaskModels) {
        this.context = context;
        this.completedTaskModels = completedTaskModels;
    }

    @Override
    public CompletedtaskAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(context)
                .inflate(R.layout.completed_task_row, null,false);
        return new CompletedtaskAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull final CompletedtaskAdapter.MyViewHolder myViewHolder,final int i) {
        myViewHolder.tv_employees.setText(completedTaskModels.get(i).getEmployee());
        myViewHolder.tv_task_detail.setText(completedTaskModels.get(i).getTask_detail());
        myViewHolder.tv_recording.setText(completedTaskModels.get(i).getRecording());
        myViewHolder.tv_assign_date.setText(completedTaskModels.get(i).getAssign_date());
        myViewHolder.tv_completion_date.setText(completedTaskModels.get(i).getCompletion_date());
        myViewHolder.tv_status.setText(completedTaskModels.get(i).getStatus());
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        myViewHolder.tv_recording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), web_activity.class);
                intent.putExtra("url", "https://step.focusspoint.com/"+completedTaskModels.get(i).getRecording());
                context.startActivity(intent);
            }
        });





    }
    @Override
    public int getItemCount() {
        return completedTaskModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_recording,tv_status,tv_employees,tv_assign_date,tv_completion_date,tv_task_detail;
        LinearLayout layout;
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
