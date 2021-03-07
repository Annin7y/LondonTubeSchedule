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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.pojo.OvergroundStatus;

public class OvergroundStatusAdapter extends RecyclerView.Adapter<OvergroundStatusAdapter.OvergroundStatusAdapterViewHolder>
{
    private static final String TAG = OvergroundStatusAdapter.class.getSimpleName();

    private ArrayList<OvergroundStatus> overgroundStatusList = new ArrayList<OvergroundStatus>();
    private Context context;
    private OvergroundStatusAdapter.OvergroundStatusAdapterOnClickHandler mOvergroundStatusClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface OvergroundStatusAdapterOnClickHandler
    {
        void onClick(OvergroundStatus textOvergroundStatusClick);
    }
    /**
     * Creates an Overground Adapter.
     *
     * @param overgroundStatusClickHandler The on-click handler for this adapter. This single handler is called
     *                         *                     when an item is clicked.
     */
    public OvergroundStatusAdapter(OvergroundStatusAdapter.OvergroundStatusAdapterOnClickHandler overgroundStatusClickHandler, ArrayList<OvergroundStatus> overgroundStatusList, Context context)
    {
        mOvergroundStatusClickHandler = overgroundStatusClickHandler;
        this.overgroundStatusList = overgroundStatusList;
        this.context = context;
    }

    /**
     * Cache of the children views for an overground list item.
     */
    public class OvergroundStatusAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.overground_name)
        public TextView overgroundName;

        @BindView(R.id.overground_status_name)
        public TextView overgroundStatusDesc;

        @BindView(R.id.overground_status_reason)
        public TextView overgroundStatusReason;

        public OvergroundStatusAdapterViewHolder(View view) {
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
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            OvergroundStatus textOvergroundStatusClick = overgroundStatusList.get(adapterPosition);
            mOvergroundStatusClickHandler.onClick(textOvergroundStatusClick);
        }
    }
        @Override
        public OvergroundStatusAdapter.OvergroundStatusAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
        {
            Context context = viewGroup.getContext();
            int layoutIdForListItem = R.layout.overground_list_item;
            LayoutInflater inflater = LayoutInflater.from(context);
            boolean shouldAttachToParentImmediately = false;
            View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
            return new OvergroundStatusAdapter.OvergroundStatusAdapterViewHolder(view);
        }

    @Override
    public void onBindViewHolder(OvergroundStatusAdapter.OvergroundStatusAdapterViewHolder holder, int position)
    {
        //Binding data
        final OvergroundStatus overgroundStatusView = overgroundStatusList.get(position);

        holder.overgroundName.setText(overgroundStatusView.getModeName());
        holder.overgroundStatusDesc.setText(overgroundStatusView.getModeStatusDesc());
        holder.overgroundStatusReason.setText(overgroundStatusView.getModeStatusReason());
    }

    @Override
    public int getItemCount()
    {
        return overgroundStatusList.size();
    }

    public void setOvergroundList(ArrayList<OvergroundStatus> mOvergroundStatusList)
    {
        this.overgroundStatusList = mOvergroundStatusList;
        notifyDataSetChanged();
    }

    }



