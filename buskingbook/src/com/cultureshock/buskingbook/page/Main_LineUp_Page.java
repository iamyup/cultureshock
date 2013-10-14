package com.cultureshock.buskingbook.page;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cultureshock.buskingbook.list.LineUpListView;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.LineUpObject;
import com.cultureshock.buskingbook.service.ServiceType;
import com.cultureshock.buskingbook.R;



public class Main_LineUp_Page extends LinearLayout implements View.OnClickListener, HttpClientNet.OnResponseListener{
	
	public static final String TIME_TABLE_MONTH = "1";
	public static final String TIME_TABLE_TODAY = "2";
	public static final String TIME_TABLE_PLACE = "3";
	public static final String TIME_TABLE_TEAM  = "4";
	
	private Context mContext;
	private View v = null;
	private ArrayList<LineUpObject> lineUpArr = new ArrayList<LineUpObject>();
	private LinearLayout m_oLayoutListView;
	private LineUpListView lineUpListView ;
	private TextView m_oTxtPlace;
	private TextView m_oTxtSelectAll;
	private TextView m_oTxtSelectLike;
	private RelativeLayout m_oBtnSearch;
	
	//bottom
	private LinearLayout m_oBtnSearchAll;
	private LinearLayout m_oBtnSearchLike;
	
	Dialog dialog;
	public Main_LineUp_Page(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
		mContext = context;
		initView();
		requestTimeTable();
	}
	
	public void show() {
		dialog.show();
	}
	
	public void dismissPopup(){
		dialog.dismiss();
	}
	

	private void initView() {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(R.layout.main_lineup, null);
		this.addView(v);
		
		m_oLayoutListView = (LinearLayout)v.findViewById(R.id.lineup_list);
		m_oTxtSelectAll = (TextView)v.findViewById(R.id.select_all_text);
		m_oTxtSelectLike = (TextView)v.findViewById(R.id.select_like_text);
		m_oTxtPlace = (TextView)v.findViewById(R.id.top_search_place);
		m_oBtnSearch = (RelativeLayout)v.findViewById(R.id.top_search);
		m_oBtnSearch.setOnClickListener(this);
		m_oBtnSearchAll = (LinearLayout)v.findViewById(R.id.select_all);
		m_oBtnSearchAll.setOnClickListener(this);
		m_oBtnSearchLike = (LinearLayout)v.findViewById(R.id.select_like);
		m_oBtnSearchLike.setOnClickListener(this);
		MainHomeFragment.getInstance().setTitle("Line Up");
		
		
	}

	@Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		try{
			Object o = resContent;
			JSONObject object = new JSONObject(resContent);
			if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_TIME_TABLE"))
			{
				JSONArray todayTime = object.getJSONArray("data");
				for(int i=0; i<todayTime.length(); i++)
				{
					JSONObject jsonObject = todayTime.getJSONObject(i);
					String year = jsonObject.optString("year");
					String month = jsonObject.optString("month");
					String day = jsonObject.optString("day");
					String time = jsonObject.optString("time");
					String dayOfweek = jsonObject.optString("dayOfweek");
					String place = jsonObject.optString("place");
					String teamname = jsonObject.optString("teamname");
					String joincount = jsonObject.optString("joincount");
					lineUpArr.add(new LineUpObject(year,month,day,time,dayOfweek,place,teamname,joincount));
					
					if(lineUpListView == null)
					{
						lineUpListView = new LineUpListView(mContext);
						lineUpListView.setListData(lineUpArr);
					}
					m_oLayoutListView.addView(lineUpListView);
				}
			}
			else if (object.getJSONObject("result").optString("type").equals("ServiceType.MSG.TIME_TABLE_SEND"))
			{
				Toast.makeText(mContext, "일정을 등록하였습니다", Toast.LENGTH_SHORT );
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
	public void requestTimeTable()
	{
		GregorianCalendar calendar = new GregorianCalendar();
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_TIME_TABLE);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("type", TIME_TABLE_MONTH));
		loginParams.add(new Params("year", calendar.get(Calendar.YEAR)+""));
		loginParams.add(new Params("month", (calendar.get(Calendar.MONTH)+1)+""));
		loginParams.add(new Params("day", calendar.get(Calendar.DATE)+""));
		loginParams.add(new Params("place",""));
		loginParams.add(new Params("teamname",""));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		MainActivity.getInstance().startProgressDialog();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.top_search :
			{
				//여기서 검색 
				break;
			}
			case R.id.select_all : 
			{
				m_oBtnSearchAll.setBackgroundColor(0xff01d0d2);
				m_oBtnSearchLike.setBackgroundColor(0xffeaeaea);
				
				m_oTxtSelectAll.setTextColor(0xffffffff);
				m_oTxtSelectLike.setTextColor(0xff01d0d2);
				break;
			}
			case R.id.select_like : 
			{
				m_oBtnSearchAll.setBackgroundColor(0xffeaeaea);
				m_oBtnSearchLike.setBackgroundColor(0xff01d0d2);
				
				m_oTxtSelectAll.setTextColor(0xff01d0d2);
				m_oTxtSelectLike.setTextColor(0xffffffff);
				
				break;
			}
		}
		
	}
	

}
