package com.cultureshock.buskingbook.main;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.cultureshock.buskingbook.R;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class ListSlidingActivity extends SlidingFragmentActivity {

    private int mTitleRes;
    protected Fragment mFrag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the Behind View
        setBehindContentView(R.layout.menu_frame);
        FragmentTransaction t = this.getSupportFragmentManager()
                .beginTransaction();
        mFrag = new LeftMenuFragment();
        t.replace(R.id.menu_frame, mFrag);
        t.commit();

        // customize the SlidingMenu
        SlidingMenu sm = getSlidingMenu();
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setShadowDrawable(R.drawable.shadow);
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.setFadeDegree(0.35f);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
    }

    public class BasePagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments = new ArrayList<Fragment>();
        private ViewPager mPager;

        public BasePagerAdapter(FragmentManager fm, ViewPager vp) {
            super(fm);
            mPager = vp;
            mPager.setAdapter(this);
            for (int i = 0; i < 3; i++) {
                addTab(new LeftMenuFragment());
            }
        }

        public void addTab(Fragment frag) {
            mFragments.add(frag);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

}
