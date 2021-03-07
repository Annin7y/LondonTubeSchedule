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

public class OvergroundStatus implements Parcelable
{
    /**
     * Mode id
     */
    private String modeId;

    /**
     * Overground mode name
     */

    private String modeName;


    /**
     * Overground line status description
     */
    private String modeStatusDesc;

    /**
     * Overground line status reason
     */
    private String modeStatusReason;

    /**
     * List of overground lines
     */
    public OvergroundStatus(String modeId, String modeName, String modeStatusDesc, String modeStatusReason)
    {
        this.modeId = modeId;
        this.modeName = modeName;
        this.modeStatusDesc = modeStatusDesc;
        this.modeStatusReason = modeStatusReason;
    }
    public void setModeId(String modeId)
    {
        this.modeId = modeId;
    }

    public String getModeId()
    {
        return modeId;
    }

    public void setModeName(String modeName)
    {
        this.modeName = modeName;
    }

    public String getModeName()
    {
        return modeName;
    }

    public void setModeStatusDesc(String modeStatusDesc)
    {
        this.modeStatusDesc= modeStatusDesc;
    }

    public String getModeStatusDesc()
    {
        return modeStatusDesc;
    }

    public void setModeStatusReason(String modeStatusReason)
    {
        this.modeStatusReason = modeStatusReason;
    }

    public String getModeStatusReason()
    {
        return modeStatusReason;
    }

    protected OvergroundStatus(Parcel in)
    {
        modeId = in.readString();
        modeName = in.readString();
        modeStatusDesc = in.readString();
        modeStatusReason = in.readString();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(modeId);
        dest.writeString(modeName);
        dest.writeString(modeStatusDesc);
        dest.writeString(modeStatusReason);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<OvergroundStatus> CREATOR = new Parcelable.Creator<OvergroundStatus>()
    {
        @Override
        public OvergroundStatus createFromParcel(Parcel in)
        {
            return new OvergroundStatus(in);
        }

        @Override
        public OvergroundStatus[] newArray(int size)
        {
            return new OvergroundStatus[size];
        }
    };
}
