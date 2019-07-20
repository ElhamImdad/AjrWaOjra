package com.example.smoot.ajerwaojra.Helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class MyGridLayout extends GridLayoutManager {

    public MyGridLayout(Context context, int spanCount) {
        super(context, spanCount);
    }
    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }
    @Override
    public void onItemsUpdated(@NonNull RecyclerView recyclerView, int positionStart, int itemCount) {
        super.onItemsUpdated(recyclerView, positionStart, itemCount);
    }
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {

            super.onLayoutChildren(recycler, state);

        } catch (IndexOutOfBoundsException e) {

            Log.e("kkkk", "Inconsistency detected");
        }
    }
}
