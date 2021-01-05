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
import capstone.my.annin.londontubeschedule.pojo.Line;
import capstone.my.annin.londontubeschedule.pojo.Overground;

public class OvergroundAdapter extends RecyclerView.Adapter<OvergroundAdapter.OvergroundAdapterViewHolder>
{
    private static final String TAG = OvergroundAdapter.class.getSimpleName();

    private ArrayList<Overground> overgroundList = new ArrayList<Overground>();
    private Context context;
    private OvergroundAdapter.OvergroundAdapterOnClickHandler mOvergroundClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface OvergroundAdapterOnClickHandler
    {
        void onClick(Overground textOvergroundClick);
    }
    /**
     * Creates an Overground Adapter.
     *
     * @param overgroundClickHandler The on-click handler for this adapter. This single handler is called
     *                         *                     when an item is clicked.
     */
    public OvergroundAdapter(OvergroundAdapter.OvergroundAdapterOnClickHandler overgroundClickHandler, ArrayList<Overground> overgroundList, Context context)
    {
        mOvergroundClickHandler = overgroundClickHandler;
        this.overgroundList = overgroundList;
        this.context = context;
    }

    /**
     * Cache of the children views for an overground list item.
     */
    public class OvergroundAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.overground_name)
        public TextView overgroundName;

        @BindView(R.id.overground_status_name)
        public TextView overgroundStatusDesc;

        @BindView(R.id.overground_status_reason)
        public TextView overgroundStatusReason;

        public OvergroundAdapterViewHolder(View view) {
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
            Overground textOvergroundClick = overgroundList.get(adapterPosition);
            mOvergroundClickHandler.onClick(textOvergroundClick);
        }
    }
        @Override
        public OvergroundAdapter.OvergroundAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
        {
            Context context = viewGroup.getContext();
            int layoutIdForListItem = R.layout.overground_list_item;
            LayoutInflater inflater = LayoutInflater.from(context);
            boolean shouldAttachToParentImmediately = false;
            View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
            return new OvergroundAdapter.OvergroundAdapterViewHolder(view);
        }

    @Override
    public void onBindViewHolder(OvergroundAdapter.OvergroundAdapterViewHolder holder, int position)
    {
        //Binding data
        final Overground overgroundView = overgroundList.get(position);

        holder.overgroundName.setText(overgroundView.getModeName());
        holder.overgroundStatusDesc.setText(overgroundView.getModeStatusDesc());
        holder.overgroundStatusReason.setText(overgroundView.getModeStatusReason());
    }

    @Override
    public int getItemCount()
    {
        return overgroundList.size();
    }

    public void setOvergroundList(ArrayList<Overground> mOvergroundList)
    {
        this.overgroundList = mOvergroundList;
        notifyDataSetChanged();
    }

    }



