package capstone.my.annin.londontubeschedule.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import capstone.my.annin.londontubeschedule.pojo.Lines;

public interface LineDao
{
    @Query("SELECT * FROM lines ORDER BY line_name")
    LiveData<List<Lines>> loadAllLines();

    @Query("SELECT * FROM lines WHERE lineId =:id")
    LiveData<Lines> getSelectedLine(String id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertLine(Lines lineEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateLine(Lines lineEntry);

    @Delete
    int deleteLine(Lines lineEntry);
}
