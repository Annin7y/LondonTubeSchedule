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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.pojo.Line;

public class FavoritesRoomAdapter extends RecyclerView.Adapter<FavoritesRoomAdapter.FavoritesRoomAdapterViewHolder>
{
    private static final String TAG = FavoritesRoomAdapter.class.getSimpleName();

    public List<Line> roomLineList;
    private LineAdapter.LineAdapterOnClickHandler mClickHandler;
    private Context context;

    public FavoritesRoomAdapter(LineAdapter.LineAdapterOnClickHandler clickHandler, Context context)
    {
        mClickHandler = clickHandler;
        this.context = context;
    }

    public class FavoritesRoomAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @BindView(R.id.line_name)
        TextView lineName;

        public FavoritesRoomAdapterViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            int adapterPosition = getAdapterPosition();
            Line lineClick = roomLineList.get(adapterPosition);
            mClickHandler.onClick(lineClick);
        }
    }

    @Override
    public void onBindViewHolder(FavoritesRoomAdapter.FavoritesRoomAdapterViewHolder holder, int position)
    {
        //Binding data
        final Line lineView = roomLineList.get(position);
        holder.lineName.setText(lineView.getLineName());
    }

    public void setLine(List<Line> line)
    {
        roomLineList = line;
        notifyDataSetChanged();
    }

    public Line getMovieAt(int position)
    {
        return roomLineList.get(position);
    }

    @Override
    public FavoritesRoomAdapter.FavoritesRoomAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.line_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new FavoritesRoomAdapter.FavoritesRoomAdapterViewHolder(view);
    }

    public int getItemCount()
        {
            if(roomLineList != null)
                return roomLineList.size();
            else return 0;
        }
}
