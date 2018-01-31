package com.infi.teamdrawer.database;

import com.raizlabs.android.dbflow.structure.database.FlowCursor;

import java.util.List;

public class QueryListResult<TModel> implements IQueryResult<List<TModel>> {

    private final FlowCursor mCursorResult;
    private List<TModel> mListModels;

    public QueryListResult(FlowCursor cursorResult, List<TModel> model) {
        this.mCursorResult = cursorResult;
        this.mListModels = model;
    }

    public FlowCursor getCursor() {
        return mCursorResult;
    }

    @Override
    public String toString() {
        return "QuerySingleResult{" +
                "mCursorResult=" + mCursorResult +
                ", mListModels=" + mListModels +
                '}';
    }

    @Override
    public List<TModel> getQueryResult() {
        return mListModels;
    }
}
