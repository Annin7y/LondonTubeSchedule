package capstone.my.annin.londontubeschedule.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Station implements Parcelable
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

    public Station(String stationId, String stationName, double latLocation, double lonLocation)
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

    protected Station(Parcel in)
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
    public static final Parcelable.Creator<Station> CREATOR = new Parcelable.Creator<Station>()
    {
        @Override
        public Station createFromParcel(Parcel in)
        {
            return new Station(in);
        }

        @Override
        public Station[] newArray(int size)
        {
            return new Station[size];
        }
    };
}
