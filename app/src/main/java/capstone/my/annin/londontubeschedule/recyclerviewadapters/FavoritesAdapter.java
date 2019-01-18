package capstone.my.annin.londontubeschedule.recyclerviewadapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.data.TubeLineContract;
import capstone.my.annin.londontubeschedule.model.Lines;

public class FavoritesAdapter{ // extends RecyclerView.Adapter<FavoritesAdapter.FavoritesAdapterViewHolder>
//{
//    private static final String TAG = FavoritesAdapter.class.getSimpleName();
//
//    private Context context;
//    private Cursor cursor;
//    private LinesAdapter.LinesAdapterOnClickHandler mClickHandler;
//
//    public FavoritesAdapter(LinesAdapter.LinesAdapterOnClickHandler clickHandler, Context context)
//    {
//        mClickHandler = clickHandler;
//        this.context = context;
//    }
//    public class FavoritesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        @BindView(R.id.line_name)
//        TextView lineName;
//
//        public FavoritesAdapterViewHolder(View view) {
//            super(view);
//            ButterKnife.bind(this, view);
//            view.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            cursor.moveToPosition(getAdapterPosition());
//
//            String lineName = cursor.getString(cursor.getColumnIndexOrThrow(TubeLineContract.TubeLineEntry.COLUMN_LINES_NAME));
//            String lineId = cursor.getString(cursor.getColumnIndexOrThrow(TubeLineContract.TubeLineEntry.COLUMN_LINES_ID));
//
//            Lines lines = new Lines(lineName, lineId);
//
//            mClickHandler.onClick(lines);
//        }
//    }
//
//    @Override
//        public void onBindViewHolder(FavoritesAdapter.FavoritesAdapterViewHolder holder, int position)
//        {
//            // get to the right location in the cursor
//            cursor.moveToPosition(position);
//
//            // Determine the values of the wanted data
//            int lineIdIndex = cursor.getColumnIndexOrThrow(TubeLineContract.TubeLineEntry.COLUMN_LINES_ID);
//            int lineNameColumnIndex = cursor.getColumnIndexOrThrow(TubeLineContract.TubeLineEntry.COLUMN_LINES_NAME);
//
//            final int id = cursor.getInt(lineIdIndex);
//            String stationName = cursor.getString(lineNameColumnIndex);
//
//            holder.itemView.setTag(id);
//            holder.lineName.setText(stationName);
//            Log.e(TAG, "Failed to load line text.");
//
//        }
//
//        @Override
//        public FavoritesAdapter.FavoritesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
//        {
//            Context context = viewGroup.getContext();
//            int layoutIdForListItem = R.layout.line_list_item;
//            LayoutInflater inflater = LayoutInflater.from(context);
//            boolean shouldAttachToParentImmediately = false;
//            View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
//            return new FavoritesAdapter.FavoritesAdapterViewHolder(view);
//        }
//
//        public Cursor swapCursor(Cursor c)
//        {
//            // check if this cursor is the same as the previous cursor (mCursor)
//            if (cursor == c)
//            {
//                return null; // bc nothing has changed
//            }
//
//            Cursor temp = cursor;
//            this.cursor = c; // new cursor value assigned
//
//            //check if this is a valid cursor, then update the cursor
//            if (c != null)
//            {
//                this.notifyDataSetChanged();
//            }
//            return temp;
//        }
//
//        @Override
//        public int getItemCount()
//        {
//            if (null == cursor)
//                return 0;
//
//            return cursor.getCount();
//
//        }
    }




