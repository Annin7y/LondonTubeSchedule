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

public class Schedule implements Parcelable
{
    /**
     * Station naptan id
     */
    private String stationNaptanId;

    /**
     * Station name
     */
    private String stationScheduleName;

    /**
     * Destination name
     */
    private String destinationName;

    /**
     * Current location
     */
    private String currentLocation;

    /**
     * directionTowards
     */
    private String directionTowards;

    /**
     * expectedArrival
     */
    private String expectedArrival;


    public Schedule(String stationNaptanId, String stationScheduleName, String destinationName, String currentLocation, String directionTowards, String expectedArrival)
    {
        this.stationNaptanId = stationNaptanId;
        this.stationScheduleName = stationScheduleName;
        this.destinationName = destinationName;
        this.currentLocation = currentLocation;
        this.directionTowards = directionTowards;
        this.expectedArrival = expectedArrival;
    }

    public void setStationNaptanId(String stationNaptanId)
    {
        this.stationNaptanId = stationNaptanId;
    }

    public String getStationNaptanId()
    {
        return stationNaptanId;
    }

    public void setStationScheduleName(String stationScheduleName)
    {
        this.stationScheduleName = stationScheduleName;
    }

    public String getStationScheduleName()
    {
        return stationScheduleName;
    }

    public void setDestinationName(String destinationName)
    {
        this.destinationName = destinationName;
    }

    public String getDestinationName()
    {
        return destinationName;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getCurrentLocation()
    {
        return currentLocation;
    }

    public void setDirectionTowards(String directionTowards)
    {
        this.directionTowards = directionTowards;
    }

    public String getDirectionTowards()
    {
        return directionTowards;
    }

    public void setExpectedArrival(String expectedArrival)
    {
        this.expectedArrival = expectedArrival;
    }

    public String getExpectedArrival()
    {
        return expectedArrival;
    }

    protected Schedule(Parcel in)
    {
        stationNaptanId = in.readString();
        stationScheduleName = in.readString();
        destinationName = in.readString();
        currentLocation = in.readString();
        directionTowards = in.readString();
        expectedArrival = in.readString();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(stationNaptanId);
        dest.writeString(stationScheduleName);
        dest.writeString(destinationName);
        dest.writeString(currentLocation);
        dest.writeString(directionTowards);
        dest.writeString(expectedArrival);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Schedule> CREATOR = new Parcelable.Creator<Schedule>()
    {
        @Override
        public Schedule createFromParcel(Parcel in)
        {
            return new Schedule(in);
        }

        @Override
        public Schedule[] newArray(int size)
        {
            return new Schedule[size];
        }
    };
}

