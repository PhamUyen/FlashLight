package com.uyenpham.diploma.flashlight.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

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

public class CallReicerver extends BroadcastReceiver {
    private static String mLastState;
    String number = "";
    String contrycode = null;
    Contact data = null;
    DatabaseHelper dbHelper = null;
    Context mContext;
    String starttime;
    String stoptime;
    String currenTime = "";
    private FlashPatternt patternt;

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra("state");
        Log.e("CallReicerver", state);
        if (!state.equals(mLastState)) {
            mLastState = state;
            dbHelper = FlashlightApplication.getInstance().getDatabase();
            mContext = context;
            if (PreferenceUtils.getBoolean(mContext, Const.KEY_TURN_BY_POWER, false)) {
                context.startService(new Intent(mContext, ScreenOnService.class));
            }
        }
        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            number = intent.getExtras().getString("incoming_number");
            Log.e("CallReicerver", number);
            data = dbHelper.getContactByNumber(number);
            if (data == null) {
                Log.e("CallReicerver", "+" + CommonFuntions.GetCountryZipCode(mContext));
                contrycode = ("+" + CommonFuntions.GetCountryZipCode(mContext));
                number = number.replace(this.contrycode, "0");
                number = number.replace(" ", "");
                data = dbHelper.getContactByNumber(number);
            }
            if (data == null) {
                FlashUtil.flickerFlash(context);
            } else {
                if (data.isFlashCall() == 1) {
                    Log.e("CallReicerver", data.getNumber());
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
                        this.starttime = PreferenceUtils.getString(context, Const.KEY_TIME_ON,
                                "00:00");
                        this.stoptime = PreferenceUtils.getString(context, Const.KEY_TIME_ON,
                                "05:00");
                        currenTime = localSimpleDateFormat.format(localDate);
                        starttime = PreferenceUtils.getString(context, Const.KEY_TIME_ON, "00:00") +
                                ":00";
                        stoptime = PreferenceUtils.getString(context, Const.KEY_TIME_ON, "05:00") +
                                ":00";
                        if ((starttime.equalsIgnoreCase("00:00:00")) && (stoptime.equalsIgnoreCase
                                ("05:00:00"))) {
                            if (PreferenceUtils.getBoolean(context, Const.KEY_ALLOW_FLASH_CALL,
                                    true)) {
                                setStateCall(patternt);
                            }
                        } else {
                            try {
                                if (CommonFuntions.isTimeBetweenTwoTime(starttime, currenTime,
                                        stoptime)) {
                                    FlashUtil.stopFlickerFlash();
                                } else {
                                    if (PreferenceUtils.getBoolean(context, Const
                                                    .KEY_ALLOW_FLASH_CALL,
                                            true)) {
                                        setStateCall(patternt);
                                    }
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                                if (PreferenceUtils.getBoolean(context, Const.KEY_ALLOW_FLASH_CALL,
                                        true)) {
                                    setStateCall(patternt);
                                }
                            }
                        }
                    } else {
                        if (PreferenceUtils.getBoolean(context, Const.KEY_ALLOW_FLASH_CALL, true)) {
                            setStateCall(patternt);
                        }
                    }
                }
            }

        } else {
            FlashUtil.stopFlickerFlash();
        }
    }

    private void setStateCall(FlashPatternt patternt) {
        Log.e("CallReicerver", "isFlash");
        if (CommonFuntions.Isscreenlocked(mContext)) {
            if (PreferenceUtils.getBoolean(mContext, Const.KEY_FLASH_WHEN_LOCK, false)) {
                settingCameraforcall(patternt);
            }
        } else {
            settingCameraforcall(patternt);
        }
    }

    public void settingCameraforcall(FlashPatternt patternt) {
        Log.e("tag", " called settingCameraforcall");
        float batteryLevel = CommonFuntions.getBatteryLevel(mContext);
        if (PreferenceUtils.getBoolean(mContext, Const.KEY_PERCENT_BATTERY, false)) {
            if (batteryLevel > PreferenceUtils.getInt(mContext, Const.KEY_PERCENT_BATTERY, 20)) {
                CommonFuntions.tryFlash(patternt.getTime(), mContext);
            } else {
                return;
            }
        }
        CommonFuntions.tryFlash(patternt.getTime(), mContext);
    }
}
