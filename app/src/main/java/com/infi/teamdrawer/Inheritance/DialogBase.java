package com.infi.teamdrawer.Inheritance;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;

/**
 * Created by Ohad Shiffer on 8/17/15.
 *
 */
public abstract class DialogBase extends AppCompatDialog implements DialogBaseInterface {

    public DialogBase(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();
    }
}
