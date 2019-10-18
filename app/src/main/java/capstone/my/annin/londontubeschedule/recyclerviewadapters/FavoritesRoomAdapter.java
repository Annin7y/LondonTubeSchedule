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
import capstone.my.annin.londontubeschedule.pojo.Lines;

public class FavoritesRoomAdapter extends RecyclerView.Adapter<FavoritesRoomAdapter.FavoritesRoomAdapterViewHolder>
{
    private static final String TAG = FavoritesRoomAdapter.class.getSimpleName();

    public List<Lines> roomLinesList;
    private LinesAdapter.LinesAdapterOnClickHandler mClickHandler;
    private Context context;

    public FavoritesRoomAdapter(LinesAdapter.LinesAdapterOnClickHandler clickHandler, Context context)
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
            Lines lineClick = roomLinesList.get(adapterPosition);
            mClickHandler.onClick(lineClick);
        }
    }

    @Override
    public void onBindViewHolder(FavoritesRoomAdapter.FavoritesRoomAdapterViewHolder holder, int position)
    {
        //Binding data
        final Lines lineView = roomLinesList.get(position);
        holder.lineName.setText(lineView.getLineName());
    }

    public void setLines(List<Lines> lines)
    {
        roomLinesList = lines;
        notifyDataSetChanged();
    }

    public Lines getMovieAt(int position)
    {
        return roomLinesList.get(position);
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
            if(roomLinesList != null)
                return roomLinesList.size();
            else return 0;
        }
}
