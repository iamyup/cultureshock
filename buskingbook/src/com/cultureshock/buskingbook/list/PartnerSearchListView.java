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
import com.cultureshock.buskingbook.list.LineUpListView.IntromTeamListAdapter;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.LineUpObject;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.object.PartnerSearchObject;
import com.cultureshock.buskingbook.object.TeamMemberObject;
import com.cultureshock.buskingbook.object.TeamObject;
import com.cultureshock.buskingbook.page.TeamPageFragment;
import com.cultureshock.buskingbook.service.ServiceType;
import com.cultureshock.buskingbook.util.AsyncImageLoader;
import com.cultureshock.buskingbook.util.Util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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


public class PartnerSearchListView extends ListView implements HttpClientNet.OnResponseListener
{
	private Context mContext;
	private PartnerAdapter listAdapter;
	
	ViewHolder holder;
	private ArrayList<PartnerSearchObject> partnerObject = new ArrayList<PartnerSearchObject>();
	private AsyncImageLoader m_oAsyncImageLoader = new AsyncImageLoader();
	
	public PartnerSearchListView(Context context) {
		super(context);
		mContext = context;
		initListView();
	}
	public void setListData(ArrayList<PartnerSearchObject> list) 
	{
		this.partnerObject = (ArrayList<PartnerSearchObject>) list;
		listAdapter = new PartnerAdapter(partnerObject);
		setAdapter(listAdapter);
	}
	public PartnerSearchListView(Context context, AttributeSet attrs) {
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

	class PartnerAdapter extends ArrayAdapter<PartnerSearchObject> 
	{
		private ImageView mImg;
		private TextView mTeamname;
		private TextView mComent;
		private TextView mTime;
		private LinearLayout mSend;
		private LinearLayout mTeamInfo;
		private LinearLayout mSh;
	    private List<WeakReference<View>> mRecycleList = new ArrayList<WeakReference<View>>();
		
		public void recycle() {
	        for (WeakReference<View> ref : mRecycleList) {
	            Util.recursiveRecycle(ref.get());
	        }
	    }

		public PartnerAdapter(List<PartnerSearchObject> objects) {
			super(mContext, 0, objects);
		}
		
		
		
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(mContext);
				convertView = inflater.inflate(R.layout.item_search_partner, null);
			
				mImg = (ImageView)convertView.findViewById(R.id.search_partner_img);
				mTeamname = (TextView) convertView.findViewById(R.id.search_partner_text_name_teamname);
				mComent = (TextView) convertView.findViewById(R.id.search_partner_coment);
				mTime = (TextView) convertView.findViewById(R.id.search_partner_text_date);
				mSh = (LinearLayout)convertView.findViewById(R.id.sh_item);
				mSend = (LinearLayout) convertView.findViewById(R.id.partner_busker_message_send);
				mTeamInfo = (LinearLayout) convertView.findViewById(R.id.partner_busker_page);
				holder = new ViewHolder();
				holder.img = mImg;
				holder.sh= mSh;
				holder.teamname = mTeamname;
				holder.send = mSend;
				holder.coment = mComent;
				holder.time = mTime;
				holder.teamInfo = mTeamInfo;
				
				convertView.setTag(holder);
				
		        mRecycleList.add(new WeakReference<View>(convertView));
				
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			convertView.setTag(R.id.imageId, position);
			holder.send.setTag(R.id.imageId, position);
			holder.teamInfo.setTag(R.id.imageId, position);
			PartnerSearchObject itemObject = getItem(position);
			if(position ==0)
			{
				holder.sh.setVisibility(View.VISIBLE);
			}
			else
			{
				holder.sh.setVisibility(View.GONE);
			}
			Drawable default1 = null;
	    	default1 =  mContext.getResources().getDrawable(R.drawable.default_pf);
	    	m_oAsyncImageLoader.setImageDrawableAsync(holder.img,itemObject.getImg(),default1,default1,mContext);
	    	holder.coment.setText(itemObject.getComent());
			holder.teamname.setText(itemObject.getName());
			holder.time.setText(itemObject.getDate());

	    	holder.teamInfo.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int pos = (Integer) v.getTag(R.id.imageId);
					if(!getItem(pos).getTeamName().equals(""))
					{
						Bundle o = new Bundle();
						o.putString("object", getItem(pos).getTeamName());
						MainActivity.getInstance().replaceFragment(TeamPageFragment.class, o, true);
					}
					else
					{
						Toast.makeText(mContext, "팀페이지가 없습니다", Toast.LENGTH_SHORT).show();
					}
				}
			});
			holder.send.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(LoginInfoObject.getInstance().isLogin())
					{
						int pos = (Integer) v.getTag(R.id.imageId);
						PartnerSearchObject itemObject = getItem(pos);
						SendMessagePopup o = new SendMessagePopup(mContext, itemObject);
						
					}
				}
			});
		
	    	
			return convertView;
		}
	}
	

	class ViewHolder 
	{
		private ImageView img;
		private TextView teamname;
		private LinearLayout teamInfo;
		private TextView time;
		private LinearLayout send;
		private TextView coment;
		private LinearLayout sh;
		
	}

	public void notifyData()
	{
		listAdapter.notifyDataSetChanged(); 
	}

	@Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		
	}
	
	

}
