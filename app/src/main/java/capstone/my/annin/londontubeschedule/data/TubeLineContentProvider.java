package capstone.my.annin.londontubeschedule.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

public class TubeLineContentProvider extends ContentProvider
{
    //Tag for the log messages
    public static final String LOG_TAG = TubeLineContentProvider.class.getSimpleName();

    /**
     * URI matcher code for the content URI for the lines table
     */
    public static final int LINES = 100;

    /**
     * URI matcher code for the content URI for one line in the lines table
     */
    public static final int LINE_WITH_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    // Static initializer. This is run the first time anything is called from this class.
    public static UriMatcher buildUriMatcher()
    {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(TubeLineContract.CONTENT_AUTHORITY, TubeLineContract.PATH_LINES, LINES);
        uriMatcher.addURI(TubeLineContract.CONTENT_AUTHORITY, TubeLineContract.PATH_LINES + "/#", LINE_WITH_ID);

        return uriMatcher;
    }

    /**
     * Database helper object
     */
    private TubeLineDbHelper mTubeLineDbHelper;

    @Override
    public boolean onCreate()
    {
        // Complete onCreate() and initialize a TubeLineDbhelper on startup
        // [Hint] Declare the DbHelper as a global variable
        Context context = getContext();
        mTubeLineDbHelper = new TubeLineDbHelper(context);
        return true;
    }

    // Implement insert to handle requests to insert a single new row of data
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values)
    {
        // Get access to the line database (to write new data to)
        final SQLiteDatabase db = mTubeLineDbHelper.getWritableDatabase();

        // Write URI matching code to identify the match for the lines directory
        int match = sUriMatcher.match(uri);
        Uri returnUri; // URI to be returned

        switch (match)
        {
            case LINES:
                // Insert new values into the database
                // Inserting values into lines table
                long id = db.insert(TubeLineContract.TubeLineEntry.TABLE_NAME, null, values);
                if (id > 0)
                {
                    returnUri = ContentUris.withAppendedId(TubeLineContract.TubeLineEntry.CONTENT_URI, id);
                }
                else
                {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            // Set the value for the returnedUri and write the default case for unknown URI's
            // Default case throws an UnsupportedOperationException
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver if the uri has been changed, and return the newly inserted URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return constructed uri (this points to the newly inserted row of data)
        return returnUri;
    }

    // Implement query to handle requests for data by URI
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder)
    {
        // Get access to underlying database (read-only for query)
        final SQLiteDatabase db = mTubeLineDbHelper.getReadableDatabase();

        // Write URI match code and set a variable to return a Cursor
        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        // Query for the lines directory and write a default case
        switch (match)
        {
            // Query for the lines directory
            case LINES:
                retCursor = db.query(TubeLineContract.TubeLineEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Set a notification URI on the Cursor and return that Cursor
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the desired Cursor
        return retCursor;
    }

    /**
     * Update inventory in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more pets).
     * Return the number of rows that were successfully updated.
     */
    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs)
    {
        //Keep track of if an update occurs
        int rowsUpdated;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case LINE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                rowsUpdated = mTubeLineDbHelper.getWritableDatabase().update(TubeLineContract.TubeLineEntry.TABLE_NAME, values, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
        if (rowsUpdated != 0)
        {
            //set notifications if a row was updated
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // return number of rows updated
        return rowsUpdated;
    }

    // Implement delete to delete a single row of data
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs)
    {
        // Get access to the database and write URI matching code to recognize a single item
        final SQLiteDatabase db = mTubeLineDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        // Keep track of the number of deleted rows
        int rowsDeleted; // starts as 0

        //if (null == selection) selection = "1";
        // Write the code to delete a single row of data
        // [Hint] Use selections to delete an item by its row ID
        switch (match)
        {
            // Handle the single item case, recognized by the ID included in the URI path
            case LINE_WITH_ID:
                // Get the line ID from the URI path
                String id = uri.getPathSegments().get(1);
                // Use selections/selectionArgs to filter for this ID
                rowsDeleted = db.delete(TubeLineContract.TubeLineEntry.TABLE_NAME, "id=?", new String[]{id});

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver of a change and return the number of items deleted
        if (rowsDeleted != 0)
        {
            // A line was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }

    @Override
    public String getType(@NonNull Uri uri)
    {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case LINES:
                return TubeLineContract.TubeLineEntry.CONTENT_LIST_TYPE;
            case LINE_WITH_ID:
                return "vnd.android.cursor.item" + "/" + TubeLineContract.CONTENT_AUTHORITY + "/" + TubeLineContract.PATH_LINES;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }
}
