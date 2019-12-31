package com.example.navigationdrawer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class Support extends AppCompatActivity {

    ImageView iv_call,iv_whatsapp1,iv_whatsapp2,iv_facebook,iv_youtube,iv_email,iv_back,iv_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_support);

        iv_call = findViewById(R.id.iv_call);
        iv_whatsapp1 = findViewById(R.id.iv_whatsapp1);
        iv_whatsapp2 = findViewById(R.id.iv_whatsapp2);
        iv_facebook = findViewById(R.id.iv_facbook);
        iv_youtube = findViewById(R.id.iv_youtube);
        iv_email = findViewById(R.id.iv_email);
        iv_back = findViewById(R.id.iv_back);
        iv_web = findViewById(R.id.iv_web);

        iv_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.focusspoint.com"));
                startActivity(browserIntent);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        iv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:03008222528"));
                startActivity(intent);
            }
        });

        iv_whatsapp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String text = "I have a query.";// Replace with your message.
                    String toNumber = "+923008222528";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+text));
                    startActivity(intent);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
//                String toNumber = "+923008222528"; // contains spaces.
//                toNumber = toNumber.replace("+", "").replace(" ", "");
//                Intent sendIntent = new Intent("android.intent.action.MAIN");
//                sendIntent.putExtra("jid", toNumber + "@s.whatsapp.net");
//                sendIntent.putExtra(Intent.EXTRA_TEXT, "I have a query");
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.setPackage("com.whatsapp");
//                sendIntent.setType("text/plain");
//                startActivity(sendIntent);
            }
        });
        iv_whatsapp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String text = "I have a query.";// Replace with your message.
                    String toNumber = "+923058222528";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+text));
                    startActivity(intent);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
//                String toNumber = "+923058222528"; // contains spaces.
//                toNumber = toNumber.replace("+", "").replace(" ", "");
//                Intent sendIntent = new Intent("android.intent.action.MAIN");
//                sendIntent.putExtra("jid", toNumber + "@s.whatsapp.net");
//                sendIntent.putExtra(Intent.EXTRA_TEXT, "I have a query");
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.setPackage("com.whatsapp");
//                sendIntent.setType("text/plain");
//                startActivity(sendIntent);
            }
        });
        iv_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  facebookintent = getOpenFacebookIntent(Support.this);
                startActivity(facebookintent);

            }
        });
        iv_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.youtube.com/user/focusspoint/"));
                intent.setPackage("com.google.android.youtube");
                startActivity(intent);
            }
        });

        iv_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"admin@focusspoint.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "");
                i.putExtra(Intent.EXTRA_TEXT   , "");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static Intent getOpenFacebookIntent(Context context) {

        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/209398962516749"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("facbook.com/focusspoint"));
        }
    }
}
