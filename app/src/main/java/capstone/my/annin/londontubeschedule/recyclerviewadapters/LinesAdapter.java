package capstone.my.annin.londontubeschedule.recyclerviewadapters;

import android.content.Context;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.model.Lines;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LinesAdapter extends RecyclerView.Adapter<LinesAdapter.LinesAdapterViewHolder>
{
    private static final String TAG = LinesAdapter.class.getSimpleName();

    private ArrayList<Lines> linesList = new ArrayList<Lines>();
    private Context context;
    private LinesAdapterOnClickHandler mLineClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface LinesAdapterOnClickHandler
    {
        void onClick(Lines textLineClick);
    }

    /**
     * Creates a Lines Adapter.
     *
     *  @param lineClickHandler The on-click handler for this adapter. This single handler is called
     *      *                     when an item is clicked.
     */
    public LinesAdapter(LinesAdapterOnClickHandler lineClickHandler, ArrayList<Lines> linesList, Context context)
    {
        mLineClickHandler = lineClickHandler;
        this.linesList = linesList;
        this.context = context;
    }

    /**
     * Cache of the children views for a line list item.
     */
    public class LinesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @BindView(R.id.line_name)
        public TextView lineName;

        public LinesAdapterViewHolder(View view)
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
            Lines textLineClick = linesList.get(adapterPosition);
            mLineClickHandler.onClick(textLineClick);
        }
    }

    @Override
    public LinesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.line_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new LinesAdapterViewHolder(view);
    }

    /**
     * Cache of the children views for a line list item.
     */
    @Override
    public void onBindViewHolder(LinesAdapterViewHolder holder, int position)
    {
        //Binding data
        final Lines lineView = linesList.get(position);

        holder.lineName.setText(lineView.getLineName());
    }

    @Override
    public int getItemCount()
    {
        return linesList.size();
    }

    public void setLinesList(ArrayList<Lines> mLinesList)
    {
       this.linesList = mLinesList;
       notifyDataSetChanged();
    }
}

