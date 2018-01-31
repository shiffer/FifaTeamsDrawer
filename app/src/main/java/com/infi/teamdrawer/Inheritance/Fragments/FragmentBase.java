package com.infi.teamdrawer.Inheritance.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infi.teamdrawer.Inheritance.Activities.ActivityBase;


/**
 * Created by Ohad Shiffer on 27/11/17.
 *
 */
public abstract class FragmentBase extends Fragment {


    private boolean mIsSavedInstanceStateReached;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return onCreateFragmentView(inflater, container, savedInstanceState);
    }

    protected abstract View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public void onStart() {
        mIsSavedInstanceStateReached = false;

        super.onStart();
    }

    @Override
    public void onResume() {
        mIsSavedInstanceStateReached = false;

        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mIsSavedInstanceStateReached = true;

        super.onSaveInstanceState(outState);
    }

    public boolean isSavedInstanceStateReached() {
        return mIsSavedInstanceStateReached;
    }

    public ActivityBase getActivityBase() {
        return getActivityBase(false);
    }

    public ActivityBase getActivityBase(final boolean forceFragmentVisibility) {
        final ActivityBase activityBase = (ActivityBase) getActivity();
        if ((!forceFragmentVisibility || isVisible()) && activityBase != null && activityBase.getState() < ActivityBase.ACTIVITY_LIFECYCLE_STATE_REACHED_ON_STOP) {
            return activityBase;
        } else {
            return null;
        }
    }

    public abstract String getFragmentTag();

    public void onPagerSelectedPageChanged(boolean isVisible) {}

    /**
     * A human readable name for UI purposes such as pager
     * The fragment might not be attached at the time this method is called, please use the context arg
     * @return the fragment name or null
     * @param context Host context
     */
    public String getFragmentName(Context context) {
        return null;
    }

    public final void updateArguments(Bundle args) {
        final Bundle arguments = getArguments();
        if (arguments == null && !isAdded()) {
            setArguments(args);
        } else if (arguments != null) {
            arguments.clear();
            arguments.putAll(args);
        }
    }

    public <D> Loader<D> initLoader(int id, Bundle args, LoaderManager.LoaderCallbacks<D> callback) {
        return getActivityBase().getSupportLoaderManager().initLoader(id, args, callback);
    }

    public void destroyLoader(int id) {
        getActivityBase().getSupportLoaderManager().destroyLoader(id);
    }
}
