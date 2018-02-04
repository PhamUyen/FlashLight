package com.uyenpham.diploma.flashlight.utils;

import android.content.Context;
import android.hardware.Camera;
import android.os.Handler;

import static com.uyenpham.diploma.flashlight.view.fragment.SwitchFlashFragment.isFlash;

public class FlashUtil {
    private static Camera cam;
    private static boolean isBlink;
    private static int blinkDelay;
    private static Handler mHandler = new Handler();
    private static Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                if (isBlink) {
                    flashOff();
                } else {
                    flashOn();
                }

            } finally {
                mHandler.postDelayed(mStatusChecker, blinkDelay);
            }
        }
    };

    //turn on flash
    public static void flashOn() {
        if (cam == null) {
            cam = Camera.open();
        }
        isBlink = true;
        Camera.Parameters paramOn = cam.getParameters();
        paramOn.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        cam.setParameters(paramOn);
        cam.startPreview();
        isFlash =true;
    }

    //turn off flash
    private static void flashOff() {
        isBlink = false;
        Camera.Parameters paramOff = cam.getParameters();
        paramOff.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        cam.setParameters(paramOff);
        cam.startPreview();
    }
    //make flash flicker on/off
    public static void flickerFlash(Context context) {
        stopFlickerFlash();
        isFlash =true;
        if (cam == null) {
            cam = Camera.open();
        }
        mStatusChecker.run();
    }

    //stop flicker flash
    public static void stopFlickerFlash() {
        isFlash =false;
        mHandler.removeCallbacks(mStatusChecker);
        if (cam != null) {
            cam.stopPreview();
            cam.release();
            cam = null;
        }
    }

    public static void setBlinkDelay(int delay) {
        blinkDelay = delay;
    }

}
