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

package capstone.my.annin.londontubeschedule.ui;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import capstone.my.annin.londontubeschedule.R;

public class CategoryAdapter extends FragmentPagerAdapter
{
    /**
     * Context of the app
     */
    private Context mContext;

    /**
     * Create a new {@link CategoryAdapter} object.
     *  @param context
     * @param fm is the fragment manager that will keep each fragment's state in the adapter
     */
    public CategoryAdapter(MainActivity context, FragmentManager fm)
    {
        super(fm);
        mContext = context;
    }

    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */
    @Override
    public Fragment getItem(int position)
    {
        if (position == 0)
        {
            return new TubeLineFragment();
        } else
        {
            return new OvergroundLineFragment();
        }
    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount()
    {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        if (position == 0)
        {
            return mContext.getString(R.string.category_tube_line);
        }
         else
             {
            return mContext.getString(R.string.category_overground);
        }
    }
}

