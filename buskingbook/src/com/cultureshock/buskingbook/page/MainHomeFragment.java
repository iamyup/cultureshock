package com.cultureshock.buskingbook.page;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.component.ViewPagerAdapter;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;

public class MainHomeFragment extends Fragment implements OnClickListener{
    private FragmentActivity mContext;
    private static MainHomeFragment mInstance;
    private TextView m_oBtnTest;
    private ImageView m_oBtnList;
    private ViewPager m_oViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        mInstance = this;

        m_oBtnTest = (TextView) getActivity().findViewById(R.id.test_main);
        m_oBtnTest.setOnClickListener(this);
        m_oBtnList = (ImageView) getActivity()
                .findViewById(R.id.title_btn_menu);
        m_oBtnList.setOnClickListener(this);
        setPager();
    }

    public void setTitle(String txt)
    {
    	m_oBtnTest.setText(txt);
    }
    public void setPager() {
        m_oViewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity());
        m_oViewPager.setAdapter(adapter);
        m_oViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {

                } else if (position == 1) {
                } else if (position == 2) {
                } else {
                }
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }
        });
    }

    public static MainHomeFragment getInstance() {
        return mInstance;
    }

    public void setDataUI() {
        if (getView() == null) {
            return;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public synchronized void onClick(View v) {
        switch (v.getId()) {
        // case R.id.test_main :
        // {
        // Bundle bundle = new Bundle();
        // bundle.putString("album_id", "");
        // MainActivity.getInstance().replaceFragment(MusicChartFragment.class,
        // bundle, true);
        // break;
        // }
        case R.id.title_btn_menu: {
            MainActivity.getInstance().showMenu();
            break;
        }

        }

    }


}
