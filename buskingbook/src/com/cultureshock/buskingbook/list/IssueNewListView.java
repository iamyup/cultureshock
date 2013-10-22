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


public class IssueNewListView extends ListView implements HttpClientNet.OnResponseListener
{
	private Context mContext;
	private IssueAdapter listAdapter;
	
	ViewHolder holder;
	private ArrayList<TeamObject> teamObject = new ArrayList<TeamObject>();
	private AsyncImageLoader m_oAsyncImageLoader = new AsyncImageLoader();
	private TeamObject m_oSelectTeam;
	
	public IssueNewListView(Context context) {
		super(context);
		mContext = context;
		initListView();
	}
	public void setListData(ArrayList<TeamObject> list) 
	{
		this.teamObject = (ArrayList<TeamObject>) list;
		listAdapter = new IssueAdapter(teamObject);
		setAdapter(listAdapter);
	}
	public IssueNewListView(Context context, AttributeSet attrs) {
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

	class IssueAdapter extends ArrayAdapter<TeamObject> 
	{
		private ImageView mImg;
		private TextView mTeamname;
		private TextView mRanking;
		private TextView mTime;
		private ImageView mLikeImg;
		private LinearLayout mLike;
		private TextView mLikeCount;
		private LinearLayout mTeamInfo;
		
		private LinearLayout mBtnMemberAdd;
	    private List<WeakReference<View>> mRecycleList = new ArrayList<WeakReference<View>>();
		
		public void recycle() {
	        for (WeakReference<View> ref : mRecycleList) {
	            Util.recursiveRecycle(ref.get());
	        }
	    }

		public IssueAdapter(List<TeamObject> objects) {
			super(mContext, 0, objects);
		}
		
		
		
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(mContext);
				convertView = inflater.inflate(R.layout.item_new_team, null);
			
				mImg = (ImageView)convertView.findViewById(R.id.busker_join_image_2);
				mTeamname = (TextView) convertView.findViewById(R.id.busker_join_teamname_2);
				mRanking = (TextView) convertView.findViewById(R.id.busker_join_ranking_2);
				mTime = (TextView) convertView.findViewById(R.id.team_time_info);
				
				mLike = (LinearLayout) convertView.findViewById(R.id.like_select);
				mLikeImg = (ImageView) convertView.findViewById(R.id.item_lineup_bottom_likecount_img);
				mLikeCount= (TextView) convertView.findViewById(R.id.item_lineup_bottom_likecount);
				mTeamInfo = (LinearLayout) convertView.findViewById(R.id.team_page);
				holder = new ViewHolder();
				holder.teamMemberAdd = mBtnMemberAdd;
				holder.img = mImg;
				holder.teamname = mTeamname;
				holder.like = mLike;
				holder.ranking = mRanking;
				holder.likeImg = mLikeImg;
				holder.likeCount = mLikeCount;
				holder.teamInfo = mTeamInfo;
				
				convertView.setTag(holder);
				
		        mRecycleList.add(new WeakReference<View>(convertView));
				
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			convertView.setTag(R.id.imageId, position);
			holder.like.setTag(R.id.imageId, position);
			holder.teamInfo.setTag(R.id.imageId, position);
			TeamObject itemObject = getItem(position);
			
			Drawable default1 = null;
	    	default1 =  mContext.getResources().getDrawable(R.drawable.default_a);
	    	m_oAsyncImageLoader.setImageDrawableAsync(holder.img,itemObject.getTeamThum(),default1,default1,mContext);
	    	holder.ranking.setText("NEW");
			holder.likeCount.setText(itemObject.getLikeCount()+"");
			holder.teamname.setText(itemObject.getTeamName());
//			holder.time.setText(itemObject.getTime());
			for(int i = 0 ; i< LoginInfoObject.getInstance().getLikeTeamList().size() ; i++)
			{
				if(LoginInfoObject.getInstance().getLikeTeamList().get(i).equals(itemObject.getTeamName()))
				{
					holder.likeImg.setBackgroundResource(R.drawable.heart_o);
					break;
				}
			}
//			for(int i = 0 ; i<LoginInfoObject.getInstance().g)
//			m_oAsyncImageLoader.setImageDrawableAsync(holder.img,,default1,default1,mContext);
	    	holder.teamInfo.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int pos = (Integer) v.getTag(R.id.imageId);
					Bundle o = new Bundle();
					o.putString("object", getItem(pos).getTeamName());
					MainActivity.getInstance().replaceFragment(TeamPageFragment.class, o, true);
				}
			});
			holder.like.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(LoginInfoObject.getInstance().isLogin())
					{
						int pos = (Integer) v.getTag(R.id.imageId);
						TeamObject itemObject = null;
						if(BaseActivity.searchTeam(getItem(pos).getTeamName()) != null)
						{
							 itemObject = getItem(pos);
							 int check = 0;
							 for(int i = 0 ; i< itemObject.getLikeMans().size() ; i++)
							 {
									if(itemObject.getLikeMans().get(i).equals(LoginInfoObject.getInstance().getId()))
									{
										Toast.makeText(mContext, "이미 좋아하는 팀입니다.", Toast.LENGTH_SHORT).show();
										check = 1;
										break;
									}
							 }
						 	 if(check != 1)
						 	 { 
								//통신이후 좋아요 카운트 하나 늘리면됨 
								m_oSelectTeam = itemObject;
								requestTeamLikeUp();
							 }
						}
						else
						{
							//팀이 없습니다 
						}
					}
					else
					{
						new LoginAlertPopup(mContext);
					}
				}
			});
		
	    	
			return convertView;
		}
	}
	

	class ViewHolder 
	{
		private LinearLayout teamMemberAdd;
		private ImageView img;
		private TextView teamname;
		private LinearLayout teamInfo;
		
		private TextView time;
		private LinearLayout like;
		private TextView ranking;
		private ImageView likeImg;
		private TextView likeCount;
		
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
			if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_TEAM_LIKE_UP"))
			{
				String result = object.getJSONObject("data").optString("result","");
				if(result.equals("true"))
				{
					Toast.makeText(mContext, "좋아요", Toast.LENGTH_LONG).show(); 
					//개인데이터에 내가 좋아요 눌른 팀 체크
					LoginInfoObject.getInstance().getLikeTeamList().add(m_oSelectTeam.getTeamName());
					//로컬 카운트 + 1 , 그 팀 이 직접가지고 있는 라이크 눌른 인원들에 대한 아이디 추가
					for(int i = 0 ; i < BaseActivity.getTeamObject().size() ; i++)
					{
						if(m_oSelectTeam.getTeamName().equals(BaseActivity.getTeamObject().get(i).getTeamName()))
						{
							BaseActivity.getTeamObject().get(i).setLikeCount(BaseActivity.getTeamObject().get(i).getLikeCount()+1);
							BaseActivity.getTeamObject().get(i).getLikeMans().add(LoginInfoObject.getInstance().getId());
							notifyData();
							break;
							
						}
					}
					
				}
				else
				{
					Toast.makeText(mContext, "좋아요 실패했습니다", Toast.LENGTH_LONG).show();
				}
			}
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			((MainActivity)mContext).stopProgressDialog() ;
		}
	}
	public String setLikeTeam()
	{
		String likeTeam = "";
		for( int i  = 0 ; i <= LoginInfoObject.getInstance().getLikeTeamList().size() ; i++)
		{
			if(LoginInfoObject.getInstance().getLikeTeamList().size() == 0 )
			{
				likeTeam = m_oSelectTeam.getTeamName();
				break;
			}
			if( i == LoginInfoObject.getInstance().getLikeTeamList().size() -1 )
			{
				likeTeam += LoginInfoObject.getInstance().getLikeTeamList().get(i) + ","+m_oSelectTeam.getTeamName();
				break;
			}
			likeTeam += LoginInfoObject.getInstance().getLikeTeamList().get(i)+",";
		}
		return likeTeam;
	}
	public String setLikeMan()
	{
		String str = "";
		for( int i = 0 ; i <= m_oSelectTeam.getLikeMans().size() ; i++)
		{
			if(m_oSelectTeam.getLikeMans().size() == 0 )
			{
				str = LoginInfoObject.getInstance().getId();
				break;
			}
			if( i == m_oSelectTeam.getLikeMans().size() -1)
			{
				str += m_oSelectTeam.getLikeMans().get(i)+","+LoginInfoObject.getInstance().getId();
				break;
			}
			str += m_oSelectTeam.getLikeMans().get(i)+",";
		}
		return str;
	}
	public void requestTeamLikeUp()
	{
		GregorianCalendar calendar = new GregorianCalendar();
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_TEAM_LIKE_UP);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("teamname", m_oSelectTeam.getTeamName()));
		loginParams.add(new Params("likecount", (m_oSelectTeam.getLikeCount() + 1)+""));
		loginParams.add(new Params("id", LoginInfoObject.getInstance().getId()));
		loginParams.add(new Params("liketeam",setLikeTeam()));
		loginParams.add(new Params("likeman", setLikeMan()));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		((MainActivity)mContext).startProgressDialog();
	}
	

}
