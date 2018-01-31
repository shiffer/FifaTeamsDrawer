package com.infi.teamdrawer.binding.adapter.simple.model;


import android.databinding.Observable;
import android.support.annotation.LayoutRes;

/**
 * Created by roberto on 17/01/2017.
 * Have fun!
 */

public interface IDataBindingAdapterItemBaseVM extends Observable {

    int getBindingVariable();

    @LayoutRes
    int getLayoutResId();
}
