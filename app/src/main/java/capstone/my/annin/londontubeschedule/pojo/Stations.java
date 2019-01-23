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

    public Stations(String stationId, String stationName)
    {
        this.stationId = stationId;
        this.stationName = stationName;
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

    protected Stations(Parcel in)
    {
        stationId = in.readString();
        stationName = in.readString();
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
    }
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Stations> CREATOR = new Parcelable.Creator<Stations>()
    {
        @Override
        public Stations createFromParcel(Parcel in) {
            return new Stations(in);
        }

        @Override
        public Stations[] newArray(int size) {
            return new Stations[size];
        }
    };
}
