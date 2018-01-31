//package com.infi.teamdrawer.database;
//
//import android.content.Context;
//
//import com.infi.teamdrawer.main.TeamsFragment.TeamVM;
//import com.raizlabs.android.dbflow.structure.database.FlowCursor;
//
///**
// * Created by ohadshiffer
// * on 31/01/2018.
// */
//
//public class LoaderProvider {
//
//    private static FlowQueryCursorLoader createRecommendedTeamsLoader(final Context context) {
//        return new FlowQueryCursorLoader<TeamVM, QueryListResult>(context,
//                TeamVM.class,
//                QueryListResult.class,
//                FtdContentProvider.TeamsProvider.getEndPoint()) {
//            @Override
//            protected FlowCursor prepareCursor() {
//                return null;
//            }
//        };
//    }
//
//
//
//}
