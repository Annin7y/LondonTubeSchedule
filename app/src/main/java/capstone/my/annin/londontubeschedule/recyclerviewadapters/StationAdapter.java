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

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.pojo.Station;

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.StationAdapterViewHolder>
{
    private static final String TAG = StationAdapter.class.getSimpleName();

    private ArrayList<Station> stationList = new ArrayList<Station>();
    private Context context;
    private StationAdapterOnClickHandler mStationClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface StationAdapterOnClickHandler
    {
        void onClick(Station textStationClick);
    }

    /**
     * Creates a Stations Adapter.
     *
     * @param stationClickHandler The on-click handler for this adapter. This single handler is called
     *                            *                     when an item is clicked.
     */
    public StationAdapter(StationAdapterOnClickHandler stationClickHandler, ArrayList<Station> stationList, Context context)
    {
        mStationClickHandler = stationClickHandler;
        this.stationList = stationList;
        this.context = context;
    }

    /**
     * Cache of the children views for a station list item.
     */
    public class StationAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @BindView(R.id.station_name)
        public TextView stationName;

        @BindView(R.id.timeline)
        public TimelineView mTimelineView;

        public StationAdapterViewHolder(View view, int viewType)
        {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
            mTimelineView.initLine(viewType);

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
            Station textStationClick = stationList.get(adapterPosition);
            mStationClickHandler.onClick(textStationClick);
        }
    }

    @Override
    public StationAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.station_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new StationAdapterViewHolder(view, viewType);
    }

    /**
     * Cache of the children views for a station list item.
     */
    @Override
    public void onBindViewHolder(StationAdapterViewHolder holder, int position)
    {
        //Binding data
        final Station stationView = stationList.get(position);

        holder.stationName.setText(stationView.getStationName());

    }

    @Override
    public int getItemViewType(int position)
    {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public int getItemCount()
    {
        return stationList.size();
    }

    public void setStationList(ArrayList<Station> mStationList)
    {
        this.stationList = mStationList;
        notifyDataSetChanged();
    }
}
