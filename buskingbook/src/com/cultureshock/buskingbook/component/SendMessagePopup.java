package com.cultureshock.buskingbook.component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.http.client.HttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.LoginInfoObject;
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

public class SendMessagePopup extends LinearLayout implements HttpClientNet.OnResponseListener {
	
	private Context mContext;
	Dialog dialog;
	private View v = null;
	private EditText m_oPhone;
	private EditText m_oComent;
	private TextView m_otxt;
	private LinearLayout m_oSend;
	private ImageView m_oExit;
	private String name;
	private String id;
	private PartnerSearchObject object;
	public SendMessagePopup(Context context,PartnerSearchObject o) {
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
		v = inflater.inflate(R.layout.popup_send_messge, null);
		this.addView(v);
		dialog = new Dialog(mContext, R.style.Dialog);
		dialog.addContentView(this, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		dialog.setCanceledOnTouchOutside(false);
		m_otxt = (TextView) v.findViewById(R.id.send_popup_t);
		m_oPhone = (EditText)v.findViewById(R.id.partner_add_phone);
		m_oComent = (EditText)v.findViewById(R.id.partner_add_edittext);
		m_oSend = (LinearLayout)v.findViewById(R.id.send_messge_o);
		m_oExit = (ImageView)v.findViewById(R.id.exit);
		m_oExit.setOnClickListener(exit);
		m_oSend.setOnClickListener(BtnConfirm);
		m_otxt.setText("TO."+object.getName());
		show();
		
	}
	private final OnClickListener BtnConfirm = new OnClickListener() {
		
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(m_oPhone.getText().toString().equals(""))
			{
				Toast.makeText(mContext, "핸드폰 번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
			}
			else if(m_oComent.getText().toString().equals(""))
			{
				Toast.makeText(mContext, "보내실 글을 써주세요", Toast.LENGTH_SHORT).show();
			}
			else
			{
				requestTimeTable();
			}
			
		}
	};
	private final OnClickListener exit = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		};
	public void requestTimeTable()
	{
		GregorianCalendar calendar = new GregorianCalendar();
		String ampm=null;
    	if(calendar.get(Calendar.AM_PM)==1)
    	{
    		ampm="pm";
    	}
    	else
    	{
    		ampm="am";
    	}
		String time = calendar.get(Calendar.YEAR) + ". "+(calendar.get(Calendar.MONTH)+1)+". "+calendar.get(Calendar.DATE)
				+" " +calendar.get(calendar.HOUR)+":"+calendar.get(calendar.MINUTE)+" "+ampm+" "+calendar.get(calendar.SECOND);
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_MESSAGE_ADD);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("name", object.getName() ));
		loginParams.add(new Params("id", object.getId() ));
		loginParams.add(new Params("sendid", LoginInfoObject.getInstance().getId()));
		loginParams.add(new Params("sendname", LoginInfoObject.getInstance().getName() ));
		loginParams.add(new Params("sendphone", m_oPhone.getText().toString()));
		loginParams.add(new Params("coment", m_oComent.getText().toString()));
		loginParams.add(new Params("times", time));
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
			
			if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_MESSAGE_ADD"))
			{
				dismissPopup();
				Toast.makeText(mContext, "메세지를 보냈습니다.", Toast.LENGTH_SHORT).show();
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
