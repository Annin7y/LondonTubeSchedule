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

//import capstone.my.annin.londontubeschedule.data.TubeLineContract;


//public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesAdapterViewHolder>
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
//
//    public class FavoritesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
//    {
//        @BindView(R.id.line_name)
//        TextView lineName;
//
//        public FavoritesAdapterViewHolder(View view)
//        {
//            super(view);
//            ButterKnife.bind(this, view);
//            view.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v)
//        {
//            cursor.moveToPosition(getAdapterPosition());
//
//            String lineName = cursor.getString(cursor.getColumnIndexOrThrow(TubeLineContract.TubeLineEntry.COLUMN_LINES_NAME));
//            String lineId = cursor.getString(cursor.getColumnIndexOrThrow(TubeLineContract.TubeLineEntry.COLUMN_LINES_ID));
//            String lineStatus= cursor.getString(cursor.getColumnIndexOrThrow(TubeLineContract.TubeLineEntry.COLUMN_LINES_STATUS_DESC));
//            String lineReason = cursor.getString(cursor.getColumnIndexOrThrow(TubeLineContract.TubeLineEntry.COLUMN_LINES_STATUS_REASON));
//
//            Lines lines = new Lines(lineName, lineId, lineStatus, lineReason);
//
//            mClickHandler.onClick(lines);
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(FavoritesAdapter.FavoritesAdapterViewHolder holder, int position)
//    {
//        // get to the right location in the cursor
//        cursor.moveToPosition(position);
//
//        // Determine the values of the wanted data
//        int lineIdIndex = cursor.getColumnIndexOrThrow(TubeLineContract.TubeLineEntry.COLUMN_LINES_ID);
//        int lineNameColumnIndex = cursor.getColumnIndexOrThrow(TubeLineContract.TubeLineEntry.COLUMN_LINES_NAME);
//
//        final String id = cursor.getString(lineIdIndex);
//        String stationName = cursor.getString(lineNameColumnIndex);
//
//        holder.itemView.setTag(id);
//        holder.lineName.setText(stationName);
//        Log.v(TAG, "Line text loaded.");
//    }
//
//    @Override
//    public FavoritesAdapter.FavoritesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
//    {
//        Context context = viewGroup.getContext();
//        int layoutIdForListItem = R.layout.line_list_item;
//        LayoutInflater inflater = LayoutInflater.from(context);
//        boolean shouldAttachToParentImmediately = false;
//        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
//        return new FavoritesAdapter.FavoritesAdapterViewHolder(view);
//    }
//
//    public Cursor swapCursor(Cursor c)
//    {
//        // check if this cursor is the same as the previous cursor (mCursor)
//        if (cursor == c)
//        {
//            return null; // bc nothing has changed
//        }
//
//        Cursor temp = cursor;
//        this.cursor = c; // new cursor value assigned
//
//        //check if this is a valid cursor, then update the cursor
//        if (c != null)
//        {
//            this.notifyDataSetChanged();
//        }
//        return temp;
//    }
//
//    @Override
//    public int getItemCount()
//    {
//        if (null == cursor)
//            return 0;
//
//        return cursor.getCount();
//    }
//}




