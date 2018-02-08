package com.uyenpham.diploma.flashlight.utils;

import android.app.ActionBar;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.view.View;

import com.uyenpham.diploma.flashlight.R;
import com.uyenpham.diploma.flashlight.model.App;
import com.uyenpham.diploma.flashlight.model.Contact;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.uyenpham.diploma.flashlight.view.fragment.SwitchFlashFragment.isFlash;

public class CommonFuntions {
    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static void hideActionBar(Activity context) {
        View decorView = context.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = context.getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    public static ArrayList<Contact> getListContact(Context context) {
        ArrayList<Contact> listContact = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Contact contact = new Contact(id, name, phoneNo, 0, 0, 1, 1);
                        listContact.add(contact);
                    }
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
        return listContact;
    }

    public static ArrayList<Contact> link2ListContact(ArrayList<Contact> listDB,
                                                      ArrayList<Contact> listDevice) {
        if (listDB.size() > 0) {
            for (int i = 0; i < listDevice.size(); i++) {
                for (int j = 0; j < listDB.size(); j++) {
                    if (listDevice.get(i).compareTo(listDB.get(j)) == 1) {
                        listDevice.get(i).setFlashCall(listDB.get(j).isFlashCall());
                        listDevice.get(i).setFlashSMS(listDB.get(j).isFlashSMS());
                        listDevice.get(i).setPatternCall(listDB.get(j).getPatternCall());
                        listDevice.get(i).setPatternSMS(listDB.get(j).getPatternSMS());
                    }
                }
            }
        }
        return listDevice;
    }
    public static ArrayList<App> getListApp(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> list =packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        ArrayList<App> applist = new ArrayList<>();
        for (ApplicationInfo info : list) {
            try {
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    if ((info.flags & ApplicationInfo.FLAG_SYSTEM) != 1) {
                        applist.add(new App(info.packageName,info.loadLabel(packageManager).toString(),((BitmapDrawable)info.loadIcon(packageManager)).getBitmap(),0, 3));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return applist;
    }
    public static ArrayList<App> link2ListApp(ArrayList<App> listDB,
                                                      ArrayList<App> listDevice) {
        if (listDB.size() > 0) {
            for (int i = 0; i < listDevice.size(); i++) {
                for (int j = 0; j < listDB.size(); j++) {
                    if (listDevice.get(i).compareTo(listDB.get(j)) == 1) {
                        listDevice.get(i).setFlash(listDB.get(j).isFlash());
                        listDevice.get(i).setPatternFlash(listDB.get(j).getPatternFlash());
                    }
                }
            }
        }
        return listDevice;
    }


    public static float getBatteryLevel(Context context)
    {
        Intent localIntent = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        int i = localIntent.getIntExtra("level", -1);
        int j = localIntent.getIntExtra("scale", -1);
        if ((i == -1) || (j == -1)) {
            return 50.0F;
        }
        return 100.0F * (i / j);
    }

    public static boolean isTimeBetweenTwoTime(String paramString1, String paramString2, String paramString3)
            throws ParseException
    {
        if ((paramString1.matches("^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$")) && (paramString2.matches("^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$")) && (paramString3.matches("^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$")))
        {
            Date localDate1 = new SimpleDateFormat("HH:mm:ss").parse(paramString1);
            Calendar localCalendar1 = Calendar.getInstance();
            localCalendar1.setTime(localDate1);
            Date localDate2 = new SimpleDateFormat("HH:mm:ss").parse(paramString3);
            Calendar localCalendar2 = Calendar.getInstance();
            localCalendar2.setTime(localDate2);
            Date localDate3 = new SimpleDateFormat("HH:mm:ss").parse(paramString2);
            Calendar localCalendar3 = Calendar.getInstance();
            localCalendar3.setTime(localDate3);
            if (localDate2.compareTo(localDate3) < 0)
            {
                localCalendar2.add(5, 1);
                localDate2 = localCalendar2.getTime();
            }
            if (localDate1.compareTo(localDate3) < 0)
            {
                localCalendar1.add(5, 1);
                localDate1 = localCalendar1.getTime();
            }
            if (localDate2.before(localDate1)) {
                return false;
            }
            if (localDate2.after(localDate3))
            {
                localCalendar3.add(5, 1);
                localDate3 = localCalendar3.getTime();
            }
            if (localDate2.before(localDate3))
            {
                System.out.println("RESULT, Time lies b/w");
                return true;
            }
            return false;
        }
        throw new IllegalArgumentException("Not a valid time, expecting HH:MM:SS format");
    }
    public static boolean Isscreenlocked(Context context)
    {
        if (((KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE)).inKeyguardRestrictedInputMode())
        {
            return true;
        }
        return false;
    }
    public static String GetCountryZipCode(Context context) {
        String str1 = "";
        String str2 = ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getSimCountryIso().toUpperCase();
        String[] arrayOfString1 = context.getResources().getStringArray(R.array.CountryCodes);
        for (int i = 0;i < arrayOfString1.length; i++) {
                String[] arrayOfString2 = arrayOfString1[i].split(",");
                if (arrayOfString2[1].trim().equals(str2.trim())) {
                    str1 = arrayOfString2[0];
                }
        }
        return str1;
    }
    public static void tryFlash(int time,Context context) {
        setTimeOff(time);
        FlashUtil.flickerFlash(context);
        isFlash = true;
    }

    private static void setTimeOff(int time) {
        if (time != 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    FlashUtil.stopFlickerFlash();
                }
            }, time);
        }
    }
}
