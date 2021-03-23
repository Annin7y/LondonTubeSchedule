package capstone.my.annin.londontubeschedule.recyclerviewadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.pojo.LineColor;

public class LineColorGuideAdapter extends RecyclerView.Adapter<LineColorGuideAdapter.LineColorGuideAdapterViewHolder>
{
    private static final String TAG = LineColorGuideAdapter.class.getSimpleName();

    private List<LineColor> lineColorList;
    private Context context;

    public LineColorGuideAdapter(List<LineColor> lineColorlist, Context context)
    {
        this.lineColorList = lineColorlist;
        this.context = context;
    }

    /**
     * Cache of the children views for a line list item.
     */
    public static class LineColorGuideAdapterViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.line_color_name)
        public TextView lineName;

        public LineColorGuideAdapterViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
    @Override
    public LineColorGuideAdapter.LineColorGuideAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.line_color_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new LineColorGuideAdapter.LineColorGuideAdapterViewHolder(view);
    }

    /**
     * Cache of the children views for a line list item.
     */
    @Override
    public void onBindViewHolder(LineColorGuideAdapter.LineColorGuideAdapterViewHolder holder, int position)
    {
        //Binding data
        final LineColor lineColorView = lineColorList.get(position);

        holder.lineName.setText(lineColorView.getLineName());


        switch(position)
        {
            case 0:
                holder.lineName.setTextColor(ContextCompat.getColor(context, R.color.colorBakerloo));
                break;

            case 1:
                holder.lineName.setTextColor(ContextCompat.getColor(context, R.color.colorCentral));

                break;

            case 2:
                holder.lineName.setTextColor(ContextCompat.getColor(context, R.color.colorCircle));
                break;

            case 3:
                holder.lineName.setTextColor(ContextCompat.getColor(context, R.color.colorDistrict));
                break;

            case 4: holder.lineName.getText().toString().contains("Hammersmith");
                holder.lineName.setTextColor(ContextCompat.getColor(context, R.color.colorHammersmithCity));
                break;

            case 5:
                holder.lineName.setTextColor(ContextCompat.getColor(context, R.color.colorJubilee));
                break;

            case 6:
                holder.lineName.setTextColor(ContextCompat.getColor(context, R.color.colorMetropolitan));
                break;

            case 7:
                holder.lineName.setTextColor(ContextCompat.getColor(context, R.color.colorNorthern));
                break;

            case 8:
                holder.lineName.setTextColor(ContextCompat.getColor(context, R.color.colorPiccadilly));
                break;

            case 9:
                holder.lineName.setTextColor(ContextCompat.getColor(context, R.color.colorVictoria));
                break;

            case 10:
                holder.lineName.setTextColor(ContextCompat.getColor(context, R.color.colorWaterloo));
                break;

            case 11:
                holder.lineName.setTextColor(ContextCompat.getColor(context, R.color.colorOverground));
                break;
        }
    }

    @Override
    public int getItemCount()
    {
        return lineColorList.size();
    }

}
