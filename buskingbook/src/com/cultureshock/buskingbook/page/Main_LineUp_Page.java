package com.cultureshock.buskingbook.page;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cultureshock.buskingbook.list.LineUpListView;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.object.LineUpObject;
import com.cultureshock.buskingbook.R;



public class Main_LineUp_Page extends LinearLayout implements View.OnClickListener, HttpClientNet.OnResponseListener{
	
	private Context mContext;
	private View v = null;
	private LinearLayout m_oLayoutListView;
	private LineUpListView lineUpListView ;
	private TextView m_oTxtPlace;
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
		m_oTxtPlace = (TextView)v.findViewById(R.id.top_search_place);
		m_oBtnSearch = (RelativeLayout)v.findViewById(R.id.top_search);
		m_oBtnSearch.setOnClickListener(this);
		m_oBtnSearchAll = (LinearLayout)v.findViewById(R.id.select_all);
		m_oBtnSearchAll.setOnClickListener(this);
		m_oBtnSearchLike = (LinearLayout)v.findViewById(R.id.select_like);
		m_oBtnSearchLike.setOnClickListener(this);
		MainHomeFragment.getInstance().setTitle("라인업");
		ArrayList<LineUpObject> lineUpArr = new ArrayList<LineUpObject>();
		lineUpArr.add(new LineUpObject("2010","08","13","14:00","화","홍대놀이터","버스커버스커","5"));
		lineUpArr.add(new LineUpObject("2010","08","13","16:00","화","홍대놀이터","버스커버스커","5"));
		lineUpArr.add(new LineUpObject("2010","08","14","14:00","화","홍대놀이터","버스커버스커","5"));
		lineUpArr.add(new LineUpObject("2010","08","15","14:00","화","홍대놀이터","버스커버스커","5"));
		
		if(lineUpListView == null)
		{
			lineUpListView = new LineUpListView(mContext);
			lineUpListView.setListData(lineUpArr);
		}
		m_oLayoutListView.addView(lineUpListView);
	}

	@Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		
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
				m_oBtnSearchAll.setBackgroundColor(0xff38bdd1);
				m_oBtnSearchLike.setBackgroundColor(0xffa3a3a3);
				break;
			}
			case R.id.select_like : 
			{
				m_oBtnSearchAll.setBackgroundColor(0xffa3a3a3);
				m_oBtnSearchLike.setBackgroundColor(0xff38bdd1);
				break;
			}
		}
		
	}
	

}
