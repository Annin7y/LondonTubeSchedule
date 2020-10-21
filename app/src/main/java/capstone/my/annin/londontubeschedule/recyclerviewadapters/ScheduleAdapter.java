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

package capstone.my.annin.londontubeschedule.recyclerviewadapters;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.pojo.Schedule;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleAdapterViewHolder>
{
    private static final String TAG = ScheduleAdapter.class.getSimpleName();

    private ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
    private Context context;

    /**
     * Creates a Schedule Adapter.
     */
    public ScheduleAdapter(ArrayList<Schedule> scheduleList, Context context)
    {
        this.scheduleList = scheduleList;
        this.context = context;
    }

    /**
     * Cache of the children views for a schedule list item.
     */
    public class ScheduleAdapterViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.schedule_station_name)
        public TextView stationScheduleName;

        @BindView(R.id.schedule_destination_name)
        public TextView destinationName;

        @BindView(R.id.schedule_current_location)
        public TextView currentLocation;

        @BindView(R.id.schedule_towards)
        public TextView directionTowards;

        @BindView(R.id.schedule_arrival)
        public TextView expectedArrival;

        @BindView(R.id.schedule_platform)
        public TextView platformName;


        public ScheduleAdapterViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public ScheduleAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.schedule_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new ScheduleAdapterViewHolder(view);
    }

    /**
     * Cache of the children views for a schedule list item.
     */
    @Override
    public void onBindViewHolder(ScheduleAdapterViewHolder holder, int position)
    {
        //Binding data
        final Schedule stationView = scheduleList.get(position);

        holder.stationScheduleName.setText(stationView.getStationScheduleName());
        holder.destinationName.setText(stationView.getDestinationName());
        holder.currentLocation.setText(stationView.getCurrentLocation());
        holder.directionTowards.setText(stationView.getDirectionTowards());
        holder.expectedArrival.setText(stationView.getExpectedArrival());
        holder.platformName.setText(stationView.getPlatformName());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        //convert time zone to London UK time(GMT)
        //Code based on the first answer in the following stackoverflow post:
       // https://stackoverflow.com/questions/22814263/how-to-set-the-timezone-for-string-parsing-in-android
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = null;

        try
        {
            //Relative date code based on this example:
            //https://stackoverflow.com/questions/49441035/dateutils-getrelativetimespanstring-returning-a-formatted-date-string-instead-of
            date = simpleDateFormat.parse(stationView.getExpectedArrival());
            //convert the date to milliseconds; CharSequence parameter below must be a long
            if(date != null)
            {
            long timeInMilliseconds = date.getTime();

            //Convert the date to a relative future date("in 4 minutes"); code based on this example:
            //https://stackoverflow.com/questions/49441035/dateutils-getrelativetimespanstring-returning-a-formatted-date-string-instead-of
           CharSequence relativeDate = DateUtils.getRelativeTimeSpanString(timeInMilliseconds,
                    System.currentTimeMillis(),
                    DateUtils.MINUTE_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_RELATIVE);

           //convert CharSequence to String; Based on the following StackOverflow post:
           // https://stackoverflow.com/questions/35305236/converting-from-charsequence-to-string-in-java
            String futureDate =  String.valueOf(relativeDate);
            holder.expectedArrival.setText(futureDate);
        }}
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        //Previous code displaying time commented out; replaced with the relative date above
      //  SimpleDateFormat newDateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
      //  String finalDate = newDateFormat.format(date);

      //  holder.expectedArrival.setText(relativeDate);
    }

    @Override
    public int getItemCount()
    {
        return scheduleList.size();
    }

    public void setScheduleList(ArrayList<Schedule> mScheduleList)
    {
        this.scheduleList = mScheduleList;
        notifyDataSetChanged();
    }
}
