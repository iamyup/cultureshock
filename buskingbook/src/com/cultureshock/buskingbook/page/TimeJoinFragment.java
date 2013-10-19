package com.cultureshock.buskingbook.page;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.component.ViewPagerAdapter;
import com.cultureshock.buskingbook.framework.BaseActivity;
import com.cultureshock.buskingbook.main.LeftMenuFragment;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.LineUpObject;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.object.TeamMemberObject;
import com.cultureshock.buskingbook.object.TeamObject;
import com.cultureshock.buskingbook.service.ServiceType;

public class TimeJoinFragment extends Fragment implements View.OnClickListener, HttpClientNet.OnResponseListener{
    private FragmentActivity mContext;
    private static TimeJoinFragment mInstance;
    /*
     * version 1
     */
private LinearLayout m_oBtnConfirm;
	
	private TextView m_oTimeYear;
	private TextView m_oTimeMonth;
	private int[] timeTable = {R.id.calendar_txt_1,R.id.calendar_txt_2,R.id.calendar_txt_3,R.id.calendar_txt_4,R.id.calendar_txt_5,R.id.calendar_txt_6,R.id.calendar_txt_7,R.id.calendar_txt_8,
			R.id.calendar_txt_9,R.id.calendar_txt_10,R.id.calendar_txt_11,R.id.calendar_txt_12,R.id.calendar_txt_13,R.id.calendar_txt_14,R.id.calendar_txt_15,R.id.calendar_txt_16,R.id.calendar_txt_17,
			R.id.calendar_txt_18,R.id.calendar_txt_19,R.id.calendar_txt_20,R.id.calendar_txt_21,R.id.calendar_txt_22,R.id.calendar_txt_23,R.id.calendar_txt_24,R.id.calendar_txt_25,R.id.calendar_txt_26,
			R.id.calendar_txt_27,R.id.calendar_txt_28,R.id.calendar_txt_29,R.id.calendar_txt_30,R.id.calendar_txt_31,R.id.calendar_txt_32,R.id.calendar_txt_33,R.id.calendar_txt_34,R.id.calendar_txt_35,
			R.id.calendar_txt_36,R.id.calendar_txt_37,R.id.calendar_txt_38,R.id.calendar_txt_39,R.id.calendar_txt_40,R.id.calendar_txt_41,R.id.calendar_txt_42};
	
	private int[] joinCount = {R.id.calendar_txt_join_1,R.id.calendar_txt_join_2,R.id.calendar_txt_join_3,R.id.calendar_txt_join_4,R.id.calendar_txt_join_5,R.id.calendar_txt_join_6,R.id.calendar_txt_join_7,R.id.calendar_txt_join_8,
			R.id.calendar_txt_join_9,R.id.calendar_txt_join_10,R.id.calendar_txt_join_11,R.id.calendar_txt_join_12,R.id.calendar_txt_join_13,R.id.calendar_txt_join_14,R.id.calendar_txt_join_15,R.id.calendar_txt_join_16,R.id.calendar_txt_join_17,
			R.id.calendar_txt_join_18,R.id.calendar_txt_join_19,R.id.calendar_txt_join_20,R.id.calendar_txt_join_21,R.id.calendar_txt_join_22,R.id.calendar_txt_join_23,R.id.calendar_txt_join_24,R.id.calendar_txt_join_25,R.id.calendar_txt_join_26,
			R.id.calendar_txt_join_27,R.id.calendar_txt_join_28,R.id.calendar_txt_join_29,R.id.calendar_txt_join_30,R.id.calendar_txt_join_31,R.id.calendar_txt_join_32,R.id.calendar_txt_join_33,R.id.calendar_txt_join_34,R.id.calendar_txt_join_35,
			R.id.calendar_txt_join_36,R.id.calendar_txt_join_37,R.id.calendar_txt_join_38,R.id.calendar_txt_join_39,R.id.calendar_txt_join_40,R.id.calendar_txt_join_41,R.id.calendar_txt_join_42};
	
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
//	
//	private EditText m_oEditTextPlace;
	private ArrayList<String> m_FlagPlace;
	private Spinner m_oSpinnerPlace;
	private String place;
	private EditText m_oEditTextDay;
	private EditText m_oEditTextHour;
	private EditText m_oEditTextMin;

	private GregorianCalendar today = new GregorianCalendar(); 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.time_join, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        mInstance = this;
        setUi();
        setValue();
    }
    public void setValue()
	{
		GregorianCalendar calendar = new GregorianCalendar();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH)+1;
		setDate(year,month);
//		requestMonth(year,month);
	}
    public void setUi()
    {
    	m_oBtnConfirm = (LinearLayout)getActivity().findViewById(R.id.popup_modify_myalbum_btn_confirm);
    	m_oBtnConfirm.setOnClickListener(this);
    	m_oTimeYear = (TextView)getActivity().findViewById(R.id.time_table_year);
		m_oTimeMonth = (TextView)getActivity().findViewById(R.id.time_table_month);
		m_oBtnPrev = (TextView)getActivity().findViewById(R.id.time_table_prev);
		m_oBtnNext = (TextView)getActivity().findViewById(R.id.time_table_next);
//		m_oEditTextTeamname = (EditText)findViewById(R.id.popup_send_edit_teamname);
		m_oSpinnerPlace = (Spinner)getActivity().findViewById(R.id.time_join_place);
		m_FlagPlace = new ArrayList<String>();
		
		m_FlagPlace.add(0, "홍대");
		m_FlagPlace.add(1, "청계천");
		m_FlagPlace.add(2, "여의도 한강 시민 공원");
		m_FlagPlace.add(3, "이태원");
		ArrayAdapter<String> spinitem = new ArrayAdapter<String>(mContext, R.layout.spinner_item_2, m_FlagPlace);
        spinitem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        m_oSpinnerPlace.setAdapter(spinitem);
        m_oSpinnerPlace.setOnItemSelectedListener(selectedListener);
//		m_oEditTextTime = (EditText)findViewById(R.id.popup_send_edit_time);
		m_oEditTextHour = (EditText)getActivity().findViewById(R.id.time_join_time_hour);
		m_oEditTextMin = (EditText)getActivity().findViewById(R.id.time_join_time_min);
		m_oEditTextDay = (EditText)getActivity().findViewById(R.id.time_join_calendar);
		m_oEditTextDay.setEnabled(false);
		m_oBtnNext.setOnClickListener(this);
		m_oBtnPrev.setOnClickListener(this);
    }
    private OnItemSelectedListener selectedListener = new OnItemSelectedListener() 
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) 
        {
            // TODO Auto-generated method stub
            if ( m_FlagPlace.size() > 0 )
            {
            	place = m_oSpinnerPlace.getSelectedItem().toString();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) 
        {
            // TODO Auto-generated method stub

        }
    };
    public static TimeJoinFragment getInstance() {
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
    public synchronized void onClick(View v) 
    {
        switch (v.getId()) 
        {
        case R.id.popup_modify_myalbum_btn_confirm :
        {
			if(m_oEditTextDay.getText().toString().equals(""))
			{
				Toast.makeText(mContext, "날짜를 선택해주세요", Toast.LENGTH_SHORT );
			}
			else if(m_oEditTextHour.getText().toString().equals("")||m_oEditTextMin.getText().toString().equals(""))
			{
				Toast.makeText(mContext, "시간을 입력해주세요", Toast.LENGTH_SHORT );
			}
			else if(place.equals(""))
			{
				Toast.makeText(mContext, "장소를 입력해주세요", Toast.LENGTH_SHORT );
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
				GregorianCalendar calendar = new GregorianCalendar(Integer.parseInt(year[0]),Integer.parseInt(month[0]),Integer.parseInt(today[0])); //해당 월 의 첫날 
				int dayOfweek = calendar.get(Calendar.DAY_OF_WEEK) - 1; // 첫날의 요일 결정
				requestAddCalendar(year[0],month[0],today[0],m_oEditTextHour.getText().toString()+":"+m_oEditTextMin.getText().toString(),week[dayOfweek],place,LoginInfoObject.getInstance().getMyteam() );
//				dialog.dismiss();
			}
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
						TextView k = (TextView)getActivity().findViewById(timeTable[i]);
						k.setBackgroundColor(0xf01d0d2);
					}
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
		for( int i = 0 ;i < 42 ; i++)
		{
			count[i] = 0 ;
			TextView k = (TextView)getActivity().findViewById(joinCount[i]);
			k.setText("");
		}
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
				MainActivity.getInstance().replaceFragment(MainHomeFragment.class, null, false);
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
		

}
