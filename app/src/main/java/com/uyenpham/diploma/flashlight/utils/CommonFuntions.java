package com.uyenpham.diploma.flashlight.utils;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.provider.ContactsContract;
import android.view.View;

import com.uyenpham.diploma.flashlight.model.App;
import com.uyenpham.diploma.flashlight.model.Contact;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

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
}
