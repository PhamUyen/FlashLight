package com.uyenpham.diploma.flashlight.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.util.Log;

/**
 * Created by Ka on 2/8/2018.
 */

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NLService extends NotificationListenerService {

    private String TAG = this.getClass().getSimpleName();
    private NLServiceReceiver nlservicereciver;
    private String action = "com.uyenpham.diploma.flashlight.NOTIFICATION_LISTENER_SERVICE_EXAMPLE";

    @Override
    public void onCreate() {
        super.onCreate();
        nlservicereciver = new NLServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(action);
        registerReceiver(nlservicereciver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(nlservicereciver);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        Log.i(TAG, "**********  onNotificationPosted");
        Log.i(TAG, "ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn
                .getPackageName());
        Intent i = new Intent(action);
        i.putExtra("notification_event", "onNotificationPosted :" + sbn.getPackageName() + "\n");
        sendBroadcast(i);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
    }

    class NLServiceReceiver extends BroadcastReceiver {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent i1 = new Intent(action);
            i1.putExtra("notification_event", "=====================");
            sendBroadcast(i1);
            int i = 1;
            for (StatusBarNotification sbn : NLService.this.getActiveNotifications()) {
                Intent i2 = new Intent(action);
                i2.putExtra("notification_event", i + " " + sbn.getPackageName() + "\n");
                sendBroadcast(i2);
                i++;
            }
            Intent i3 = new Intent(action);
            i3.putExtra("notification_event", "===== Notification List ====");
            sendBroadcast(i3);
        }
    }

}
