package com.cultureshock.buskingbook.page;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONObject;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.component.ViewPagerAdapter;
import com.cultureshock.buskingbook.list.IssueNewListView;
import com.cultureshock.buskingbook.list.LikeTeamListView;
import com.cultureshock.buskingbook.main.LeftMenuFragment;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.ArticleObject;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.object.TeamObject;
import com.cultureshock.buskingbook.service.ServiceType;
import com.cultureshock.buskingbook.util.AsyncImageLoader;
import com.cultureshock.buskingbook.util.Util;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TimePosterFragment extends Fragment implements View.OnClickListener{
    private FragmentActivity mContext;
    private ImageView m_oBtnList;
    private String teamname;
    private AsyncImageLoader m_oAsyncImageLoader = new AsyncImageLoader();
    private GoogleMap map2;
	private ImageView m_oImgPoster;
	private TextView m_oTxtCalender;
	private TextView m_oTxtCalender2;
	private TextView m_oTxtTitle;
	private TextView m_oTxtContent;
	private double mapX=0.0;
	private double mapY=0.0;
	private String realYear;
	private String realMonth;
	private String realToday;
	private String realWeek;
	private String time;
	private String title;
	private String content;
	private SupportMapFragment mMapFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.time_poster, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        
        MainActivity.getInstance().offBottom();
        Bundle bundle = getArguments();
        if(bundle !=null)
        {
			if( bundle.getString("object")!= null)
			{
				  teamname = bundle.getString("object");
				  realYear = bundle.getString("year");
				  realMonth = bundle.getString("month");
				  realToday = bundle.getString("day");
				  realWeek = bundle.getString("week");
				  String x = bundle.getString("mapX");
				  String y = bundle.getString("mapY");
				  mapX  = Double.parseDouble(x);
				  mapY  = Double.parseDouble(y);
				  time = bundle.getString("time");
				  title = bundle.getString("title");
				  content = bundle.getString("content");
			}
        }
        
        m_oBtnList = (ImageView) getActivity()
                .findViewById(R.id.title_btn_menu);
        m_oBtnList.setOnClickListener(this);
//        map2 = ((SupportMapFragment) getActivity().getSupportFragmentManager()
//                .findFragmentById(R.id.map2)).getMap();
    	m_oImgPoster = (ImageView)getActivity().findViewById(R.id.time_three_img);
    	m_oTxtCalender = (TextView)getActivity().findViewById(R.id.time_three_calendar);
    	m_oTxtCalender2 = (TextView)getActivity().findViewById(R.id.time_three_calendar2);
    	m_oTxtTitle = (TextView)getActivity().findViewById(R.id.time_three_title);
    	m_oTxtContent = (TextView)getActivity().findViewById(R.id.time_three_content);
    	if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) == ConnectionResult.SUCCESS) {
            setUpMapIfNeeded();
        }
    }
    private void setUpMapIfNeeded() {

        if (mMapFragment == null) {

            mMapFragment = SupportMapFragment.newInstance();
            FragmentTransaction fragmentTransaction =
                            getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.map, mMapFragment);
            fragmentTransaction.commit(); 
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                	MainActivity.getInstance().startProgressDialog();
                	setUpMap();
                	MainActivity.getInstance().stopProgressDialog();
                }
            }, 1000);
        }
    }

    private void setUpMap() {       

        map2 = mMapFragment.getMap();  
        setting();
        // map is null!

    }

    @Override
    public void onResume()
    {
        super.onResume();
        setUpMapIfNeeded();     
    }
    public void setting()
    {
    	if(mapX!=0 && mapY!=0)
    	{
    		MarkerOptions marker = new MarkerOptions().position(
                    new LatLng(mapX, mapY)).title("");
    		map2.addMarker(marker);
    		CameraUpdate update = CameraUpdateFactory.newLatLng(
                    new LatLng(mapX, mapY));
    		
    		map2.moveCamera(update);
    	}
    	 
         // 위도,경도
    	CameraUpdate zoom2 = CameraUpdateFactory.zoomTo(15);
		map2.animateCamera(zoom2);
    	m_oTxtCalender.setText(realYear+"년 "+realMonth+"월 "+realToday+"일 "+" ");
    	m_oTxtCalender2.setText(realWeek +"요일 "+ time );
    	Drawable default1;
		default1 = getActivity().getResources().getDrawable(R.drawable.default_busker);
		TeamObject object = null;
		for(int i = 0 ; i < MainActivity.getTeamObject().size() ; i++)
		{
			if(teamname.equals(MainActivity.getTeamObject().get(i).getTeamName()))
			{
				object = MainActivity.getTeamObject().get(i);
				break;
				
			}
		}
		if(object!=null)
		m_oAsyncImageLoader.setImageDrawableAsync(m_oImgPoster,object.getTeamThum(),default1,default1,getActivity());
		m_oTxtTitle.setText(title);
		m_oTxtContent.setText(content);
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
       
    }
    @Override
    public synchronized void onClick(View v) {
        switch (v.getId()) {
        case R.id.title_btn_menu:
            MainActivity.getInstance().showMenu();
            break;
        }
    }
}
