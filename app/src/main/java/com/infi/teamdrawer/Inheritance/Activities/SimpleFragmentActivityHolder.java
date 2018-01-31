package com.infi.teamdrawer.Inheritance.Activities;

import android.os.Bundle;

import com.infi.teamdrawer.Inheritance.Fragments.FragmentBase;
import com.infi.teamdrawer.R;
import com.infi.teamdrawer.databinding.SimpleFragmentContainerBinder;

/**
 * Created by Lior Zamir - Have fun - 28/02/2017.
 */

public abstract class SimpleFragmentActivityHolder extends ActivityBinderBase<SimpleFragmentContainerBinder> {
    @Override
    public int getLayout() {
        return R.layout.simple_fragment_container;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null && getFragmentManager().findFragmentById(R.id.fragment_container) == null) {
            final FragmentBase fragment = getFragment(getIntent().getExtras());
            addFragment(R.id.fragment_container, fragment, fragment.getFragmentTag());
        }
    }

    @Override
    public void onBackPressed() {
        if (isTaskRoot()) { // If Fragment Activity is the last activity that mean we got entrance from notification or feed
                            // we should return to the main activity
            finish();
        } else {
            super.onBackPressed();
        }
    }

    protected abstract FragmentBase getFragment(Bundle extras);
}
