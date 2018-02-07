package com.uyenpham.diploma.flashlight.model;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Ka on 2/4/2018.
 */

public class App implements Serializable, Comparable<App> {
    private Bitmap icon;
    private String name;
    private int isFlash;
    private int patternFlash;
    private String id;

    public App() {
    }

    public App(Bitmap icon, String name, int isFlash, int patternFlash) {
        this.icon = icon;
        this.name = name;
        this.isFlash = isFlash;
        this.patternFlash = patternFlash;
    }

    public App(String id, String name, Bitmap icon, int isFlash, int patternFlash) {
        this.icon = icon;
        this.name = name;
        this.isFlash = isFlash;
        this.patternFlash = patternFlash;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int isFlash() {
        return isFlash;
    }

    public void setFlash(int flash) {
        isFlash = flash;
    }

    public int getPatternFlash() {
        return patternFlash;
    }

    public void setPatternFlash(int patternFlash) {
        this.patternFlash = patternFlash;
    }

    @Override
    public int compareTo(@NonNull App app) {
        return id.compareToIgnoreCase(app.getId());
    }
}
