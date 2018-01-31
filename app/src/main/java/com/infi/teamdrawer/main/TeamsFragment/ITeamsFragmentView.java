package com.infi.teamdrawer.main.TeamsFragment;

import com.infi.teamdrawer.binding.adapter.simple.DataBindingAdapter;
import com.infi.teamdrawer.mvp.IBaseView;

/**
 * Created by ohadshiffer
 * on 24/12/2017.
 */

public interface ITeamsFragmentView
    extends IBaseView<ITeamsFragmentPresenter> {

    void initRecyclerView(DataBindingAdapter<TeamVM> adapter);

    void initViewModel(TeamsFragmentVM viewModel);

    String getTeamName();

    void onAddTeam(TeamVM team);

    void initLoader();

}
