
package com.cultureshock.buskingbook.main;


import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cultureshock.buskingbook.FirstStartActivity;
import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.component.LoadingPopup;
import com.cultureshock.buskingbook.component.LoginAlertPopup;
import com.cultureshock.buskingbook.list.LineUpListView;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.LineUpObject;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.object.TeamObject;
import com.cultureshock.buskingbook.page.LikeTeamFragment;
import com.cultureshock.buskingbook.page.MainHomeFragment;
import com.cultureshock.buskingbook.page.PaperEditFragment;
import com.cultureshock.buskingbook.page.PartnerSearchFragment;
import com.cultureshock.buskingbook.page.TeamPageFragment;
import com.cultureshock.buskingbook.page.TeamPageSettingFragment;
import com.cultureshock.buskingbook.service.ServiceType;


public class MainActivity extends BuskingMainActivity implements OnClickListener,HttpClientNet.OnResponseListener {
	
	private static ArrayList<TeamObject> teamObject = new ArrayList<TeamObject>();
	private Context mContext;
    private static MainActivity mInstance;
    private LoadingPopup loading;
    
    private LinearLayout m_oBtnBottom;
    private LinearLayout m_oBtnSearchHome;
	private LinearLayout m_oBtnSearchFriend;
	private LinearLayout m_oBtnSearchLike;
	private LinearLayout m_oBtnSearchTeam;
	
	private ImageView m_oImgSearchHome;
	private ImageView m_oImgSearchFriend;
	private ImageView m_oImgSearchLike;
	private ImageView m_oImgSearchTeam;
	
	private TextView m_oTxtSelectHome;
	private TextView m_oTxtSelectFriend;
	private TextView m_oTxtSelectLike;
	private TextView m_oTxtSelectTeam;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	 super.onCreate(savedInstanceState);
         setContentView(R.layout.main);
         mContext = this;
         mInstance = this;
         requestRegId();
         setUi();
    }
    public void setUi()
    {
    	m_oTxtSelectHome = (TextView) findViewById(R.id.select_all_home_text);
		m_oTxtSelectFriend = (TextView) findViewById(R.id.select_all_friend_text);
		m_oTxtSelectLike = (TextView) findViewById(R.id.select_all_like_text);
		m_oTxtSelectTeam = (TextView) findViewById(R.id.select_all_team_text);
		
		
		m_oBtnBottom= (LinearLayout) findViewById(R.id.lineup_bottom);
		
		m_oBtnSearchHome = (LinearLayout) findViewById(R.id.select_home_layout);
		m_oBtnSearchHome.setOnClickListener(this);
		m_oBtnSearchFriend = (LinearLayout) findViewById(R.id.select_friend_layout);
		m_oBtnSearchFriend.setOnClickListener(this);
		m_oBtnSearchLike = (LinearLayout) findViewById(R.id.select_like_layout);
		m_oBtnSearchLike.setOnClickListener(this);
		m_oBtnSearchTeam = (LinearLayout) findViewById(R.id.select_team_layout);
		m_oBtnSearchTeam.setOnClickListener(this);
		
		m_oImgSearchHome = (ImageView) findViewById(R.id.select_home_img);
		m_oImgSearchFriend= (ImageView) findViewById(R.id.select_friend_img);
		m_oImgSearchLike= (ImageView) findViewById(R.id.select_like_img);
		m_oImgSearchTeam= (ImageView) findViewById(R.id.select_team_img);
		if(LoginInfoObject.getInstance().getMyteam().equals(""))
		{
			m_oImgSearchTeam.setBackgroundResource(R.drawable.planet_ic_o);	
			m_oBtnSearchTeam.setBackgroundColor(0xffe6e7e9);
			m_oTxtSelectTeam.setTextColor(0xffbcbdbf);
		}
		else
		{
			m_oImgSearchTeam.setBackgroundResource(R.drawable.planet_btn);	
			m_oBtnSearchTeam.setBackgroundColor(0xffffffff);
			m_oTxtSelectTeam.setTextColor(0xff01d0d2);
		}
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public static MainActivity getInstance() {
        return mInstance;
    }
    public void offBottom()
    {
    	m_oBtnBottom.setVisibility(View.GONE);
    }
    public void onBottom()
    {
    	m_oBtnBottom.setVisibility(View.VISIBLE);
    }
    public void requestRegId()
    {
    	HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_GCM_ID_JOIN);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("id",LoginInfoObject.getInstance().getId()));
		loginParams.add(new Params("regId",FirstStartActivity.regId));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		startProgressDialog();
    }
	public static ArrayList<TeamObject> getTeamObject() {
		return teamObject;
	}

	public static void setTeamObject(ArrayList<TeamObject> teamObject) {
		MainActivity.teamObject = teamObject;
	}
	public static TeamObject searchTeam(String teamName)
	{
		for(int i = 0 ; i < teamObject.size() ; i++)
		{
			if(teamObject.get(i).getTeamName().equals(teamName))
			{
				return teamObject.get(i);
			}
		}
		return null;
	}
	public void startProgressDialog() 
	{
		if( loading == null )
		{
			loading = new LoadingPopup(this);
			loading.start();
		}
	}
    @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
    	Log.d("sf",getCurFragment().getClass().getName());
    	if(getCurFragment().getClass().getName().equals("com.cultureshock.buskingbook.page.MainHomeFragment"))
    	{
    		if(!LeftMenuFragment.getInstance().autoLogin)
			{
				SharedPreferences sp = getSharedPreferences("autologin", MODE_PRIVATE);
				SharedPreferences.Editor editer = sp.edit();
				editer.putBoolean("autologinboolean", true);
				editer.putString("id", LoginInfoObject.getInstance().getId());
				editer.putString("pwd", LoginInfoObject.getInstance().getPwd());
				editer.commit();
			}
			else
			{
				SharedPreferences sp = getSharedPreferences("autologin", MODE_PRIVATE);
				SharedPreferences.Editor editer = sp.edit();
				editer.putBoolean("autologinboolean", false);
				editer.remove("id");
				editer.remove("pwd");
				editer.commit();
			}
    		finish();
   		 	MainActivity.getTeamObject().clear();
   		 	System.exit(0);
    	}
    	else if(getCurFragment().getClass().getName().equals("com.cultureshock.buskingbook.page.PartnerSearchAddFragment"))
		{
    		MainActivity.getInstance().replaceFragment(PartnerSearchFragment.class, null, false);
		}
    	else if(getCurFragment().getClass().getName().equals("com.cultureshock.buskingbook.page.TeamPageSettingFragment"))
		{
    		TeamPageSettingFragment.getInstance().back();
		}
    	else if(getCurFragment().getClass().getName().equals("com.cultureshock.buskingbook.page.TeamPageFragment"))
		{
			MainActivity.getInstance().replaceFragment(MainHomeFragment.class, null, false);
			m_oBtnSearchHome.setBackgroundColor(0xff01d0d2);
			m_oBtnSearchFriend.setBackgroundColor(0xffffffff);
			m_oBtnSearchLike.setBackgroundColor(0xffffffff);

			m_oTxtSelectHome.setTextColor(0xffffffff);
			m_oTxtSelectFriend.setTextColor(0xff01d0d2);
			m_oTxtSelectLike.setTextColor(0xff01d0d2);
			
			
			m_oImgSearchHome.setBackgroundResource(R.drawable.explore_btn_o);
			m_oImgSearchFriend.setBackgroundResource(R.drawable.friends_btn);
			m_oImgSearchLike.setBackgroundResource(R.drawable.love_btn);
			if(LoginInfoObject.getInstance().getMyteam().equals(""))
			{
				m_oImgSearchTeam.setBackgroundResource(R.drawable.planet_ic_o);	
				m_oBtnSearchTeam.setBackgroundColor(0xffe6e7e9);
				m_oTxtSelectTeam.setTextColor(0xffbcbdbf);
			}
			else
			{
				m_oImgSearchTeam.setBackgroundResource(R.drawable.planet_btn);	
				m_oBtnSearchTeam.setBackgroundColor(0xffffffff);
				m_oTxtSelectTeam.setTextColor(0xff01d0d2);
			}
		}
    	else
    	{
    		MainActivity.getInstance().replaceFragment(MainHomeFragment.class, null, false);
    	}
		 
		
	}
	public void stopProgressDialog() 
	{
		if( loading != null )
		{
			loading.stop();
			loading = null;
		}
	}

    public void showPaper() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Fragment frg = fm.findFragmentById(R.id.content_frame);
        if ( frg.getClass() != PaperEditFragment.class ) {
            replaceFragment(PaperEditFragment.class, null, false);
        }
    }

	@Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		try{
			Object o = resContent;
			JSONObject object = new JSONObject(resContent);
			if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_GCM_ID_JOIN"))
			{
				String result = object.getJSONObject("data").optString("result","");
				String reason = object.getJSONObject("data").optString("reason","");
				if(result.equals("true"))
				{
//					Toast.makeText(this, "레그아이디등록", Toast.LENGTH_LONG).show();
				}
				else
				{
					Toast.makeText(this, reason, Toast.LENGTH_LONG).show();
				}
			}
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			stopProgressDialog() ;
		}

	}	
	public void replaceFragment(Class<?> clss, Bundle bundle, boolean isAddStack) {
        Fragment fragment = Fragment.instantiate(this, clss.getName(), bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        if ( isAddStack ) {
            ft.addToBackStack(null);
        }
        ft.commitAllowingStateLoss();
        MainActivity.getInstance().showContent();
    }

    public Fragment getCurFragment() {
        Fragment frg = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        return frg;
    }
    public  void goHomeFragment(Context context) {
        if ( context instanceof MainActivity ) {
            FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
            fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            Fragment frg = fm.findFragmentById(R.id.content_frame);
            if ( frg.getClass() != MainHomeFragment.class ) {
                replaceFragment(MainHomeFragment.class, null, false);
            }
        }
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		onBottom();
		switch(v.getId())
		{
			
			case R.id.select_home_layout: {
	
				m_oBtnSearchHome.setBackgroundColor(0xff01d0d2);
				m_oBtnSearchFriend.setBackgroundColor(0xffffffff);
				m_oBtnSearchLike.setBackgroundColor(0xffffffff);
	
				m_oTxtSelectHome.setTextColor(0xffffffff);
				m_oTxtSelectFriend.setTextColor(0xff01d0d2);
				m_oTxtSelectLike.setTextColor(0xff01d0d2);
				
				
				m_oImgSearchHome.setBackgroundResource(R.drawable.explore_btn_o);
				m_oImgSearchFriend.setBackgroundResource(R.drawable.friends_btn);
				m_oImgSearchLike.setBackgroundResource(R.drawable.love_btn);
				if(LoginInfoObject.getInstance().getMyteam().equals(""))
				{
					m_oImgSearchTeam.setBackgroundResource(R.drawable.planet_ic_o);	
					m_oBtnSearchTeam.setBackgroundColor(0xffe6e7e9);
					m_oTxtSelectTeam.setTextColor(0xffbcbdbf);
				}
				else
				{
					m_oImgSearchTeam.setBackgroundResource(R.drawable.planet_btn);	
					m_oBtnSearchTeam.setBackgroundColor(0xffffffff);
					m_oTxtSelectTeam.setTextColor(0xff01d0d2);
				}
				MainActivity.getInstance().replaceFragment(MainHomeFragment.class, null, false);
				break;
			}
			case R.id.select_friend_layout: {
				if (LoginInfoObject.getInstance().isLogin()) 
				{
					m_oBtnSearchHome.setBackgroundColor(0xffffffff);
					m_oBtnSearchFriend.setBackgroundColor(0xff01d0d2);
					m_oBtnSearchLike.setBackgroundColor(0xffffffff);
		
					m_oTxtSelectHome.setTextColor(0xff01d0d2);
					m_oTxtSelectFriend.setTextColor(0xffffffff);
					m_oTxtSelectLike.setTextColor(0xff01d0d2);
					
					m_oImgSearchHome.setBackgroundResource(R.drawable.explore_btn);
					m_oImgSearchFriend.setBackgroundResource(R.drawable.friends_btn_o);
					m_oImgSearchLike.setBackgroundResource(R.drawable.love_btn);
					if(LoginInfoObject.getInstance().getMyteam().equals(""))
					{
						m_oImgSearchTeam.setBackgroundResource(R.drawable.planet_ic_o);	
						m_oBtnSearchTeam.setBackgroundColor(0xffe6e7e9);
						m_oTxtSelectTeam.setTextColor(0xffbcbdbf);
					}
					else
					{
						m_oImgSearchTeam.setBackgroundResource(R.drawable.planet_btn);	
						m_oBtnSearchTeam.setBackgroundColor(0xffffffff);
						m_oTxtSelectTeam.setTextColor(0xff01d0d2);
					}
					MainActivity.getInstance().replaceFragment(PartnerSearchFragment.class, null, false);
				}
				else
				{
					new LoginAlertPopup(mContext);
				}
				break;
			}
			
			case R.id.select_like_layout: {
				if (LoginInfoObject.getInstance().isLogin()) 
				{
					m_oBtnSearchHome.setBackgroundColor(0xffffffff);
					m_oBtnSearchFriend.setBackgroundColor(0xffffffff);
					m_oBtnSearchLike.setBackgroundColor(0xff01d0d2);
		
					m_oTxtSelectHome.setTextColor(0xff01d0d2);
					m_oTxtSelectFriend.setTextColor(0xff01d0d2);
					m_oTxtSelectLike.setTextColor(0xffffffff);
					
					m_oImgSearchHome.setBackgroundResource(R.drawable.explore_btn);
					m_oImgSearchFriend.setBackgroundResource(R.drawable.friends_btn);
					m_oImgSearchLike.setBackgroundResource(R.drawable.love_btn_o);
					if(LoginInfoObject.getInstance().getMyteam().equals(""))
					{
						m_oImgSearchTeam.setBackgroundResource(R.drawable.planet_ic_o);	
						m_oBtnSearchTeam.setBackgroundColor(0xffe6e7e9);
						m_oTxtSelectTeam.setTextColor(0xffbcbdbf);
					}
					else
					{
						m_oImgSearchTeam.setBackgroundResource(R.drawable.planet_btn);	
						m_oBtnSearchTeam.setBackgroundColor(0xffffffff);
						m_oTxtSelectTeam.setTextColor(0xff01d0d2);
					}
		
					MainActivity.getInstance().replaceFragment(LikeTeamFragment.class, null, false);
				}
				else
				{
					new LoginAlertPopup(mContext);
				}
				break;
			}
			case R.id.select_team_layout: {
				if (LoginInfoObject.getInstance().isLogin()) 
				{
					if(!LoginInfoObject.getInstance().getMyteam().equals(""))
					{
						m_oBtnSearchHome.setBackgroundColor(0xffffffff);
						m_oBtnSearchFriend.setBackgroundColor(0xffffffff);
						m_oBtnSearchLike.setBackgroundColor(0xffffffff);
			
						m_oTxtSelectHome.setTextColor(0xff01d0d2);
						m_oTxtSelectFriend.setTextColor(0xff01d0d2);
						m_oTxtSelectLike.setTextColor(0xff01d0d2);
						
						m_oImgSearchHome.setBackgroundResource(R.drawable.explore_btn);
						m_oImgSearchFriend.setBackgroundResource(R.drawable.friends_btn);
						m_oImgSearchLike.setBackgroundResource(R.drawable.love_btn);
						if(LoginInfoObject.getInstance().getMyteam().equals(""))
						{
							m_oImgSearchTeam.setBackgroundResource(R.drawable.planet_ic_o);	
							m_oBtnSearchTeam.setBackgroundColor(0xffe6e7e9);
							m_oTxtSelectTeam.setTextColor(0xffbcbdbf);
						}
						else
						{
							m_oImgSearchTeam.setBackgroundResource(R.drawable.planet_btn_o);	
							m_oBtnSearchTeam.setBackgroundColor(0xff01d0d2);
							m_oTxtSelectTeam.setTextColor(0xffffffff);
						}
			
						Bundle o = new Bundle();
			    		o.putString("object", LoginInfoObject.getInstance().getMyteam());
			    		MainActivity.getInstance().replaceFragment(TeamPageFragment.class, o, true);
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
	public void checkDim()
	{
		if(LoginInfoObject.getInstance().getMyteam().equals(""))
		{
			m_oImgSearchTeam.setBackgroundResource(R.drawable.planet_ic_o);	
			m_oBtnSearchTeam.setBackgroundColor(0xffe6e7e9);
			m_oTxtSelectTeam.setTextColor(0xffbcbdbf);
		}
		else
		{
			m_oImgSearchTeam.setBackgroundResource(R.drawable.planet_btn);	
			m_oBtnSearchTeam.setBackgroundColor(0xffffffff);
			m_oTxtSelectTeam.setTextColor(0xff01d0d2);
		}
	}
  
}
