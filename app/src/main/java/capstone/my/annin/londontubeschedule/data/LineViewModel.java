package capstone.my.annin.londontubeschedule.data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import capstone.my.annin.londontubeschedule.pojo.Line;

public class LineViewModel extends AndroidViewModel
{
    private LineRepository mRepository;
    private LiveData<List<Line>> mAllLines;

    public LineViewModel(Application application)
    {
        super(application);
        mRepository = new LineRepository(application);
        mAllLines = mRepository.loadAllLines();
    }

    public LiveData<List<Line>> loadAllLines()
    {
        return mAllLines;
    }

    public void insert(Line linesEntry) {
        mRepository.insert(linesEntry);

    }

    public void delete(Line lineEntry) {
        mRepository.delete(lineEntry);
    }

    public void select(String lineId) {
        mRepository.select(lineId);
    }

    public LiveData<Boolean> isFavorite()
    {
        return mRepository.isFavorite();
    }



//    public LiveData<Boolean> insert(Line lineEntry)
//    {
//        mRepository.insert(lineEntry);
//        return LineRepository.isInsertOk;
//
//    }
//
//    public LiveData<Boolean> delete(Line lineEntry)
//    {
//        mRepository.delete(lineEntry);
//        return LineRepository.isDeleteOk;
//    }

//    public LiveData<Line> select(String id)
//    {
//        return mRepository.select(id);
//    }

    //Method used when testing running the database on the main thread
//    public boolean select(String lineId)
//    {
//        return mRepository.select(lineId);
//    }
}







