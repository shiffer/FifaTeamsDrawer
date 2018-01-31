package com.infi.teamdrawer.database;

import android.net.Uri;

import com.infi.teamdrawer.main.TeamsFragment.TeamVM;

/**
 * Created by ohadshiffer
 * on 31/01/2018.
 */

public class FtdContentProvider {

    public static final String AUTHORITY = "com.infi.teamdrawer";

    public static final String BASE_CONTENT_URI = "content://";

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = Uri.parse(BASE_CONTENT_URI + AUTHORITY).buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    public static class TeamsProvider {
        static final String ENDPOINT = TeamVM.TABLE_NAME;

        public static Uri getEndPoint() {
            return buildUri(ENDPOINT);
        }
    }

}
