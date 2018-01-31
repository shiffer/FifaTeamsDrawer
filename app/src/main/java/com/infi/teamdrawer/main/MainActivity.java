package com.infi.teamdrawer.main;

import android.os.Bundle;

import com.infi.teamdrawer.Inheritance.Activities.ActivityBinderMVPBase;
import com.infi.teamdrawer.R;
import com.infi.teamdrawer.databinding.ActivityMainBinding;
import com.infi.teamdrawer.main.TeamsFragment.TeamsFragment;

public class MainActivity
        extends ActivityBinderMVPBase<ActivityMainBinding, IMainActivityPresenter, IMainActivityView>
        implements IMainActivityView {

    private static final String TAG = "MainActivity";

    @Override
    protected IMainActivityPresenter initPresenter(Bundle savedInstanceState) {
        return new MainActivityPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(R.id.fragment_container, TeamsFragment.newInstance(), TeamsFragment.TAG);
    }

}
