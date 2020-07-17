package capstone.my.annin.londontubeschedule.recyclerviewadapters;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.pojo.Line;

public class LineAdapter extends RecyclerView.Adapter<LineAdapter.LineAdapterViewHolder>
{
    private static final String TAG = LineAdapter.class.getSimpleName();

    private ArrayList<Line> lineList = new ArrayList<Line>();
    private Context context;
    private LineAdapterOnClickHandler mLineClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface LineAdapterOnClickHandler
    {
        void onClick(Line textLineClick);
    }

    /**
     * Creates a Lines Adapter.
     *
     * @param lineClickHandler The on-click handler for this adapter. This single handler is called
     *                         *                     when an item is clicked.
     */
    public LineAdapter(LineAdapterOnClickHandler lineClickHandler, ArrayList<Line> lineList, Context context)
    {
        mLineClickHandler = lineClickHandler;
        this.lineList = lineList;
        this.context = context;
    }

    /**
     * Cache of the children views for a line list item.
     */
    public class LineAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @BindView(R.id.line_name)
        public TextView lineName;

        @BindView(R.id.line_status_name)
        public TextView lineStatusDesc;

        @BindView(R.id.line_status_reason)
        public TextView lineStatusReason;

        public LineAdapterViewHolder(View view)
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
            Line textLineClick = lineList.get(adapterPosition);
            mLineClickHandler.onClick(textLineClick);
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
        final Line lineView = lineList.get(position);

        holder.lineName.setText(lineView.getLineName());
        holder.lineStatusDesc.setText(lineView.getLineStatusDesc());
        holder.lineStatusReason.setText(lineView.getLineStatusReason());

        switch(position)
        {
            case 0: holder.lineName.getText().toString().contains("Bakerloo");
                holder.lineName.setTextColor(ContextCompat.getColor(context, R.color.colorBakerloo));
                break;

            case 1: holder.lineName.getText().toString().contains("Central");
            holder.lineName.setTextColor(ContextCompat.getColor(context, R.color.colorCentral));
            break;

            case 2: holder.lineName.getText().toString().contains("Circle");
                holder.lineName.setTextColor(ContextCompat.getColor(context, R.color.colorCentral));
                break;

            case 3: holder.lineName.getText().toString().contains("District");
                holder.lineName.setTextColor(ContextCompat.getColor(context, R.color.colorDistrict));
                break;

            case 4: holder.lineName.getText().toString().contains("Hammersmith");
                holder.lineName.setTextColor(ContextCompat.getColor(context, R.color.colorHammersmithCity));
                break;

            case 5: holder.lineName.getText().toString().contains("Jubilee");
                holder.lineName.setTextColor(ContextCompat.getColor(context, R.color.colorJubilee));
                break;

            case 6: holder.lineName.getText().toString().contains("Metropolitan");
                holder.lineName.setTextColor(ContextCompat.getColor(context, R.color.colorMetropolitan));
                break;

            case 7: holder.lineName.getText().toString().contains("Northern");
                holder.lineName.setTextColor(ContextCompat.getColor(context, R.color.colorNorthern));
                break;

                case 8: holder.lineName.getText().toString().contains("Piccadilly");
                holder.lineName.setTextColor(ContextCompat.getColor(context, R.color.colorPiccadilly));
                break;

            case 9: holder.lineName.getText().toString().contains("Victoria");
                holder.lineName.setTextColor(ContextCompat.getColor(context, R.color.colorVictoria));
                break;

            case 10: holder.lineName.getText().toString().contains("Waterloo");
                holder.lineName.setTextColor(ContextCompat.getColor(context, R.color.colorWaterloo));
                break;
        }
    }

    @Override
    public int getItemCount()
    {
        return lineList.size();
    }

    public void setLineList(ArrayList<Line> mLineList)
    {
        this.lineList = mLineList;
        notifyDataSetChanged();
    }
}

