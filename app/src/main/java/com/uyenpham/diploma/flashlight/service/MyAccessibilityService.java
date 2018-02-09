package com.uyenpham.diploma.flashlight.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.uyenpham.diploma.flashlight.FlashlightApplication;
import com.uyenpham.diploma.flashlight.model.App;
import com.uyenpham.diploma.flashlight.model.FlashPatternt;
import com.uyenpham.diploma.flashlight.utils.CommonFuntions;
import com.uyenpham.diploma.flashlight.utils.Const;
import com.uyenpham.diploma.flashlight.utils.PreferenceUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static com.uyenpham.diploma.flashlight.utils.CommonFuntions.isTimeBetweenTwoTime;

public class MyAccessibilityService extends AccessibilityService {
    private final AccessibilityServiceInfo info = new AccessibilityServiceInfo();
    public static String Lastpackagename = "parleg";
    float batteryLevel;
    private boolean isInit = false;
    private Timer mTimer;
    String starttime;
    String stoptime;
    int syshour;
    int sysmin;
    private int syssecond;
    TimerTask timerTask = new TimerTask() {
        public void run() {
        }
    };

    @Override
    public void onCreate() {
        Log.e("CallReicerver", "onCreate");
        super.onCreate();
        this.mTimer = new Timer();
        this.mTimer.schedule(this.timerTask, 2000L, 2000L);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            this.mTimer.cancel();
            this.timerTask.cancel();
            Intent localIntent = new Intent("com.android.techtrainner");
            localIntent.putExtra("yourvalue", "torestore");
            sendBroadcast(localIntent);
            return;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        Log.e("CallReicerver", accessibilityEvent.toString());
        if (PreferenceUtils.getBoolean(this, Const.KEY_NIGHT_MODE, false)) {
            Calendar localCalendar = Calendar.getInstance();
            syshour = localCalendar.get(Calendar.HOUR);
            sysmin = localCalendar.get(Calendar.MINUTE);
            syssecond = localCalendar.get(Calendar.SECOND);
            Date localDate = new Date();
            localDate.setHours(this.syshour);
            localDate.setMinutes(this.sysmin);
            localDate.setSeconds(this.syssecond);
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            starttime = PreferenceUtils.getString(this, Const.KEY_TIME_ON, "00:00");
            stoptime = PreferenceUtils.getString(this, Const.KEY_TIME_ON, "05:00");
            String str1 = null;
            ;
            String str2 = starttime + ":00";
            String str3 = stoptime + ":00";
            if ((str2.equalsIgnoreCase("00:00:00")) && (str3.equalsIgnoreCase("05:00:00"))) {
                notificationreciver(accessibilityEvent);
            }
            try {
                if (!isTimeBetweenTwoTime(str2, str3, str1)) {
                    notificationreciver(accessibilityEvent);
                    return;
                }
            } catch (ParseException localParseException) {
                localParseException.printStackTrace();
                return;
            }
        }
        notificationreciver(accessibilityEvent);
    }

    @Override
    public void onInterrupt() {
        isInit = false;
    }

    @Override
    public void onServiceConnected() {
        Log.e("CallReicerver", "onServiceConnected");
        {
            if (isInit) {
                return;
            }
            AccessibilityServiceInfo localAccessibilityServiceInfo = new AccessibilityServiceInfo();
            localAccessibilityServiceInfo.eventTypes = 64;
            localAccessibilityServiceInfo.notificationTimeout = 100L;
            localAccessibilityServiceInfo.feedbackType = -1;
            setServiceInfo(localAccessibilityServiceInfo);
            isInit = true;
            AlarmManager localAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            PendingIntent localPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent
                    (getBaseContext(), Notification_handler.class), 0);
            localAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock
                            .elapsedRealtime(), 120000L,
                    localPendingIntent);
        }

    }

    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
        return START_STICKY;
    }

    public void settingCamera(FlashPatternt flashPatternt) {
        Log.e("CallReicerver", "MyAccessibilityService");
        try {
            batteryLevel = CommonFuntions.getBatteryLevel(this);
            if (PreferenceUtils.getBoolean(this, Const.KEY_LOW_BATTERY)) {
                if (this.batteryLevel > PreferenceUtils.getInt(this, Const.KEY_PERCENT_BATTERY,
                        20)) {
                    CommonFuntions.tryFlash(flashPatternt.getTime(), this);
                }
            } else {
                CommonFuntions.tryFlash(flashPatternt.getTime(), this);
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }
    private void notificationreciver(AccessibilityEvent paramAccessibilityEvent) {
        if (!Lastpackagename.equals(paramAccessibilityEvent.getPackageName().toString())) {
//            this.constants.Isscreenlocked();
            if ((paramAccessibilityEvent.getParcelableData() instanceof Notification)) {
                String str = paramAccessibilityEvent.getPackageName().toString();
//                new ApplicationData();
                App localApplicationData = FlashlightApplication.getInstance().getDatabase()
                        .getAppByID(str.toString());
                if ((localApplicationData != null) && (localApplicationData.isFlash() == 1)) {
                    if (PreferenceUtils.getBoolean(this, Const.KEY_FLASH_WHEN_LOCK)) {
                        if ((CommonFuntions.Isscreenlocked(this)) && (PreferenceUtils
                                .getBoolean(this, Const.KEY_ALLOW_FLASH_Noti, false))) {
                            ArrayList<FlashPatternt> pList = FlashlightApplication.getInstance()
                                    .getDatabase().getAllPattern();
                            for (int j = 0; j < pList.size(); j++) {
                                FlashPatternt localPatternData2 = FlashlightApplication
                                        .getInstance().getDatabase().getPattertByID((
                                                pList.get(j)).getId());
                                Lastpackagename = str.toString();
                                settingCamera(localPatternData2);
                                break;
                            }
                        }
                    } else if (PreferenceUtils
                            .getBoolean(this, Const.KEY_ALLOW_FLASH_Noti) && !(CommonFuntions
                            .Isscreenlocked(this))) {
                        ArrayList<FlashPatternt> pList = FlashlightApplication.getInstance()
                                .getDatabase().getAllPattern();
                        for (int i = 0; i < pList.size(); i++) {
                            FlashPatternt localPatternData1 = FlashlightApplication
                                    .getInstance().getDatabase().getPattertByID((
                                            pList.get(i)).getId());
                            Lastpackagename = str.toString();
                            settingCamera(localPatternData1);
                        }
                    }
                }
            }
        }
    }

    public class Notification_handler
            extends BroadcastReceiver {
        public void onReceive(Context paramContext, Intent paramIntent) {
            MyAccessibilityService.Lastpackagename = "parleg";
        }
    }
}
