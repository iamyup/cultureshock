
package com.cultureshock.buskingbook.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.page.MainHomeFragment;


public class LeftMenuFragment extends Fragment implements OnClickListener {
    private static Activity mActivity;
    private static LeftMenuFragment mInstance;
    private TextView m_oBtnTest;
    private TextView m_oBtnHome;


    public static LeftMenuFragment getInstance() {
        if ( mInstance == null ) {
            mInstance = new LeftMenuFragment();
        }
        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_left_menu, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = getActivity();
        mInstance = this;
        m_oBtnTest = (TextView)getActivity().findViewById(R.id.test_left);
        m_oBtnHome = (TextView)getActivity().findViewById(R.id.test_home);
        m_oBtnTest.setOnClickListener(this);
        m_oBtnHome.setOnClickListener(this);
    }

    // the meat of switching the above fragment
    private void switchFragment(Fragment fragment) {
        if ( getActivity() == null )
            return;

        if ( getActivity() instanceof BuskingMainActivity ) {
            BuskingMainActivity fca = (BuskingMainActivity) getActivity();
            fca.switchContent(fragment);
        }
    }

   

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onResume()
     */
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //loginSatatus();
    }

   

   

    @Override
    public void onClick(View v) {
        Fragment newContent = null;
        switch ( v.getId() ) {
	        case R.id.test_home :
	        {
	        	MainActivity.getInstance().replaceFragment(MainHomeFragment.class, null, false);
	        	break;
	        }
	        case R.id.test_left :
	        {
//	        	MainActivity.getInstance().replaceFragment(MainHomeFragment.class, null, true);
	        	break;
	        }
        }
    }

   
}
