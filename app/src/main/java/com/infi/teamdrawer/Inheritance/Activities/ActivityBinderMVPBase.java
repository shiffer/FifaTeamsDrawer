package com.infi.teamdrawer.Inheritance.Activities;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.widget.Toast;

import com.infi.teamdrawer.mvp.IBasePresenter;
import com.infi.teamdrawer.mvp.IBaseView;

/**
 * Created by Ohad Shiffer
 * 25 October 2016
 */

public abstract class ActivityBinderMVPBase<BindingType extends ViewDataBinding,
        PresenterType extends IBasePresenter<ViewType>,
        ViewType extends IBaseView<PresenterType>>
        extends ActivityBinderBase<BindingType>
        implements IBaseView<PresenterType> {

    private PresenterType mPresenter;

    protected abstract PresenterType initPresenter(Bundle savedInstanceState);

    public abstract @LayoutRes
    int getLayout();

    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = initPresenter(savedInstanceState);
        super.onCreate(savedInstanceState);

        if (mPresenter != null) {
            mPresenter.onViewCreated(savedInstanceState, getIntent().getExtras());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (getPresenter() != null) {
            setViewOnPresenter();
            getPresenter().onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (getPresenter() != null) {
            getPresenter().onSaveState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        setViewOnPresenter();
        super.onStart();
        attachPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resumePresenter();
    }

    @Override
    protected void onPause() {
        pausePresenter();
        super.onPause();
    }

    @Override
    protected void onStop() {
        dettachPresenter();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (getPresenter() != null) {
            getPresenter().onViewDestroyed();
        }

        mPresenter = null;

        super.onDestroy();
    }

    @Override
    public void setPresenter(PresenterType presenter) {
        mPresenter = presenter;
    }


    @Override
    public PresenterType getPresenter() {
        return mPresenter;
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(int strRes) {
        Toast.makeText(this, strRes, Toast.LENGTH_SHORT).show();
    }

    private void resumePresenter() {
        final PresenterType presenter = getPresenter();
        if(presenter != null) {
            presenter.onViewResumed();
        }
    }

    private void pausePresenter() {
        final PresenterType presenter = getPresenter();
        if(presenter != null) {
            presenter.onViewPaused();
        }
    }

    private void attachPresenter() {
        final PresenterType presenter = getPresenter();
        if(presenter != null) {
            presenter.onViewAttached();
        }
    }

    private void dettachPresenter() {
        final PresenterType presenter = getPresenter();
        if(presenter != null) {
            presenter.onViewDetached();
        }
    }

    private void setViewOnPresenter() {
        final PresenterType presenter = getPresenter();
        if(presenter != null) {
            presenter.setView(this);
        }
    }
}
