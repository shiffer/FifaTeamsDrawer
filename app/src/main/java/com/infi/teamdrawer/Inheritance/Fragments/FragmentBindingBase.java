package com.infi.teamdrawer.Inheritance.Fragments;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class FragmentBindingBase<T extends ViewDataBinding> extends FragmentBase {

    private T mBinding;

    protected void onSetBinding(T binding){

    }

    public abstract @LayoutRes
    int getLayout();

    protected boolean isFragmentAttachedToRoot(){
        return false;
    }
    @Override
    protected View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getLayout(), container, isFragmentAttachedToRoot());
        View view = mBinding.getRoot();
        onSetBinding(mBinding);
        return view;
    }

    public T getBinder(){
        return mBinding;
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
