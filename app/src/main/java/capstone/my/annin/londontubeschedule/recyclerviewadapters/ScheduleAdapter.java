package capstone.my.annin.londontubeschedule.recyclerviewadapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = null;

        try
        {
            date = simpleDateFormat.parse(stationView.getExpectedArrival());
            date.toString();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        SimpleDateFormat newDateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
        String finalDate = newDateFormat.format(date);

        holder.expectedArrival.setText(finalDate);
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
