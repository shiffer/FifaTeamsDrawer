package com.infi.teamdrawer.main;

import com.infi.teamdrawer.mvp.BasePresenter;

/**
 * Created by ohadshiffer
 * on 24/12/2017.
 */

public class MainActivityPresenter
        extends BasePresenter<IMainActivityView>
        implements IMainActivityPresenter {

    public MainActivityPresenter(IMainActivityView view) {
        super(view);
    }

}
