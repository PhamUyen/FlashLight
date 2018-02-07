package com.uyenpham.diploma.flashlight.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by Ka on 2/8/2018.
 */

public class CallReicerver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try{
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                Toast.makeText(context,"Ringing State ",Toast.LENGTH_SHORT).show();
                //stop service when have incoming call
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                Toast.makeText(context,"Idle State",Toast.LENGTH_SHORT).show();
                //restart service when end call
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
