package com.uyenpham.diploma.flashlight.view.adapter;

import android.view.View;

public interface IRecycleListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
