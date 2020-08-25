/* Copyright 2020 Anastasia Annin

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package capstone.my.annin.londontubeschedule.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

@Entity(tableName = "line")
public class Line implements Parcelable
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
     * List of lines
     */
    public Line(String lineId, String lineName, String lineStatusDesc, String lineStatusReason)
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

    protected Line(Parcel in)
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
    public static final Parcelable.Creator<Line> CREATOR = new Parcelable.Creator<Line>()
    {
        @Override
        public Line createFromParcel(Parcel in)
        {
            return new Line(in);
        }

        @Override
        public Line[] newArray(int size)
        {
            return new Line[size];
        }
    };
}
