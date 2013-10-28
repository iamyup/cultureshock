package com.cultureshock.buskingbook.list;

import java.lang.ref.WeakReference;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.component.LoginAlertPopup;
import com.cultureshock.buskingbook.component.SendMessagePopup;
import com.cultureshock.buskingbook.framework.BaseActivity;
import com.cultureshock.buskingbook.list.LineUpListView.IntromTeamListAdapter;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.LineUpObject;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.object.MessageObject;
import com.cultureshock.buskingbook.object.PartnerSearchObject;
import com.cultureshock.buskingbook.object.TeamMemberObject;
import com.cultureshock.buskingbook.object.TeamObject;
import com.cultureshock.buskingbook.page.TeamPageFragment;
import com.cultureshock.buskingbook.service.ServiceType;
import com.cultureshock.buskingbook.util.AsyncImageLoader;
import com.cultureshock.buskingbook.util.Util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Contacts.Intents;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MessageReadListView extends ListView implements HttpClientNet.OnResponseListener
{
	private Context mContext;
	private MessageAdapter listAdapter;
	
	ViewHolder holder;
	private ArrayList<MessageObject> partnerObject = new ArrayList<MessageObject>();
	
	public MessageReadListView(Context context) {
		super(context);
		mContext = context;
		initListView();
	}
	public void setListData(ArrayList<MessageObject> list) 
	{
		this.partnerObject = (ArrayList<MessageObject>) list;
		listAdapter = new MessageAdapter(partnerObject);
		setAdapter(listAdapter);
	}
	public MessageReadListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		
		initListView();
	}
	public void initListView()
	{
        setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT));
        setFadingEdgeLength(0);
        setDividerHeight(0);
        
	}
	
	public void recycle() 
	{
		if (listAdapter != null)
			listAdapter.recycle();
    }

	class MessageAdapter extends ArrayAdapter<MessageObject> 
	{
		private ImageView mExit;
		private TextView mName;
		private TextView mTimes;
		private TextView mPhone;
		private TextView mComent;
		private LinearLayout mSend;
		private LinearLayout mAddBtn;
		
	    private List<WeakReference<View>> mRecycleList = new ArrayList<WeakReference<View>>();
		
		public void recycle() {
	        for (WeakReference<View> ref : mRecycleList) {
	            Util.recursiveRecycle(ref.get());
	        }
	    }

		public MessageAdapter(List<MessageObject> objects) {
			super(mContext, 0, objects);
		}
		
		
		
		
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(mContext);
				convertView = inflater.inflate(R.layout.popup_read_messge, null);
			
				mExit = (ImageView)convertView.findViewById(R.id.exit);
				mName = (TextView) convertView.findViewById(R.id.send_popup_t);
				mTimes = (TextView) convertView.findViewById(R.id.times);
				mPhone = (TextView) convertView.findViewById(R.id.partner_add_phone);
				mComent = (TextView) convertView.findViewById(R.id.partner_add_edittext);
				mAddBtn = (LinearLayout)convertView.findViewById(R.id.add_phone_btn);
				mSend = (LinearLayout) convertView.findViewById(R.id.send_messge_o);
				
				holder = new ViewHolder();
				holder.exit = mExit;
				holder.name = mName;
				holder.send = mSend;
				holder.times = mTimes;
				holder.phone = mPhone;
				holder.coment = mComent;
				holder.addBtn = mAddBtn;
				
				convertView.setTag(holder);
				
		        mRecycleList.add(new WeakReference<View>(convertView));
				
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			convertView.setTag(R.id.imageId, position);
			holder.send.setTag(R.id.imageId, position);
			holder.addBtn.setTag(R.id.imageId, position);
			holder.exit.setTag(R.id.imageId, position);
			MessageObject itemObject = getItem(position);
	    	
	    	holder.coment.setText(itemObject.getComent());
			holder.name.setText("FROM."+itemObject.getSendname());
			
			holder.times.setText(itemObject.getTimes().substring(0, itemObject.getTimes().length()-2));
			holder.phone.setText(itemObject.getSendphone());
			holder.exit.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int pos = (Integer) v.getTag(R.id.imageId);
					//데이터 삭제
					MessageObject itemObject = getItem(pos);
					requestDeleteMessage(LoginInfoObject.getInstance().getId(), itemObject.getSendid(), itemObject.getTimes());
				}
			});
	    	holder.addBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int pos = (Integer) v.getTag(R.id.imageId);
					//전화번호 등록하도록
					Intent intent = new Intent(Intent.ACTION_INSERT);
					ComponentName insertComponentName = new ComponentName(
					               "com.android.contacts", "com.android.contacts.ui.EditContactActivity");
					intent.setComponent(insertComponentName);
					MessageObject itemObject = getItem(pos);
					Bundle bundle = new Bundle();
					bundle.putString(Intents.Insert.PHONE, itemObject.getSendphone());
					bundle.putString(Intents.Insert.NAME, itemObject.getName());
					intent.putExtras(bundle);
					mContext.startActivity(intent);
				}
			});
			holder.send.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(LoginInfoObject.getInstance().isLogin())
					{
						int pos = (Integer) v.getTag(R.id.imageId);
						MessageObject itemObject = getItem(pos);
						PartnerSearchObject o =new PartnerSearchObject(itemObject.getSendid(),itemObject.getName(),"","","","","");
						SendMessagePopup k = new SendMessagePopup(mContext, o);
						
					}
				}
			});
		
	    	
			return convertView;
		}
	}
	

	class ViewHolder 
	{
		private ImageView exit;
		private TextView name;
		private TextView times;
		private TextView phone;
		private TextView coment;
		private LinearLayout addBtn;
		
		private LinearLayout send;
		private LinearLayout sh;
		
	}

	public void notifyData()
	{
		listAdapter.notifyDataSetChanged(); 
	}

	@Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		try{
			
			Object o = resContent;
			JSONObject object = new JSONObject(resContent);
			
			if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_MESSAGE_DELETE"))
			{
				String result = object.getJSONObject("data").optString("result","");
				String reason = object.getJSONObject("data").optString("reason","");
				if(result.equals("true"))
				{
					String id = object.getJSONObject("data").optString("id","");
					String sendid = object.getJSONObject("data").optString("sendid","");
					String times = object.getJSONObject("data").optString("times","");
					for(int i = 0 ;i<partnerObject.size();i++)
					{
						if(partnerObject.get(i).getId().equals(id)&&
								partnerObject.get(i).getSendid().equals(sendid)&&
								partnerObject.get(i).getTimes().equals(times))
						{
							partnerObject.remove(i);
							break;
						}
					}
					notifyData();
				}
				else
				{
					Toast.makeText(mContext, "메세지를 삭제에 실패했습니다", Toast.LENGTH_SHORT).show();
				}
				
				
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
	public void requestDeleteMessage(String id, String sendid, String times)
	{
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_MESSAGE_DELETE);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("id", id));
		loginParams.add(new Params("sendid", sendid));
		loginParams.add(new Params("times", times));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		((MainActivity)mContext).startProgressDialog();
	}
	
	

}
