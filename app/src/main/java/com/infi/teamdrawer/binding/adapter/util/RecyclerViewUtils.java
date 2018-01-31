package com.infi.teamdrawer.binding.adapter.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by roberto on 05/02/2017.
 * Have fun!
 */

public class RecyclerViewUtils {

    public static int findViewPosition(View view) {
        if (view == null || view instanceof RecyclerView || view.getParent() == null || !(view.getParent() instanceof View)) {
            return RecyclerView.NO_POSITION;
        }
        if (view.getParent() instanceof RecyclerView) {
            return ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
        }

        return findViewPosition((View) view.getParent());
    }
}
