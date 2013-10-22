package com.cultureshock.buskingbook.component;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.cultureshock.buskingbook.page.FourPage;
import com.cultureshock.buskingbook.page.MainHomeFragment;
import com.cultureshock.buskingbook.page.Main_LineUp_Page;
import com.cultureshock.buskingbook.page.ThreePage;
import com.cultureshock.buskingbook.page.Main_issue_Page;

public class ViewPagerAdapter extends PagerAdapter implements
        View.OnClickListener {
    // private AsyncImageLoader m_oAsyncImageLoader = new AsyncImageLoader();
    public static final int NUM_ITEMS = 2;
    private Context m_Context = null;
    private LayoutInflater inflater = null;
    private View v = null;

    // public ArrayList<TeamObject> m_oTeamObject = null;

    public ViewPagerAdapter() {
        super();
    }

    public ViewPagerAdapter(Context context) {
        super();
        m_Context = context;
        inflater = (LayoutInflater) m_Context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // public void setTeamObjectPagerData(ArrayList<TeamObject> datas)
    // {
    // m_oTeamObject = datas;
    // }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public void destroyItem(View pager, int position, Object view) {
        // TODO Auto-generated method stub
        ((ViewPager) pager).removeView((View) view);
    }

    // ??????��????��????��?
    @Override
    public Object instantiateItem(View pager, int position) {
        // TODO Auto-generated method stub
        final int pos = position;
        if (position == 0) {
            // v = inflater.inflate(R.layout.one, null);
            v = new Main_LineUp_Page(m_Context);
            MainHomeFragment.getInstance().setTitle("라인 UP");
//            Log.d("0", "0");
        } else if (position == 1) {
            // v = inflater.inflate(R.layout.two, null);
            v = new Main_issue_Page(m_Context);
//            MainHomeFragment.getInstance().setTitle("이슈 UP");
        }
//            Log.d("1", "1");
//        } else if (position == 2) {
//            // v = inflater.inflate(R.layout.three, null);
//            v = new ThreePage(m_Context);
//            Log.d("2", "2");
//        } else if (position == 3) {
//            // v = inflater.inflate(R.layout.four, null);
//            v = new FourPage(m_Context);
//            Log.d("3", "3");
//        }

        // mainImg.setTag(R.id.imageId, position);
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
