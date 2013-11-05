package com.cultureshock.buskingbook.page;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.list.BuskerSearchListView;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.object.TeamObject;

public class BuskerSearchFragment extends Fragment implements
		View.OnClickListener{
	private FragmentActivity mContext;
	private LinearLayout mImg;
	private LinearLayout mListLayout;
	private LinearLayout mNoDataLayout;
	private ImageView m_oBtnList;
	private EditText m_oEditTextSearch;
	private BuskerSearchListView m_oListTeamView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.busker_search, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mContext = getActivity();
		setUi();
	}

	public void setUi() {
		m_oBtnList = (ImageView) getActivity()
				.findViewById(R.id.title_btn_menu);
		m_oBtnList.setOnClickListener(this);
		m_oEditTextSearch = (EditText)getActivity().findViewById(R.id.busker_search_edittext);
		
		TextWatcher textWatcherInput = new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
				//이쪽 문제가 있다 일단.. 가자.. 
				ArrayList<TeamObject> o = new ArrayList<TeamObject>();
				
				String data = s.toString();
				for(int i = 0 ; i <MainActivity.getTeamObject().size() ; i++)
				{
					if(MainActivity.getTeamObject().get(i).getTeamName().contains(data))
					{
						o.add(MainActivity.getTeamObject().get(i));
					}
				}
				if(o.size()==0)
				{
					mNoDataLayout.setVisibility(View.VISIBLE);
					mListLayout.setVisibility(View.GONE);
				}
				else
				{
					mNoDataLayout.setVisibility(View.GONE);
					mListLayout.setVisibility(View.VISIBLE);
					m_oListTeamView.setListData(o);
					m_oListTeamView.notifyData();
				}
			}
		};	
		m_oEditTextSearch.addTextChangedListener(textWatcherInput);
		mImg = (LinearLayout) getActivity().findViewById(R.id.busker_search_btn);
		mNoDataLayout= (LinearLayout) getActivity().findViewById(R.id.list_view_no);
		mImg.setOnClickListener(this);
		mListLayout = (LinearLayout) getActivity().findViewById(
				R.id.list_view);
		if(m_oListTeamView == null)
		{
			m_oListTeamView = new BuskerSearchListView(mContext);
			m_oListTeamView.setListData(MainActivity.getTeamObject());
		}
		mListLayout.addView(m_oListTeamView);
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
	public synchronized void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_btn_menu: {
			MainActivity.getInstance().showMenu();
			break;
		}
		case R.id.busker_search_btn: {
			InputMethodManager imm = (InputMethodManager)mContext.getSystemService(mContext.INPUT_METHOD_SERVICE);

			//키보드를 없앤다.

			imm.hideSoftInputFromWindow(m_oEditTextSearch.getWindowToken(),0);

			ArrayList<TeamObject> o = new ArrayList<TeamObject>();
			
			String data = m_oEditTextSearch.getText().toString();
			if(data.equals(""))
			{
				m_oListTeamView.setListData(MainActivity.getTeamObject());
				m_oListTeamView.notifyData();
				break;
			}
			for(int i = 0 ; i <MainActivity.getTeamObject().size() ; i++)
			{
				if(MainActivity.getTeamObject().get(i).getTeamName().contains(data))
				{
					o.add(MainActivity.getTeamObject().get(i));
				}
			}
			if(o.size()==0)
			{
				mNoDataLayout.setVisibility(View.VISIBLE);
				mListLayout.setVisibility(View.GONE);
			}
			else
			{
				mNoDataLayout.setVisibility(View.GONE);
				mListLayout.setVisibility(View.VISIBLE);
				m_oListTeamView.setListData(o);
				m_oListTeamView.notifyData();
			}
			
			break;
		}
		
		}

	}

	

}
