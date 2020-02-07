package capstone.my.annin.londontubeschedule.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Stations implements Parcelable
{
    /**
     * Station id
     */
    private String stationId;

    /**
     * Station name
     */
    private String stationName;

    /**
     * Station lat location
     */
    private double latLocation;

    /**
     * Station lon location
     */
    private double lonLocation;

    public Stations(String stationId, String stationName, double latLocation, double lonLocation)
    {
        this.stationId = stationId;
        this.stationName = stationName;
        this.latLocation = latLocation;
        this.lonLocation = lonLocation;
    }

    public void setStationId(String stationId)
    {
        this.stationId = stationId;
    }

    public String getStationId()
    {
        return stationId;
    }

    public void setStationName(String stationName)
    {
        this.stationName = stationName;
    }

    public String getStationName()
    {
        return stationName;
    }

    public void setLatLocation(double latLocation)
    {
        this.latLocation = latLocation;
    }

    public double getLatLocation()
    {
        return latLocation;
    }

    public void setLonLocation(double lonLocation)
    {
        this.lonLocation = lonLocation;
    }

    public double getLonLocation()
    {
        return lonLocation;
    }

    protected Stations(Parcel in)
    {
        stationId = in.readString();
        stationName = in.readString();
        latLocation = in.readDouble();
        lonLocation = in.readDouble();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(stationId);
        dest.writeString(stationName);
        dest.writeDouble(latLocation);
        dest.writeDouble(lonLocation);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Stations> CREATOR = new Parcelable.Creator<Stations>()
    {
        @Override
        public Stations createFromParcel(Parcel in)
        {
            return new Stations(in);
        }

        @Override
        public Stations[] newArray(int size)
        {
            return new Stations[size];
        }
    };
}
