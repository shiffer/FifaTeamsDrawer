package com.infi.teamdrawer.Inheritance.Fragments;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.infi.teamdrawer.mvp.IBasePresenter;
import com.infi.teamdrawer.mvp.IBaseView;

/**
 * Created by infibond on 13/12/2016.
 *
 */

public abstract class FragmentBinderMVPBase
        <BindingType extends ViewDataBinding, PresenterType extends IBasePresenter<ViewType>, ViewType extends IBaseView<PresenterType>>
        extends FragmentBindingBase<BindingType>
        implements IBaseView<PresenterType> {

    private PresenterType mPresenter;

    private Toast mToast;

    protected abstract PresenterType initPresenter();

    @Override
    public void setPresenter(PresenterType presenter) {
        mPresenter = presenter;
    }

    @Override
    protected View onCreateFragmentView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        mPresenter = initPresenter();
        return super.onCreateFragmentView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getPresenter() != null) {
            getPresenter().onViewCreated(savedInstanceState, getArguments());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (getPresenter() != null) {
            setViewOnPresenter();
            getPresenter().onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onStart() {
        setViewOnPresenter();
        super.onStart();
        attachPresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        resumePresenter();
    }

    @Override
    public void onPause() {
        pausePresenter();
        super.onPause();
    }

    @Override
    public void onStop() {
        dettachPresenter();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        if (getPresenter() != null) {
            getPresenter().onViewDestroyed();
        }

        mPresenter = null;

        super.onDestroyView();
    }

    @Override
    public PresenterType getPresenter() {
        return mPresenter;
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (getPresenter() != null) {
            getPresenter().onSaveState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(int strRes) {
        Toast.makeText(getContext(), strRes, Toast.LENGTH_SHORT).show();
    }
}
