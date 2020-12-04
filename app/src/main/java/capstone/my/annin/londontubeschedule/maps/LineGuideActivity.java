package capstone.my.annin.londontubeschedule.maps;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.my.annin.londontubeschedule.R;
import capstone.my.annin.londontubeschedule.pojo.Line;
import capstone.my.annin.londontubeschedule.recyclerviewadapters.LineAdapter;
import capstone.my.annin.londontubeschedule.ui.MainActivity;
import timber.log.Timber;

public class LineGuideActivity extends AppCompatActivity implements LineAdapter.LineAdapterOnClickHandler
{
    @BindView(R.id.line_color_guide)
    TextView lineColorGuide;
    RecyclerView mLineRecyclerView;
    private LineAdapter lineAdapter;
    private ArrayList<Line> lineArrayList = new ArrayList<>();
    private Context context;
   // ArrayList<String> lineListArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_guide);
        context = getApplicationContext();

        // Bind the views
        ButterKnife.bind(this);

        lineAdapter = new LineAdapter(this, lineArrayList, context);

        mLineRecyclerView.setAdapter(lineAdapter);

        RecyclerView.LayoutManager mLineLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLineRecyclerView.setLayoutManager(mLineLayoutManager);

        ArrayList<Integer> colors = new ArrayList<>(Arrays.asList(R.color.colorBakerloo, R.color.colorCentral, R.color.colorCircle,
                R.color.colorDistrict, R.color.colorHammersmithCity, R.color.colorJubilee, R.color.colorMetropolitan, R.color.colorNorthern,
                R.color.colorPiccadilly, R.color.colorVictoria, R.color.colorWaterloo));


        //Setting an ArrayList to TextView based on the code sample below:
        //https://www.android-examples.com/set-string-array-list-data-into-textview-android/#:~:text=Add%20array%20list%20data%20into,list%20elements%20data%20on%20it.
//        ArrayValueAddFunction();
//
//        LinearLayout LinearLayoutView = new LinearLayout(this);
//        TextView DisplayStringArray = new TextView(this);
//        DisplayStringArray.setTextSize(11);
//        LinearLayoutView.addView(DisplayStringArray);
//        for (int i=0; i<lineListArray.size();i++){
//            DisplayStringArray.append(lineListArray.get(i));
//            DisplayStringArray.append("\n");
//        }
//        setContentView(LinearLayoutView);

    }




    @Override
    public void onClick(Line line)
    {

    }


//    private void ArrayValueAddFunction()
//    {
//        lineListArray.add("Bakerloo");
//        lineListArray .add("Central");
//        lineListArray .add("Circle");
//        lineListArray.add("District");
//        lineListArray.add("Hammersmith & City");
//        lineListArray .add("Jubilee");
//        lineListArray .add("Metropolitan");
//        lineListArray .add("Northern");
//        lineListArray.add("Piccadilly");
//        lineListArray.add("Victoria");
//        lineListArray .add("Waterloo & City");
//
//    }

}