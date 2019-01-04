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

    private ArrayList<Lines> lineList = new ArrayList<Lines>();
    private Context context;

    /**
     * Creates a Line Adapter.
     */
    public LineAdapter(ArrayList<Lines> lineList, Context context)
    {
        this.lineList= lineList;
        this.context = context;
    }

    /**
     * Cache of the children views for an ingredients list item.
     */
    public class IngredientsAdapterViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.ingredient_quantity)
        public TextView lineName;

        @BindView(R.id.ingredient_measure)
        public TextView ingredientMeasure;

        @BindView(R.id.ingredient_name)
        public TextView ingredientName;

        public IngredientsAdapterViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
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
    public class LineAdapterViewHolder extends RecyclerView.ViewHolder {

        @Override
        public void onBindViewHolder(LineAdapterViewHolder holder, int position)
        {
            //Binding data
            final Lines lineView = lineList.get(position);

            holder.lineName.setText(lineView.getLineName());

        }

        @Override
        public int getItemCount()
        {
            return ingredientsList.size();
        }

        public void setIngredientsList(ArrayList<Ingredients> mIngredientsList)
        {
            this.ingredientsList.addAll(mIngredientsList);
            notifyDataSetChanged();
        }
    }
}
