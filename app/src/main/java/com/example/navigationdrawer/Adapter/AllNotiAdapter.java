package com.example.navigationdrawer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawer.Model.AllNotiModel;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.web_activity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

public class AllNotiAdapter extends RecyclerView.Adapter<AllNotiAdapter.MyViewHolder> {
    private Context context;
    private List<AllNotiModel> allnoticefeedModelList;

    public AllNotiAdapter(Context context, List<AllNotiModel> allnoticefeedModelList) {
        this.context = context;
        this.allnoticefeedModelList = allnoticefeedModelList;
    }

    @Override
    public AllNotiAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(context)
                .inflate(R.layout.notices_single_row, null,false);
        return new AllNotiAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull AllNotiAdapter.MyViewHolder myViewHolder,final int i) {
//          myViewHolder.tv_notices.setText(allnoticefeedModelList.get(i).getNotices());
            myViewHolder.tv_notices.setText(Html.fromHtml(allnoticefeedModelList.get(i).getNotices()));

//          myViewHolder.tv_notices.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
//          myViewHolder.tv_notices.setLinksClickable(true);
//          myViewHolder.tv_notices.setTextColor(000000);


//        myViewHolder.tv_employee.setText(String.valueOf(allnoticefeedModelList.get(i).getId()));
            myViewHolder.tv_notices.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context,allnoticefeedModelList.get(i).getNotices(),Toast.LENGTH_LONG).show();
                    try{
                        String html = allnoticefeedModelList.get(i).getNotices();
                        Document document = Jsoup.parse(html);
                        Element link = document.select("a").first();
                        String url = link.attr("href");
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);

//                        Toast.makeText(context,link.attr("href"), Toast.LENGTH_SHORT).show();

//                        Intent intent = new Intent(context.getApplicationContext(), web_activity.class);
//                        intent.putExtra("url",allnoticefeedModelList.get(i).getNotices());
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(intent);
                    }catch (Exception ex){
                        Toast.makeText(context, "The following notice doesn't contain link.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }
    @Override
    public int getItemCount() {
        return allnoticefeedModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_notices,tv_time,tv_date,tv_status,tv_employee,tv_completion;
        public MyViewHolder(final View itemView) {
            super(itemView);
            tv_notices = itemView.findViewById(R.id.tv_employee);
//            tv_date = itemView.findViewById(R.id.tv_date);
//            tv_employee = itemView.findViewById(R.id.tv_employee);
//            tv_status = itemView.findViewById(R.id.tv_status);
//            tv_completion = itemView.findViewById(R.id.tv_completion);
        }
    }
}
