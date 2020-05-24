package capstone.my.annin.londontubeschedule;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import capstone.my.annin.londontubeschedule.ui.MainActivity;

public class EspressoTest
{
    @RunWith(AndroidJUnit4ClassRunner.class)
    public class EspressoTest
    {
        @Rule
        public ActivityTestRule<MainActivity> activityTestRule =
                new ActivityTestRule<>(MainActivity.class);

        //Testing clicking on a position in the RecyclerView
        //Code based on the following YouTube video: https://www.youtube.com/watch?v=56xINIkzBy8
        @Test
        public void scrollToPosition() {
            onView(withId(R.id.recyclerview_main)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
        }

        //Testing line count shown
        //Code based on the following code samples:
        //http://qaru.site/questions/229713/how-to-count-recyclerview-items-with-espresso
        //https://github.com/twilio/mobile-sdk-sample-android/blob/master/twilio-auth-sample/src/androidTest/java/com/twilio/authsample/matchers/RecyclerViewItemCountAssertion.java
        //https://stackoverflow.com/questions/36399787/how-to-count-recyclerview-items-with-espresso/37339656
        //https://stackoverflow.com/questions/51678563/how-to-test-recyclerview-viewholder-text-with-espresso
        public class RecyclerViewItemCountAssertion implements ViewAssertion
        {
            private final int expectedCount;

            public RecyclerViewItemCountAssertion(int expectedCount)
            {
                this.expectedCount = expectedCount;
            }

            @Override
            public void check(View view, NoMatchingViewException noViewFoundException)
            {
                if (noViewFoundException != null)
                {
                    throw noViewFoundException;
                }

                RecyclerView recyclerView = (RecyclerView) view;
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                assertThat(adapter.getItemCount(), is(expectedCount));

            }
        }

        @Test
        public void lineCountTest()
        {
            onView(withId(R.id.recyclerview_main)).check(new RecyclerViewItemCountAssertion(11));
        }
    }

}
