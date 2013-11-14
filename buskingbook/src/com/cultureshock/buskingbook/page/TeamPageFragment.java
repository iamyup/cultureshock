package com.cultureshock.buskingbook.page;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.component.LoginAlertPopup;
import com.cultureshock.buskingbook.component.ViewPagerAdapter;
import com.cultureshock.buskingbook.list.LineUpListView;
import com.cultureshock.buskingbook.main.LeftMenuFragment;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.LineUpObject;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.object.TeamMemberObject;
import com.cultureshock.buskingbook.object.TeamObject;
import com.cultureshock.buskingbook.service.ServiceType;
import com.cultureshock.buskingbook.util.AsyncImageLoader;

public class TeamPageFragment extends Fragment implements View.OnClickListener, HttpClientNet.OnResponseListener{
    private FragmentActivity mContext;
    private AsyncImageLoader m_oAsyncImageLoader = new AsyncImageLoader();
    private ArrayList<LineUpObject> lineUpObject = new ArrayList<LineUpObject>();
    private String teamName;
    private String ranking;
    private TeamObject selectMyTeam;
    private ImageView m_oBtnList;
    private ImageView m_oBtnSettingTeam;
    private ImageView mImg;
	private TextView mTeamname;
	private TextView mGenre;
	private TextView mTeamInfo;
	private TextView mTime;
	private TextView mPlace;
	private LinearLayout mLike;
	private TextView mRanking;
	private ImageView mLikeImg;
	private ImageView mBungeAlarm;
	private TextView mLikeCount;
	private int check ;
	private TextView mTimeInfo;
	private String time="";
	private RelativeLayout mListTime;
	private LinearLayout mListFacebook;
	private boolean checkTeamHost = false;
	private TextView m_oNotice;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.team_page, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        MainActivity.getInstance().onBottom();
        requestTimeTable();
        Bundle bundle = getArguments();
        teamName = bundle.getString("object");
        if(teamName.equals(LoginInfoObject.getInstance().getMyteam()))
        {
        	checkTeamHost = true;
        }
        for(int i = 0 ; i < MainActivity.getTeamObject().size() ; i++)
		{
			if(teamName.equals(MainActivity.getTeamObject().get(i).getTeamName()))
			{
				selectMyTeam = MainActivity.getTeamObject().get(i);
				ranking = (i+1)+"";
				break;
			}
		}
        setUi();
    }

    public void setUi()
    {
    	m_oBtnList = (ImageView) getActivity().findViewById(R.id.title_btn_menu);
        m_oBtnList.setOnClickListener(this);
        m_oBtnSettingTeam = (ImageView) getActivity().findViewById(R.id.myteam_btn);
        m_oBtnSettingTeam.setOnClickListener(this);
    	mImg = (ImageView)getActivity().findViewById(R.id.busker_join_image_2);
		mTeamname = (TextView) getActivity().findViewById(R.id.busker_join_teamname_2);
		mGenre = (TextView)getActivity().findViewById(R.id.busker_join_genre_2);
		mTeamInfo = (TextView)getActivity().findViewById(R.id.busker_join_info_2);
		mRanking = (TextView) getActivity().findViewById(R.id.busker_join_ranking_2);
		mLike = (LinearLayout) getActivity().findViewById(R.id.like_select);
		mLike.setOnClickListener(this);
		mLikeImg = (ImageView) getActivity().findViewById(R.id.item_lineup_bottom_likecount_img);
		mLikeCount = (TextView) getActivity().findViewById(R.id.item_lineup_bottom_likecount);
		mTimeInfo  = (TextView) getActivity().findViewById(R.id.team_time_info);
		mBungeAlarm = (ImageView)getActivity().findViewById(R.id.team_time_bunge);
		mBungeAlarm.setOnClickListener(this);
		mListTime = (RelativeLayout) getActivity().findViewById(R.id.team_time_list);
		mListFacebook = (LinearLayout) getActivity().findViewById(R.id.team_facebook_table);
		m_oNotice = (TextView)getActivity().findViewById(R.id.notice);
		dataUiset();
    }
    public void dataUiset()
    {
    	try{
    	for(int i = 0 ; i< LoginInfoObject.getInstance().getLikeTeamList().size() ; i++)
		{
			if(LoginInfoObject.getInstance().getLikeTeamList().get(i).equals(selectMyTeam.getTeamName()))
			{
				mLikeImg.setBackgroundResource(R.drawable.heart_o);
				break;
			}
		}
    	
    	Drawable default1 = null;
    	default1 =  mContext.getResources().getDrawable(R.drawable.loading_new_2);
    	m_oAsyncImageLoader.setImageDrawableAsync(mImg,selectMyTeam.getTeamThum(),default1,default1,mContext);
    	mTeamname.setText(selectMyTeam.getTeamName());
    	mGenre.setText(selectMyTeam.getTeamSong());
    	mTeamInfo.setText(selectMyTeam.getTeamComent());
    	mRanking.setText("TOP " + ranking);
    	mLikeCount.setText(selectMyTeam.getLikeCount()+"");
    	m_oNotice.setText(selectMyTeam.getNotice());
    	if(checkTeamHost)
    	{
    		m_oBtnSettingTeam.setVisibility(View.VISIBLE);
    	}
    	else
    	{
    		m_oBtnSettingTeam.setVisibility(View.GONE);
    	}
    	
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
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
    public void requestGcm()
	{
		GregorianCalendar calendar = new GregorianCalendar();
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_GCM_GO_EVENT);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("teamname", LoginInfoObject.getInstance().getMyteam()));
		loginParams.add(new Params("time", time));
		loginParams.add(new Params("place", ""));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		MainActivity.getInstance().startProgressDialog();
	}	
    @Override
    public synchronized void onClick(View v) 
    {
        switch (v.getId()) 
        {
	        case R.id.team_time_bunge:
	        {
	        	if(LoginInfoObject.getInstance().getMyteam().equals(teamName))
	        	{
	        		if(time.equals(""))
		        	{
		        		Toast.makeText(mContext, "등록된 공연이 없습니다", Toast.LENGTH_SHORT).show();
		        	}
		        	else
		        	{
		        		Toast.makeText(mContext, "번개 알림!!", Toast.LENGTH_SHORT).show();
		        		requestGcm();
		        	}
	        	}
	        	else
	        	{
	        	}
	        	
	        	
	            break;
	        }
	        case R.id.title_btn_menu:
	        {
	            MainActivity.getInstance().showMenu();
	            break;
	        }
		    case R.id.myteam_btn:
		    {
		    	Bundle o = new Bundle();
	    		o.putString("object", teamName);
	    		MainActivity.getInstance().replaceFragment(TeamPageSettingFragment.class, o, true);
		        break;
		    }
		    case R.id.like_select : 
		    {

				// TODO Auto-generated method stub
				if(LoginInfoObject.getInstance().isLogin())
				{
					 for(int i = 0 ; i< selectMyTeam.getLikeMans().size() ; i++)
					 {
							if(selectMyTeam.getLikeMans().get(i).equals(LoginInfoObject.getInstance().getId()))
							{
								Toast.makeText(mContext, "이미 좋아하는 팀입니다.", Toast.LENGTH_SHORT).show();
								check = 1;
								break;
							}
					 }
				 	 if(check != 1)
				 	 { 
						//통신이후 좋아요 카운트 하나 늘리면됨 
						requestTeamLikeUp();
					 }
				}
				else
				{
					new LoginAlertPopup(mContext);
				}
			
		    	break;
		    }
        }

    }
    public String setLikeTeam()
	{
		String likeTeam = "";
		for( int i  = 0 ; i <= LoginInfoObject.getInstance().getLikeTeamList().size() ; i++)
		{
			if(LoginInfoObject.getInstance().getLikeTeamList().size() == 0 )
			{
				likeTeam = selectMyTeam.getTeamName();
				break;
			}
			if( i == LoginInfoObject.getInstance().getLikeTeamList().size() -1 )
			{
				likeTeam += LoginInfoObject.getInstance().getLikeTeamList().get(i) + ","+selectMyTeam.getTeamName();
				break;
			}
			likeTeam += LoginInfoObject.getInstance().getLikeTeamList().get(i)+",";
		}
		return likeTeam;
	}
	public String setLikeMan()
	{
		String str = "";
		for( int i = 0 ; i <= selectMyTeam.getLikeMans().size() ; i++)
		{
			if(selectMyTeam.getLikeMans().size() == 0 )
			{
				str = LoginInfoObject.getInstance().getId();
				break;
			}
			if( i == selectMyTeam.getLikeMans().size() -1)
			{
				str += selectMyTeam.getLikeMans().get(i)+","+LoginInfoObject.getInstance().getId();
				break;
			}
			str += selectMyTeam.getLikeMans().get(i)+",";
		}
		return str;
	}
	
    public void requestTeamLikeUp()
	{
		GregorianCalendar calendar = new GregorianCalendar();
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_TEAM_LIKE_UP);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("teamname", selectMyTeam.getTeamName()));
		loginParams.add(new Params("likecount", (selectMyTeam.getLikeCount() + 1)+""));
		loginParams.add(new Params("id", LoginInfoObject.getInstance().getId()));
		loginParams.add(new Params("liketeam",setLikeTeam()));
		loginParams.add(new Params("likeman", setLikeMan()));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		((MainActivity)mContext).startProgressDialog();
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
					LoginInfoObject.getInstance().getLikeTeamList().add(selectMyTeam.getTeamName());
					//로컬 카운트 + 1 , 그 팀 이 직접가지고 있는 라이크 눌른 인원들에 대한 아이디 추가
					for(int i = 0 ; i < MainActivity.getTeamObject().size() ; i++)
					{
						if(selectMyTeam.getTeamName().equals(MainActivity.getTeamObject().get(i).getTeamName()))
						{
							MainActivity.getTeamObject().get(i).setLikeCount(MainActivity.getTeamObject().get(i).getLikeCount()+1);
							MainActivity.getTeamObject().get(i).getLikeMans().add(LoginInfoObject.getInstance().getId());
							dataUiset();
							break;
							
						}
					}
					
				}
				else
				{
					Toast.makeText(mContext, "좋아요 실패했습니다", Toast.LENGTH_LONG).show();
				}
			}
			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_TIME_TABLE"))
			{
				JSONArray todayTime = object.getJSONArray("data");
				for(int i=0; i<todayTime.length(); i++)
				{
					JSONObject jsonObject = todayTime.getJSONObject(i);
					String year = jsonObject.optString("year");
					String month = jsonObject.optString("month");
					String day = jsonObject.optString("day");
					String time = jsonObject.optString("time");
					String dayOfweek = jsonObject.optString("dayOfweek");
					String place = jsonObject.optString("place");
					String teamname = jsonObject.optString("teamname");
					String joincount = jsonObject.optString("joincount");
					if(teamname.equals(this.teamName))
					{
						lineUpObject.add(new LineUpObject(year,month,day,time,dayOfweek,place,teamname,joincount));
					}
				}
				if(lineUpObject.size() != 0)
		    	{
					time = lineUpObject.get(0).getMonth()+"/"+lineUpObject.get(0).getDay()+" "+lineUpObject.get(0).getDayOfweek() 
		    				+" " +lineUpObject.get(0).getTime()+" @"+lineUpObject.get(0).getPlace();
		    		mTimeInfo.setText(time);
		    	}
		    	else
		    	{
		    		mTimeInfo.setText("등록된 공연이 없습니다");

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
	public void requestTimeTable()
	{
		GregorianCalendar calendar = new GregorianCalendar();
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_TIME_TABLE);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("type", Main_LineUp_Page.TIME_TABLE_MONTH));
		loginParams.add(new Params("year", calendar.get(Calendar.YEAR)+""));
		loginParams.add(new Params("month", (calendar.get(Calendar.MONTH)+1)+""));
		loginParams.add(new Params("day", calendar.get(Calendar.DATE)+""));
		loginParams.add(new Params("place",""));
		loginParams.add(new Params("teamname",""));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		MainActivity.getInstance().startProgressDialog();
	}

}
