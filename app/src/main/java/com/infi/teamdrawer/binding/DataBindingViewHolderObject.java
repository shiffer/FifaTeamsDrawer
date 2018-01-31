package com.infi.teamdrawer.binding;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by roberto on 17/11/2016.
 * Have fun!
 */

public abstract class DataBindingViewHolderObject<T extends ViewDataBinding, O> extends RecyclerView.ViewHolder {

    private T binder;

    public DataBindingViewHolderObject(T binder) {
        super(binder.getRoot());
        this.binder = binder;
    }

    public T getBinder() {
        return binder;
    }

    public abstract void bindData(O data);
}
