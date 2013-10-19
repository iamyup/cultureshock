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
import com.cultureshock.buskingbook.framework.BaseActivity;
import com.cultureshock.buskingbook.list.LineUpListView.IntromTeamListAdapter;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.LineUpObject;
import com.cultureshock.buskingbook.object.LoginInfoObject;
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


public class MemberListView extends ListView implements HttpClientNet.OnResponseListener
{
	private Context mContext;
	private TeamMemberListAdapter listAdapter;
	
	ViewHolder holder;
	private ArrayList<TeamMemberObject> teamMemberObject = new ArrayList<TeamMemberObject>();
	private AsyncImageLoader m_oAsyncImageLoader = new AsyncImageLoader();
	public MemberListView(Context context) {
		super(context);
		mContext = context;
		initListView();
	}
	public void setListData(ArrayList<TeamMemberObject> arrayData) 
	{
		this.teamMemberObject = arrayData;
		listAdapter = new TeamMemberListAdapter(teamMemberObject);
		setAdapter(listAdapter);
	}
	public MemberListView(Context context, AttributeSet attrs) {
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

	class TeamMemberListAdapter extends ArrayAdapter<TeamMemberObject> 
	{
		private ImageView mImg;
		private TextView mTeamname;
		private LinearLayout mBtnMemberAdd;
	    private List<WeakReference<View>> mRecycleList = new ArrayList<WeakReference<View>>();
		
		public void recycle() {
	        for (WeakReference<View> ref : mRecycleList) {
	            Util.recursiveRecycle(ref.get());
	        }
	    }

		public TeamMemberListAdapter(List<TeamMemberObject> objects) {
			super(mContext, 0, objects);
		}
		
		
		
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(mContext);
				convertView = inflater.inflate(R.layout.item_main_lineup, null);
			
				mImg = (ImageView)convertView.findViewById(R.id.item_lineup_image);
				mTeamname = (TextView) convertView.findViewById(R.id.item_lineup_teamname);
			
				holder = new ViewHolder();
				holder.teamMemberAdd = mBtnMemberAdd;
				holder.img = mImg;
				holder.teamname = mTeamname;
				convertView.setTag(holder);
				
		        mRecycleList.add(new WeakReference<View>(convertView));
				
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			convertView.setTag(R.id.imageId, position);
			holder.teamMemberAdd.setTag(R.id.imageId, position);
			TeamMemberObject itemObject = getItem(position);
			convertView.setTag(R.id.imageId, position);
			holder.teamname.setText(itemObject.getName()+"님");
			Drawable default1 = null;
	    	default1 =  mContext.getResources().getDrawable(R.drawable.default_a);
	    	if(position == teamMemberObject.size()-1)
	    	{
	    		holder.teamMemberAdd.setVisibility(View.VISIBLE);
	    	}
	    	else
	    	{
	    		holder.teamMemberAdd.setVisibility(View.GONE);
	    	}
	    	holder.teamMemberAdd.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int pos = (Integer) v.getTag(R.id.imageId);
//					Bundle o = new Bundle();
//					o.putString("object", getItem(pos).getTeamName());
//					MainActivity.getInstance().replaceFragment(TeamPageFragment.class, o, true);
				}
			});
//			for(int i = 0 ; i<LoginInfoObject.getInstance().g)
//			m_oAsyncImageLoader.setImageDrawableAsync(holder.img,,default1,default1,mContext);
			return convertView;
		}
	}
	

	class ViewHolder 
	{
		private LinearLayout teamMemberAdd;
		private ImageView img;
		private TextView teamname;
		private LinearLayout teamInfo;
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
