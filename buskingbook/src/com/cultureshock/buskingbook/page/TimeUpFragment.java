package com.cultureshock.buskingbook.page;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cultureshock.buskingbook.FirstStartActivity;
import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.component.ViewPagerAdapter;
import com.cultureshock.buskingbook.main.LeftMenuFragment;
import com.cultureshock.buskingbook.main.LoginJoinActivity;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
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
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;

public class TimeUpFragment extends Fragment implements OnClickListener, HttpClientNet.OnResponseListener{
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
    
    
    private RelativeLayout one;
    private RelativeLayout two;
    private RelativeLayout three;
    
    
    // one
    private LinearLayout m_oBtnPMAM;
    /*
     * version 1
     */
	private LinearLayout m_oBtnConfirm;
	private boolean pmamcheck= false;
	private TextView m_oTimeYear;
	private TextView m_oTimeMonth;
	private int[] timeTable = {R.id.calendar_txt_1,R.id.calendar_txt_2,R.id.calendar_txt_3,R.id.calendar_txt_4,R.id.calendar_txt_5,R.id.calendar_txt_6,R.id.calendar_txt_7,R.id.calendar_txt_8,
			R.id.calendar_txt_9,R.id.calendar_txt_10,R.id.calendar_txt_11,R.id.calendar_txt_12,R.id.calendar_txt_13,R.id.calendar_txt_14,R.id.calendar_txt_15,R.id.calendar_txt_16,R.id.calendar_txt_17,
			R.id.calendar_txt_18,R.id.calendar_txt_19,R.id.calendar_txt_20,R.id.calendar_txt_21,R.id.calendar_txt_22,R.id.calendar_txt_23,R.id.calendar_txt_24,R.id.calendar_txt_25,R.id.calendar_txt_26,
			R.id.calendar_txt_27,R.id.calendar_txt_28,R.id.calendar_txt_29,R.id.calendar_txt_30,R.id.calendar_txt_31,R.id.calendar_txt_32,R.id.calendar_txt_33,R.id.calendar_txt_34,R.id.calendar_txt_35,
			R.id.calendar_txt_36,R.id.calendar_txt_37,R.id.calendar_txt_38,R.id.calendar_txt_39,R.id.calendar_txt_40,R.id.calendar_txt_41,R.id.calendar_txt_42};
	
	private int[] timeTableLayout = {R.id.calendar_1,R.id.calendar_2,R.id.calendar_3,R.id.calendar_4,R.id.calendar_5,R.id.calendar_6,R.id.calendar_7,R.id.calendar_8,
			R.id.calendar_9,R.id.calendar_10,R.id.calendar_11,R.id.calendar_12,R.id.calendar_13,R.id.calendar_14,R.id.calendar_15,R.id.calendar_16,R.id.calendar_17,
			R.id.calendar_18,R.id.calendar_19,R.id.calendar_20,R.id.calendar_21,R.id.calendar_22,R.id.calendar_23,R.id.calendar_24,R.id.calendar_25,R.id.calendar_26,
			R.id.calendar_27,R.id.calendar_28,R.id.calendar_29,R.id.calendar_30,R.id.calendar_31,R.id.calendar_32,R.id.calendar_33,R.id.calendar_34,R.id.calendar_35,
			R.id.calendar_36,R.id.calendar_37,R.id.calendar_38,R.id.calendar_39,R.id.calendar_40,R.id.calendar_41,R.id.calendar_42};
	
	private int[] count = { 0, 0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0, 0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0, 0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0, 0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0, 0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0,0,0,0,0,0,0,0};

	private ArrayList<LineUpObject> timeTableObjectList = new ArrayList<LineUpObject>();
	
	private TextView m_oBtnPrev;
	private TextView m_oBtnNext;
	private int startDay;
	private int endDay;
	private int saveEndDay;
	private int year;
	private int month;
	private EditText m_oEditTextDay;
	
	private String hour="";
	private String min="";
	private String ampm="PM";
	private EditText m_oEditTextHour;
	private EditText m_oEditTextMin;

	private String buskerTime ;
	private String buskerPlace;
	private GregorianCalendar today = new GregorianCalendar(); 

	private String first="";
	
	private LinearLayout hourUp;
	private LinearLayout hourDown;
	private LinearLayout minUp;
	private LinearLayout minDown;
	
	private LinearLayout m_oBtnTwoStepNext;
	private LinearLayout m_oBtnFinish;
	
	//구글맵 
	private SupportMapFragment mMapFragment;
	private GoogleMap mGoogleMap;
	private double mapX=0.0;
	private double mapY=0.0;
	
	//꾸미기 
	private SupportMapFragment mMapFragment2;
	private GoogleMap map2;
	private EditText m_oEditTextTitle;
	private EditText m_oEditTextContent;
	private ImageView m_oImgPoster;
	private TextView m_oTxtCalender;
	private TextView m_oTxtCalender2;
	private String realYear;
	private String realMonth;
	private String realToday;
	private String realWeek;
	private AsyncImageLoader m_oAsyncImageLoader = new AsyncImageLoader();
	private ArrayList<String> detailPlace = new ArrayList<String>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.time_join, container, false);
        
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
        MainActivity.getInstance().offBottom();
        m_oBtnTest = (TextView) getActivity().findViewById(R.id.test_main);
        m_oBtnTest.setOnClickListener(this);
        m_oBtnList = (LinearLayout) getActivity()
                .findViewById(R.id.title_btn_menu);
        m_oBtnList.setOnClickListener(this);
        
        m_oBtnTwoStepNext = (LinearLayout)getActivity().findViewById(R.id.popup_modify_myalbum_btn_confirm2);
        m_oBtnTwoStepNext.setOnClickListener(this);
        m_oBtnFinish = (LinearLayout)getActivity().findViewById(R.id.popup_modify_myalbum_btn_confirm3);
        m_oBtnFinish.setOnClickListener(this);
        one = (RelativeLayout) getActivity().findViewById(R.id.time_first);
        two = (RelativeLayout) getActivity().findViewById(R.id.time_two);
        three = (RelativeLayout)getActivity().findViewById(R.id.time_three);
        
        m_oBtnLineUp = (LinearLayout) getActivity().findViewById(R.id.top_menu1);
        m_oBtnIssueUp = (LinearLayout) getActivity().findViewById(R.id.top_menu2);
        m_oBtnBuskers = (LinearLayout) getActivity().findViewById(R.id.top_menu3);
//        m_oBtnLineUp.setOnClickListener(this);
//        m_oBtnIssueUp.setOnClickListener(this);
//        m_oBtnBuskers.setOnClickListener(this);
        
        m_oTxtLineUp = (TextView) getActivity().findViewById(R.id.top_menu1_text);
        m_oTxtIssueUp = (TextView) getActivity().findViewById(R.id.top_menu2_text);
        m_oTxtBuskers = (TextView) getActivity().findViewById(R.id.top_menu3_text);
        
        m_oLineLineUp = (LinearLayout) getActivity().findViewById(R.id.top_line_menu1);
        m_oLineIssueUp = (LinearLayout) getActivity().findViewById(R.id.top_line_menu2);
        m_oLineBuskers = (LinearLayout) getActivity().findViewById(R.id.top_line_menu3);

        hourUp = (LinearLayout)getActivity().findViewById(R.id.si_up);
		hourDown = (LinearLayout)getActivity().findViewById(R.id.si_down);
		minUp = (LinearLayout)getActivity().findViewById(R.id.min_up);
		minDown = (LinearLayout)getActivity().findViewById(R.id.min_down);
		minUp.setOnClickListener(this);
		minDown.setOnClickListener(this);
		hourUp.setOnClickListener(this);
		hourDown.setOnClickListener(this);
		m_oBtnPMAM = (LinearLayout)getActivity().findViewById(R.id.text_ampm); 
		m_oBtnPMAM.setOnClickListener(this);
    	m_oBtnConfirm = (LinearLayout)getActivity().findViewById(R.id.popup_modify_myalbum_btn_confirm);
    	m_oBtnConfirm.setOnClickListener(this);
    	m_oTimeYear = (TextView)getActivity().findViewById(R.id.time_table_year);
		m_oTimeMonth = (TextView)getActivity().findViewById(R.id.time_table_month);
		m_oBtnPrev = (TextView)getActivity().findViewById(R.id.time_table_prev);
		m_oBtnNext = (TextView)getActivity().findViewById(R.id.time_table_next);
		m_oEditTextHour = (EditText)getActivity().findViewById(R.id.time_join_time_hour);
		m_oEditTextMin = (EditText)getActivity().findViewById(R.id.time_join_time_min);
		m_oEditTextDay = (EditText)getActivity().findViewById(R.id.time_join_calendar);
		m_oEditTextHour.setEnabled(false);
		m_oEditTextMin.setEnabled(false);
		m_oEditTextDay.setEnabled(false);
		m_oBtnNext.setOnClickListener(this);
		m_oBtnPrev.setOnClickListener(this);
		if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) == ConnectionResult.SUCCESS) {
            setUpMapIfNeeded();
        }
        
        
        m_oEditTextTitle = (EditText)getActivity().findViewById(R.id.time_three_title);
    	m_oEditTextContent = (EditText)getActivity().findViewById(R.id.time_three_content);
    	m_oImgPoster = (ImageView)getActivity().findViewById(R.id.time_three_img);
    	m_oTxtCalender = (TextView)getActivity().findViewById(R.id.time_three_calendar);
    	m_oTxtCalender2 = (TextView)getActivity().findViewById(R.id.time_three_calendar2);
		setValue();
    }
    private void setUpMapIfNeeded() {

        if (mMapFragment == null) {

            mMapFragment = SupportMapFragment.newInstance();
            FragmentTransaction fragmentTransaction =
                            getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.map, mMapFragment);
            fragmentTransaction.commit(); 
            if(mMapFragment2 ==null){
            mMapFragment2 = SupportMapFragment.newInstance();
            FragmentTransaction fragmentTransaction2 =
                            getFragmentManager().beginTransaction();
            fragmentTransaction2.replace(R.id.map2, mMapFragment2);
            fragmentTransaction2.commit(); 
            }
            
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                	setUpMap();
                }
            }, 1000);
        }
    }

    private void setUpMap() {       

    	mGoogleMap = mMapFragment.getMap(); 
    	map2 = mMapFragment2.getMap(); 
     // 보기 좋게 확대 zoom 하기
        mGoogleMap.setMyLocationEnabled(true);
        Location myplace = mGoogleMap.getMyLocation();
        CameraUpdate update = null;
        if(myplace == null)
        {
        	 update = CameraUpdateFactory.newLatLng(
                     new LatLng(37.55827526286578, 126.98209136724472));
        }
        else
        {
        	 update = CameraUpdateFactory.newLatLng(
                     new LatLng(myplace.getLatitude(), myplace.getLongitude()));
        }
       
        // 위도,경도
        mGoogleMap.moveCamera(update);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);
        mGoogleMap.animateCamera(zoom);
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                // TODO Auto-generated method stub
            	MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(point.latitude, point.longitude)).title("");
            	mapX = point.latitude;
            	mapY = point.longitude;
            	Log.d("x",point.latitude+"");
            	Log.d("y",point.longitude+"");
            	if(mapX!=0 && mapY!=0)
            	{
            		mGoogleMap.clear();
            		mGoogleMap.addMarker(marker);
            	}
            	else
            	{
            		mGoogleMap.addMarker(marker);
            	}
                
            }
        });
        // map is null!

    }
    public void setValue()
	{
		GregorianCalendar calendar = new GregorianCalendar();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH)+1;
		setDate(year,month);
//		requestMonth(year,month);
	}
    public void setTitle(String txt)
    {
    	m_oBtnTest.setText(txt);
    }
  

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public synchronized void onClick(View v) {
        switch (v.getId()) {
        case R.id.popup_modify_myalbum_btn_confirm2 :
        {
        	if(mapX==0.0 || mapY==0.0)
        	{
        		Toast.makeText(mContext, "장소를 선택해주세요!", Toast.LENGTH_SHORT ).show();
        	}
        	else
        	{
        		threeStep();
        	}
        	break;
        }
        case R.id.popup_modify_myalbum_btn_confirm3 :
        {
        	if(m_oEditTextDay.getText().toString().equals(""))
			{
				Toast.makeText(mContext, "날짜를 선택해주세요", Toast.LENGTH_SHORT ).show();
				break;
			}
			else if(hour.equals("")||min.equals(""))
			{
				Toast.makeText(mContext, "시간을 입력해주세요", Toast.LENGTH_SHORT ).show();
				break;
			}
			else if(mapX==0.0 || mapY==0.0)
        	{
        		Toast.makeText(mContext, "장소를 선택해주세요!", Toast.LENGTH_SHORT ).show();
        		break;
        	}
			else if(m_oEditTextTitle.getText().toString().equals(""))
			{
				Toast.makeText(mContext, "공연제목을 입력해주세요!", Toast.LENGTH_SHORT ).show();
        		break;
			}
			else if(m_oEditTextContent.getText().toString().equals(""))
			{
				Toast.makeText(mContext, "공연내용을 설명해주세요!", Toast.LENGTH_SHORT ).show();
        		break;
			}
			else
			{
				if(this.today.get(Calendar.YEAR) > Integer.parseInt(realYear))
				{
					Toast.makeText(mContext, "지난날짜는 등록이 안됩니다.", Toast.LENGTH_SHORT ).show();
					break;
				}
				else if(this.today.get(Calendar.YEAR) == Integer.parseInt(realYear))
				{
					if(this.today.get(Calendar.MONTH)+1 > Integer.parseInt(realMonth))
					{
						Toast.makeText(mContext, "지난날짜는 등록이 안됩니다.", Toast.LENGTH_SHORT ).show();
						break;
					}
					else if(this.today.get(Calendar.MONTH)+1 == Integer.parseInt(realMonth))
					{
						if(this.today.get(Calendar.DATE) > Integer.parseInt(realToday))
						{
							Toast.makeText(mContext, "지난날짜는 등록이 안됩니다.", Toast.LENGTH_SHORT ).show();
							break;
						}
					}
				}
			}
        	String p = null;
        	try{
        	p = detailPlace.get(2)+detailPlace.get(1);
        	}
        	catch(Exception e)
        	{
        		p = detailPlace.get(1)+detailPlace.get(0);
        		e.printStackTrace();
        	}
        	if(Integer.parseInt(hour)<10)
        	{
        		hour = "0"+hour;
        	}
        	if(Integer.parseInt(min)<10)
        	{
        		min = "0"+min;
        	}
        	requestAddCalendar(realYear,realMonth,realToday,hour+":"+min+" "+ampm,realWeek,p,LoginInfoObject.getInstance().getMyteam(),mapX,mapY );
        	break;
        }
        case R.id.si_up :
        {
        	if(m_oEditTextHour.getText().toString().equals(""))
        	{
        		m_oEditTextHour.setText(1+"");
        		hour = 1+"";
        	}
        	else
        	{
	        	int count = Integer.parseInt(m_oEditTextHour.getText().toString());
	        	count ++;
	        	if(count>12)
	        	{
	        		count = 0;
	        		String c = count+"";
	        		m_oEditTextHour.setText("");
	        		m_oEditTextHour.setText(c);
	        	}
	        	else
	        	{
	        		String c = count+"";
	        		m_oEditTextHour.setText("");
	        		m_oEditTextHour.setText(c);
	        	}
	        	hour = count+"";
        	}
        	break;
        }
        case R.id.si_down :
        {
        	if(m_oEditTextHour.getText().toString().equals(""))
        	{
        		m_oEditTextHour.setText(1+"");
        		hour = 1+"";
        	}
        	else
        	{
	        	int count = Integer.parseInt(m_oEditTextHour.getText().toString());
	        	count --;
	        	if(count<0)
	        	{
	        		break;
	        	}
	        	else
	        	{
	        		String c = count+"";
	        		m_oEditTextHour.setText("");
	        		m_oEditTextHour.setText(c);
	        	}
	        	hour = count+"";
        	}
        	break;
        }
        case R.id.min_up :
        {
        	if(m_oEditTextMin.getText().toString().equals(""))
        	{
        		m_oEditTextMin.setText(00+"");
        		min = 0+"";
        	}
        	else
        	{
	        	int count = Integer.parseInt(m_oEditTextMin.getText().toString());
	        	count = count + 15;
	        	if(count>=60)
	        	{
	        		count = 0;
	        		String c = count+"";
	        		m_oEditTextMin.setText("");
	        		m_oEditTextMin.setText(c);
	        	}
	        	else
	        	{
	        		String c = count+"";
	        		m_oEditTextMin.setText("");
	        		m_oEditTextMin.setText(c);
	        	}
	        	min = count+"";
        	}
        	break;
        }
        case R.id.min_down :
        {
        	if(m_oEditTextMin.getText().toString().equals(""))
        	{
        		m_oEditTextMin.setText(00+"");
        		min = 0+"";
        	}
        	else
        	{
	        	int count = Integer.parseInt(m_oEditTextMin.getText().toString());
	        	count = count - 15;
	        	if(count<0)
	        	{
	        		count = 0;
	        		String c = count+"";
	        		m_oEditTextMin.setText("");
	        		m_oEditTextMin.setText(c);
	        	}
	        	else
	        	{
	        		String c = count+"";
	        		m_oEditTextMin.setText("");
	        		m_oEditTextMin.setText(c);
	        	}
	        	min = count+"";
        	}
        	break;
        }
        case R.id.text_ampm :
        {
        	if(pmamcheck)
        	{
        		m_oBtnPMAM.setBackgroundResource(R.drawable.pm2);
        		pmamcheck = false;
        		ampm = "PM";
        	}
        	else
        	{
        		m_oBtnPMAM.setBackgroundResource(R.drawable.am2);
        		pmamcheck = true;
        		ampm = "AM";
        	}
        	break;
        }
        case R.id.popup_modify_myalbum_btn_confirm :
        {
			if(m_oEditTextDay.getText().toString().equals(""))
			{
				Toast.makeText(mContext, "날짜를 선택해주세요", Toast.LENGTH_SHORT ).show();
			}
			else if(hour.equals("")||min.equals(""))
			{
				Toast.makeText(mContext, "시간을 입력해주세요", Toast.LENGTH_SHORT ).show();
			}
			else
			{
				String[] week ={"일", "월", "화", "수", "목", "금", "토"};
				String [] day = m_oEditTextDay.getText().toString().split(",");
				String [] year = day[0].split("년");
				String [] month = day[1].split("월");
				String [] today = day[2].split("일");
				year[0] = year[0].trim();
				month[0] = month[0].trim();
				today[0] = today[0].trim();
				
				if(this.today.get(Calendar.YEAR) > Integer.parseInt(year[0]))
				{
					Toast.makeText(mContext, "지난날짜는 등록이 안됩니다.", Toast.LENGTH_SHORT ).show();
					break;
				}
				else if(this.today.get(Calendar.YEAR) == Integer.parseInt(year[0]))
				{
					if(this.today.get(Calendar.MONTH)+1 > Integer.parseInt(month[0]))
					{
						Toast.makeText(mContext, "지난날짜는 등록이 안됩니다.", Toast.LENGTH_SHORT ).show();
						break;
					}
					else if(this.today.get(Calendar.MONTH)+1 == Integer.parseInt(month[0]))
					{
						if(this.today.get(Calendar.DATE) > Integer.parseInt(today[0]))
						{
							Toast.makeText(mContext, "지난날짜는 등록이 안됩니다.", Toast.LENGTH_SHORT ).show();
							break;
						}
					}
				}
				GregorianCalendar calendar = new GregorianCalendar(Integer.parseInt(year[0]),Integer.parseInt(month[0]),Integer.parseInt(today[0])); //해당 월 의 첫날 
				int dayOfweek = calendar.get(Calendar.DAY_OF_WEEK) - 1; // 첫날의 요일 결정
				realYear = year[0];
				realMonth = month[0];
				realToday = today[0];
				realWeek = week[dayOfweek];
				TimeUpFragment.getInstance().twoStep();
//				requestAddCalendar(year[0],month[0],today[0],hour+":"+min+" "+ampm,week[dayOfweek],"",LoginInfoObject.getInstance().getMyteam() );
//				dialog.dismiss();
				
			}
			break;
        }
        case R.id.time_table_prev : 
		{
			
			month --;
			if( month == 0)
			{
				month = 1;
			}
//			if( month == 0 )
//			{
//				year --;
//				month = 12;
//			}
			setDate(year,month);
//			requestMonth(year,month);
			break;
		}
		case R.id.time_table_next : 
		{
			month ++;
			if(month == 13)
			{
				month = 12;
			}
//			if( month == 13)
//			{
//				year ++;
//				month = 1;
//			}
			setDate(year,month);
//			requestMonth(year,month);
			break;
		}
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
            
            one.setVisibility(View.VISIBLE);
            two.setVisibility(View.GONE);
            three.setVisibility(View.GONE);
            break;
        case R.id.top_menu2:
        	m_oTxtLineUp.setTextColor(0xffb2b2b2);
            m_oTxtIssueUp.setTextColor(0xff01d0d2);
            m_oTxtBuskers.setTextColor(0xffb2b2b2);
            m_oLineLineUp.setBackgroundColor(0xffffffff);
            m_oLineIssueUp.setBackgroundColor(0xff01d0d2);
            m_oLineBuskers.setBackgroundColor(0xffffffff);
            
            one.setVisibility(View.GONE);
            two.setVisibility(View.VISIBLE);
            three.setVisibility(View.GONE);
            break;
        case R.id.top_menu3:
        	m_oTxtLineUp.setTextColor(0xffb2b2b2);
            m_oTxtIssueUp.setTextColor(0xffb2b2b2);
            m_oTxtBuskers.setTextColor(0xff01d0d2);
            m_oLineLineUp.setBackgroundColor(0xffffffff);
            m_oLineIssueUp.setBackgroundColor(0xffffffff);
            m_oLineBuskers.setBackgroundColor(0xff01d0d2);
            one.setVisibility(View.GONE);
            two.setVisibility(View.GONE);
            three.setVisibility(View.VISIBLE);
            break;
        }
    }
    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();
        clearUiResource();
        Log.d("여기디스트로이","");
        if(getActivity() != null)
        {
	        Util.recursiveRecycle(((ViewGroup) getActivity().findViewById(R.id.content_frame)), false);
			Util.unbindDrawables(((ViewGroup) getActivity().findViewById(R.id.content_frame)));
	        ((ViewGroup) getActivity().findViewById(R.id.content_frame)).removeAllViews();
	        Util.recursiveRecycle(((ViewGroup) getActivity().findViewById(R.id.map)), false);
			Util.unbindDrawables(((ViewGroup) getActivity().findViewById(R.id.map)));
	        Util.recursiveRecycle(((ViewGroup) getActivity().findViewById(R.id.map2)), false);
			Util.unbindDrawables(((ViewGroup) getActivity().findViewById(R.id.map2)));
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
         m_oBtnConfirm = null;
      	 m_oTimeYear = null;
     	 m_oTimeMonth= null;
     	 m_oBtnPrev = null;
     	 m_oBtnNext = null;
     	 m_oEditTextDay= null;
     	 m_oEditTextHour = null;
     	 if(mGoogleMap!=null)
     		 mGoogleMap.clear();
     	 mGoogleMap = null;
     	 if(map2!=null)
     		 map2.clear();
    	 map2 = null;
    	 m_oEditTextTitle = null;
    	 m_oEditTextContent = null;
    	 m_oImgPoster = null;
    	 m_oTxtCalender = null;
    	 m_oTxtCalender2 = null;
    }
    public void setDate(int Year,int Month)
	{
		try{
		String[] week ={"일", "월", "화", "수", "목", "금", "토"};
		
		for( int i = 0; i < startDay ;i++)
		{
			LinearLayout o = (LinearLayout)getActivity().findViewById(timeTableLayout[i]);
			o.setVisibility(View.VISIBLE);
		}
		// 마지막날 이후 클릭이벤트 없애기
		if( saveEndDay != 0)
		{
			for(int i = saveEndDay+1 ; i <= timeTableLayout.length - 1; i++)
			{
				LinearLayout o = (LinearLayout)getActivity().findViewById(timeTableLayout[i]);
				o.setVisibility(View.VISIBLE);
			}
		}
		
		m_oTimeYear.setText(Year+"년");
		m_oTimeMonth.setText(Month+"월");
		
		GregorianCalendar calendar = new GregorianCalendar(Year,Month-1,1); //해당 월 의 첫날 
		startDay = calendar.get(Calendar.DAY_OF_WEEK) - 1; // 첫날의 요일 결정
		endDay = calendar.getActualMaximum(Calendar.DATE); //마지막 날짜
		for(int i = startDay, j = 1 ; j<= endDay ; i++,j++)
		{
			TextView o = (TextView)getActivity().findViewById(timeTable[i]); //그 첫날부터 1일 ~ 마지막날까지 setText
			o.setText(j+"");
			if(j == endDay)
				saveEndDay = i;
		}
		
		// 첫날 전 날 클릭이벤트 없애기 
		for( int i = 0; i < startDay ;i++)
		{
			LinearLayout o = (LinearLayout)getActivity().findViewById(timeTableLayout[i]);
			o.setVisibility(View.INVISIBLE);
		}
		
		// 마지막날 이후 클릭이벤트 없애기
		for(int i = saveEndDay + 1 ; i <= timeTableLayout.length -1 ;i++)
		{
			LinearLayout o = (LinearLayout)getActivity().findViewById(timeTableLayout[i]);
			o.setVisibility(View.INVISIBLE);
		}
		//글씨색깔
		for ( int i = 0 ; i < 42 ; i++)
		{
			TextView k = (TextView)getActivity().findViewById(timeTable[i]);
			k.setBackgroundColor(0xfffffff);
		}
		//오늘의 날짜에 색깔칠하기 & 버튼이벤트 주기
		for( int i = startDay , j = 1; i <= saveEndDay ; i++, j++)
		{
			LinearLayout o = (LinearLayout)getActivity().findViewById(timeTableLayout[i]);
			o.setTag(R.id.imageId, j);
			//오늘의날짜에 색깔칠하기
			if(Year == today.get(Calendar.YEAR))
			{
				if( Month == today.get(Calendar.MONTH) +1 )
				{
					if(today.get(Calendar.DATE) + startDay - 1 == i)
					{
						LinearLayout k = (LinearLayout)getActivity().findViewById(timeTableLayout[i]);
						k.setBackgroundColor(0xf01d0d2);
					}
				}
				else
				{
					LinearLayout k = (LinearLayout)getActivity().findViewById(timeTableLayout[i]);
					k.setBackgroundResource(R.drawable.c_100px);
				}
			}
			o.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//여기다가 이제 이벤트
					int pos = (Integer)v.getTag(R.id.imageId);
//					TimeTableDetailPopup popup = new TimeTableDetailPopup(mContext);
//					ArrayList<TimeTableObject> listData = new ArrayList<TimeTableObject>();
//					//팝업
//					for(int i = 0 ; i< timeTableObjectList.size() ; i++)
//					{
//						if(timeTableObjectList.get(i).getDay().equals(pos+""))
//						{
//							listData.add(timeTableObjectList.get(i));
//						}
//					}
//					popup.setData(listData);
//					popup.show();
					m_oEditTextDay.setText(year+"년, "+month+"월, "+pos +"일 ");
				}
			});
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
    public void reset()
	{
//		for( int i = 0 ;i < 42 ; i++)
//		{
//			count[i] = 0 ;
//			TextView k = (TextView)v.findViewById(joinCount[i]);
//			k.setText("");
//		}
		if(timeTableObjectList.size() != 0)
		{
			timeTableObjectList.clear();
		}
	}
    public void requestMonth(int year,int month)
	{
		GregorianCalendar calendar = new GregorianCalendar();
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_TIME_TABLE);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("type", Main_LineUp_Page.TIME_TABLE_MONTH));
		loginParams.add(new Params("year", year+""));
		loginParams.add(new Params("month", month+""));
		loginParams.add(new Params("day", calendar.get(Calendar.DATE)+""));
		loginParams.add(new Params("place",""));
		loginParams.add(new Params("teamname",""));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		MainActivity.getInstance().startProgressDialog();
	}
    public void requestAddCalendar(String year, String month , String day,String time, String dayOfweek,String place, String teamname,double mapX, double mapY)
	{
		GregorianCalendar calendar = new GregorianCalendar();
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_TIME_TABLE_SEND);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		buskerPlace = place;
		buskerTime = month+"/"+day+" "+time+" "+dayOfweek;
		loginParams.add(new Params("year", year));
		loginParams.add(new Params("month", month));
		loginParams.add(new Params("day", day));
		loginParams.add(new Params("time", time));
		loginParams.add(new Params("dayOfweek", dayOfweek));
		loginParams.add(new Params("place", place));
		loginParams.add(new Params("teamname",teamname));
		loginParams.add(new Params("mapX",mapX+""));
		loginParams.add(new Params("mapY",mapY+""));
		loginParams.add(new Params("title",m_oEditTextTitle.getText().toString()));
		loginParams.add(new Params("content",m_oEditTextContent.getText().toString()));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		MainActivity.getInstance().startProgressDialog();
		
	}
   
    @Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		try{
			Object o = resContent;
			JSONObject object = new JSONObject(resContent);
			if(!object.isNull("status"))
			{
				if(object.getString("status").equals("OK"))
				{
					JSONArray a = object.getJSONArray("results");
					
					JSONArray t = a.getJSONObject(0).getJSONArray("address_components");
					for(int i=0; i<t.length(); i++)
					{
						JSONObject jsonObject = t.getJSONObject(i);
						String name = jsonObject.optString("long_name");
						detailPlace.add(name);
					}
				}
			}
			
			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_TIME_TABLE"))
			{
//				reset();
//				JSONArray todayTime = object.getJSONArray("data");
//				for(int i=0; i<todayTime.length(); i++)
//				{
//					JSONObject jsonObject = todayTime.getJSONObject(i);
//					String year = jsonObject.optString("year");
//					String month = jsonObject.optString("month");
//					String day = jsonObject.optString("day");
//					String time = jsonObject.optString("time");
//					String dayOfweek = jsonObject.optString("dayOfweek");
//					String place = jsonObject.optString("place");
//					String teamname = jsonObject.optString("teamname");
//					String joincount = jsonObject.optString("joincount");
//					timeTableObjectList.add(new TimeTableObject(year,month,day,time,dayOfweek,place,teamname,joincount));
//					if(this.year == Integer.parseInt(year))
//					{
//						if( this.month == Integer.parseInt(month))
//						{
//							for ( int j = 0 ; j< 42 ; j ++ )
//							{
//								if( ((TextView)findViewById(timeTable[j])).getText().equals(day) )
//								{
//									count[j] ++;
//									break;
//								}
//							}
//						}
//					}
//				}
//				for( int i = 0 ; i< 42 ; i++)
//				{
//					if(count[i] != 0)
//					{
//						TextView k = (TextView)findViewById(joinCount[i]);
//						k.setText("( "+count[i]+" )");
//						k.setTextColor(0xff000000);
//						
//					}
//				}
			}
			else if (object.getJSONObject("result").optString("type").equals("ServiceType.MSG.TIME_TABLE_SEND"))
			{
				Toast.makeText(mContext, "일정을 등록하였습니다", Toast.LENGTH_SHORT );
				requestGcm();
				
				MainActivity.getInstance().onBackPressed();
				LeftMenuFragment.getInstance().loginSatatus();
			}
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			MainActivity.getInstance().stopProgressDialog() ;
		}

	}
    public void requestPlace()
	{
    	String x = mapX+"";
    	String y = mapY+"";
    	String url ="http://maps.googleapis.com/maps/api/geocode/json?latlng="+x+","+y+"&sensor=false&language=ko";
		GregorianCalendar calendar = new GregorianCalendar();
		HttpClientNet loginService = new HttpClientNet(url);
		loginService.doAsyncExecute(this);
		MainActivity.getInstance().startProgressDialog();
	}
	public void requestGcm()
	{
		GregorianCalendar calendar = new GregorianCalendar();
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_GCM_GO_EVENT);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("teamname", LoginInfoObject.getInstance().getMyteam()));
		loginParams.add(new Params("time", buskerTime));
		loginParams.add(new Params("place", buskerPlace));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		MainActivity.getInstance().startProgressDialog();
	}	
	public void twoStep()
    {
    	m_oTxtLineUp.setTextColor(0xff01d0d2);
        m_oTxtIssueUp.setTextColor(0xff01d0d2);
        m_oTxtBuskers.setTextColor(0xffb2b2b2);
        m_oLineLineUp.setBackgroundColor(0xff01d0d2);
        m_oLineIssueUp.setBackgroundColor(0xff01d0d2);
        m_oLineBuskers.setBackgroundColor(0xffffffff);
        one.setVisibility(View.GONE);
        two.setVisibility(View.VISIBLE);
        three.setVisibility(View.GONE);
    }
	public void threeStep()
    {
    	m_oTxtLineUp.setTextColor(0xff01d0d2);
        m_oTxtIssueUp.setTextColor(0xff01d0d2);
        m_oTxtBuskers.setTextColor(0xff01d0d2);
        m_oLineLineUp.setBackgroundColor(0xff01d0d2);
        m_oLineIssueUp.setBackgroundColor(0xff01d0d2);
        m_oLineBuskers.setBackgroundColor(0xff01d0d2);
        one.setVisibility(View.GONE);
        two.setVisibility(View.GONE);
        three.setVisibility(View.VISIBLE);
        
        // 위도,경도
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
    	m_oTxtCalender2.setText(realWeek +"요일 "+ ampm +" "+hour+"시 " + min+"분 " );
    	Drawable default1;
		default1 = getActivity().getResources().getDrawable(R.drawable.default_busker);
		TeamObject object = null;
		for(int i = 0 ; i < MainActivity.getTeamObject().size() ; i++)
		{
			if(LoginInfoObject.getInstance().getMyteam().equals(MainActivity.getTeamObject().get(i).getTeamName()))
			{
				object = MainActivity.getTeamObject().get(i);
				break;
				
			}
		}
		if(object!=null)
		m_oAsyncImageLoader.setImageDrawableAsync(m_oImgPoster,object.getTeamThum(),default1,default1,getActivity());
		requestPlace();
    }
}
