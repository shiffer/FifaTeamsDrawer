package com.infi.teamdrawer.database;

import com.raizlabs.android.dbflow.structure.database.FlowCursor;

public interface IQueryResult<T> {

    FlowCursor getCursor();
    T getQueryResult();
}
