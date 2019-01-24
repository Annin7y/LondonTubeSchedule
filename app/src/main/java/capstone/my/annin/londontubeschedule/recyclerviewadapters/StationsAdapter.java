package capstone.my.annin.londontubeschedule.recyclerviewadapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.pojo.Stations;

public class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.StationsAdapterViewHolder>
{
    private static final String TAG = StationsAdapter.class.getSimpleName();

    private ArrayList<Stations> stationsList = new ArrayList<Stations>();
    private Context context;
    private StationsAdapterOnClickHandler mStationClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface StationsAdapterOnClickHandler
    {
        void onClick(Stations textStationClick);
    }

    /**
     * Creates a Stations Adapter.
     *
     * @param stationClickHandler The on-click handler for this adapter. This single handler is called
     *                            *                     when an item is clicked.
     */
    public StationsAdapter(StationsAdapterOnClickHandler stationClickHandler, ArrayList<Stations> stationsList, Context context)
    {
        mStationClickHandler = stationClickHandler;
        this.stationsList = stationsList;
        this.context = context;
    }

    /**
     * Cache of the children views for a station list item.
     */
    public class StationsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @BindView(R.id.station_name)
        public TextView stationName;

        public StationsAdapterViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
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
            Stations textStationClick = stationsList.get(adapterPosition);
            mStationClickHandler.onClick(textStationClick);
        }
    }

    @Override
    public StationsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.station_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new StationsAdapterViewHolder(view);
    }

    /**
     * Cache of the children views for a station list item.
     */
    @Override
    public void onBindViewHolder(StationsAdapterViewHolder holder, int position)
    {
        //Binding data
        final Stations stationView = stationsList.get(position);

        holder.stationName.setText(stationView.getStationName());
    }

    @Override
    public int getItemCount()
    {
        return stationsList.size();
    }

    public void setStationsList(ArrayList<Stations> mStationsList)
    {
        this.stationsList = mStationsList;
        notifyDataSetChanged();
    }
}
