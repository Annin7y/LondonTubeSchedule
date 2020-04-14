package capstone.my.annin.londontubeschedule.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import capstone.my.annin.londontubeschedule.pojo.Line;

@Dao
public interface LineDao
{
    @Query("SELECT * FROM Line ORDER BY line_name")
    LiveData<List<Line>> loadAllLines();

    @Query("SELECT * FROM Line WHERE lineId =:id")
    //LiveData<Lines> getSelectedLine(String id);
    //Method used when testing running the database on the main thread &
    //setting isFavorite in the Repository
    Line getSelectedLine(String id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertLine(Line lineEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateLine(Line lineEntry);

    @Delete
    int deleteLine(Line lineEntry);
}
