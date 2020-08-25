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







