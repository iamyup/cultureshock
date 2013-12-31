package com.cultureshock.buskingbook.component;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.cultureshock.buskingbook.page.Buskers_Page;
import com.cultureshock.buskingbook.page.MainHomeFragment;
import com.cultureshock.buskingbook.page.Main_LineUp_Page;
import com.cultureshock.buskingbook.page.Main_issue_Page;
import com.cultureshock.buskingbook.page.Time_Place_Page;
import com.cultureshock.buskingbook.page.Time_Poster_Page;
import com.cultureshock.buskingbook.page.Time_Select_Page;

public class TimeViewPagerAdapter extends PagerAdapter implements
        View.OnClickListener {
    public static final int NUM_ITEMS = 3;
    private Context m_Context = null;
    private LayoutInflater inflater = null;
    private View v = null;
    private View issueView = null;
    private View mainView = null;

    public TimeViewPagerAdapter() {
        super();
    }

    public TimeViewPagerAdapter(Context context) {
        super();
        m_Context = context;
        inflater = (LayoutInflater) m_Context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public void destroyItem(View collection, int position, Object o) {
    	try{
        View view = (View)o;
        if(view.getClass().getName().equals("com.cultureshock.buskingbook.page.Time_Select_Page"))
        {
        	((Time_Select_Page)view).recycleResoure();
        	((Time_Place_Page)issueView).recycleResoure();
        	Log.d("1",view.getClass().getName());
        }
        else if(view.getClass().getName().equals("com.cultureshock.buskingbook.page.Time_Place_Page"))
        {
        	((Time_Place_Page)view).recycleResoure();
        	Log.d("2",view.getClass().getName());
        }
        else if(view.getClass().getName().equals("com.cultureshock.buskingbook.page.Time_Poster_Page"))
        {
        	((Time_Poster_Page)view).recycleResoure();
        	((Time_Place_Page)issueView).recycleResoure();
        	Log.d("3",view.getClass().getName());
        }
        ((ViewPager) collection).removeView(view);
        view = null;
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    @Override
    public Object instantiateItem(View pager, int position) {
        // TODO Auto-generated method stub
        final int pos = position;
//        viewpagerNum = position;
        if(position == 0)
        {
        	 v = new Time_Select_Page(m_Context);
        	 Log.d("1",v.getClass().getName());
        }
        else if(position == 1)
        {
        	v = new Time_Place_Page(m_Context);
        	issueView = v;
        	
        	Log.d("2",v.getClass().getName());
        }
        else
        {
        	if(mainView != null )
        	((Time_Select_Page)mainView).recycleResoure();
        	if(issueView !=null)
        	((Time_Place_Page)issueView).recycleResoure();
        	 v = new Time_Poster_Page(m_Context);
        }
        ((ViewPager) pager).addView(v, 0);
        return v;
    }

    @Override
    public void finishUpdate(View arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        // TODO Auto-generated method stub
        return view == obj;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public Parcelable saveState() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
        }
    }
}
