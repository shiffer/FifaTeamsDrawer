package com.infi.teamdrawer.mvp;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by infi on 10/10/2016.
 */

public interface IBasePresenter<T extends IBaseView> {
    T getView();

    void onViewResumed();
    void onViewPaused();
    void setView(IBaseView view);
    void onViewAttached();
    void onViewDetached();
    void onViewCreated(Bundle savedInstanceState, Bundle extras);
    void onViewDestroyed();
    void onActivityResult(int requestCode, int resultCode, Intent data);
    void onSaveState(Bundle state);
}
