package com.infi.teamdrawer.Inheritance;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.infi.teamdrawer.database.FtdDatabase;
import com.raizlabs.android.dbflow.config.DatabaseConfig;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by ohadshiffer
 * on 31/01/2018.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initDB(this);
        Stetho.initializeWithDefaults(this);
    }

    public void initDB(Application context) {
        final FlowConfig.Builder builder = new FlowConfig.Builder(context)
                .openDatabasesOnInit(true);

        final DatabaseConfig build = new DatabaseConfig
                .Builder(FtdDatabase.class)
                .build();

        builder.addDatabaseConfig(build);

        FlowManager.init(builder.build());

        FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);

    }

}
