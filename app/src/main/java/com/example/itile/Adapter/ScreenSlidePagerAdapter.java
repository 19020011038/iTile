package com.example.itile.Adapter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.itile.Fragment.Task.DoingFragment;
import com.example.itile.Fragment.Task.FinishFragment;
import com.example.itile.Fragment.Task.UpcomingFragment;
import com.example.itile.R;


public class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};

    private final Context mContext;
//    private BooklistFragment1 booklistFragment1;
//    private BooklistFragment2 booklistFragment2;
//    private BooklistFragment3 booklistFragment3;
//    private BooklistFragment4 booklistFragment4;
//    private BooklistFragment5 booklistFragment5;

    private int NUM_PAGES = 3;
    public ScreenSlidePagerAdapter(Context context, FragmentManager fa) {
        super(fa);
        mContext = context;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
//        return PlaceholderFragment.newInstance(position + 1);
//        booklistFragment1 = new BooklistFragment1();
//        booklistFragment2 = new BooklistFragment2();
//        booklistFragment3 = new BooklistFragment3();
//        booklistFragment4 = new BooklistFragment4();
//        booklistFragment5 = new BooklistFragment5();
        switch (position)
        {
            case 0:
                String p = position+"";
                Log.d("qsh1",p);
                return UpcomingFragment.newInstance(position);
            case 1:
                String p1 = position+"";
                Log.d("qsh2",p1);
                return DoingFragment.newInstance(position);
            default:
                String p4 = position+"";
                Log.d("qsh3",p4);
                return FinishFragment.newInstance(position);
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
