package com.infi.teamdrawer.mvp;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by infibond on 20/11/2016.
 * 
 */

public abstract class BasePresenter<T extends IBaseView>
        implements IBasePresenter<T> {

    protected T mView;

    public BasePresenter(T view) {
        this.mView = view;
        if (view != null) {
            //noinspection unchecked
            view.setPresenter(this);
        }
    }

    @Override
    public void onViewResumed() {}

    @Override
    public void onViewPaused() {}

    @Override
    public void setView(IBaseView view) {
        mView = (T) view;
    }

    @Override
    public void onViewAttached() {}

    @Override
    public void onViewDetached() {
        mView = null;
    }

    @Override
    public void onViewCreated(Bundle onSavedInstanceState, Bundle extras) {}

    @Override
    public void onViewDestroyed() {}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {}

    @Override
    public final T getView() {
        return mView;
    }

    @Override
    public void onSaveState(Bundle state) {}
}
