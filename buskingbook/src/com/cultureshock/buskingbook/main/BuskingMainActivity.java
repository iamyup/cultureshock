
package com.cultureshock.buskingbook.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.page.MainHomeFragment;
import com.slidingmenu.lib.SlidingMenu;

public class BuskingMainActivity extends ListSlidingActivity {

    private Fragment mainFg;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the Above View
        if ( savedInstanceState != null )
            mainFg = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
        if ( mainFg == null )
            mainFg = Fragment.instantiate(getBaseContext(), MainHomeFragment.class.getName(), null);

        // set the Above View
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mainFg).commit();

        // set the Behind View
        setBehindContentView(R.layout.menu_frame);
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new LeftMenuFragment()).commit();

        // customize the SlidingMenu
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        getSlidingMenu().setFadeDegree(1.0f);
        
    }

   
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void switchContent(Fragment fragment) {
//        mainFg = fragment;
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
        getSlidingMenu().showContent();
    }
}
