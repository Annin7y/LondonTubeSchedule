package capstone.my.annin.londontubeschedule.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

//public class TubeLineContract
//{
//    // To prevent someone from accidentally instantiating the contract class,
//    // give it an empty constructor.
//    private TubeLineContract()
//    {
//    }
//
//    /**
//     * The "Content authority" is a name for the entire content provider, similar to the
//     * relationship between a domain name and its website.  A convenient string to use for the
//     * content authority is the package name for the app, which is guaranteed to be unique on the
//     * device.
//     */
//    public static final String CONTENT_AUTHORITY = "capstone.my.annin.londontubeschedule";
//
//    /**
//     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
//     * the content provider.
//     */
//    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
//
//    /**
//     * Possible path (appended to base content URI for possible URI's)
//     */
//    public static final String PATH_LINES = "lines";
//
//    /**
//     * Inner class that defines constant values for the lines database table.
//     * Each entry in the table represents one line.
//     */
//    public static final class TubeLineEntry implements BaseColumns
//    {
//        /**
//         * To make this a usable URI, we use the parse method which takes in a URI string and returns a Uri.
//         */
//        public static final Uri CONTENT_URI =
//                BASE_CONTENT_URI.buildUpon().appendPath(PATH_LINES).build();
//
//        /**
//         * The MIME type of the {@link #CONTENT_URI} for a list of lines.
//         */
//        public static final String CONTENT_LIST_TYPE =
//                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LINES;
//
//        /**
//         * The MIME type of the {@link #CONTENT_URI} for one line.
//         */
//        public static final String CONTENT_ITEM_TYPE =
//                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LINES;
//
//        public final static String _ID = BaseColumns._ID;
//
//        /**
//         * Name of database table for lines
//         */
//        public static final String TABLE_NAME = "lines";
//
//        /**
//         * Unique ID number for the lines table(only for use in the database table). Type: TEXT
//         */
//        public final static String COLUMN_LINES_ID = "id";
//
//        /**
//         * LINE name. Type: TEXT
//         */
//        public final static String COLUMN_LINES_NAME = "line_name";
//
//        /**
//         * LINE status. Type: TEXT
//         */
//        public final static String COLUMN_LINES_STATUS_DESC = "line_status";
//
//        /**
//         * LINE status reason. Type: TEXT
//         */
//        public final static String COLUMN_LINES_STATUS_REASON= "line_reason";
//
//    }
//}
