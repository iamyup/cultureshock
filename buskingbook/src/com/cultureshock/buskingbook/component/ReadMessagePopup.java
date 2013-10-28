package com.cultureshock.buskingbook.component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.http.client.HttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.framework.BaseActivity;
import com.cultureshock.buskingbook.list.MessageReadListView;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.object.MessageObject;
import com.cultureshock.buskingbook.object.PartnerSearchObject;
import com.cultureshock.buskingbook.service.ServiceType;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ReadMessagePopup extends LinearLayout {
	
	private Context mContext;
	Dialog dialog;
	private View v = null;
	private LinearLayout m_oListLayout;
	private ArrayList<MessageObject> object;
	private MessageReadListView messageListView;
	public ReadMessagePopup(Context context,ArrayList<MessageObject> o) {
		// TODO Auto-generated constructor stub
		super(context);
		mContext = context;
		this.object = o;
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
		v = inflater.inflate(R.layout.message, null);
		this.addView(v);
		dialog = new Dialog(mContext, R.style.Dialog);
		dialog.addContentView(this, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		dialog.setCanceledOnTouchOutside(false);
		m_oListLayout = (LinearLayout)v.findViewById(R.id.messge_layout);
		if(messageListView ==null)
		{
			messageListView = new MessageReadListView(mContext);
			messageListView.setListData(object);
		}	
		m_oListLayout.addView(messageListView);
		show();
		
	}
	
	
	
}
