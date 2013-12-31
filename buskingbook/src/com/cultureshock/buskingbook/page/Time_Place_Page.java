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

import com.cultureshock.buskingbook.list.BuskerSearchListView;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.R;

public class Time_Place_Page extends LinearLayout {

	private Context mContext;
	private View v = null;
	private LinearLayout mListLayout;
	Dialog dialog;

	public Time_Place_Page(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
		mContext = context;
		initView();
	}
	public void recycleResoure ()
	{
		mListLayout = null;
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
		v = inflater.inflate(R.layout.time_place, null);
		this.addView(v);

	}

}
