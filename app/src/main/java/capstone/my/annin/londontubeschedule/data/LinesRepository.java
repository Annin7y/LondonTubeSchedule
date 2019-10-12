package capstone.my.annin.londontubeschedule.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import capstone.my.annin.londontubeschedule.pojo.Lines;

public class LinesRepository
{
    private LinesDao mLineDao;
    private LiveData<List<Lines>> mAllLines;

    LinesRepository(Application application)
    {
        AppDatabase db = AppDatabase.getDatabase(application);
        mLineDao = db.linesDao();
        mAllLines = mLineDao.loadAllLines();
    }

}
