package com.uyenpham.diploma.flashlight.view.activity;

import android.Manifest;
import android.app.ActionBar;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.uyenpham.diploma.flashlight.R;
import com.uyenpham.diploma.flashlight.utils.FlashUtil;
import com.uyenpham.diploma.flashlight.view.customview.CompassMapCoverView;

import java.util.List;

import static com.uyenpham.diploma.flashlight.view.fragment.SwitchFlashFragment.isFlash;

//import android.location.LocationManager;

/**
 * Created by Ka on 2/5/2018.
 */

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener ,SensorEventListener {
    private CompassMapCoverView coverView;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private String[] derection = {"N", "E", "S", "W"};
    private Double userLat;
    private Double userLng;
    private LocationManager locationManager;
    private Button btnFlash;
    // record the compass picture angle turned
    private float currentDegree = 0f;
    // device sensor manager
    private SensorManager mSensorManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        coverView = findViewById(R.id.coverView);
        coverView.setDirections(derection);
        btnFlash = findViewById(R.id.btnFlash);
        findViewById(R.id.btnBack).setOnClickListener(this);
        btnFlash.setOnClickListener(this);
        findViewById(R.id.btnCurrentLocation).setOnClickListener(this);
        mSensorManager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);

    }
    private void getLastKnownLocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission
                    .ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat
                    .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                Location l = locationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    // Found best last known location: %s", l);
                    bestLocation = l;
                    userLat = bestLocation.getLatitude();
                    userLng = bestLocation.getLongitude();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLat, userLng), 17.0f));
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getLastKnownLocation();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCurrentLocation:
                if(userLat!= null && userLng != null){
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLat, userLng), 17.0f));
                }
                break;
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnFlash:
                if(isFlash){
                    btnFlash.setText("OFF");
                    FlashUtil.stopFlickerFlash();
                }else {
                    btnFlash.setText("ON");
                    FlashUtil.flashOn();
                }
                break;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float degree = Math.round(sensorEvent.values[0]);
        coverView.setCompassRotation(degree);
        currentDegree = -degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
