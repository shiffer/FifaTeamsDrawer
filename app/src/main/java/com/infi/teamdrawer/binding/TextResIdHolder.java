package com.infi.teamdrawer.binding;

import android.support.annotation.StringRes;

/**
 * Created by roberto on 22/08/2017.
 * Have fun!
 */

public class TextResIdHolder {

    @StringRes
    private int mTextResId;

    public TextResIdHolder(int textResId) {
        mTextResId = textResId;
    }

    public int getTextResId() {
        return mTextResId;
    }
}
