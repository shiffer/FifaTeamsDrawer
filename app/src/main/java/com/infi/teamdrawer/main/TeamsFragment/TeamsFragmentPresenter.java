package com.infi.teamdrawer.main.TeamsFragment;

import android.os.Bundle;

import com.infi.teamdrawer.R;
import com.infi.teamdrawer.binding.adapter.simple.DataBindingAdapter;
import com.infi.teamdrawer.mvp.BasePresenter;
import com.infi.teamdrawer.utils.JavaUtils;

import java.util.List;

/**
 * Created by ohadshiffer
 * on 24/12/2017.
 */

public class TeamsFragmentPresenter
    extends BasePresenter<ITeamsFragmentView>
    implements ITeamsFragmentPresenter {

    private DataBindingAdapter<TeamVM> mAdapter;
    private TeamsFragmentVM mViewModel;
    //private List<TeamVM> mTeams;

    public TeamsFragmentPresenter(ITeamsFragmentView view) {
        super(view);
        mViewModel = new TeamsFragmentVM();
        mViewModel.setManager(this);

        //mTeams = new ArrayList<>();
        mAdapter = new DataBindingAdapter<>();
        //mAdapter.setItems(mTeams);
    }

    @Override
    public void onViewCreated(Bundle onSavedInstanceState, Bundle extras) {
        super.onViewCreated(onSavedInstanceState, extras);

        getView().initRecyclerView(mAdapter);
        getView().initViewModel(mViewModel);

        getView().initLoader();
    }

    @Override
    public TeamsFragmentVM getViewModel() {
        return mViewModel;
    }

    @Override
    public void setItems(List<TeamVM> data) {
        mAdapter.setItems(data);
    }

    @Override
    public void onAddTeamClick() {
        final String teamName = getView().getTeamName();
        if (JavaUtils.isEmpty(teamName)) {
            getView().showToast(R.string.err_invalid_team_name);
        } else {
            final TeamVM team = new TeamVM(teamName);
            //mAdapter.addItem(team);
            getView().onAddTeam(team);
        }
    }

    @Override
    public void onConfirmClick() {
        // TODO: 24/12/2017
    }

}
