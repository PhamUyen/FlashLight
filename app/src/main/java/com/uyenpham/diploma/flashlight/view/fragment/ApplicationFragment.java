package com.uyenpham.diploma.flashlight.view.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uyenpham.diploma.flashlight.FlashlightApplication;
import com.uyenpham.diploma.flashlight.R;
import com.uyenpham.diploma.flashlight.model.App;
import com.uyenpham.diploma.flashlight.utils.Const;
import com.uyenpham.diploma.flashlight.view.activity.SettingPatternFlashActivity;
import com.uyenpham.diploma.flashlight.view.adapter.ApplicationAdapter;
import com.uyenpham.diploma.flashlight.view.adapter.IRecycleListener;

import java.util.ArrayList;

public class ApplicationFragment extends Fragment implements IRecycleListener{
    private PackageManager packageManager;
    private ArrayList<App> listApp;
    private RecyclerView rcvApp;
    private ApplicationAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(
                R.layout.fragment_application, null);

        initView(view);
        initData();
        return view;
    }
    private void initView(View view){
        rcvApp = view.findViewById(R.id.rcvApp);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvApp.setLayoutManager(linearLayoutManager);
        rcvApp.setHasFixedSize(true);
    }
    private void initData(){
        packageManager = getActivity().getPackageManager();
        listApp = FlashlightApplication.getInstance().getDatabase().getAllApp();
        adapter = new ApplicationAdapter(listApp, getActivity());
        adapter.setListener(this);
        rcvApp.setAdapter(adapter);
    }
    @Override
    public void onClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Const.KEY_NAME, listApp.get(position).getName());
        bundle.putParcelable(Const.KEY_IMAGE,listApp.get(position).getIcon());
        bundle.putInt(Const.KEY_FLASH, listApp.get(position).isFlash());
        bundle.putInt(Const.KEY_TYPE,Const.TYPE_APP);
        bundle.putInt(Const.KEY_ID_PATTERN,listApp.get(position).getPatternFlash());
        Intent intent = new Intent(getActivity(), SettingPatternFlashActivity.class);
        intent.putExtra(Const.KEY_BUNDLE, bundle);
        startActivity(intent);
    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
