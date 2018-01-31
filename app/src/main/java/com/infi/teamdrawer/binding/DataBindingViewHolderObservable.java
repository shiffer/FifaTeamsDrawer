package com.infi.teamdrawer.binding;

import android.databinding.Observable;
import android.databinding.ViewDataBinding;

/**
 * Created by roberto on 17/11/2016.
 * Have fun!
 */

public abstract class DataBindingViewHolderObservable<T extends ViewDataBinding, O extends Observable>
        extends DataBindingViewHolderObject<T, O> {

    public DataBindingViewHolderObservable(T binder) {
        super(binder);
    }
}
