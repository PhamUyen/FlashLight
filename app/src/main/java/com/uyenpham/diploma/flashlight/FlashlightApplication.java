package com.uyenpham.diploma.flashlight;

import android.app.Application;

import com.uyenpham.diploma.flashlight.utils.DatabaseHelper;

public class FlashlightApplication extends Application {
    private DatabaseHelper myDatabase;

    public static FlashlightApplication INSTANCE;
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        initDatabse();
    }


    public DatabaseHelper getDatabase() {
        return myDatabase;
    }

    public static FlashlightApplication getInstance() {
        return INSTANCE;
    }

    private void initDatabse(){
         myDatabase= new DatabaseHelper(this);
    }
}
