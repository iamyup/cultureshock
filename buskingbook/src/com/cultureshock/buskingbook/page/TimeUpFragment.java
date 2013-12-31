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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.component.TimeViewPagerAdapter;
import com.cultureshock.buskingbook.component.ViewPagerAdapter;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.util.Util;

public class TimeUpFragment extends Fragment implements OnClickListener{
	private static TimeUpFragment mInstance;
    private FragmentActivity mContext;
    private TextView m_oBtnTest;
    private LinearLayout m_oBtnList;
    private ViewPager m_oViewPager;
    
    private LinearLayout m_oBtnLineUp;
    private LinearLayout m_oBtnIssueUp;
    private LinearLayout m_oBtnBuskers;
    
    private TextView m_oTxtLineUp;
    private TextView m_oTxtIssueUp;
    private TextView m_oTxtBuskers;
    
    private LinearLayout m_oLineLineUp;
    private LinearLayout m_oLineIssueUp;
    private LinearLayout m_oLineBuskers;
    
    private String year;
    private String mon;
    private String day;
    private String week;
    private String ampm;
    private String hour;
    private String min;
    private String place;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.time_menu_top, container, false);
        
    }
    @Override
	public void onResume() {
        super.onResume();
    }
    public static TimeUpFragment getInstance() {
        return mInstance;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        mInstance = this;
        MainActivity.getInstance().onBottom();
        m_oBtnTest = (TextView) getActivity().findViewById(R.id.test_main);
        m_oBtnTest.setOnClickListener(this);
        m_oBtnList = (LinearLayout) getActivity()
                .findViewById(R.id.title_btn_menu);
        m_oBtnList.setOnClickListener(this);
        
        m_oBtnLineUp = (LinearLayout) getActivity().findViewById(R.id.top_menu1);
        m_oBtnIssueUp = (LinearLayout) getActivity().findViewById(R.id.top_menu2);
        m_oBtnBuskers = (LinearLayout) getActivity().findViewById(R.id.top_menu3);
        m_oBtnLineUp.setOnClickListener(this);
        m_oBtnIssueUp.setOnClickListener(this);
        m_oBtnBuskers.setOnClickListener(this);
        
        m_oTxtLineUp = (TextView) getActivity().findViewById(R.id.top_menu1_text);
        m_oTxtIssueUp = (TextView) getActivity().findViewById(R.id.top_menu2_text);
        m_oTxtBuskers = (TextView) getActivity().findViewById(R.id.top_menu3_text);
        
        m_oLineLineUp = (LinearLayout) getActivity().findViewById(R.id.top_line_menu1);
        m_oLineIssueUp = (LinearLayout) getActivity().findViewById(R.id.top_line_menu2);
        m_oLineBuskers = (LinearLayout) getActivity().findViewById(R.id.top_line_menu3);
        setPager();

    }
    
    public void setTitle(String txt)
    {
    	m_oBtnTest.setText(txt);
    }
    public void setPager() {
        m_oViewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
        TimeViewPagerAdapter adapter = new TimeViewPagerAdapter(getActivity());
        m_oViewPager.setAdapter(adapter);
        m_oViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                if (position == 0) 
                {
                	m_oTxtLineUp.setTextColor(0xff01d0d2);
                    m_oTxtIssueUp.setTextColor(0xff949494);
                    m_oTxtBuskers.setTextColor(0xff949494);
                    
                    m_oLineLineUp.setBackgroundColor(0xff01d0d2);
                    m_oLineIssueUp.setBackgroundColor(0xffffffff);
                    m_oLineBuskers.setBackgroundColor(0xffffffff);
                    
                   
                } 
                else if (position == 1) 
                {
                	m_oTxtLineUp.setTextColor(0xff949494);
                    m_oTxtIssueUp.setTextColor(0xff01d0d2);
                    m_oTxtBuskers.setTextColor(0xff949494);
                    m_oLineLineUp.setBackgroundColor(0xffffffff);
                    m_oLineIssueUp.setBackgroundColor(0xff01d0d2);
                    m_oLineBuskers.setBackgroundColor(0xffffffff);
                    
                }
                else if (position == 2) 
                {
                	m_oTxtLineUp.setTextColor(0xff949494);
                    m_oTxtIssueUp.setTextColor(0xff949494);
                    m_oTxtBuskers.setTextColor(0xff01d0d2);
                    m_oLineLineUp.setBackgroundColor(0xffffffff);
                    m_oLineIssueUp.setBackgroundColor(0xffffffff);
                    m_oLineBuskers.setBackgroundColor(0xff01d0d2);
                    
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



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    private void setCurrentInflateItem(int type){
        if(type==0){
        	m_oViewPager.setCurrentItem(0);
        }else if(type==1){
        	m_oViewPager.setCurrentItem(1);
        }else{
        	m_oViewPager.setCurrentItem(2);
        }
    }
    @Override
    public synchronized void onClick(View v) {
        switch (v.getId()) {
        case R.id.title_btn_menu:
            MainActivity.getInstance().showMenu();
            break;
        case R.id.times_join_btn:
        	MainActivity.getInstance().replaceFragment(TimeUpFragment.class, null, false);
            break;
        case R.id.top_menu1:
        	m_oTxtLineUp.setTextColor(0xff01d0d2);
            m_oTxtIssueUp.setTextColor(0xffb2b2b2);
            m_oTxtBuskers.setTextColor(0xffb2b2b2);
            
            m_oLineLineUp.setBackgroundColor(0xff01d0d2);
            m_oLineIssueUp.setBackgroundColor(0xffffffff);
            m_oLineBuskers.setBackgroundColor(0xffffffff);
            setCurrentInflateItem(0);
            break;
        case R.id.top_menu2:
        	m_oTxtLineUp.setTextColor(0xffb2b2b2);
            m_oTxtIssueUp.setTextColor(0xff01d0d2);
            m_oTxtBuskers.setTextColor(0xffb2b2b2);
            m_oLineLineUp.setBackgroundColor(0xffffffff);
            m_oLineIssueUp.setBackgroundColor(0xff01d0d2);
            m_oLineBuskers.setBackgroundColor(0xffffffff);
            setCurrentInflateItem(1);
            break;
        case R.id.top_menu3:
        	m_oTxtLineUp.setTextColor(0xffb2b2b2);
            m_oTxtIssueUp.setTextColor(0xffb2b2b2);
            m_oTxtBuskers.setTextColor(0xff01d0d2);
            m_oLineLineUp.setBackgroundColor(0xffffffff);
            m_oLineIssueUp.setBackgroundColor(0xffffffff);
            m_oLineBuskers.setBackgroundColor(0xff01d0d2);
            setCurrentInflateItem(2);
            break;
        }
    }
    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();
        clearUiResource();
        if(getActivity() != null)
        {
	        Util.recursiveRecycle(((ViewGroup) getActivity().findViewById(R.id.content_frame)), false);
			Util.unbindDrawables(((ViewGroup) getActivity().findViewById(R.id.content_frame)));
	        ((ViewGroup) getActivity().findViewById(R.id.content_frame)).removeAllViews();
        }
		System.gc();
    }
    public void clearUiResource()
    {
    	m_oBtnList = null;
    	m_oBtnTest  = null;
    	 
         m_oBtnLineUp = null;
         m_oBtnIssueUp = null;
         m_oBtnBuskers = null;
         
         m_oTxtLineUp = null;
         m_oTxtIssueUp= null;
         m_oTxtBuskers = null;
         
         m_oLineLineUp = null;
         m_oLineIssueUp= null;
         m_oLineBuskers = null;
    }
    public void setTime(String year, String mon, String day,String week, String ampm, String hour,String min)
    {
    	this.year= year;
    	this.mon = mon;
    	this.day = day;
    	this.week = week;
    	this.ampm = ampm;
    	this.hour = hour;
    	this.min = min;
    }
    public void twoStep()
    {
    	m_oTxtLineUp.setTextColor(0xffb2b2b2);
        m_oTxtIssueUp.setTextColor(0xff01d0d2);
        m_oTxtBuskers.setTextColor(0xffb2b2b2);
        m_oLineLineUp.setBackgroundColor(0xffffffff);
        m_oLineIssueUp.setBackgroundColor(0xff01d0d2);
        m_oLineBuskers.setBackgroundColor(0xffffffff);
        setCurrentInflateItem(1);
    }
    
}
