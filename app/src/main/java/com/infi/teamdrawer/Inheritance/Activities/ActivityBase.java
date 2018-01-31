package com.infi.teamdrawer.Inheritance.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.infi.teamdrawer.Inheritance.Fragments.FragmentBase;
import com.infi.teamdrawer.utils.JavaUtils;
import com.infi.teamdrawer.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Ohad Shiffer
 * on 27/11/17.
 */
public abstract class ActivityBase extends AppCompatActivity {

    public static final int ACTIVITY_LIFECYCLE_STATE_NOT_INITIALIZED = 0;
    public static final int ACTIVITY_LIFECYCLE_STATE_REACHED_ON_NEW_INTENT = ACTIVITY_LIFECYCLE_STATE_NOT_INITIALIZED + 1;
    public static final int ACTIVITY_LIFECYCLE_STATE_REACHED_ON_CREATE = ACTIVITY_LIFECYCLE_STATE_REACHED_ON_NEW_INTENT + 1;
    public static final int ACTIVITY_LIFECYCLE_STATE_REACHED_ON_START = ACTIVITY_LIFECYCLE_STATE_REACHED_ON_CREATE + 1;
    public static final int ACTIVITY_LIFECYCLE_STATE_REACHED_ON_RESUME = ACTIVITY_LIFECYCLE_STATE_REACHED_ON_START + 1;
    public static final int ACTIVITY_LIFECYCLE_STATE_REACHED_ON_PAUSE = ACTIVITY_LIFECYCLE_STATE_REACHED_ON_RESUME + 1;
    public static final int ACTIVITY_LIFECYCLE_STATE_REACHED_ON_STOP = ACTIVITY_LIFECYCLE_STATE_REACHED_ON_PAUSE + 1;
    public static final int ACTIVITY_LIFECYCLE_STATE_REACHED_ON_DESTROY = ACTIVITY_LIFECYCLE_STATE_REACHED_ON_STOP + 1;

    private int mLastActivityLifecycleState = ACTIVITY_LIFECYCLE_STATE_NOT_INITIALIZED;
    private boolean mIsSavedInstanceStateReached;

    private PendingStartActivity mPendingStartActivity;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLastActivityLifecycleState = ACTIVITY_LIFECYCLE_STATE_REACHED_ON_CREATE;
        mIsSavedInstanceStateReached = false;

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        mLastActivityLifecycleState = ACTIVITY_LIFECYCLE_STATE_REACHED_ON_START;

        super.onStart();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        mIsSavedInstanceStateReached = true;

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        mLastActivityLifecycleState = ACTIVITY_LIFECYCLE_STATE_REACHED_ON_RESUME;
        mIsSavedInstanceStateReached = false;

        super.onResume();

        if (mPendingStartActivity != null) {
            startActivityIfForeground(mPendingStartActivity.mIntent, mPendingStartActivity.mFinishAfter,
                    mPendingStartActivity.mRequestCode, mPendingStartActivity.mOptions);
            mPendingStartActivity = null;
        }
    }

    @Override
    protected void onPause() {
        mLastActivityLifecycleState = ACTIVITY_LIFECYCLE_STATE_REACHED_ON_PAUSE;

        super.onPause();
    }

    @Override
    protected void onStop() {
        mLastActivityLifecycleState = ACTIVITY_LIFECYCLE_STATE_REACHED_ON_STOP;

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mLastActivityLifecycleState = ACTIVITY_LIFECYCLE_STATE_REACHED_ON_DESTROY;

        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isActivityAlive() {
        return !isDestroyed() && !isFinishing();
    }

    /**
     * A transaction can only be committed with this method prior to its containing activity saving its state.
     * If the commit is attempted after that point, an exception will be thrown.
     * This is because the state after the commit can be lost if the activity needs to be restored from its state.
     */
    public boolean isCanHandleActivity() {
        return isActivityAlive() && !isSavedInstanceStateReached();
    }

    public int getState() {
        return mLastActivityLifecycleState;
    }

    public boolean isSavedInstanceStateReached() {
        return mIsSavedInstanceStateReached;
    }

    public void addFragment(@IdRes int containerViewId,
                            @NonNull Fragment fragment,
                            @NonNull String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                //.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                .add(containerViewId, fragment, fragmentTag)
                .disallowAddToBackStack()
                .commit();
    }

    public void addFragment(@IdRes int containerViewId,
                            @NonNull Fragment fragment,
                            @NonNull String fragmentTag,
                            @NonNull String backStackStateName) {
        getSupportFragmentManager()
                .beginTransaction()
                //.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                .add(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit();
    }

    public void removeFragment(@IdRes int containerViewId) {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(getSupportFragmentManager().findFragmentById(containerViewId))
                .commit();
    }

    public void replaceFragment(@IdRes int containerViewId,
                                @NonNull FragmentBase fragment,
                                @Nullable String backStackStateName) {
        final FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(containerViewId, fragment, fragment.getFragmentTag());

        if (JavaUtils.isNotEmpty(backStackStateName)) {
            transaction.addToBackStack(fragment.getFragmentTag());
        }

        transaction.commitAllowingStateLoss();

    }

    @IntDef({ACTIVITY_LIFECYCLE_STATE_NOT_INITIALIZED, ACTIVITY_LIFECYCLE_STATE_REACHED_ON_CREATE, ACTIVITY_LIFECYCLE_STATE_REACHED_ON_START,
            ACTIVITY_LIFECYCLE_STATE_REACHED_ON_RESUME, ACTIVITY_LIFECYCLE_STATE_REACHED_ON_PAUSE, ACTIVITY_LIFECYCLE_STATE_REACHED_ON_STOP,
            ACTIVITY_LIFECYCLE_STATE_REACHED_ON_DESTROY})
    @Retention(RetentionPolicy.SOURCE)

    public @interface ActivityLifecycleState {
    }

    public @Nullable
    String doNotRemoveDataOfEntity() {
        return null;
    }

    public <D> Loader<D> initLoader(int id, Bundle args, LoaderManager.LoaderCallbacks<D> callback) {
        return getSupportLoaderManager().initLoader(id, args, callback);
    }

    public void destroyLoader(int id) {
        getSupportLoaderManager().destroyLoader(id);
    }

    public boolean startActivityIfForeground(Intent intent, boolean finishAfter) {
        return startActivityIfForeground(intent, finishAfter, null, null);
    }

    public boolean startActivityIfForeground(Intent intent, boolean finishAfter, @Nullable Integer requestCode) {
        return startActivityIfForeground(intent, finishAfter, requestCode, null);
    }

    public boolean startActivityIfForeground(@NonNull Intent intent, boolean finishAfter, @Nullable Integer requestCode, @Nullable Bundle options) {
        if (mLastActivityLifecycleState >= ACTIVITY_LIFECYCLE_STATE_REACHED_ON_PAUSE) {
            mPendingStartActivity = new PendingStartActivity(intent, finishAfter, options, requestCode);
            return false;
        }

        if (requestCode != null) {
            ActivityCompat.startActivityForResult(this, intent, requestCode, options);
        } else {
            ActivityCompat.startActivity(this, intent, options);
        }

        if (finishAfter) {
            finish();
        }

        return true;
    }


    private static class PendingStartActivity {
        private Intent mIntent;
        private Bundle mOptions;
        private Integer mRequestCode;
        private boolean mFinishAfter;

        public PendingStartActivity(Intent intent, boolean finishAfter, Bundle options, Integer requestCode) {
            mIntent = intent;
            mFinishAfter = finishAfter;
            mOptions = options;
            mRequestCode = requestCode;
        }
    }

}
