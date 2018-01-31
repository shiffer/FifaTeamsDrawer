package com.infi.teamdrawer.database;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.InstanceAdapter;
import com.raizlabs.android.dbflow.structure.database.FlowCursor;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * Created by infi on 03/07/2016.
 *
 */
public abstract class FlowQueryCursorLoader<TModel, TResult extends IQueryResult> extends CustomQueryBaseLoader<TModel, TResult> {

    final InstanceAdapter mInstanceAdapter;

    /**
     * Creates an empty unspecified CursorLoader.  You must follow this with
     * calls to {@link #setUri(Uri)}, {@link #setSelection(String)}, etc
     * to specify the query to perform.
     */
    public FlowQueryCursorLoader(Context context, Class<TModel> modelClass, Class<TResult> resultClass, Uri uri) {
        super(context, modelClass, resultClass, uri);
        mInstanceAdapter = FlowManager.getInstanceAdapter(modelClass);
    }

    public FlowQueryCursorLoader(Context context, Type modelType, Class modelClass, Class<TResult> resultClass, Uri uri) {
        super(context, modelType, modelClass, resultClass, uri);
        mInstanceAdapter = FlowManager.getInstanceAdapter(modelClass);
    }


    /**
     * Creates a fully-specified CursorLoader.  See
     * {@link ContentResolver#query(Uri, String[], String, String[], String)
     * ContentResolver.query()} for documentation on the meaning of the
     * parameters.  These will be passed as-is to that call.
     */
    public FlowQueryCursorLoader(Context context, Class<TModel> modelClass, Class<TResult> resultClass, Uri uri, String[] projection, String selection,
                                 String[] selectionArgs, String sortOrder) {
        super(context, modelClass, resultClass, uri, projection, selection, selectionArgs, sortOrder);
        mInstanceAdapter = FlowManager.getInstanceAdapter(modelClass);
    }


    @NonNull
    protected IQueryResult<TModel> processQueryResult(final FlowCursor cursor) {
        IQueryResult modelQueryResult = null;
        if(mResultClass == QuerySingleResult.class){
            modelQueryResult = querySingleResult(cursor, mInstanceAdapter);
        }else{
            modelQueryResult = queryListResult(cursor, mInstanceAdapter);
        }
        return modelQueryResult;
    }

    @NonNull
    protected IQueryResult queryListResult(final FlowCursor cursor, final InstanceAdapter<TModel> instanceAdapter) {
        List<TModel> list =  instanceAdapter.getListModelLoader().convertToData(cursor, null);
        return new QueryListResult<>(cursor, list);
    }

    @NonNull
    protected IQueryResult querySingleResult(final FlowCursor cursor, final InstanceAdapter<TModel> instanceAdapter) {
        TModel model = instanceAdapter.getSingleModelLoader().convertToData(cursor, null);
        return new QuerySingleResult<>(cursor, model);
    }

    protected void postLoadFromCursor(IQueryResult<TModel> modelQueryResult) {

    }

    @Override
    public void cancelLoadInBackground() {
        super.cancelLoadInBackground();

        synchronized (this) {
            if (mCancellationSignal != null) {
                mCancellationSignal.cancel();
            }
        }
    }


    /* Runs on the UI thread */
    @Override
    public void deliverResult(IQueryResult<TModel> queryResult) {
        if (isReset()) {
            // An async query came in while the loader is stopped
            closeCursor(queryResult);
            return;
        }

        if (queryResult != null) {
            mCachedData = queryResult;

            if (isStarted()) {
                super.deliverResult(queryResult);
            }
        }
    }


    /**
     * Starts an asynchronous load of the contacts list data. When the result is ready the callbacks
     * will be called on the UI thread. If a previous load has been completed and is still valid
     * the result may be passed to the callbacks immediately.
     * <p/>
     * Must be called from the UI thread
     */
    @Override
    protected void onStartLoading() {
        if (mCachedData != null) {
            deliverResult(mCachedData);
        }
        if (takeContentChanged() || mCachedData == null) {
            forceLoad();
        }
    }

    /**
     * Must be called from the UI thread
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    @Override
    public void onCanceled(IQueryResult<TModel> data) {
        closeCursor(data);
    }

    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        closeCursor(mCachedData);
    }

    private void closeCursor(IQueryResult data) {
        if (data != null && data.getCursor() != null) {
            final FlowCursor cursor = data.getCursor();
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        mCachedData = null;
    }

    public Uri getUri() {
        return mUri;
    }

    public void setUri(Uri uri) {
        mUri = uri;
    }

    public String[] getProjection() {
        return mProjection;
    }

    public void setProjection(String[] projection) {
        mProjection = projection;
    }

    public String getSelection() {
        return mSelection;
    }

    public void setSelection(String selection) {
        mSelection = selection;
    }

    public String[] getSelectionArgs() {
        return mSelectionArgs;
    }

    public void setSelectionArgs(String[] selectionArgs) {
        mSelectionArgs = selectionArgs;
    }

    public String getSortOrder() {
        return mSortOrder;
    }

    public void setSortOrder(String sortOrder) {
        mSortOrder = sortOrder;
    }

    @Override
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(prefix, fd, writer, args);
        writer.print(prefix);
        writer.print("mUri=");
        writer.println(mUri);
        writer.print(prefix);
        writer.print("mProjection=");
        writer.println(Arrays.toString(mProjection));
        writer.print(prefix);
        writer.print("mSelection=");
        writer.println(mSelection);
        writer.print(prefix);
        writer.print("mSelectionArgs=");
        writer.println(Arrays.toString(mSelectionArgs));
        writer.print(prefix);
        writer.print("mSortOrder=");
        writer.println(mSortOrder);
        writer.print(prefix);
        writer.print("mCachedData=");
        writer.println(mCachedData);
    }
}
