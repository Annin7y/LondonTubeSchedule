package android.my.annin.londontubeschedule.ui;

import android.my.annin.londontubeschedule.R;
import android.my.annin.londontubeschedule.asynctask.TubeLineAsyncTaskInterface;
import android.my.annin.londontubeschedule.recyclerviewadapters.LineAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity  implements LineAdapter.LineAdapterOnClickHandler, TubeLineAsyncTaskInterfaceAsyncTaskInterface
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }


//    //Display if there is no internet connection
//    public void showErrorMessage()
//    {
//        Snackbar
//                .make(mCoordinatorLayout, "Please check your internet connection", Snackbar.LENGTH_INDEFINITE)
//                .setAction("Retry", new MyClickListener())
//                .show();
//        mRecyclerView.setVisibility(View.INVISIBLE);
//        mLoadingIndicator.setVisibility(View.VISIBLE);
//    }



}
