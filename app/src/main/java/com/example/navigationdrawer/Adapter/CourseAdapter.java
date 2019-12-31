package com.example.navigationdrawer.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.navigationdrawer.Model.CourseFeedModel;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.interfaces.ClickListener;

import java.lang.ref.WeakReference;
import java.util.List;

public class CourseAdapter  extends RecyclerView.Adapter<CourseAdapter.MyViewHolder>  {

    Context context;
    private final ClickListener listener;
    private List<CourseFeedModel> courseFeedModelList;



    public CourseAdapter(Context context, ClickListener listener, List<CourseFeedModel> courseFeedModelList) {
        this.context = context;
        this.listener = listener;
        this.courseFeedModelList = courseFeedModelList;
    }

    @Override
    public CourseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(context)
                .inflate(R.layout.course_single_row, null,false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_couse_name.setText(courseFeedModelList.get(i).getCTitle());
        myViewHolder.tv_course_amount.setText(courseFeedModelList.get(i).getCFees());
    }

    @Override
    public int getItemCount() {
        return courseFeedModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_couse_name,tv_course_amount;
        Button btn_view;
        private WeakReference<ClickListener> listenerRef;


        public MyViewHolder(final View itemView) {
            super(itemView);

            tv_couse_name = itemView.findViewById(R.id.course_name);
            tv_course_amount = itemView.findViewById(R.id.course_price);
            btn_view = itemView.findViewById(R.id.btnview);

            btn_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onPositionClicked(getAdapterPosition());
                }
            });
        }
    }
}
