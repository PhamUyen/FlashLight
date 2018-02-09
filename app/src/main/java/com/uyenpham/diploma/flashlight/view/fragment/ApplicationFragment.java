package com.uyenpham.diploma.flashlight.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uyenpham.diploma.flashlight.FlashlightApplication;
import com.uyenpham.diploma.flashlight.R;
import com.uyenpham.diploma.flashlight.model.App;
import com.uyenpham.diploma.flashlight.utils.Const;
import com.uyenpham.diploma.flashlight.view.activity.SettingPatternFlashActivity;
import com.uyenpham.diploma.flashlight.view.adapter.ApplicationAdapter;
import com.uyenpham.diploma.flashlight.view.adapter.IRecycleListener;

import java.util.ArrayList;

public class ApplicationFragment extends Fragment implements IRecycleListener,SearchView.OnQueryTextListener{
    private ArrayList<App> listApp;
    private RecyclerView rcvApp;
    private ApplicationAdapter adapter;
    private RelativeLayout rltNotFound;
    private TextView tvNotfound;
    private SearchView searchView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(
                R.layout.fragment_application, null);

        initView(view);
        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        listApp.clear();
        listApp = FlashlightApplication.getInstance().getDatabase().getAllApp();
        setAdapter(listApp,getActivity());
    }

    private void initView(View view){
        rcvApp = view.findViewById(R.id.rcvApp);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvApp.setLayoutManager(linearLayoutManager);
        rcvApp.setHasFixedSize(true);

        rltNotFound = view.findViewById(R.id.rltNotFound);
        tvNotfound = view.findViewById(R.id.tvNotFound);
        searchView =view.findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.onActionViewCollapsed();
                setAdapter(listApp,getActivity());
                return true;
            }
        });
    }
    private void initData(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2){
            listApp = FlashlightApplication.getInstance().getDatabase().getAllApp();
            if(listApp.size() >0){
                rltNotFound.setVisibility(View.GONE);
                rcvApp.setVisibility(View.VISIBLE);
                adapter = new ApplicationAdapter(listApp, getActivity());
                adapter.setListener(this);
                rcvApp.setAdapter(adapter);
            }else {
                rltNotFound.setVisibility(View.VISIBLE);
                rcvApp.setVisibility(View.GONE);
            }
        }else {
            tvNotfound.setText("Not support version < 18");
            rltNotFound.setVisibility(View.VISIBLE);
            rcvApp.setVisibility(View.GONE);
        }
    }
    @Override
    public void onClick(View view, int position) {
        Bundle bundle = new Bundle();
        App app =adapter.getListApp().get(position);
        bundle.putString(Const.KEY_NAME, app.getName());
        bundle.putParcelable(Const.KEY_IMAGE,app.getIcon());
        bundle.putInt(Const.KEY_FLASH, app.isFlash());
        bundle.putInt(Const.KEY_TYPE,Const.TYPE_APP);
        bundle.putInt(Const.KEY_ID_PATTERN,app.getPatternFlash());
        bundle.putString(Const.KEY_ID_APP,app.getId());
        Intent intent = new Intent(getActivity(), SettingPatternFlashActivity.class);
        intent.putExtra(Const.KEY_BUNDLE, bundle);
        startActivity(intent);
    }

    @Override
    public void onLongClick(View view, int position) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        query = query.toString().toLowerCase();

        ArrayList<App>filteredList = new ArrayList<>();

        for (int i = 0; i < listApp.size(); i++) {

            final String text = listApp.get(i).getName().toLowerCase();
            if (text.contains(query)) {

                filteredList.add(listApp.get(i));
            }
        }
        setAdapter(filteredList, getActivity());
        return true;
    }
    private void setAdapter(ArrayList<App> list, Context context) {
        adapter = new ApplicationAdapter(list, context);
        rcvApp.setAdapter(adapter);
        adapter.setListener(this);
//        tvNumberFriend.setText(list.size() + " Friends");
    }
}
