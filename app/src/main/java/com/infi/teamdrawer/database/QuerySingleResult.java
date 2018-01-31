package com.infi.teamdrawer.database;

import com.raizlabs.android.dbflow.structure.database.FlowCursor;

public class QuerySingleResult<TModel>  implements IQueryResult<TModel> {

    private final FlowCursor mCursorResult;
    private TModel mModel;

    public QuerySingleResult(FlowCursor cursorResult, TModel model) {
        this.mCursorResult = cursorResult;
        this.mModel = model;
    }

    public FlowCursor getCursor() {
        return mCursorResult;
    }

    @Override
    public String toString() {
        return "QuerySingleResult{" +
                "mCursorResult=" + mCursorResult +
                ", mModel=" + mModel +
                '}';
    }

    @Override
    public TModel getQueryResult() {
        return mModel;
    }
}
