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

import android.os.Parcel;
import android.os.Parcelable;

public class OvergroundSchedule implements Parcelable
{
    /**
     * Overground Station naptan id
     */
    private String overStatNaptanId;

    /**
     * Overground Station name
     */
    private String overStatSchName;

    /**
     * Overground Destination name
     */
    private String overDestName;


    /**
     * Overground Expected Arrival
     */
    private String overExpArrival;

    /**
     * Overground Platform Name
     */
    private String overPlatformName;

    public OvergroundSchedule(String overStatNaptanId, String overStatSchName, String overDestName, String overExpArrival, String overPlatformName)
    {
        this.overStatNaptanId = overStatNaptanId;
        this.overStatSchName = overStatSchName;
        this.overDestName = overDestName;
        this.overExpArrival = overExpArrival;
        this.overPlatformName = overPlatformName;
    }

    public void setOverStatNaptanId(String overStatNaptanId)
    {
        this.overStatNaptanId = overStatNaptanId;
    }

    public String getOverStatNaptanId()
    {
        return overStatNaptanId;
    }

    public void setOverStatSchName(String overStatSchName)
    {
        this.overStatSchName = overStatSchName;
    }

    public String getOverStatSchName()
    {
        return overStatSchName;
    }

    public void setOverDestName(String overDestName)
    {
        this.overDestName = overDestName;
    }

    public String getOverDestName()
    {
        return overDestName;
    }

    public void setOverExpArrival(String overExpArrival)
    {
        this.overExpArrival = overExpArrival;
    }

    public String getOverExpArrival()
    {
        return overExpArrival;
    }

    public void setOverPlatformName(String overPlatformName)
    {
        this.overPlatformName = overPlatformName;
    }

    public String getOverPlatformName()
    {
        return overPlatformName;
    }

    protected OvergroundSchedule(Parcel in)
    {
        overStatNaptanId= in.readString();
        overStatSchName = in.readString();
        overDestName = in.readString();
        overExpArrival = in.readString();
        overPlatformName = in.readString();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(overStatNaptanId);
        dest.writeString(overStatSchName);
        dest.writeString(overDestName);
        dest.writeString(overExpArrival);
        dest.writeString(overPlatformName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<OvergroundSchedule> CREATOR = new Parcelable.Creator<OvergroundSchedule>()
    {
        @Override
        public OvergroundSchedule createFromParcel(Parcel in)
        {
            return new OvergroundSchedule(in);
        }

        @Override
        public OvergroundSchedule[] newArray(int size)
        {
            return new OvergroundSchedule[size];
        }
    };
}
