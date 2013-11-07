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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cultureshock.buskingbook.list.LineUpListView;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.LineUpObject;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.service.ServiceType;
import com.cultureshock.buskingbook.FirstStartActivity;
import com.cultureshock.buskingbook.R;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class Main_LineUp_Page extends LinearLayout implements
		View.OnClickListener, HttpClientNet.OnResponseListener {

	public static final String TIME_TABLE_MONTH = "1";
	public static final String TIME_TABLE_TODAY = "2";
	public static final String TIME_TABLE_PLACE = "3";
	public static final String TIME_TABLE_TEAM = "4";

	private Context mContext;
	private View v = null;
	private ArrayList<LineUpObject> lineUpArr = new ArrayList<LineUpObject>();

	private LinearLayout m_oLayoutListView;
	private LineUpListView lineUpListView;
	private TextView m_oTxtPlace;
	
	private RelativeLayout m_oBtnSearch;

	private LinearLayout m_oBtnSearchPlaceLayout;
	private RelativeLayout m_oBtnPlace1;
	private RelativeLayout m_oBtnPlace2;
	private RelativeLayout m_oBtnPlace3;
	private RelativeLayout m_oBtnPlace4;

	private LinearLayout m_oNoBusker;
	// bottom
	

	private boolean checkPlace = false;

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

	public void dismissPopup() {
		dialog.dismiss();
	}

	private void initView() {
		// TODO Auto-generated method stub

		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(R.layout.main_lineup, null);
		this.addView(v);

		m_oLayoutListView = (LinearLayout) v.findViewById(R.id.lineup_list);
		
		m_oTxtPlace = (TextView) v.findViewById(R.id.top_search_place);
		m_oBtnSearch = (RelativeLayout) v.findViewById(R.id.top_search);
		m_oBtnSearch.setOnClickListener(this);
		
		m_oBtnSearchPlaceLayout = (LinearLayout) v
				.findViewById(R.id.top_search_place_layout);
		m_oBtnPlace1 = (RelativeLayout) v.findViewById(R.id.top_place_1);
		m_oBtnPlace2 = (RelativeLayout) v.findViewById(R.id.top_place_2);
		m_oBtnPlace3 = (RelativeLayout) v.findViewById(R.id.top_place_3);
		m_oBtnPlace4 = (RelativeLayout) v.findViewById(R.id.top_place_4);
		m_oNoBusker = (LinearLayout) v.findViewById(R.id.lineup_list_no);
		
		m_oBtnPlace1.setOnClickListener(this);
		m_oBtnPlace2.setOnClickListener(this);
		m_oBtnPlace3.setOnClickListener(this);
		m_oBtnPlace4.setOnClickListener(this);


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
					if (Integer.parseInt(year) == calendar.get(Calendar.YEAR)) {
						if (Integer.parseInt(month) == calendar
								.get(Calendar.MONTH) + 1) {
							if (Integer.parseInt(day) >= calendar
									.get(Calendar.DATE)) {
								lineUpArr.add(new LineUpObject(year, month,
										day, time, dayOfweek, place, teamname,
										joincount));
							}
						}
					}

				}
				if (lineUpListView == null) {
					lineUpListView = new LineUpListView(mContext);

				}
				lineUpListView.setListData(lineUpArr);
				m_oLayoutListView.addView(lineUpListView);
				if (lineUpArr.size() == 0) {
					m_oNoBusker.setVisibility(View.VISIBLE);

				} else {
					m_oNoBusker.setVisibility(View.GONE);

				}

			} else if (object.getJSONObject("result").optString("type")
					.equals("ServiceType.MSG.TIME_TABLE_SEND")) {
				Toast.makeText(mContext, "일정을 등록하였습니다", Toast.LENGTH_SHORT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MainActivity.getInstance().stopProgressDialog();
		}
	}

	public void requestTimeTable() {
		GregorianCalendar calendar = new GregorianCalendar();
		HttpClientNet loginService = new HttpClientNet(
				ServiceType.MSG_TIME_TABLE);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("type", TIME_TABLE_MONTH));
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

	public ArrayList<LineUpObject> sortPlaceLineUp(String name) {
		ArrayList<LineUpObject> lineUpArrp1 = new ArrayList<LineUpObject>();
		for (int i = 0; i < lineUpArr.size(); i++) {
			if (lineUpArr.get(i).getPlace().contains(name)) {
				lineUpArrp1.add(lineUpArr.get(i));
			}
		}
		return lineUpArrp1;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.top_search: {
			// 여기서 검색
			if (checkPlace == false) {
				m_oBtnSearchPlaceLayout.setVisibility(View.VISIBLE);
				checkPlace = true;
			} else {
				m_oBtnSearchPlaceLayout.setVisibility(View.GONE);
				checkPlace = false;
			}

			break;
		}
		case R.id.top_place_1: {

			m_oTxtPlace.setText("홍대");
			m_oBtnSearchPlaceLayout.setVisibility(View.GONE);
			if (sortPlaceLineUp("홍대").size() == 0) {
				m_oNoBusker.setVisibility(View.VISIBLE);

			} else {
				m_oNoBusker.setVisibility(View.GONE);
			}
			lineUpListView.setListData(sortPlaceLineUp("홍대"));
			lineUpListView.notifyData();

			break;
		}
		case R.id.top_place_2: {
			ArrayList<LineUpObject> lineUpArrp1 = new ArrayList<LineUpObject>();
			m_oTxtPlace.setText("청계천");
			m_oBtnSearchPlaceLayout.setVisibility(View.GONE);
			if (sortPlaceLineUp("청계천").size() == 0) {
				m_oNoBusker.setVisibility(View.VISIBLE);
			} else {
				m_oNoBusker.setVisibility(View.GONE);
			}

			lineUpListView.setListData(sortPlaceLineUp("청계천"));
			lineUpListView.notifyData();
			break;
		}
		case R.id.top_place_3: {
			m_oTxtPlace.setText("여의도 한강 시민 공원");
			m_oBtnSearchPlaceLayout.setVisibility(View.GONE);
			if (sortPlaceLineUp("여의도 한강 시민 공원").size() == 0) {
				m_oNoBusker.setVisibility(View.VISIBLE);
			} else {
				m_oNoBusker.setVisibility(View.GONE);
			}

			lineUpListView.setListData(sortPlaceLineUp("여의도 한강 시민 공원"));
			lineUpListView.notifyData();
			break;
		}
		case R.id.top_place_4: {
			m_oTxtPlace.setText("이태원");
			m_oBtnSearchPlaceLayout.setVisibility(View.GONE);
			if (sortPlaceLineUp("이태원").size() == 0) {
				m_oNoBusker.setVisibility(View.VISIBLE);
			} else {
				m_oNoBusker.setVisibility(View.GONE);
			}

			lineUpListView.setListData(sortPlaceLineUp("이태원"));
			lineUpListView.notifyData();
			break;
		}
		
		}

	}

}
