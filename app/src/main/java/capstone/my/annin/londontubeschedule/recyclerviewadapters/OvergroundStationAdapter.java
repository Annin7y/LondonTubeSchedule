package capstone.my.annin.londontubeschedule.recyclerviewadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.pojo.OvergroundStation;

public class OvergroundStationAdapter extends RecyclerView.Adapter<OvergroundStationAdapter.OvergroundStationAdapterViewHolder>
{
    private static final String TAG = OvergroundStationAdapter.class.getSimpleName();

    private ArrayList<OvergroundStation> statOverList = new ArrayList<OvergroundStation>();
    private Context context;
    private OvergroundStationAdapter.OvergroundStationAdapterOnClickHandler mOverStationClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface OvergroundStationAdapterOnClickHandler
    {
        void onClick(OvergroundStation textOverStationClick);
    }

    /**
     * Creates an Overground Stations Adapter.
     *
     * @param overStatClickHandler The on-click handler for this adapter. This single handler is called
     *                            *                     when an item is clicked.
     */
    public OvergroundStationAdapter(OvergroundStationAdapter.OvergroundStationAdapterOnClickHandler overStatClickHandler, ArrayList<OvergroundStation> statOverList, Context context)
    {
        mOverStationClickHandler = overStatClickHandler;
        this.statOverList = statOverList;
        this.context = context;
    }

    /**
     * Cache of the children views for an overground station list item.
     */
    public class OvergroundStationAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @BindView(R.id.stat_over_name)
        public TextView statOverName;

        @BindView(R.id.timeline_over_stat)
        public TimelineView mOverTimelineView;

        public OvergroundStationAdapterViewHolder(View view, int viewType)
        {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
            mOverTimelineView.initLine(viewType);

        }

        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v)
        {
            int adapterPosition = getAdapterPosition();
            OvergroundStation textStationClick = statOverList.get(adapterPosition);
            mOverStationClickHandler.onClick(textStationClick);
        }
    }

    @Override
    public OvergroundStationAdapter.OvergroundStationAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.overground_station_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new OvergroundStationAdapter.OvergroundStationAdapterViewHolder(view, viewType);
    }

    /**
     * Cache of the children views for an overground station list item.
     */
    @Override
    public void onBindViewHolder(OvergroundStationAdapter.OvergroundStationAdapterViewHolder holder, int position)
    {
        //Binding data
        final OvergroundStation stationView = statOverList.get(position);

        holder.statOverName.setText(stationView.getStationName());

    }

    @Override
    public int getItemViewType(int position)
    {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public int getItemCount()
    {
        return statOverList.size();
    }

    public void setStationList(ArrayList<OvergroundStation> mStationList)
    {
        this.statOverList = mStationList;
        notifyDataSetChanged();
    }

}
