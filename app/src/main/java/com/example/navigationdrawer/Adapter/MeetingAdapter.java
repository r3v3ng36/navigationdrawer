package com.example.navigationdrawer.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;

import com.example.navigationdrawer.LoginActivity;
import com.example.navigationdrawer.queryform;
import com.google.android.material.textfield.TextInputEditText;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.navigationdrawer.Model.MeetingModal;
import com.example.navigationdrawer.R;

import java.util.List;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MyViewHolder>  {

    Context context;
    private List<MeetingModal> MeetingModalList;
    SharedPreferences preferences;
    int index;
    public MeetingAdapter(Context context, List<MeetingModal> MeetingModalList) {
        this.context = context;
        this.MeetingModalList = MeetingModalList;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);


    }

    @Override
    public MeetingAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(context)
                .inflate(R.layout.meeting_single_row, null,false);
        return new MeetingAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingAdapter.MyViewHolder myViewHolder, int i) {
        index = i;
        myViewHolder.date.setText(MeetingModalList.get(i).getDate());
        myViewHolder.title.setText(MeetingModalList.get(i).getTitle());
        myViewHolder.checkIn.setText(MeetingModalList.get(i).getCheckIn());
        myViewHolder.checkOut.setText(MeetingModalList.get(i).getCheckOut());
        //myViewHolder.meetingId.setText(MeetingModalList.get(i).getzMeetingId());
        //myViewHolder.meetingPass.setText(MeetingModalList.get(i).getzMeetingPass());
        final String meetID = MeetingModalList.get(i).getzMeetingId();
        final String meetPass = MeetingModalList.get(i).getzMeetingPass();

        myViewHolder.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("meetingId",meetID);
                editor.putString("meetingPass",meetPass);
                editor.apply();
                Intent intent = new Intent(context, queryform.class);
                context.startActivity(intent);
            }
        });


    }
    @Override
    public int getItemCount() {
        return MeetingModalList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView date,checkIn,checkOut,title;
        Button startBtn;
        public MyViewHolder(final View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            date = itemView.findViewById(R.id.date);
            title = itemView.findViewById(R.id.title);
            checkIn = itemView.findViewById(R.id.checkIn);
            checkOut = itemView.findViewById(R.id.checkOut);
            //meetingId = itemView.findViewById(R.id.meetingId);
           // meetingPass = itemView.findViewById(R.id.meetingPass);
            startBtn = itemView.findViewById(R.id.start_btn);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION)
            {

            }

        }
    }
}

