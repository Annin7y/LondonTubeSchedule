package capstone.my.annin.londontubeschedule.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import capstone.my.annin.londontubeschedule.pojo.Lines;

@Dao
public interface LinesDao
{
    @Query("SELECT * FROM lines ORDER BY line_name")
    LiveData<List<Lines>> loadAllLines();

    @Query("SELECT * FROM lines WHERE lineId =:id")
    //LiveData<Lines> getSelectedLine(String id);
    //Method used when testing running the database on the main thread &
    //setting isFavorite in the Repository
    Lines getSelectedLine(String id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertLine(Lines lineEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateLine(Lines lineEntry);

    @Delete
    int deleteLine(Lines lineEntry);
}
