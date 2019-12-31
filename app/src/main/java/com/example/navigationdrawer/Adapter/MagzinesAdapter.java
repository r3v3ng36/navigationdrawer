package com.example.navigationdrawer.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.navigationdrawer.Model.MagzineModel;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.interfaces.ClickListener;

import java.util.List;

public class MagzinesAdapter extends RecyclerView.Adapter<MagzinesAdapter.MyViewHolder> {

    Context context;
    List<MagzineModel>magzineModelList;
    private final ClickListener listener;

    public MagzinesAdapter(Context context, ClickListener listener, List<MagzineModel> magzineModelList) {
        this.context = context;
        this.listener = listener;
        this.magzineModelList = magzineModelList;
    }

    @Override
    public MagzinesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(context)
                .inflate(R.layout.magzines_single_row, null,false);

        return new MagzinesAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MagzinesAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.date.setText(magzineModelList.get(i).getDate());
        myViewHolder.course.setText(magzineModelList.get(i).getCTitle());
        myViewHolder.file.setText(magzineModelList.get(i).getFileTitle());
    }

    @Override
    public int getItemCount() {
        return magzineModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView course,file,date;
        ImageView iv_view_video;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            course = itemView.findViewById(R.id.course);
            file= itemView.findViewById(R.id.file);
            date = itemView.findViewById(R.id.magzinedate);
            iv_view_video = itemView.findViewById(R.id.iv_view_video);
            iv_view_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onPositionClicked(getAdapterPosition());
                }
            });
        }
    }
}
