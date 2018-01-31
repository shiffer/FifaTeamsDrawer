package com.infi.teamdrawer.main.TeamsFragment;

import com.infi.teamdrawer.mvp.IBasePresenter;

import java.util.List;

/**
 * Created by ohadshiffer
 * on 24/12/2017.
 */

public interface ITeamsFragmentPresenter
        extends IBasePresenter<ITeamsFragmentView>,
        TeamsFragmentVM.TeamsFragmentManager {

    TeamsFragmentVM getViewModel();

    void setItems(List<TeamVM> data);

}
