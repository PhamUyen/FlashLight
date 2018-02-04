package com.uyenpham.diploma.flashlight.model;

import java.io.Serializable;

/**
 * Created by Ka on 2/4/2018.
 */

public class Contact implements Serializable {
    private String name;
    private String number;
    private boolean isFlashCall;
    private boolean isFlashSMS;
    private int patternSMS;
    private int patternCall;

    public Contact() {
    }

    public Contact(String name, String number, boolean isFlashCall, boolean isFlashSMS, int
            patternSMS, int patternCall) {
        this.name = name;
        this.number = number;
        this.isFlashCall = isFlashCall;
        this.isFlashSMS = isFlashSMS;
        this.patternSMS = patternSMS;
        this.patternCall = patternCall;
    }

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isFlashCall() {
        return isFlashCall;
    }

    public void setFlashCall(boolean flashCall) {
        isFlashCall = flashCall;
    }

    public boolean isFlashSMS() {
        return isFlashSMS;
    }

    public void setFlashSMS(boolean flashSMS) {
        isFlashSMS = flashSMS;
    }

    public int getPatternSMS() {
        return patternSMS;
    }

    public void setPatternSMS(int patternSMS) {
        this.patternSMS = patternSMS;
    }

    public int getPatternCall() {
        return patternCall;
    }

    public void setPatternCall(int patternCall) {
        this.patternCall = patternCall;
    }
}
