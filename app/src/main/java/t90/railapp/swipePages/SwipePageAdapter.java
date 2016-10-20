package t90.railapp.swipePages;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


public class SwipePageAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> swipePages;

    public SwipePageAdapter(FragmentManager fm, ArrayList<Fragment> pages) {
        super(fm);
        swipePages = pages;
    }

    @Override
    public Fragment getItem(int i) {
        return swipePages.get(i);
    }

    @Override
    public int getCount() {
        return swipePages.size();
    }
}
