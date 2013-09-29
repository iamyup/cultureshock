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

public class LeftMenuFragment extends Fragment implements OnClickListener {
    private static Activity mActivity;
    private static LeftMenuFragment mInstance;
    private TextView mBtnTempMusician;
    private TextView mBtnTempNormal;

    public static LeftMenuFragment getInstance() {
        if (mInstance == null) {
            mInstance = new LeftMenuFragment();
        }
        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_left_menu_musician, container,
                false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = getActivity();
        mInstance = this;
        mBtnTempMusician = (TextView) getActivity().findViewById(
                R.id.temp_musician);
        mBtnTempNormal = (TextView) getActivity()
                .findViewById(R.id.temp_normal);
        if (mBtnTempMusician != null) {
            mBtnTempMusician.setOnClickListener(this);
        }
        if (mBtnTempNormal != null) {
            mBtnTempNormal.setOnClickListener(this);
        }
    }

    // the meat of switching the above fragment
    private void switchFragment(Fragment fragment) {
        if (getActivity() == null)
            return;

        if (getActivity() instanceof BuskingMainActivity) {
            BuskingMainActivity fca = (BuskingMainActivity) getActivity();
            fca.switchContent(fragment);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.support.v4.app.Fragment#onResume()
     */
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // loginSatatus();
    }

    @Override
    public void onClick(View v) {
    }

}
