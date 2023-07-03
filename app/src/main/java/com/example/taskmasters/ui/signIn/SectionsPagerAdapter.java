package com.example.taskmasters.ui.signIn;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.taskmasters.R;
import com.example.taskmasters.model.user.User;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;
    private final User user;

    public SectionsPagerAdapter(Context context, FragmentManager fm, User user) {
        super(fm);
        mContext = context;
        this.user = user;
    }

    @Override
    public Fragment getItem(int position) {
        PlaceholderFragment fragment = PlaceholderFragment.newInstance(position + 1, user);
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = mContext.getResources().getString(TAB_TITLES[position]);
        SpannableString spannableString = new SpannableString(title);

        int color = Color.BLACK;

        spannableString.setSpan(new ForegroundColorSpan(color), 0, spannableString.length(), 0);
        return spannableString;
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}