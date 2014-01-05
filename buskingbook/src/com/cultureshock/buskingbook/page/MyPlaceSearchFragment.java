package com.cultureshock.buskingbook.page;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.drawable.Drawable;
import android.location.Location;
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
import com.cultureshock.buskingbook.list.LineUpListView;
import com.cultureshock.buskingbook.main.LeftMenuFragment;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.ArticleObject;
import com.cultureshock.buskingbook.object.LineUpObject;
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

public class MyPlaceSearchFragment extends Fragment implements View.OnClickListener, HttpClientNet.OnResponseListener{
    private FragmentActivity mContext;
    private ImageView m_oBtnList;
    private GoogleMap map2;
    private ArrayList<LineUpObject> lineUpArr = new ArrayList<LineUpObject>();
	private SupportMapFragment mMapFragment;
	Location myplace =null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.myplace, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        
        MainActivity.getInstance().offBottom();
        
        m_oBtnList = (ImageView) getActivity()
                .findViewById(R.id.title_btn_menu);
        m_oBtnList.setOnClickListener(this);
//        map2 = ((SupportMapFragment) getActivity().getSupportFragmentManager()
//                .findFragmentById(R.id.map2)).getMap();
        requestTimeTable();
    	if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) == ConnectionResult.SUCCESS) {
            setUpMapIfNeeded();
        }
    }
    public void requestTimeTable() {
		GregorianCalendar calendar = new GregorianCalendar();
		HttpClientNet loginService = new HttpClientNet(
				ServiceType.MSG_TIME_TABLE);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("type", Main_LineUp_Page.TIME_TABLE_MONTH));
		loginParams.add(new Params("year", calendar.get(Calendar.YEAR) + ""));
		loginParams.add(new Params("month", (calendar.get(Calendar.MONTH) + 1)
				+ ""));
		loginParams.add(new Params("day", calendar.get(Calendar.DATE) + ""));
		loginParams.add(new Params("place", ""));
		loginParams.add(new Params("teamname", ""));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		MainActivity.getInstance().startProgressDialog();
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
    public void setting()
    {
    	map2.setMyLocationEnabled(true);
        myplace = map2.getMyLocation();
        CameraUpdate update = null;
        if(myplace == null)
        {
        	 update = CameraUpdateFactory.newLatLng(
                     new LatLng(37.481036785092684,127.01778419315814));
        }
        else
        {
        	 update = CameraUpdateFactory.newLatLng(
                     new LatLng(myplace.getLatitude(), myplace.getLongitude()));
        }
       
        // 위도,경도
        map2.moveCamera(update);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(13);
        map2.animateCamera(zoom);
        for(int i = 0 ; i<lineUpArr.size(); i++)
        {
        	MarkerOptions marker = new MarkerOptions().position(
                    new LatLng(Double.parseDouble(lineUpArr.get(i).getMapX()),Double.parseDouble(lineUpArr.get(i).getMapY())));
                    marker.title(lineUpArr.get(i).getTeamName());
        		marker.snippet(lineUpArr.get(i).getTime());
        		map2.addMarker(marker);
        		
        }
    	
    }
    @Override
    public void onResume()
    {
        super.onResume();
        setUpMapIfNeeded();     
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
    @Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		try {
			Object o = resContent;
			JSONObject object = new JSONObject(resContent);
			GregorianCalendar calendar = new GregorianCalendar();
			if (object.getJSONObject("result").optString("type")
					.equals("ServiceType.MSG_TIME_TABLE")) {
				JSONArray todayTime = object.getJSONArray("data");
				for (int i = 0; i < todayTime.length(); i++) {
					JSONObject jsonObject = todayTime.getJSONObject(i);
					String year = jsonObject.optString("year");
					String month = jsonObject.optString("month");
					String day = jsonObject.optString("day");
					String time = jsonObject.optString("time");
					String dayOfweek = jsonObject.optString("dayOfweek");
					String place = jsonObject.optString("place");
					String teamname = jsonObject.optString("teamname");
					String joincount = jsonObject.optString("joincount");
					String mapX = jsonObject.optString("mapX");
					String mapY = jsonObject.optString("mapY");
//					if(myplace.getLatitude()+1>=Double.parseDouble(mapX))
//					{
//						
//					}
//					else if(myplace.getLatitude()-1<=Double.parseDouble(mapX) )
//					{
//						
//					}
//					else
//					{
//						continue;
//					}
//					if(myplace.getLongitude()+1>=Double.parseDouble(mapY))
//					{
//						
//					}
//					else if(myplace.getLongitude()-1<=Double.parseDouble(mapY) )
//					{
//						
//					}
//					else
//					{
//						continue;
//					}
					String title = jsonObject.optString("title");
					String content = jsonObject.optString("content");
					if (Integer.parseInt(year) == calendar.get(Calendar.YEAR)) {
						if (Integer.parseInt(month) == calendar
								.get(Calendar.MONTH) + 1) {
							if (Integer.parseInt(day) >= calendar
									.get(Calendar.DATE)) {
								lineUpArr.add(new LineUpObject(year, month,
										day, time, dayOfweek, place, teamname,
										joincount,mapX,mapY,title,content));
							}
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			MainActivity.getInstance().stopProgressDialog();
		}
	}
}
