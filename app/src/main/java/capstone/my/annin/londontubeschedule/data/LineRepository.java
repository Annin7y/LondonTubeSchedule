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

package capstone.my.annin.londontubeschedule.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import capstone.my.annin.londontubeschedule.pojo.Line;

public class LineRepository
{
    private LineDao mLineDao;
    private LiveData<List<Line>> mAllLines;
//    public static MutableLiveData<Boolean> isInsertOk = new MutableLiveData<>();
//    public static MutableLiveData<Boolean> isDeleteOk = new MutableLiveData<>();
    public static MutableLiveData<Boolean> isFavorite = new MutableLiveData<>();

    LineRepository(Application application)
    {
        AppDatabase db = AppDatabase.getDatabase(application);
        mLineDao = db.lineDao();
        mAllLines = mLineDao.loadAllLines();
    }
    LiveData<List<Line>> loadAllLines()
    {
        return mAllLines;
    }

    public void insert(Line lineEntry)
    {
        new insertAsyncTask(mLineDao).execute(lineEntry);
    }

    public void delete(Line lineEntry)
    {
        new deleteAsyncTask(mLineDao).execute(lineEntry);
    }

//Method used when testing running the database on the main thread
//    public boolean select(String id)
//    {
//        Line line = mLineDao.getSelectedLine(id);
//        return line != null;
//    }

    //Method used when declaring isFavorite in the StationListActivity
//   public LiveData<Line> select(String id)
//    {
//       return mLineDao.getSelectedLine(id);
//   }

    //Method used when declaring isFavorite in the Repository
    public void select(String id)
    {
        new selectAsyncTask().execute(id);
    }

    private class selectAsyncTask extends AsyncTask<String, Void, Line>
    {

        @Override
        protected Line doInBackground(final String... params) {
            return mLineDao.getSelectedLine(params[0]);
        }

        @Override
        protected void onPostExecute(Line lines) {
            if (lines != null) {
                setFavorite(true);
            } else {
                setFavorite(false);
            }
        }
    }
    public void setFavorite(boolean value)
    {
        isFavorite.setValue(value);
    }

    public MutableLiveData<Boolean> isFavorite()
    {
        return isFavorite;
    }


    //isInsertOk and isDeleteOk methods commented out: combined into a single isFavorite variable
    //will be checked in the Repository instead of the DetailActivity
//    public static void setInsertOk(boolean value)
//    {
//        isInsertOk.setValue(value);
//    }

    private class insertAsyncTask extends AsyncTask<Line, Void, Long>
    {
        private LineDao mAsyncTaskDao;

        insertAsyncTask(LineDao dao)
        {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Long doInBackground(final Line... params)
        {
            return mAsyncTaskDao.insertLine(params[0]);

        }

        @Override
        protected void onPostExecute(Long id)
        {
            if(id != -1)
            {
                //LineRepository.this.setInsertOk(true);
                LineRepository.this.setFavorite(true);
            }
            else
            {
                //LineRepository.this.setInsertOk(false);
                LineRepository.this.setFavorite(false);
            }

        }

    }

//    public static void setDeleteOk(boolean value)
//    {
//        isDeleteOk.setValue(value);
//    }

    private class deleteAsyncTask extends AsyncTask<Line, Void, Integer>
    {
        private LineDao mAsyncTaskDao;

        deleteAsyncTask(LineDao dao)
        {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Integer doInBackground(final Line... params)
        {
            return mAsyncTaskDao.deleteLine(params[0]);

        }

        @Override
        protected void onPostExecute(Integer rowsDeleted)
        {
            if(rowsDeleted > 0)
            {
               // LineRepository.this.setDeleteOk(true);
                LineRepository.this.setFavorite(false);
            }
            else
            {
               // LineRepository.this.setDeleteOk(false);
                LineRepository.this.setFavorite(true);
            }
        }
    }
}
