package capstone.my.annin.londontubeschedule.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import capstone.my.annin.londontubeschedule.pojo.Lines;

public class LinesRepository
{
    private LinesDao mLineDao;
    private LiveData<List<Lines>> mAllLines;
//    public static MutableLiveData<Boolean> isInsertOk = new MutableLiveData<>();
//    public static MutableLiveData<Boolean> isDeleteOk = new MutableLiveData<>();
    public static MutableLiveData<Boolean> isFavorite = new MutableLiveData<>();

    LinesRepository(Application application)
    {
        AppDatabase db = AppDatabase.getDatabase(application);
        mLineDao = db.linesDao();
        mAllLines = mLineDao.loadAllLines();
    }
    LiveData<List<Lines>> loadAllLines()
    {
        return mAllLines;
    }

    public void insert(Lines lineEntry)
    {
        new insertAsyncTask(mLineDao).execute(lineEntry);
    }

    public void delete(Lines lineEntry)
    {
        new deleteAsyncTask(mLineDao).execute(lineEntry);
    }

//Method used when testing running the database on the main thread
//    public boolean select(String id)
//    {
//        Lines line = mLineDao.getSelectedLine(id);
//
//        return line != null;
//    }

    public void select(String id)
    {
        new selectAsyncTask().execute(id);
    }

    private class selectAsyncTask extends AsyncTask<String, Void,Lines>
    {

        @Override
        protected Lines doInBackground(final String... params) {
            return mLineDao.getSelectedLine(params[0]);
        }

        @Override
        protected void onPostExecute(Lines lines) {
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

    private class insertAsyncTask extends AsyncTask<Lines, Void, Long>
    {
        private LinesDao mAsyncTaskDao;

        insertAsyncTask(LinesDao dao)
        {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Long doInBackground(final Lines... params)
        {
            return mAsyncTaskDao.insertLine(params[0]);

        }

        @Override
        protected void onPostExecute(Long id)
        {
            if(id != -1)
            {
                //LinesRepository.this.setInsertOk(true);
                LinesRepository.this.setFavorite(true);
            }
            else
            {
                //LinesRepository.this.setInsertOk(false);
                LinesRepository.this.setFavorite(false);
            }

        }

    }

//    public static void setDeleteOk(boolean value)
//    {
//        isDeleteOk.setValue(value);
//    }

    private class deleteAsyncTask extends AsyncTask<Lines, Void, Integer>
    {
        private LinesDao mAsyncTaskDao;

        deleteAsyncTask(LinesDao dao)
        {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Integer doInBackground(final Lines... params)
        {
            return mAsyncTaskDao.deleteLine(params[0]);

        }

        @Override
        protected void onPostExecute(Integer  rowsDeleted)
        {
            if(rowsDeleted > 0)
            {
               // LinesRepository.this.setDeleteOk(true);
                LinesRepository.this.setFavorite(false);
            }
            else
            {
               // LinesRepository.this.setDeleteOk(false);
                LinesRepository.this.setFavorite(true);
            }
        }
    }
}
