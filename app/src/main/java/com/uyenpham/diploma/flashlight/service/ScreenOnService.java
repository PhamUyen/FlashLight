package com.uyenpham.diploma.flashlight.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.uyenpham.diploma.flashlight.utils.Const;
import com.uyenpham.diploma.flashlight.utils.FlashUtil;
import com.uyenpham.diploma.flashlight.utils.PreferenceUtils;

public class ScreenOnService extends Service {
    BroadcastReceiver mybroadcast;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mybroadcast != null){
            unregisterReceiver(mybroadcast);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.mybroadcast = new BroadcastReceiver()
        {
            public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
            {
                if(PreferenceUtils.getBoolean(ScreenOnService.this, Const.KEY_TURN_BY_POWER, true)){
                    FlashUtil.stopFlickerFlash();
                }
            }
        };
        registerReceiver(this.mybroadcast, new IntentFilter("android.intent.action.SCREEN_ON"));
        registerReceiver(this.mybroadcast, new IntentFilter("android.intent.action.SCREEN_OFF"));
        return START_STICKY;
    }
}
