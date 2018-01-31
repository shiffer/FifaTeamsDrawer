package com.infi.teamdrawer.mvp;

import com.infi.teamdrawer.utils.JavaUtils;

import java.lang.ref.WeakReference;

/**
 * Created by infi on 18/10/2016.
 *
 */

public class MvpCallback<T extends IBasePresenter>/*<K>, K extends IBaseView>*/ {

    private WeakReference<T> mPresenter;

    public MvpCallback(T presenter) {
        mPresenter = new WeakReference<>(presenter);
    }

    public T getPresenter() {
        return JavaUtils.getWeakRef(mPresenter);
    }
//
//    public K getView(){
//        final T presenter = getPresenter();
//
//        if(presenter != null){
//            return presenter.getView();
//        }
//
//        return null;
//    }
}
