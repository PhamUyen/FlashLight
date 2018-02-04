package com.uyenpham.diploma.flashlight.model;

/**
 * Created by Ka on 2/4/2018.
 */

public class FlashPatternt {
    private String name;
    private int time;
    private int tpye;

    public FlashPatternt(String name, int time, int tpye) {
        this.name = name;
        this.time = time;
        this.tpye = tpye;
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
}
