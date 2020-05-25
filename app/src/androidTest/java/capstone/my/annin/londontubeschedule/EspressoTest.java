package capstone.my.annin.londontubeschedule;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import capstone.my.annin.londontubeschedule.ui.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

    @RunWith(AndroidJUnit4ClassRunner.class)
    public class EspressoTest
    {
        private static final int ITEMFINDINLIST = 0;

        @Rule
        public ActivityTestRule<MainActivity> activityTestRule =
                new ActivityTestRule<>(MainActivity.class);
 /*
 Testing clicking on a position in the RecyclerView
        Code based on the following YouTube videos: https://www.youtube.com/watch?v=56xINIkzBy8
        https://www.youtube.com/watch?v=wWO0mA-OcZo
  */
//        @Test
//        public void scrollToPosition()
//        {
//            onView(withId(R.id.recyclerview_main)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
//        }
//
//        //Testing line count shown
//        //Code based on the following code samples:
//        //http://qaru.site/questions/229713/how-to-count-recyclerview-items-with-espresso
//        //https://github.com/twilio/mobile-sdk-sample-android/blob/master/twilio-auth-sample/src/androidTest/java/com/twilio/authsample/matchers/RecyclerViewItemCountAssertion.java
//        //https://stackoverflow.com/questions/36399787/how-to-count-recyclerview-items-with-espresso/37339656
//        //https://stackoverflow.com/questions/51678563/how-to-test-recyclerview-viewholder-text-with-espresso
//        public class RecyclerViewItemCountAssertion implements ViewAssertion
//        {
//            private final int expectedCount;
//
//
//            public RecyclerViewItemCountAssertion(int expectedCount)
//            {
//                this.expectedCount = expectedCount;
//            }
//
//            @Override
//            public void check(View view, NoMatchingViewException noViewFoundException)
//            {
//                if (noViewFoundException != null)
//                {
//                    throw noViewFoundException;
//                }
//                RecyclerView recyclerView = (RecyclerView) view;
//                RecyclerView.Adapter adapter = recyclerView.getAdapter();
//                assertThat(adapter.getItemCount(), is(expectedCount));
//
//            }
//        }
//
//        @Test
//        public void lineCountTest()
//        {
//            onView(withId(R.id.recyclerview_main)).check(new RecyclerViewItemCountAssertion(11));
//        }

/*
 Testing clicking on a position in the RecyclerView
        Code based on the following YouTube video:
        https://www.youtube.com/watch?v=wWO0mA-OcZo
  */
        @Test
        public void viewMatchTest()
        {
            Espresso.onView(ViewMatchers.withId(R.id.recyclerview_main)).perform(RecyclerViewActions.actionOnItemAtPosition(ITEMFINDINLIST,click()));
            String itemVal = "Bakerloo";
            Espresso.onView(withText(itemVal)).check(matches(isDisplayed()));
        }

    }


