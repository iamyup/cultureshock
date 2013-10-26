package com.cultureshock.buskingbook.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cultureshock.buskingbook.FirstStartActivity;
import com.cultureshock.buskingbook.GCMIntentService;
import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.page.BuskerJoinFragment;
import com.cultureshock.buskingbook.page.PartnerSearchFragment;
import com.cultureshock.buskingbook.page.MainHomeFragment;
import com.cultureshock.buskingbook.page.TeamPageFragment;
import com.cultureshock.buskingbook.page.TimeJoinFragment;
import com.cultureshock.buskingbook.page.LikeTeamFragment;
import com.cultureshock.buskingbook.util.AsyncImageLoader;

public class LeftMenuFragment extends Fragment implements View.OnClickListener {
    private static Activity mActivity;
    private static LeftMenuFragment mInstance;
    private TextView mBtnTempMusician;
    private TextView mBtnTempNormal;
    
    private AsyncImageLoader m_oAsyncImageLoader = new AsyncImageLoader();
    
    private LinearLayout m_oLayoutNoLogin;
    private LinearLayout m_oLayoutLogin;
    
    private TextView m_oTxtMyName;
    private TextView m_oTxtMyTeam;
    private ImageView m_oImgMy;
    private LinearLayout m_LayoutMusician;
    private LinearLayout m_BtnBuskerPage;
    private LinearLayout m_BtnBuskerJoin;
    private RelativeLayout m_BtnPartnerSearch;
    private LinearLayout m_BtnBuskerSearch;
    private LinearLayout m_BtnLikeSearh;
    private RelativeLayout m_BtnAlarm;
    private RelativeLayout m_BtnInfo;
    private LinearLayout m_BtnBuskerRegister;
    private RelativeLayout m_BtnLogout;
    private ImageView m_oImgAutoLogin;
    private boolean autoLogin = false;
    private ImageView m_oImgAlarm;

    public static LeftMenuFragment getInstance() {
        if (mInstance == null) {
            mInstance = new LeftMenuFragment();
        }
        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_left_menu_musician, container,
                false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = getActivity();
        mInstance = this;
        mBtnTempMusician = (TextView) getActivity().findViewById(
                R.id.temp_musician);
        mBtnTempNormal = (TextView) getActivity()
                .findViewById(R.id.temp_normal);
        if (mBtnTempMusician != null) {
            mBtnTempMusician.setOnClickListener(this);
        }
        if (mBtnTempNormal != null) {
            mBtnTempNormal.setOnClickListener(this);
        }
        setUi();
    }
    public void setUi()
    {
    	m_oLayoutNoLogin = (LinearLayout)getActivity().findViewById(R.id.left_menu_no_login);
    	m_oLayoutLogin = (LinearLayout)getActivity().findViewById(R.id.left_menu_login);
    	m_BtnLogout = (RelativeLayout)getActivity().findViewById(R.id.logout_layout);
    	m_oLayoutNoLogin.setOnClickListener(this);
    	if(LoginInfoObject.getInstance().getId().equals(""))
    	{
    		m_oLayoutNoLogin.setVisibility(View.VISIBLE);
    		m_BtnLogout.setVisibility(View.GONE);
    		m_oLayoutLogin.setVisibility(View.GONE);
    	}
    	else
    	{
    		m_BtnLogout.setVisibility(View.VISIBLE);
    		m_oLayoutNoLogin.setVisibility(View.GONE);
    		m_oLayoutLogin.setVisibility(View.VISIBLE);
    		m_oImgMy = (ImageView)getActivity().findViewById(R.id.profile_image);
    		Drawable default1;
    		
    		Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/tmp_profile.jpg");
    		if (bitmap != null) {
    		    default1 = new BitmapDrawable(getResources(), bitmap);
    		} else {
    		    default1 = getActivity().getResources().getDrawable(R.drawable.default_pf);
    		}
    		m_oAsyncImageLoader.setImageDrawableAsync(m_oImgMy,LoginInfoObject.getInstance().getMyImg(),default1,default1,getActivity());
        	m_oTxtMyName = (TextView)getActivity().findViewById(R.id.name_home_left);
        	m_oTxtMyName.setText(LoginInfoObject.getInstance().getName());
        	m_oTxtMyTeam = (TextView)getActivity().findViewById(R.id.team_name_home_left);
        	m_oTxtMyTeam.setText(LoginInfoObject.getInstance().getMyteam());
        	
        	m_LayoutMusician = (LinearLayout)getActivity().findViewById(R.id.musician_join);
        	
            m_BtnBuskerPage = (LinearLayout)getActivity().findViewById(R.id.musician_page);
            m_BtnBuskerJoin = (LinearLayout)getActivity().findViewById(R.id.musician_busking_join);
            m_BtnPartnerSearch = (RelativeLayout)getActivity().findViewById(R.id.musician_partner);
            
            m_BtnBuskerSearch = (LinearLayout)getActivity().findViewById(R.id.musician_busker_search); 
            m_BtnLikeSearh = (LinearLayout)getActivity().findViewById(R.id.musician_like_search);
            m_BtnAlarm = (RelativeLayout)getActivity().findViewById(R.id.musician_alarm);
            m_BtnInfo = (RelativeLayout)getActivity().findViewById(R.id.musician_info);
            m_BtnBuskerRegister = (LinearLayout)getActivity().findViewById(R.id.busker_join_btn);
            m_oImgAutoLogin = (ImageView)getActivity().findViewById(R.id.musician_privacy_auto_two);
            m_oImgAlarm = (ImageView)getActivity().findViewById(R.id.busker_alarm_img_two);
            m_BtnBuskerPage.setOnClickListener(this);
            m_BtnBuskerJoin.setOnClickListener(this);
            m_BtnPartnerSearch.setOnClickListener(this);
            m_BtnBuskerSearch.setOnClickListener(this);
            m_BtnLikeSearh.setOnClickListener(this);
            m_BtnAlarm.setOnClickListener(this);
            m_BtnInfo.setOnClickListener(this);
            m_BtnBuskerRegister.setOnClickListener(this);
            m_BtnLogout.setOnClickListener(this);
            if(LoginInfoObject.getInstance().getMyteam().equals("") )
            {
            	m_LayoutMusician.setVisibility(View.GONE);
            	m_BtnBuskerRegister.setVisibility(View.VISIBLE);
            }
            else
            {
            	m_LayoutMusician.setVisibility(View.VISIBLE);
            	m_BtnBuskerRegister.setVisibility(View.GONE);
            }
            SharedPreferences sp = mActivity.getSharedPreferences("autologin", mActivity.MODE_PRIVATE);
            if(sp.getBoolean("autologinboolean", false))
    		{
    			autoLogin = false;
    			m_oImgAutoLogin.setBackgroundResource(R.drawable.on_btn);
    		}
    		else
    		{
    			autoLogin = true;
    			m_oImgAutoLogin.setBackgroundResource(R.drawable.off_btn);
    		}
            SharedPreferences sp2 = mActivity.getSharedPreferences("isPopup", mActivity.MODE_PRIVATE);
        	GCMIntentService.isPopup = sp2.getBoolean("autologinboolean", true);
        	
            if(GCMIntentService.isPopup)
            {
            	m_oImgAlarm.setBackgroundResource(R.drawable.on_btn);
            }
            else
            {
            	m_oImgAlarm.setBackgroundResource(R.drawable.off_btn);
            }
    	}
    	
        
    }

    // the meat of switching the above fragment
    private void switchFragment(Fragment fragment) {
        if (getActivity() == null)
            return;

        if (getActivity() instanceof BuskingMainActivity) {
            BuskingMainActivity fca = (BuskingMainActivity) getActivity();
            fca.switchContent(fragment);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.support.v4.app.Fragment#onResume()
     */
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }
    public void loginSatatus()
    {
    	if(LoginInfoObject.getInstance().getId().equals(""))
    	{
    		m_BtnLogout.setVisibility(View.GONE);
    		m_oLayoutNoLogin.setVisibility(View.VISIBLE);
    		m_oLayoutLogin.setVisibility(View.GONE);
    		Drawable default1;
    		default1 = getActivity().getResources().getDrawable(R.drawable.default_pf);
    		m_oAsyncImageLoader.setImageDrawableAsync(m_oImgMy,LoginInfoObject.getInstance().getMyImg(),default1,default1,getActivity());
        	m_oTxtMyName.setText(LoginInfoObject.getInstance().getName());
        	m_oTxtMyTeam.setText(LoginInfoObject.getInstance().getMyteam());
    	}
    	else
    	{
    		m_BtnLogout.setVisibility(View.VISIBLE);
    		m_oLayoutNoLogin.setVisibility(View.GONE);
    		m_oLayoutLogin.setVisibility(View.VISIBLE);
    		
    		Drawable default1;
    		default1 = getActivity().getResources().getDrawable(R.drawable.default_pf);
    		m_oAsyncImageLoader.setImageDrawableAsync(m_oImgMy,LoginInfoObject.getInstance().getMyImg(),default1,default1,getActivity());
        	m_oTxtMyName.setText(LoginInfoObject.getInstance().getName());
        	m_oTxtMyTeam.setText(LoginInfoObject.getInstance().getMyteam());
    		
    	}
    	if(LoginInfoObject.getInstance().getMyteam().equals("") )
        {
        	m_LayoutMusician.setVisibility(View.GONE);
        	m_oTxtMyTeam.setText("");
        	m_BtnBuskerRegister.setVisibility(View.VISIBLE);
        }
        else
        {
        	m_LayoutMusician.setVisibility(View.VISIBLE);
        	m_oTxtMyTeam.setText(LoginInfoObject.getInstance().getMyteam());
        	m_BtnBuskerRegister.setVisibility(View.GONE);
        }
    	
    	
    	SharedPreferences sp = mActivity.getSharedPreferences("autologin", mActivity.MODE_PRIVATE);
		if(sp.getBoolean("autologinboolean", false))
		{
			autoLogin = false;
			m_oImgAutoLogin.setBackgroundResource(R.drawable.on_btn);
		}
		else
		{
			autoLogin = true;
			m_oImgAutoLogin.setBackgroundResource(R.drawable.off_btn);
		}
    	
    }

    @Override
    public void onClick(View v) {
    	switch(v.getId())
    	{
	    	case R.id.musician_page:
	    	{
	    		Bundle o = new Bundle();
	    		o.putString("object", LoginInfoObject.getInstance().getMyteam());
	    		MainActivity.getInstance().replaceFragment(TeamPageFragment.class, o, true);
	    		break;
	    	}
	    	case R.id.musician_busking_join:
	    	{
	    		MainActivity.getInstance().replaceFragment(TimeJoinFragment.class, null, false);
	    		break;
	    	}
	    	case R.id.musician_partner:
	    	{
	    		MainActivity.getInstance().replaceFragment(PartnerSearchFragment.class, null, false);
	    		break;
	    	}
	    	case R.id.musician_busker_search:
	    	{
	    		break;
	    	}
	    	case R.id.musician_like_search:
	    	{
	    		MainActivity.getInstance().replaceFragment(LikeTeamFragment.class, null, false);
	    		break;
	    	}
	    	case R.id.musician_alarm:
	    	{
	    		if(GCMIntentService.isPopup)
	            {
	    			SharedPreferences sp = mActivity.getSharedPreferences("isPopup", mActivity.MODE_PRIVATE);
					SharedPreferences.Editor editer = sp.edit();
					editer.putBoolean("isPopupboolean", false);
					editer.commit();
	    			GCMIntentService.isPopup = false;
	            	m_oImgAlarm.setBackgroundResource(R.drawable.off_btn);
	            }
	            else
	            {
	            	SharedPreferences sp = mActivity.getSharedPreferences("isPopup", mActivity.MODE_PRIVATE);
					SharedPreferences.Editor editer = sp.edit();
					editer.putBoolean("isPopupboolean", true);
					editer.commit();
	            	GCMIntentService.isPopup = true;
	            	m_oImgAlarm.setBackgroundResource(R.drawable.on_btn);
	            }
	    		break;
	    	}
	    	case R.id.musician_info:
	    	{
	    		if(autoLogin)
	    		{
	    			SharedPreferences sp = mActivity.getSharedPreferences("autologin", mActivity.MODE_PRIVATE);
					SharedPreferences.Editor editer = sp.edit();
					editer.putBoolean("autologinboolean", true);
					editer.putString("id", LoginInfoObject.getInstance().getId());
					editer.putString("pwd", LoginInfoObject.getInstance().getPwd());
					editer.commit();
	    			m_oImgAutoLogin.setBackgroundResource(R.drawable.on_btn);
	    			autoLogin = false;
	    			
	    		}
	    		else
	    		{
	    			SharedPreferences sp = mActivity.getSharedPreferences("autologin", mActivity.MODE_PRIVATE);
					SharedPreferences.Editor editer = sp.edit();
					editer.putBoolean("autologinboolean", false);
					editer.remove("id");
					editer.remove("pwd");
					editer.commit();
	    			
	    			m_oImgAutoLogin.setBackgroundResource(R.drawable.off_btn);
	    			autoLogin = true;
	    		}
	    		
	    		break;
	    	}
	    	case R.id.busker_join_btn:
	    	{
	    		MainActivity.getInstance().replaceFragment(BuskerJoinFragment.class, null, false);
	    		break;
	    	}
	    	case R.id.logout_layout:
	    	{
	    		LoginInfoObject.getInstance().setLogout();
	    		loginSatatus();
	    		MainActivity.getInstance().replaceFragment(MainHomeFragment.class, null, false);
	    		break;
	    	}
	    	case R.id.left_menu_no_login :
	    	{
	    		Intent intent = new Intent(getActivity().getApplicationContext(),
	                    LoginActivity.class);
	            startActivity(intent);
	            getActivity().finish();
	    		break;
	    	}
    	
    	}
    }

}
