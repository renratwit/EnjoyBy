package edu.wit.mobileapp.enjoyby.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.wit.mobileapp.enjoyby.All_Fragment;
import edu.wit.mobileapp.enjoyby.Expired_Fragment;
import edu.wit.mobileapp.enjoyby.Fresh_Fragment;
import edu.wit.mobileapp.enjoyby.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        // return PlaceholderFragment.newInstance(position + 1);
        Fragment fragment = null;
        switch(position) {
            case 0:
                fragment = new All_Fragment();
                break;
            case 1:
                fragment = new Fresh_Fragment();
                break;
            case 2:
                fragment = new Expired_Fragment();
                break;
            default:
                fragment = PlaceholderFragment.newInstance(position + 1);
        }

        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}