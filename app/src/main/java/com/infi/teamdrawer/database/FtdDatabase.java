package com.infi.teamdrawer.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by infi on 06/11/2016.
 *
 */

@Database(name = FtdDatabase.DATABASE_NAME,
          version = FtdDatabase.VERSION,
          foreignKeyConstraintsEnforced = true)
public class FtdDatabase {

    public static final String DATABASE_NAME = "FifaTeamsDrawerDB";

    public static final int VERSION = 1;


}