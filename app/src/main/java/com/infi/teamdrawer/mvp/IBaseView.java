package com.infi.teamdrawer.mvp;


/**
 * Created by infi on 10/10/2016.
 *
 */

public interface IBaseView<T extends IBasePresenter> {

    void setPresenter(T presenter);

    T getPresenter();

    void showToast(String s);

    void showToast(int strRes);
}
