package com.uyenpham.diploma.flashlight.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.uyenpham.diploma.flashlight.FlashlightApplication;
import com.uyenpham.diploma.flashlight.R;
import com.uyenpham.diploma.flashlight.model.FlashPatternt;
import com.uyenpham.diploma.flashlight.utils.Const;
import com.uyenpham.diploma.flashlight.view.adapter.PatternAdapter;

import java.util.ArrayList;

public class ChoosePatternActivity extends AppCompatActivity implements View.OnClickListener, PatternAdapter.IPatterntListener {
    private ArrayList<FlashPatternt> listPattern;
    private RecyclerView rcvPattern;
    private int type;
    private int idPattern;
    private PatternAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern);

        initView();
        initData();
    }

    private void initView() {
        rcvPattern = findViewById(R.id.rcvPattern);
        findViewById(R.id.tvDone).setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Const.KEY_BUNDLE);
        type = bundle.getInt(Const.KEY_TYPE);
        idPattern = bundle.getInt(Const.KEY_ID_PATTERN);

        listPattern = FlashlightApplication.getInstance().getDatabase().getPattertByType(type);
        setChecked();
        adapter = new PatternAdapter(listPattern, this);
        adapter.setListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvPattern.setLayoutManager(linearLayoutManager);
        rcvPattern.setHasFixedSize(true);
        rcvPattern.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvDone:
                break;
            default:
                break;
        }
    }

    private void setChecked(){
        for(FlashPatternt patternt :listPattern){
            if(patternt.getId() == idPattern){
                patternt.setChecked(1);
            }
        }
    }

    @Override
    public void onRadioChecked(int position) {
        adapter.setStateRadioButton(position);
    }
}
