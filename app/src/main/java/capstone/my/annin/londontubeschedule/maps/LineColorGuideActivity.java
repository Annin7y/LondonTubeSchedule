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

package capstone.my.annin.londontubeschedule.maps;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.pojo.LineColor;
import capstone.my.annin.londontubeschedule.recyclerviewadapters.LineColorGuideAdapter;

public class LineColorGuideActivity extends AppCompatActivity
{
    @BindView(R.id.line_color_guide)
    TextView lineColorGuide;

    @BindView(R.id.recyclerview_line_guide)
    RecyclerView mLineColorRecyclerView;
    private LineColorGuideAdapter lineColorGuideAdapter;
    List<LineColor> lineColorArrayList = new ArrayList<LineColor>();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_guide);
        context = getApplicationContext();

        // Bind the views
        ButterKnife.bind(this);

        //Code based on the following code samples:
        //https://www.androidhive.info/2016/01/android-working-with-recycler-view/
        //https://www.javatpoint.com/android-recyclerview-list-example

        lineColorGuideAdapter = new LineColorGuideAdapter(lineColorArrayList, context);
        mLineColorRecyclerView.setHasFixedSize(true);
        mLineColorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mLineColorRecyclerView.setAdapter(lineColorGuideAdapter);

        prepareLineColorData();
    }

    private void prepareLineColorData()
    {
        LineColor lineColor = new LineColor(0, "Bakerloo");
        lineColorArrayList.add(lineColor);

        lineColor = new LineColor(1, "Central");
        lineColorArrayList.add(lineColor);

        lineColor = new LineColor(2, "Circle");
        lineColorArrayList.add(lineColor);

        lineColor = new LineColor(3, "District");
        lineColorArrayList.add(lineColor);

        lineColor = new LineColor(4, "Hammersmith & City");
        lineColorArrayList.add(lineColor);

        lineColor = new LineColor(5, "Jubilee");
        lineColorArrayList.add(lineColor);

        lineColor = new LineColor(6, "Metropolitan");
        lineColorArrayList.add(lineColor);

        lineColor = new LineColor(7, "Northern");
        lineColorArrayList.add(lineColor);

        lineColor = new LineColor(8, "Piccacilly");
        lineColorArrayList.add(lineColor);

        lineColor = new LineColor(9, "Victoria");
        lineColorArrayList.add(lineColor);

        lineColor = new LineColor(10, "Waterloo & City");
        lineColorArrayList.add(lineColor);

        lineColor = new LineColor(10, "Overground");
        lineColorArrayList.add(lineColor);

        lineColorGuideAdapter.notifyDataSetChanged();
    }
}