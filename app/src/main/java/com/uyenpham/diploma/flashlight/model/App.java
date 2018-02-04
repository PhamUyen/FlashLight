package com.uyenpham.diploma.flashlight.model;

/**
 * Created by Ka on 2/4/2018.
 */

public class App {
    private String icon;
    private String name;
    private boolean isFlash;
    private int patternFlash;

    public App() {
    }

    public App(String icon, String name, boolean isFlash, int patternFlash) {
        this.icon = icon;
        this.name = name;
        this.isFlash = isFlash;
        this.patternFlash = patternFlash;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFlash() {
        return isFlash;
    }

    public void setFlash(boolean flash) {
        isFlash = flash;
    }

    public int getPatternFlash() {
        return patternFlash;
    }

    public void setPatternFlash(int patternFlash) {
        this.patternFlash = patternFlash;
    }
}