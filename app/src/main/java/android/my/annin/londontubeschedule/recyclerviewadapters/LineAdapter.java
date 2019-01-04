package android.my.annin.londontubeschedule.recyclerviewadapters;

import android.content.Context;
import android.my.annin.londontubeschedule.R;
import android.my.annin.londontubeschedule.model.Lines;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LineAdapter extends RecyclerView.Adapter<LineAdapter.LineAdapterViewHolder>
{
    private static final String TAG = LineAdapter.class.getSimpleName();

    private ArrayList<Lines> linesList = new ArrayList<Lines>();
    private Context context;

    /**
     * Creates a Line Adapter.
     */
    public LineAdapter(ArrayList<Lines> linesList, Context context)
    {
        this.linesList = linesList;
        this.context = context;
    }

    /**
     * Cache of the children views for a line list item.
     */
    public class LineAdapterViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.line_name)
        public TextView lineName;

        public LineAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public LineAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.line_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new LineAdapterViewHolder(view);
    }

    /**
     * Cache of the children views for a line list item.
     */
    @Override
    public void onBindViewHolder(LineAdapterViewHolder holder, int position)
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
        this.linesList.addAll(mLinesList);
        notifyDataSetChanged();
    }
}

