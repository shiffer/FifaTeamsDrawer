package com.infi.teamdrawer.binding.adapter.util;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.infi.teamdrawer.binding.adapter.simple.DataBindingAdapter;
import com.infi.teamdrawer.binding.adapter.simple.model.IDataBindingAdapterItemBaseVM;

/**
 * Created by Roberto B. on 01/02/2017.
 * Have fun
 */

public class GridSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    private RecyclerView mRecyclerView;

    public GridSpanSizeLookup(RecyclerView recyclerView) {
        this(recyclerView, true);
    }

    public GridSpanSizeLookup(RecyclerView recyclerView, boolean cacheEnabled) {
        mRecyclerView = recyclerView;
        setSpanIndexCacheEnabled(cacheEnabled);
    }

    @Override
    public int getSpanSize(int position) {
        IDataBindingAdapterItemBaseVM item = ((DataBindingAdapter)mRecyclerView.getAdapter()).getItemAt(position);
        if (item instanceof IFullSpanSize) {
            return ((GridLayoutManager)mRecyclerView.getLayoutManager()).getSpanCount();
        } else if (item instanceof IGridSpanSize) {
            return ((IGridSpanSize) item).getSpan();
        }

        return 1;
    }
}
