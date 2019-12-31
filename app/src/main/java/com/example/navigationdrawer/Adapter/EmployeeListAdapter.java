package com.example.navigationdrawer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawer.Model.EmployeeListModel;
import com.example.navigationdrawer.R;

import java.util.List;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.MyViewHolder>{
    Context context;
    private List<EmployeeListModel> employeeListModels;

    public EmployeeListAdapter(Context context, List<EmployeeListModel> employeeListModels) {
        this.context = context;
        this.employeeListModels = employeeListModels;
    }

    @Override
    public EmployeeListAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(context)
                .inflate(R.layout.calendarfragment, null,false);
        return new EmployeeListAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeListAdapter.MyViewHolder myViewHolder, int i) {
//        myViewHolder.tv_notices.setText((Html.fromHtml(Html.fromHtml(employeeListAdapters.get(i).getNotices()).toString())));
        myViewHolder.tv_id.setText(employeeListModels.get(i).getU_id().toString());
        myViewHolder.tv_fname.setText(employeeListModels.get(i).getU_fname());
        myViewHolder.tv_lname.setText(employeeListModels.get(i).getU_lname());
        myViewHolder.tv_username.setText(employeeListModels.get(i).getU_username());


    }
    @Override
    public int getItemCount() {
        return employeeListModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id,tv_fname,tv_lname,tv_username;
        public MyViewHolder(final View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_date);
            tv_fname = itemView.findViewById(R.id.tv_assign_date);
            tv_lname = itemView.findViewById(R.id.tv_employee);
            tv_username = itemView.findViewById(R.id.tv_completion_date);
//            tv_spinner = itemView.findViewById(R.id.spinner);
//            tv_date = itemView.findViewById(R.id.tv_date);
//            tv_employee = itemView.findViewById(R.id.tv_employee);
//            tv_status = itemView.findViewById(R.id.tv_status);
//            tv_completion = itemView.findViewById(R.id.tv_completion);
        }
    }
}
