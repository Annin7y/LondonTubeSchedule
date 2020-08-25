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

package capstone.my.annin.londontubeschedule;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import capstone.my.annin.londontubeschedule.asynctask.TubeLineAsyncTask;
import capstone.my.annin.londontubeschedule.asynctask.TubeLineAsyncTaskInterface;
import capstone.my.annin.londontubeschedule.pojo.Line;
import capstone.my.annin.londontubeschedule.ui.MainActivity;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4ClassRunner.class)
public class AsyncTaskTest
{
  //Code samples consulted:
  //https://stackoverflow.com/questions/2321829/android-asynctask-testing-with-android-test-framework

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testMainActivityAsyncTask() throws Throwable
    {
        // create  a signal to let us know when our task is done.
        final CountDownLatch signal = new CountDownLatch(1);

       final TubeLineAsyncTask mainTaskTest = new TubeLineAsyncTask(result ->
       {
           assertNotNull(result);
           signal.countDown();

       });

        mainTaskTest.execute();
        signal.await(30,TimeUnit.SECONDS);
    }
}



