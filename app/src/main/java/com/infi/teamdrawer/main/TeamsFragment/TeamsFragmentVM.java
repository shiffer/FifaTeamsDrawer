package com.infi.teamdrawer.main.TeamsFragment;

import android.databinding.BaseObservable;

/**
 * Created by ohadshiffer
 * on 24/12/2017.
 */

public class TeamsFragmentVM extends BaseObservable {

    private TeamsFragmentManager mManager;

    public void setManager(TeamsFragmentManager mManager) {
        this.mManager = mManager;
    }

    public TeamsFragmentManager getManager() {
        return mManager;
    }

    public void onAddTeamClick() {
        if (mManager != null) {
            mManager.onAddTeamClick();
        }
    }

    public void onConfirmClick() {
        if (mManager != null) {
            mManager.onConfirmClick();
        }
    }

    public interface TeamsFragmentManager {
        void onAddTeamClick();
        void onConfirmClick();
    }

}
