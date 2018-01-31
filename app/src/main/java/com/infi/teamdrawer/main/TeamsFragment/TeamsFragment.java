package com.infi.teamdrawer.main.TeamsFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.infi.teamdrawer.Inheritance.Fragments.FragmentBinderMVPBase;
import com.infi.teamdrawer.R;
import com.infi.teamdrawer.binding.adapter.simple.DataBindingAdapter;
import com.infi.teamdrawer.database.FtdContentProvider;
import com.infi.teamdrawer.database.FtdDatabase;
import com.infi.teamdrawer.databinding.ListFragmentBinding;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

import java.util.List;

/**
 * Created by Ohad Shiffer
 * 27 November 2017
 */
public class TeamsFragment
        extends FragmentBinderMVPBase<ListFragmentBinding, ITeamsFragmentPresenter, ITeamsFragmentView>
        implements ITeamsFragmentView, LoaderManager.LoaderCallbacks<List<TeamVM>> {

    public static final String TAG = "TeamsFragment";

    public static TeamsFragment newInstance() {
        Bundle args = new Bundle();
        TeamsFragment fragment = new TeamsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.list_fragment;
    }

    @Override
    protected ITeamsFragmentPresenter initPresenter() {
        return new TeamsFragmentPresenter(this);
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public void initRecyclerView(DataBindingAdapter<TeamVM> adapter) {
        getBinder().recyclerView.setAdapter(adapter);
    }

    @Override
    public void initViewModel(TeamsFragmentVM viewModel) {
        getBinder().setViewModel(viewModel);
    }

    @Override
    public String getTeamName() {
        return getBinder().addEt.getText().toString();
    }

    @Override
    public void onAddTeam(final TeamVM team) {
        FlowManager.getDatabase(FtdDatabase.class).beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                final ModelAdapter<TeamVM> modelAdapter = FlowManager.getModelAdapter(TeamVM.class);
                if (!modelAdapter.exists(team, databaseWrapper)) {
                    modelAdapter.insert(team, databaseWrapper);
                    getActivityBase().getContentResolver().notifyChange(FtdContentProvider.TeamsProvider.getEndPoint(), null);
                }
            }
        }).execute();

        getBinder().addEt.setText("");
    }

    @Override
    public void initLoader() {
        getActivityBase().getSupportLoaderManager().initLoader(0, null, this).forceLoad();
    }

    @Override
    public Loader<List<TeamVM>> onCreateLoader(int id, Bundle args) {
        return new FetchData(getActivityBase());
    }

    @Override
    public void onLoadFinished(Loader<List<TeamVM>> loader, List<TeamVM> data) {
        if (getPresenter() != null) {
            getPresenter().setItems(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<TeamVM>> loader) {
        getActivityBase().getSupportLoaderManager().restartLoader(0, null, this);
    }

    private static class FetchData extends AsyncTaskLoader<List<TeamVM>> {

        public FetchData(Context context) {
            super(context);
        }

        @Override
        public List<TeamVM> loadInBackground() {
            return SQLite.select()
                    .from(TeamVM.class)
                    .queryList();
        }

        @Override
        public void deliverResult(List<TeamVM> teams) {
            super.deliverResult(teams);
        }
    }

}
