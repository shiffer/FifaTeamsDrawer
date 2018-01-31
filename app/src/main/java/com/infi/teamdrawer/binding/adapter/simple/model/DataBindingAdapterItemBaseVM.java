package com.infi.teamdrawer.binding.adapter.simple.model;

import android.databinding.BaseObservable;

import com.infi.teamdrawer.BR;

/**
 * Created by roberto on 17/01/2017.
 * Have fun!
 */

public abstract class DataBindingAdapterItemBaseVM extends BaseObservable implements IDataBindingAdapterItemBaseVM {

    private int mId;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    public DataBindingAdapterItemBaseVM(int id) {
        mId = id;
    }

    public DataBindingAdapterItemBaseVM() {
    }

    public int getId() {
        return mId;
    }
}
