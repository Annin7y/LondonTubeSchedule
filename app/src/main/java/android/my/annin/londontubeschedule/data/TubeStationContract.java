package android.my.annin.londontubeschedule.data;

import android.net.Uri;

public class TubeStationContract
{
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "android.my.annin.londontubeschedule";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

}
