package com.example.navigationdrawer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class recordview extends AppCompatActivity {
    ImageView image;
    WebView webview;
    VideoView video_view;
    Button btn_close;
    String url;
    private ProgressDialog progressBar;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.video_popup);
        String a = getIntent().getStringExtra("ABC");
        btn_close= findViewById(R.id.btn_close);
        image = findViewById(R.id.image);
        this.webview = findViewById(R.id.webview);
        video_view = findViewById(R.id.video_view);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(video_view);
        video_view.setMediaController(mediaController);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = prefs.getString("url", "");
        //Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),magazines.class);
                startActivity(intent);
            }
        });

//
    }

    @Override
    public void onBackPressed() {


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (url.contains("mp4")) {

            Uri uri = Uri.parse(url);
            video_view.setVideoURI(uri);
            video_view.start();
            webview.setVisibility(View.GONE);
            image.setVisibility(View.GONE);
        } else if (url.contains("pdf")) {
//            WebSettings settings = webview.getSettings();
//            settings.setJavaScriptEnabled(true);
//            webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
//            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//            progressBar = ProgressDialog.show(videoview.this, "", "Loading...");
//
//            webview.setWebViewClient(new WebViewClient() {
//                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                    Log.i("tag", "Processing webview url click...");
//                    view.loadUrl(url);
//                    return true;
//                }
//
//                public void onPageFinished(WebView view, String url) {
//
//                    if (progressBar.isShowing()) {
//                        progressBar.dismiss();
//                    }
//                }
//                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                    alertDialog.setTitle("Error");
//                    alertDialog.setMessage(description);
//                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            return;
//                        }
//                    });
//                    alertDialog.show();
//                }
////            });
//            video_view.setVisibility(View.GONE);
//            image.setVisibility(View.GONE);
//            //webview.loadUrl(url);
//
//            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
//            browserIntent.setDataAndType(Uri.parse(url,);
//
//            Intent chooser = Intent.createChooser(browserIntent, getString(R.string.chooser_title));
//            chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // optional
//
//            startActivity(chooser);
////
////            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
////            startActivity(browserIntent);


        }else if (url.contains("jpeg")){
            video_view.setVisibility(View.GONE);
            webview.setVisibility(View.GONE);
            Glide.with(this).load(url).into(image);

        }


    }
}
