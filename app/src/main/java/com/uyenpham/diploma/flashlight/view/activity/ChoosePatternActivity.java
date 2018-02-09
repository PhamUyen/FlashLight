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
import com.uyenpham.diploma.flashlight.model.App;
import com.uyenpham.diploma.flashlight.model.Contact;
import com.uyenpham.diploma.flashlight.model.FlashPatternt;
import com.uyenpham.diploma.flashlight.utils.CommonFuntions;
import com.uyenpham.diploma.flashlight.utils.Const;
import com.uyenpham.diploma.flashlight.utils.DatabaseHelper;
import com.uyenpham.diploma.flashlight.view.adapter.PatternAdapter;

import java.util.ArrayList;

public class ChoosePatternActivity extends AppCompatActivity implements View.OnClickListener, PatternAdapter.IPatterntListener {
    private ArrayList<FlashPatternt> listPattern;
    private RecyclerView rcvPattern;
    private int type;
    private int typePattern;
    private int idPattern;
    private PatternAdapter adapter;
    private int choosePatternID;
    private String idObj;
    private Contact contactR;
    private App app;
    private DatabaseHelper databaseHelper;

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
        findViewById(R.id.imvBack).setOnClickListener(this);
        findViewById(R.id.tvBack).setOnClickListener(this);
    }

    private void initData() {
        databaseHelper = FlashlightApplication.getInstance().getDatabase();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Const.KEY_BUNDLE);
        type = bundle.getInt(Const.KEY_TYPE);
        typePattern = bundle.getInt(Const.KEY_TYPE_PATTERN);
        idPattern = bundle.getInt(Const.KEY_ID_PATTERN);
        idObj =bundle.getString(Const.KEY_ID_OBJ);
        choosePatternID =idPattern;

        listPattern = FlashlightApplication.getInstance().getDatabase().getPattertByType(typePattern);
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
                if(type == Const.TYPE_CONTACT){
                    contactR = FlashlightApplication.getInstance().getDatabase().getContactByID(idObj);
                    if(typePattern == Const.TYPE_PATERN_CALL){
                        contactR.setPatternCall(choosePatternID);
                    }else {
                        contactR.setPatternSMS(choosePatternID);
                    }
                    CommonFuntions.resetListContact(contactR,databaseHelper);
                }else {
                    app = FlashlightApplication.getInstance().getDatabase().getAppByID(idObj);
                    app.setPatternFlash(choosePatternID);
                    CommonFuntions.resetListApp(app,databaseHelper);
                }
                Intent intent = new Intent();
                intent.putExtra(Const.KEY_ID_PATTERN,choosePatternID);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.tvBack:
            case R.id.imvBack:
                finish();
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
        choosePatternID =listPattern.get(position).getId();
    }
}
