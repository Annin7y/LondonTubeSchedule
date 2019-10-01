package capstone.my.annin.londontubeschedule.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "lines")
public class Lines implements Parcelable
{
    /**
     * Line id
     */
    @PrimaryKey
    @NonNull
    private String lineId;

    /**
     * Line name
     */
    @ColumnInfo(name = "line_name")
    private String lineName;

    /**
     * Line status description
     */
    @ColumnInfo(name = "line_status")
    private String lineStatusDesc;

    /**
     * Line status reason
     */
    @ColumnInfo(name = "line_reason")
    private String lineStatusReason;

    /**
     * ArrayList to store lineStatus child objects
     */


    /**
     * List of lines
     */
    public Lines(String lineId, String lineName, String lineStatusDesc, String lineStatusReason)
    {
        this.lineId = lineId;
        this.lineName = lineName;
        this.lineStatusDesc = lineStatusDesc;
        this.lineStatusReason = lineStatusReason;

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

    public void setLineStatusDesc(String lineStatusDesc)
    {
        this.lineStatusDesc= lineStatusDesc;
    }

    public String getLineStatusDesc()
    {
        return lineStatusDesc;
    }

    public void setLineStatusReason(String lineStatusReason)
    {
        this.lineStatusReason = lineStatusReason;
    }

    public String getLineStatusReason()
    {
        return lineStatusReason;
    }


    protected Lines(Parcel in)
    {
        lineId = in.readString();
        lineName = in.readString();
        lineStatusDesc = in.readString();
        lineStatusReason = in.readString();
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
        dest.writeString(lineStatusDesc);
        dest.writeString(lineStatusReason);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Lines> CREATOR = new Parcelable.Creator<Lines>()
    {
        @Override
        public Lines createFromParcel(Parcel in)
        {
            return new Lines(in);
        }

        @Override
        public Lines[] newArray(int size)
        {
            return new Lines[size];
        }
    };
}
