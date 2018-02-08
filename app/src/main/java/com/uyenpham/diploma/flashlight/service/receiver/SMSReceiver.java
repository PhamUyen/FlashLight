package com.uyenpham.diploma.flashlight.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.uyenpham.diploma.flashlight.FlashlightApplication;
import com.uyenpham.diploma.flashlight.model.Contact;
import com.uyenpham.diploma.flashlight.model.FlashPatternt;
import com.uyenpham.diploma.flashlight.service.ScreenOnService;
import com.uyenpham.diploma.flashlight.utils.CommonFuntions;
import com.uyenpham.diploma.flashlight.utils.Const;
import com.uyenpham.diploma.flashlight.utils.DatabaseHelper;
import com.uyenpham.diploma.flashlight.utils.FlashUtil;
import com.uyenpham.diploma.flashlight.utils.PreferenceUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ka on 2/8/2018.
 */

public class SMSReceiver extends BroadcastReceiver {
    String Number = "null";
    float batteryLevel;
    String contrycode = null;
    Contact data = null;
    DatabaseHelper dbHelper = null;
    Context mContext;
    String smscontactno;
    String starttime;
    String stoptime;
    String currenTime;
    private FlashPatternt patternt;

    @Override
    public void onReceive(Context context, Intent paramIntent) {
        dbHelper = FlashlightApplication.getInstance().getDatabase();
        mContext = context;
        if (PreferenceUtils.getBoolean(mContext, Const.KEY_TURN_BY_POWER, false)) {
            context.startService(new Intent(mContext, ScreenOnService.class));
        }
        Bundle localBundle = paramIntent.getExtras();
        if (localBundle != null) {
            Object[] arrayOfObject = (Object[]) localBundle.get("pdus");
            SmsMessage[] arrayOfSmsMessage = new SmsMessage[arrayOfObject.length];
            for (int i = 0; i < arrayOfSmsMessage.length; i++) {
                arrayOfSmsMessage[i] = SmsMessage.createFromPdu((byte[]) arrayOfObject[i]);
                smscontactno = arrayOfSmsMessage[i].getOriginatingAddress();
            }
        }
        data = dbHelper.getContactByNumber(smscontactno);
        if (data == null) {
            contrycode = ("+" + CommonFuntions.GetCountryZipCode(mContext));
            smscontactno = smscontactno.replace(this.contrycode, "");
            smscontactno = smscontactno.replace(" ", "");
            data = dbHelper.getContactByNumber(smscontactno);
        }
        if (data != null && data.isFlashSMS() == 1) {
            patternt = dbHelper.getPattertByID(data.getPatternCall());
            if (PreferenceUtils.getBoolean(context, Const.KEY_NIGHT_MODE, false)) {
                //check time
                Calendar localCalendar = Calendar.getInstance();
                int syshour = localCalendar.get(Calendar.HOUR);
                int sysmin = localCalendar.get(Calendar.MINUTE);
                int syssecond = localCalendar.get(Calendar.SECOND);
                Date localDate = new Date();
                localDate.setHours(syshour);
                localDate.setMinutes(sysmin);
                localDate.setSeconds(syssecond);
                SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                this.starttime = PreferenceUtils.getString(context, Const.KEY_TIME_ON, "00:00");
                this.stoptime = PreferenceUtils.getString(context, Const.KEY_TIME_ON, "05:00");
                currenTime = localSimpleDateFormat.format(localDate);
                starttime = PreferenceUtils.getString(context, Const.KEY_TIME_ON, "00:00") +
                        ":00";
                stoptime = PreferenceUtils.getString(context, Const.KEY_TIME_ON, "05:00") +
                        ":00";
                if ((starttime.equalsIgnoreCase("00:00:00")) && (stoptime.equalsIgnoreCase
                        ("05:00:00"))) {
                    if (PreferenceUtils.getBoolean(context, Const.KEY_ALLOW_FLASH_SMS, true)) {
                        setStateSMS(patternt);
                    }
                } else {
                    try {
                        if (CommonFuntions.isTimeBetweenTwoTime(starttime, currenTime,
                                stoptime)) {
                            FlashUtil.stopFlickerFlash();
                        } else {
                            if (PreferenceUtils.getBoolean(context, Const.KEY_ALLOW_FLASH_SMS,
                                    true)) {
                                setStateSMS(patternt);
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        if (PreferenceUtils.getBoolean(context, Const.KEY_ALLOW_FLASH_SMS,
                                true)) {
                            setStateSMS(patternt);
                        }
                    }
                }
            } else {
                if (PreferenceUtils.getBoolean(context, Const.KEY_ALLOW_FLASH_SMS, true)) {
                    setStateSMS(patternt);
                }
            }
        }
    }

    private void setStateSMS(FlashPatternt patternt) {
        if (CommonFuntions.Isscreenlocked(mContext)) {
            if (PreferenceUtils.getBoolean(mContext, Const.KEY_FLASH_WHEN_LOCK, false)) {
                settingCamera(patternt);
            }
        } else {
            settingCamera(patternt);
        }
    }

    public void settingCamera(FlashPatternt patternt) {
        float batteryLevel = CommonFuntions.getBatteryLevel(mContext);
        if (PreferenceUtils.getBoolean(mContext, Const.KEY_LOW_BATTERY, false)) {
            if (batteryLevel > PreferenceUtils.getInt(mContext, Const.KEY_PERCENT_BATTERY, 20)) {

                CommonFuntions.tryFlash(patternt.getTime(), mContext);
            }
        } else {
            CommonFuntions.tryFlash(patternt.getTime(), mContext);
        }
    }
}
