package com.example.navigationdrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.navigationdrawer.interfaces.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

import us.zoom.sdk.InviteOptions;
import us.zoom.sdk.JoinMeetingOptions;
import us.zoom.sdk.JoinMeetingParams;
import us.zoom.sdk.MeetingError;
import us.zoom.sdk.MeetingService;
import us.zoom.sdk.MeetingServiceListener;
import us.zoom.sdk.MeetingStatus;
import us.zoom.sdk.MeetingViewsOptions;
import us.zoom.sdk.StartMeetingOptions;
import us.zoom.sdk.StartMeetingParamsWithoutLogin;
import us.zoom.sdk.ZoomError;
import us.zoom.sdk.ZoomSDK;
import us.zoom.sdk.ZoomSDKAuthenticationListener;
import us.zoom.sdk.ZoomSDKInitParams;
import us.zoom.sdk.ZoomSDKInitializeListener;
import com.example.navigationdrawer.ZoomMeetingUISettingHelper;

public class ZoomMainActivity extends AppCompatActivity implements Constants,
        ZoomSDKInitializeListener, MeetingServiceListener, ZoomSDKAuthenticationListener

{
    private final static String TAG = "Zoom SDK ";
    private String meetingId;
    private String meetingPass;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_main);
        ZoomSDK zoomSDK = ZoomSDK.getInstance();
        ZoomSDKInitParams params = new ZoomSDKInitParams();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        meetingId = prefs.getString("meetingId","");

        meetingPass = prefs.getString("meetingPass","");
        name = prefs.getString("ParticipantName","");
        params.appKey = APP_KEY;
        params.appSecret = APP_SECRET;


        if(savedInstanceState == null) {
            zoomSDK.initialize(this,this, params);
        }




    }

    private void registerMeetingServiceListener() {
        ZoomSDK zoomSDK = ZoomSDK.getInstance();
        MeetingService meetingService = zoomSDK.getMeetingService();
        if(meetingService != null) {
            meetingService.addListener(this);
        }
        joinMeeting();
    }

    @Override
    public void onMeetingStatusChanged(MeetingStatus meetingStatus, int errorCode, int internalErrorCode) {

        Log.i("meeting error", "onMeetingStatusChanged, meetingStatus=" + meetingStatus + ", errorCode=" + errorCode
                + ", internalErrorCode=" + internalErrorCode);

        if(meetingStatus == MeetingStatus.MEETING_STATUS_FAILED && errorCode == MeetingError.MEETING_ERROR_CLIENT_INCOMPATIBLE) {
            Toast.makeText(this, "Version of ZoomSDK is too low!", Toast.LENGTH_LONG).show();
        }
        if(meetingStatus == MeetingStatus.MEETING_STATUS_DISCONNECTING ) {
            Toast.makeText(this, "Meeting is disconnected", Toast.LENGTH_LONG).show();
            ZoomMainActivity.this.finish();
        }
    }

    @Override
    public void onZoomSDKLoginResult(long result) {

    }

    @Override
    public void onZoomSDKLogoutResult(long result) {

    }

    @Override
    public void onZoomIdentityExpired() {

    }

    @Override
    public void onZoomSDKInitializeResult(int errorCode, int internalErrorCode) {
        Log.i(TAG, "onZoomSDKInitializeResult, errorCode=" + errorCode + ", internalErrorCode=" + internalErrorCode);

        if(errorCode != ZoomError.ZOOM_ERROR_SUCCESS) {
            Toast.makeText(this, "Failed to initialize Zoom SDK. Error: " + errorCode + ", internalErrorCode=" + internalErrorCode, Toast.LENGTH_LONG);
        } else {
            Toast.makeText(this, "Initialize Zoom SDK successfully.", Toast.LENGTH_LONG).show();

            registerMeetingServiceListener();
        }

    }

    @Override
    protected void onDestroy() {
        ZoomSDK zoomSDK = ZoomSDK.getInstance();

        if(zoomSDK.isInitialized()) {
            MeetingService meetingService = zoomSDK.getMeetingService();
            meetingService.removeListener(this);
        }
        super.onDestroy();
    }

    public String jwtToken(){
        long time = System.currentTimeMillis()/1000 +30000 ;

        String header = "{\"alg\": \"HS256\", \"typ\": \"JWT\"}";
        String payload = "{\"iss\": \"" + APP_KEY + "\"" + ", \"exp\": " + String.valueOf(time) + "}";
        try {
            String headerBase64Str = Base64.encodeToString(header.getBytes("utf-8"), Base64.NO_WRAP| Base64.NO_PADDING | Base64.URL_SAFE);
            String payloadBase64Str = Base64.encodeToString(payload.getBytes("utf-8"), Base64.NO_WRAP| Base64.NO_PADDING | Base64.URL_SAFE);
            final Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(APP_SECRET.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);

            byte[] digest = mac.doFinal((headerBase64Str + "." + payloadBase64Str).getBytes());

            return headerBase64Str + "." + payloadBase64Str + "." + Base64.encodeToString(digest, Base64.NO_WRAP| Base64.NO_PADDING | Base64.URL_SAFE);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getToken(String type) throws IOException, JSONException {
        URL zoomTokenEndpoint = new URL("https://api.zoom.us/v2/users/"+"tashfeenzaidi15@gmail.com"  + "/token?type="+type+"&access_token=" + jwtToken());
        HttpsURLConnection connection = (HttpsURLConnection) zoomTokenEndpoint.openConnection();
//connection.setRequestProperty("Content-Type", " application/json");
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream responseBody = connection.getInputStream();
            InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
            BufferedReader streamReader = new BufferedReader(responseBodyReader);
            StringBuilder responseStrBuilder = new StringBuilder();

            //get JSON String
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            connection.disconnect();
            JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
            return jsonObject.getString("token");
        } else {
            Log.d("zoom main activity", "error in connection");
            return null;
        }
    }

    public void joinMeeting(){
        // Check if the meeting number is empty.
        if(meetingId.length() == 0) {
            Toast.makeText(this, "You need to enter a meeting number/ vanity id which you want to join.", Toast.LENGTH_LONG).show();
            return;
        }

        // Step 2: Get Zoom SDK instance.
        ZoomSDK zoomSDK = ZoomSDK.getInstance();
        // Check if the zoom SDK is initialized
        if(!zoomSDK.isInitialized()) {
            Toast.makeText(ZoomMainActivity.this, "ZoomSDK has not been initialized successfully", Toast.LENGTH_LONG).show();
            return;
        }

        // Step 3: Get meeting service from zoom SDK instance.
        MeetingService meetingService = zoomSDK.getMeetingService();

        // Step 4: Configure meeting options.
        JoinMeetingOptions opts = ZoomMeetingUISettingHelper.getJoinMeetingOptions();

        //  Some available options
        opts.no_driving_mode = true;
        opts.no_invite = true;
        opts.no_meeting_end_message = false;
        opts.no_titlebar = false;
        opts.no_bottom_toolbar = false;
        opts.no_dial_in_via_phone = true;
        opts.no_dial_out_to_phone = true;
        opts.no_disconnect_audio = true;
        opts.no_share = true;
        opts.invite_options = InviteOptions.INVITE_VIA_EMAIL + InviteOptions.INVITE_VIA_SMS;
        opts.no_audio = false;
        opts.no_video = false;
        opts.meeting_views_options = MeetingViewsOptions.NO_BUTTON_SHARE;
        opts.no_meeting_error_message = false;
        opts.participant_id = "participant id";


        //Step 5: Setup join meeting parameters
        JoinMeetingParams params = new JoinMeetingParams();

        params.displayName = name;
        params.meetingNo = meetingId;
        params.password = meetingPass;

        // Step 6: Call meeting service to join meeting
        int response = meetingService.joinMeetingWithParams(this, params, opts);

    }
}
