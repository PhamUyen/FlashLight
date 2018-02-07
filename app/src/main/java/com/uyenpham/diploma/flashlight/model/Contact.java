package com.uyenpham.diploma.flashlight.model;

import java.io.Serializable;

/**
 * Created by Ka on 2/4/2018.
 */

public class Contact implements Serializable {
    private String name;
    private String number;
    private int isFlashCall;
    private int isFlashSMS;
    private int patternSMS;
    private int patternCall;
    private String id;

    public Contact() {
    }

    public Contact(String name, String number, int isFlashCall, int isFlashSMS, int
            patternSMS, int patternCall) {
        this.name = name;
        this.number = number;
        this.isFlashCall = isFlashCall;
        this.isFlashSMS = isFlashSMS;
        this.patternSMS = patternSMS;
        this.patternCall = patternCall;
    }

    public Contact(String id,String name, String number, int isFlashCall, int isFlashSMS, int patternCall,  int patternSMS ) {
        this.name = name;
        this.number = number;
        this.isFlashCall = isFlashCall;
        this.isFlashSMS = isFlashSMS;
        this.patternSMS = patternSMS;
        this.patternCall = patternCall;
        this.id = id;
    }

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int isFlashCall() {
        return isFlashCall;
    }

    public void setFlashCall(int flashCall) {
        isFlashCall = flashCall;
    }

    public int isFlashSMS() {
        return isFlashSMS;
    }

    public void setFlashSMS(int flashSMS) {
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
