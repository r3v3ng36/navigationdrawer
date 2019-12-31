package com.example.navigationdrawer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.navigationdrawer.Model.OnlineSessionModal;
import com.example.navigationdrawer.Model.MeetingModal;
import com.example.navigationdrawer.OnlineSession;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.ZoomMainActivity;
import com.example.navigationdrawer.queryform;

import java.util.List;

public class OnlineSessionAdapter extends RecyclerView.Adapter<OnlineSessionAdapter.MyViewHolder>  {

    Context context;
    private List<OnlineSessionModal> onlineSessionModalList;
    SharedPreferences preferences;
    int index;
    public OnlineSessionAdapter(Context context, List<OnlineSessionModal> onlineSessionModalList) {
        this.context = context;
        this.onlineSessionModalList = onlineSessionModalList;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);


    }

    @Override
    public OnlineSessionAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(context)
                .inflate(R.layout.onlinesession_single_row, null,false);
        return new OnlineSessionAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull OnlineSessionAdapter.MyViewHolder myViewHolder, int i) {
        index = i;
        myViewHolder.date.setText(onlineSessionModalList.get(i).getDate());
        myViewHolder.title.setText(onlineSessionModalList.get(i).getTitle());
        myViewHolder.checkIn.setText(onlineSessionModalList.get(i).getCheckIn());
        myViewHolder.checkOut.setText(onlineSessionModalList.get(i).getCheckOut());
        myViewHolder.videolink.setText(onlineSessionModalList.get(i).getVideoLink());
        final String meetID = onlineSessionModalList.get(i).getzMeetingId();
        final String meetPass = onlineSessionModalList.get(i).getzMeetingPass();
        myViewHolder.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("meetingID",meetID);
                editor.putString("meetingPass",meetPass);
                editor.apply();
//                Intent intent = new Intent(context, queryform.class);
//                context.startActivity(intent);

                Intent intent = new Intent(context, ZoomMainActivity.class);
                context.startActivity(intent);
            }
        });


        myViewHolder.invitationLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                context.startActivity(browserIntent);
            }
        });

        myViewHolder.copyimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
    @Override
    public int getItemCount() {
        return onlineSessionModalList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView date,checkIn,checkOut,title,videolink;
        Button startBtn,invitationLink;
        ImageView copyimage;
        public MyViewHolder(final View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            date = itemView.findViewById(R.id.date);
            title = itemView.findViewById(R.id.title);
            checkIn = itemView.findViewById(R.id.checkIn);
            checkOut = itemView.findViewById(R.id.checkOut);
            videolink= itemView.findViewById(R.id.videolink);
            //meetingId = itemView.findViewById(R.id.meetingId);
            // meetingPass = itemView.findViewById(R.id.meetingPass);
            startBtn = itemView.findViewById(R.id.start_btn);
            invitationLink=itemView.findViewById(R.id.invitation_btn);
            copyimage=itemView.findViewById(R.id.copyimage);
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
