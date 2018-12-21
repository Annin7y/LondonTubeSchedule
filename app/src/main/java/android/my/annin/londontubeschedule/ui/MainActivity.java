package android.my.annin.londontubeschedule.ui;

import android.my.annin.londontubeschedule.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{
    @BindView(R.id.tube_list_instructions)
    TextView tubeListInstructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind the views
        ButterKnife.bind(this);

    }
}
