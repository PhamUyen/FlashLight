package com.uyenpham.diploma.flashlight.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.uyenpham.diploma.flashlight.FlashlightApplication;
import com.uyenpham.diploma.flashlight.R;
import com.uyenpham.diploma.flashlight.model.App;
import com.uyenpham.diploma.flashlight.model.Contact;
import com.uyenpham.diploma.flashlight.model.FlashPatternt;
import com.uyenpham.diploma.flashlight.utils.CommonFuntions;
import com.uyenpham.diploma.flashlight.utils.Const;
import com.uyenpham.diploma.flashlight.utils.DatabaseHelper;
import com.uyenpham.diploma.flashlight.utils.PreferenceUtils;

import java.util.ArrayList;

import static android.Manifest.permission.CAMERA;

/**
 * Created by Ka on 2/7/2018.
 */

public class SplashActivity extends AppCompatActivity {
    private DatabaseHelper mDatabae;
    private static int PERMISSIONS_REQ_CODE = 1001;
    public static boolean isGetContact = false;
    public static boolean isGetApp = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        CommonFuntions.hideActionBar(this);
        initData();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1 && !checkPermission()) {
            requestPermission();
        } else {
            saveListContact();
            saveListApp();
            if (isGetApp && isGetContact) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }
    }

    private void initData() {
        mDatabae = FlashlightApplication.getInstance().getDatabase();
        if (PreferenceUtils.getBoolean(this, Const.KEY_FIRST_INSTALL, true)) {
            saveListDefautPattern();
            PreferenceUtils.saveBoolean(this, Const.KEY_FIRST_INSTALL, false);
        }
    }

    private void saveListContact() {
        isGetContact = false;
        ArrayList<Contact> listContact = CommonFuntions.link2ListContact(mDatabae.getAllContact()
                , CommonFuntions.getListContact(this));
        mDatabae.deleteAllContact();
        for (Contact contact : listContact) {
            mDatabae.insertContact(contact);
        }
    }

    private void saveListApp() {
        isGetApp = false;
        ArrayList<App> listApp = CommonFuntions.link2ListApp(mDatabae.getAllApp()
                , CommonFuntions.getListApp(this));
        mDatabae.deleteAllApp();
        for (App app : listApp) {
            mDatabae.insertApp(app);
        }
    }

    private void saveListDefautPattern() {
        ArrayList<FlashPatternt> list = new ArrayList<>();
        list.add(new FlashPatternt(1, "Call pattern1", "Thời lượng: liên tục", 0, Const
                .TYPE_PATERN_CALL));
        list.add(new FlashPatternt(2, "Call pattern2", "Thời lượng : 5 giây", 5 * 1000, Const
                .TYPE_PATERN_CALL));
        list.add(new FlashPatternt(3, "Notification pattern1", "Thời lượng : 2 giây", 2 * 1000,
                Const.TYPE_PATERN_SMS));
        list.add(new FlashPatternt(4, "Notification pattern2", "Thời lượng : 4 giây", 4 * 1000,
                Const.TYPE_PATERN_SMS));

        for (FlashPatternt patternt : list) {
            mDatabae.insertPattern(patternt);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //if user allow permission: enable receiver
        // disable receiver and finish app if not allow
        if (PERMISSIONS_REQ_CODE == requestCode) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] ==
                        PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[3] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[4] == PackageManager.PERMISSION_GRANTED) {
//                    saveListContact();
//                    saveListApp();
//                    if (isGetApp && isGetContact) {
//                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                        finish();
//                    }
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            }
        }
    }

    private boolean checkPermission() {
        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(),
                CAMERA);
        int thirdPer = ContextCompat.checkSelfPermission(getApplicationContext(), android
                .Manifest.permission.READ_CONTACTS);
        int fourPer = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest
                .permission.RECEIVE_SMS);
        int fivePer = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest
                .permission.READ_PHONE_STATE);
        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                thirdPer == PackageManager.PERMISSION_GRANTED &&
                fourPer == PackageManager.PERMISSION_GRANTED  &&
                fivePer == PackageManager.PERMISSION_GRANTED;
    }

    //send request permission for camera(flash)
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]
                {android.Manifest.permission.ACCESS_FINE_LOCATION,
                        CAMERA, android.Manifest.permission.READ_CONTACTS, Manifest.permission
                        .RECEIVE_SMS,Manifest.permission
                        .READ_PHONE_STATE
                }, PERMISSIONS_REQ_CODE);

    }
}
