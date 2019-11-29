package capstone.my.annin.londontubeschedule.data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import capstone.my.annin.londontubeschedule.pojo.Lines;

public class LinesViewModel extends AndroidViewModel
{
    private LinesRepository mRepository;
    private LiveData<List<Lines>> mAllLines;

    public LinesViewModel(Application application)
    {
        super(application);
        mRepository = new LinesRepository(application);
        mAllLines = mRepository.loadAllLines();
    }

    public LiveData<List<Lines>> loadAllLines()
    {
        return mAllLines;
    }

    public void insert(Lines linesEntry) {
        mRepository.insert(linesEntry);

    }

    public void delete(Lines linesEntry) {
        mRepository.delete(linesEntry);
    }

    public void select(String lineId) {
        mRepository.select(lineId);
    }

    public LiveData<Boolean> isFavorite()
    {
        return mRepository.isFavorite();
    }



//    public LiveData<Boolean> insert(Lines lineEntry)
//    {
//        mRepository.insert(lineEntry);
//        return LinesRepository.isInsertOk;
//
//    }
//
//    public LiveData<Boolean> delete(Lines lineEntry)
//    {
//        mRepository.delete(lineEntry);
//        return LinesRepository.isDeleteOk;
//    }

//    public LiveData<Lines> select(String id)
//    {
//        return mRepository.select(id);
//    }

    //Method used when testing running the database on the main thread
//    public boolean select(String lineId)
//    {
//        return mRepository.select(lineId);
//    }
}







