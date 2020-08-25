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
        //Method used when testing running the database on the main thread &
        //setting isFavorite in the Repository
   //LiveData<Line> getSelectedLine(String id);
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
