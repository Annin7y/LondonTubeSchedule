package android.my.annin.londontubeschedule.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TubeStationDbHelper {

//        extends SQLiteOpenHelper
//{
//    // The name of the database
//    private static final String DATABASE_NAME = "tubeTasksDb.db";
//
//    // If you change the database schema, you must increment the database version
//    private static final int VERSION = 10;
//
//    /**
//     * Constructs a new instance of {@link TubeStationDbHelper}.
//     *
//     * @param context of the app
//     */
//    TubeStationDbHelper(Context context)
//    {
//        super(context, DATABASE_NAME, null, VERSION);
//    }
//
//    /**
//     * Called when the tube database is created for the first time.
//     */
//    @Override
//    public void onCreate(SQLiteDatabase db)
//    {
//    }
//

    /**
     * This method discards the old table of data and calls onCreate to recreate a new one.
     * This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
     */
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
//    {
//        db.execSQL("ALTER TABLE  " + TubeStationContract.TubeStationEntry.TABLE_NAME);
//        onCreate(db);
//    }

}
