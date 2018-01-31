package com.infi.teamdrawer.Inheritance.Activities;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;

/**
 * Created by Ohad Shiffer
 * 25 October 2016
 */

public abstract class ActivityBinderBase<T extends ViewDataBinding> extends ActivityBase {

    private T mBinding;

    protected void onSetBinding(T binding) {}

    public abstract @LayoutRes
    int getLayout();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, getLayout());
        onSetBinding(mBinding);
        super.onCreate(savedInstanceState);
    }

    public T getBinder() {
        return mBinding;
    }

}
