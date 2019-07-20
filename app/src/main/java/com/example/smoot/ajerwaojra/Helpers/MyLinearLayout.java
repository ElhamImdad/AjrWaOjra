package com.example.smoot.ajerwaojra.Helpers;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

public class MyLinearLayout extends LinearLayoutManager {
    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {

        try {

            super.onLayoutChildren(recycler, state);

        } catch (IndexOutOfBoundsException e) {

            Log.e("kkkk", "Inconsistency detected");
        }

    }
    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }
}
