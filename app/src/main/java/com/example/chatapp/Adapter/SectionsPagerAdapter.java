package com.example.chatapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chatapp.Fragment.ChatFragment;
import com.example.chatapp.Fragment.FriendsFragment;
import com.example.chatapp.Fragment.SetFragment;
import com.example.chatapp.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2,R.string.tab_text_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position == 0) {
            fragment = new FriendsFragment();
            Bundle args = new Bundle();
           // args.putParcelable("USER", me);
           // Log.d("User Friends",me.toString());
            fragment.setArguments(args);
            return fragment;
        }
        else if(position==1) {
            fragment = new ChatFragment();
            Bundle args = new Bundle();
           // args.putParcelable("USER", me);
            //Log.d("User Chat",me.toString());
            fragment.setArguments(args);
            return fragment;
        }
        else {
            fragment = new SetFragment();
            Bundle args = new Bundle();
           // args.putParcelable("USER", me);
            fragment.setArguments(args);
            return fragment;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}