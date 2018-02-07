package com.uyenpham.diploma.flashlight.model;

/**
 * Created by Ka on 2/4/2018.
 */

public class FlashPatternt {
    private String name;
    private String timeStr;
    private int time;
    private int tpye;
    private int id;
    private int checked;

    public FlashPatternt(String name, int time, String timeStr) {
        this.name = name;
        this.time = time;
        this.timeStr = timeStr;
    }

    public FlashPatternt(int id,String name, String timeStr, int time, int tpye) {
        this.name = name;
        this.timeStr = timeStr;
        this.time = time;
        this.tpye = tpye;
        this.id = id;
        this.checked =checked;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTpye() {
        return tpye;
    }

    public void setTpye(int tpye) {
        this.tpye = tpye;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
}
