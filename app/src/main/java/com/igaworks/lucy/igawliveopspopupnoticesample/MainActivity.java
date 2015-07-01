package com.igaworks.lucy.igawliveopspopupnoticesample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.igaworks.IgawCommon;
import com.igaworks.liveops.IgawLiveOps;
import com.igaworks.liveops.livepopup.LiveOpsDeepLinkEventListener;
import com.igaworks.liveops.livepopup.LiveOpsPopupResourceEventListener;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {

    // Igaworks Common
    String tag = "Igaw";

    // Igaworks LiveOps PopupNotice
    String usn;
    String encryptUsn;
    String basicPopupKey = "basicPopupKey";
    String deeplinkPopupKey = "deeplinkPopupKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Your Code
        TelephonyManager manager =  (TelephonyManager)getSystemService(MainActivity.this.TELEPHONY_SERVICE);
        usn = manager.getDeviceId().toString();
        usn = usn+"a";
        encryptUsn = Base64.encodeToString(usn.getBytes(), 0);

        // Igaworks Common
        IgawCommon.startApplication(MainActivity.this);
        Log.d(tag, "startApplication ::: MainActivity");

        // Igaworks LiveOps PopupNotice
        IgawCommon.setUserId(encryptUsn);
        Log.d(tag, "setUserId ::: " + encryptUsn);

        // Igaworks LiveOps PopupNotice
        IgawLiveOps.requestPopupResource(MainActivity.this, new LiveOpsPopupResourceEventListener() {
            @Override
            public void onReceiveResource(boolean isReceive) {
                Log.d(tag, "onReceiveResource ::: " + isReceive);
            }
        });

        /*
         * Your Code
         */
    }


     /*
      * Your Code
      */


    // Your Code
    public void moveSubactivity(View view){
        Log.d(tag, "moveSubactivity ::: Button Click");

        Intent intent = new Intent(this, SubActivity.class);
        startActivity(intent);

        String usergroupKey = "SubStage";

        // Igaworks LiveOps PopupNotice Optional
        IgawLiveOps.setTargetingData(MainActivity.this, usergroupKey, "String" );
        Log.d(tag, "setTargetingData ::: " + usergroupKey);
    }

    // Your Code
    public void showPopupNotice(View view){
        Log.d(tag, "showPopupNotice ::: Button Click");

        // Igaworks LiveOps PopupNotice
        IgawLiveOps.showPopUp(MainActivity.this, basicPopupKey, null);
        Log.d(tag, "showPopUp ::: " + basicPopupKey);

    }

    // Your Code
    public void showDeepLinkPopupNotice(View view){
        Log.d(tag, "showDeepLinkPopupNotice ::: Button Click");

        // Igaworks LiveOps PopupNotice Optional
        IgawLiveOps.showPopUp(MainActivity.this, deeplinkPopupKey, new LiveOpsDeepLinkEventListener() {
            @Override
            public void onReceiveDeeplinkData(String deepLinkDataString) {
                Log.d(tag, "showPopUp ::: " + deeplinkPopupKey);


                // Your Code
                if (deepLinkDataString != null && !deepLinkDataString.equalsIgnoreCase("")){
                    JSONObject json;
                    try {
                        json = new JSONObject(deepLinkDataString);
                        Log.d(tag, "onReceiveDeeplinkData ::: " + deepLinkDataString);

                        if (json != null){
                            String param = json.getString("WebUrl");
                            Log.d(tag, "Param ::: " + param);

                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(param));
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Igaworks Common
        IgawCommon.startSession(MainActivity.this);
        Log.d(tag, "startSession ::: MainActivity" );

        /*
         * Your Code
         */
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Igaworks Common
        IgawCommon.endSession();
        Log.d(tag, "endSession ::: MainActivity");

        /*
         * Your Code
         */
    }

}
