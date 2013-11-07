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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cultureshock.buskingbook.list.BuskerSearchListView;
import com.cultureshock.buskingbook.list.LineUpListView;
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

public class Buskers_Page extends LinearLayout {

	private Context mContext;
	private View v = null;
	private LinearLayout mListLayout;
	private BuskerSearchListView m_oListTeamView;
	Dialog dialog;

	public Buskers_Page(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
		mContext = context;
		initView();
	}

	public void show() {
		dialog.show();
	}

	public void dismissPopup() {
		dialog.dismiss();
	}
	public void setUi() {
		
	}
	private void initView() {
		// TODO Auto-generated method stub

		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(R.layout.busker_search_page, null);
		this.addView(v);

		
		mListLayout = (LinearLayout) v.findViewById(
				R.id.list_view);
		if(m_oListTeamView == null)
		{
			m_oListTeamView = new BuskerSearchListView(mContext);
			m_oListTeamView.setListData(MainActivity.getTeamObject());
		}
		mListLayout.addView(m_oListTeamView);


	}

}
