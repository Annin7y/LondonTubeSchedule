package capstone.my.annin.londontubeschedule.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Lines implements Parcelable
{
    /**
     * Line id
     */
    private String lineId;

    /**
     * Line name
     */
    private String lineName;

    /**
     * List of lines
     */
   // private ArrayList<Lines> listLines;

    public Lines(String lineId, String lineName)
    {
        this.lineId = lineId;
        this.lineName = lineName;
    }

    public void setLineId(String lineId)
    {
        this.lineId = lineId;
    }

    public String getLineId()
    {
        return lineId;
    }

    public void setLineName(String lineName)
    {
        this.lineName = lineName;
    }

    public String getLineName()
    {
        return lineName;
    }

    protected Lines(Parcel in)
    {
        lineId = in.readString();
        lineName = in.readString();
    }
    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(lineId);
        dest.writeString(lineName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Lines> CREATOR = new Parcelable.Creator<Lines>()
    {
        @Override
        public Lines createFromParcel(Parcel in) {
            return new Lines(in);
        }

        @Override
        public Lines[] newArray(int size) {
            return new Lines[size];
        }
    };
}
