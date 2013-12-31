package com.cultureshock.buskingbook.page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.cultureshock.buskingbook.list.BuskerSearchListView;
import com.cultureshock.buskingbook.list.LineUpListView;
import com.cultureshock.buskingbook.main.LeftMenuFragment;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.LineUpObject;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.object.TeamObject;
import com.cultureshock.buskingbook.service.ServiceType;
import com.cultureshock.buskingbook.FirstStartActivity;
import com.cultureshock.buskingbook.R;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class Time_Select_Page extends LinearLayout implements View.OnClickListener, HttpClientNet.OnResponseListener{

	private Context mContext;
	private View v = null;
	private LinearLayout m_oBtnPMAM;
	Dialog dialog;
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
	
//	private int[] joinCount = {R.id.calendar_txt_join_1,R.id.calendar_txt_join_2,R.id.calendar_txt_join_3,R.id.calendar_txt_join_4,R.id.calendar_txt_join_5,R.id.calendar_txt_join_6,R.id.calendar_txt_join_7,R.id.calendar_txt_join_8,
//			R.id.calendar_txt_join_9,R.id.calendar_txt_join_10,R.id.calendar_txt_join_11,R.id.calendar_txt_join_12,R.id.calendar_txt_join_13,R.id.calendar_txt_join_14,R.id.calendar_txt_join_15,R.id.calendar_txt_join_16,R.id.calendar_txt_join_17,
//			R.id.calendar_txt_join_18,R.id.calendar_txt_join_19,R.id.calendar_txt_join_20,R.id.calendar_txt_join_21,R.id.calendar_txt_join_22,R.id.calendar_txt_join_23,R.id.calendar_txt_join_24,R.id.calendar_txt_join_25,R.id.calendar_txt_join_26,
//			R.id.calendar_txt_join_27,R.id.calendar_txt_join_28,R.id.calendar_txt_join_29,R.id.calendar_txt_join_30,R.id.calendar_txt_join_31,R.id.calendar_txt_join_32,R.id.calendar_txt_join_33,R.id.calendar_txt_join_34,R.id.calendar_txt_join_35,
//			R.id.calendar_txt_join_36,R.id.calendar_txt_join_37,R.id.calendar_txt_join_38,R.id.calendar_txt_join_39,R.id.calendar_txt_join_40,R.id.calendar_txt_join_41,R.id.calendar_txt_join_42};
	
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
	private String ampm="";
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
	public Time_Select_Page(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
		mContext = context;
		initView();
	}
	public void recycleResoure ()
	{
		clearUiResource();		
	}
	public void show() {
		dialog.show();
	}

	public void dismissPopup() {
		dialog.dismiss();
	}
	public void setUi()
    {
		hourUp = (LinearLayout)v.findViewById(R.id.si_up);
		hourDown = (LinearLayout)v.findViewById(R.id.si_down);
		minUp = (LinearLayout)v.findViewById(R.id.min_up);
		minDown = (LinearLayout)v.findViewById(R.id.min_down);
		minUp.setOnClickListener(this);
		minDown.setOnClickListener(this);
		hourUp.setOnClickListener(this);
		hourDown.setOnClickListener(this);
		m_oBtnPMAM = (LinearLayout)v.findViewById(R.id.text_ampm); 
		m_oBtnPMAM.setOnClickListener(this);
    	m_oBtnConfirm = (LinearLayout)v.findViewById(R.id.popup_modify_myalbum_btn_confirm);
    	m_oBtnConfirm.setOnClickListener(this);
    	m_oTimeYear = (TextView)v.findViewById(R.id.time_table_year);
		m_oTimeMonth = (TextView)v.findViewById(R.id.time_table_month);
		m_oBtnPrev = (TextView)v.findViewById(R.id.time_table_prev);
		m_oBtnNext = (TextView)v.findViewById(R.id.time_table_next);
		m_oEditTextHour = (EditText)v.findViewById(R.id.time_join_time_hour);
//		InputFilter filterAlphaNum = new InputFilter() {
//
//			@Override
//			public CharSequence filter(CharSequence source, int start, int end,
//					Spanned dest, int dstart, int dend) {
//				try{
//					Integer.parseInt(source.toString());
//					if(first.equals("")||m_oEditTextHour.getText().toString().equals("") )
//						first = source.toString();
//					if(m_oEditTextHour.getText().toString().equals(""))
//					{
//						return null;
//					}
//					if(Integer.parseInt(first) > 1)
//					{
//						if(Integer.parseInt(source.toString())>=0)
//							return "";
//					}
//					else if(Integer.parseInt(first) == 1)
//					{
//						if(Integer.parseInt(source.toString())>2 )
//							return "";
//					}
//					// TODO Auto-generated method stub
//					return null;
//				}
//				catch(Exception e)
//				{
//					e.printStackTrace();
//				}
//				return null;
//				
//			} 
//		}; 
//		InputFilter[] FilterArray = new InputFilter[2];
//		FilterArray[0] = new InputFilter.LengthFilter(2);
//		FilterArray[1] = filterAlphaNum;
//		m_oEditTextHour.setFilters(FilterArray);
		
		m_oEditTextMin = (EditText)v.findViewById(R.id.time_join_time_min);
		m_oEditTextDay = (EditText)v.findViewById(R.id.time_join_calendar);
		m_oEditTextHour.setEnabled(false);
		m_oEditTextMin.setEnabled(false);
		m_oEditTextDay.setEnabled(false);
		m_oBtnNext.setOnClickListener(this);
		m_oBtnPrev.setOnClickListener(this);
    }
    
//    private OnItemSelectedListener selectedListener2 = new OnItemSelectedListener() 
//    {
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) 
//        {
//            // TODO Auto-generated method stub
//            if ( m_FlagHour.size() > 0 )
//            {
//            	hour = m_oEditTextHour.getSelectedItem().toString();
//            	if(Integer.parseInt(hour) > 12)
//            	{
//            		ampm = "PM";
//            		hour = (Integer.parseInt(hour)-12)+"";
//            	}
//            	else
//            	{
//            		ampm = "AM";
//            	}
//            }
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> arg0) 
//        {
//            // TODO Auto-generated method stub
//
//        }
//    };
//    private OnItemSelectedListener selectedListener3 = new OnItemSelectedListener() 
//    {
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) 
//        {
//            // TODO Auto-generated method stub
//            if ( m_FlagMin.size() > 0 )
//            {
//            	min = m_oEditTextMin.getSelectedItem().toString();
//            }
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> arg0) 
//        {
//            // TODO Auto-generated method stub
//
//        }
//    };
	public void setValue()
	{
		GregorianCalendar calendar = new GregorianCalendar();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH)+1;
		setDate(year,month);
//		requestMonth(year,month);
	}
	private void initView() {
		// TODO Auto-generated method stub

		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(R.layout.time_join, null);
		this.addView(v);
		setUi();
        setValue();
        MainActivity.getInstance().offBottom();

	}
	@Override
    public synchronized void onClick(View v) 
    {
        switch (v.getId()) 
        {
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
		        	
	        	}
	        	hour = count+"";
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
	        	}
	        	hour = count+"";
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
	        	}
	        	min = count+"";
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
	        	}
	        	min = count+"";
	        	break;
	        }
	        case R.id.text_ampm :
	        {
	        	if(pmamcheck)
	        	{
	        		m_oBtnPMAM.setBackgroundResource(R.drawable.pm2);
	        		pmamcheck = false;
	        		ampm = "pm";
	        	}
	        	else
	        	{
	        		m_oBtnPMAM.setBackgroundResource(R.drawable.am2);
	        		pmamcheck = true;
	        		ampm = "am";
	        	}
	        	break;
	        }
	        case R.id.title_btn_menu:
	            MainActivity.getInstance().showMenu();
	            break;
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
					TimeUpFragment.getInstance().setTime(year[0], month[0], today[0], week[dayOfweek], ampm, hour, min);
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
        }

    }
    
    public void setDate(int Year,int Month)
	{
		try{
		String[] week ={"일", "월", "화", "수", "목", "금", "토"};
		
		for( int i = 0; i < startDay ;i++)
		{
			LinearLayout o = (LinearLayout)v.findViewById(timeTableLayout[i]);
			o.setVisibility(View.VISIBLE);
		}
		// 마지막날 이후 클릭이벤트 없애기
		if( saveEndDay != 0)
		{
			for(int i = saveEndDay+1 ; i <= timeTableLayout.length - 1; i++)
			{
				LinearLayout o = (LinearLayout)v.findViewById(timeTableLayout[i]);
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
			TextView o = (TextView)v.findViewById(timeTable[i]); //그 첫날부터 1일 ~ 마지막날까지 setText
			o.setText(j+"");
			if(j == endDay)
				saveEndDay = i;
		}
		
		// 첫날 전 날 클릭이벤트 없애기 
		for( int i = 0; i < startDay ;i++)
		{
			LinearLayout o = (LinearLayout)v.findViewById(timeTableLayout[i]);
			o.setVisibility(View.INVISIBLE);
		}
		
		// 마지막날 이후 클릭이벤트 없애기
		for(int i = saveEndDay + 1 ; i <= timeTableLayout.length -1 ;i++)
		{
			LinearLayout o = (LinearLayout)v.findViewById(timeTableLayout[i]);
			o.setVisibility(View.INVISIBLE);
		}
		//글씨색깔
		for ( int i = 0 ; i < 42 ; i++)
		{
			TextView k = (TextView)v.findViewById(timeTable[i]);
			k.setBackgroundColor(0xfffffff);
		}
		//오늘의 날짜에 색깔칠하기 & 버튼이벤트 주기
		for( int i = startDay , j = 1; i <= saveEndDay ; i++, j++)
		{
			LinearLayout o = (LinearLayout)v.findViewById(timeTableLayout[i]);
			o.setTag(R.id.imageId, j);
			//오늘의날짜에 색깔칠하기
			if(Year == today.get(Calendar.YEAR))
			{
				if( Month == today.get(Calendar.MONTH) +1 )
				{
					if(today.get(Calendar.DATE) + startDay - 1 == i)
					{
						LinearLayout k = (LinearLayout)v.findViewById(timeTableLayout[i]);
						k.setBackgroundColor(0xf01d0d2);
					}
				}
				else
				{
					LinearLayout k = (LinearLayout)v.findViewById(timeTableLayout[i]);
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
    public void requestAddCalendar(String year, String month , String day,String time, String dayOfweek,String place, String teamname)
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
			if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_TIME_TABLE"))
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
	public void clearUiResource()
    {
    	m_oBtnConfirm = null;
    	m_oTimeYear = null;
    	m_oTimeMonth= null;
    	m_oBtnPrev = null;
    	m_oBtnNext = null;
    	m_oEditTextDay= null;
    	m_oEditTextHour = null;
    }

}
