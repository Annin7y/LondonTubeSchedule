package android.my.annin.londontubeschedule.ui;

import android.my.annin.londontubeschedule.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StationListActivity extends AppCompatActivity
{
    @BindView(R.id.tube_list_instructions)
    TextView stationListInstructions;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_list);

        // Bind the views
        ButterKnife.bind(this);
    }
}
