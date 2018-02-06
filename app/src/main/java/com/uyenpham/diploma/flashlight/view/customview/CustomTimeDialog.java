package com.uyenpham.diploma.flashlight.view.customview;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.uyenpham.diploma.flashlight.R;
import com.uyenpham.diploma.flashlight.utils.PreferenceUtils;

import static com.uyenpham.diploma.flashlight.utils.Const.KEY_TIME_DELAY;


public class CustomTimeDialog extends Dialog implements RadioButton.OnCheckedChangeListener {
    private String tag;
    RadioButton rb10s, rb20s, rb1m, rb3m, rb5m, rb10m, rb30m, rbNever;

    public CustomTimeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        tag = compoundButton.getTag().toString();

    }

    public interface OnOKClickListener {
        void onClick(String id);
    }

    private OnOKClickListener onOkListener;

    public void setListener(OnOKClickListener onOkListener) {
        this.onOkListener = onOkListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_settime);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        setCancelable(true);

        rb10s = findViewById(R.id.rb10s);
        rb10s.setOnCheckedChangeListener(this);
        rb20s = findViewById(R.id.rb20s);
        rb20s.setOnCheckedChangeListener(this);
        rb1m = findViewById(R.id.rb1m);
        rb1m.setOnCheckedChangeListener(this);
        rb3m = findViewById(R.id.rb3m);
        rb3m.setOnCheckedChangeListener(this);
        rb5m = findViewById(R.id.rb5m);
        rb5m.setOnCheckedChangeListener(this);
        rb10m = findViewById(R.id.rb10m);
        rb10m.setOnCheckedChangeListener(this);
        rb30m = findViewById(R.id.rb30m);
        rb30m.setOnCheckedChangeListener(this);
        rbNever = findViewById(R.id.rbNever);
        rbNever.setOnCheckedChangeListener(this);
        setState();
        findViewById(R.id.btnOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOkListener.onClick(tag);
            }
        });
    }

    private void setState() {
        int time = PreferenceUtils.getInt(getContext(), KEY_TIME_DELAY, 0);
        switch (time) {
            case 10 * 1000:
                rb10s.setChecked(true);
                break;
            case 20 * 1000:
                rb20s.setChecked(true);
                break;
            case 60 * 1000:
                rb1m.setChecked(true);
                break;
            case 3 * 60 * 1000:
                rb3m.setChecked(true);
                break;
            case 5 * 60 * 1000:
                rb5m.setChecked(true);
                break;
            case 10 * 1000 * 60:
                rb10m.setChecked(true);
                break;
            case 30 * 60 * 1000:
                rb30m.setChecked(true);
                break;
            default:
                rbNever.setChecked(true);
                break;
        }
    }

}
