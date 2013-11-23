package com.cultureshock.buskingbook.main;


import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.component.LoadingPopup;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.object.TeamObject;
import com.cultureshock.buskingbook.page.MainHomeFragment;
import com.cultureshock.buskingbook.service.ServiceType;

public class LoginActivity extends Activity implements View.OnClickListener, HttpClientNet.OnResponseListener {
    private Context mContext;
    private LoadingPopup loading;
    private EditText m_oEmail;
    private EditText m_oPassword;
    private LinearLayout m_oBtnFacebookConfirm;
    private LinearLayout m_oBtnConfirm;
    private LinearLayout m_oBtnAutoLogin;
    private ImageView m_oImgAutoLogin;
    private boolean checkAutoLogin = true;
    
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login2);
        mContext = this;
        setUi();
       
    }
    public void setUi()
    {
    	m_oEmail = (EditText)findViewById(R.id.login_email);
    	m_oPassword = (EditText)findViewById(R.id.login_password);
    	m_oBtnConfirm = (LinearLayout)findViewById(R.id.btn_login);
    	m_oBtnFacebookConfirm = (LinearLayout)findViewById(R.id.btn_facebook_login);
    	m_oBtnAutoLogin  = (LinearLayout)findViewById(R.id.btn_autologin);
    	m_oImgAutoLogin = (ImageView)findViewById(R.id.auto_login_check);
    	m_oBtnAutoLogin.setOnClickListener(this);
    	m_oBtnFacebookConfirm.setOnClickListener(this);
    	m_oBtnConfirm.setOnClickListener(this);
    }
  
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onDestroy() {
       
        super.onDestroy();
        m_oEmail = null;
    	m_oPassword = null;
    	m_oBtnConfirm = null;
    	m_oBtnFacebookConfirm = null;
    	m_oBtnAutoLogin  = null;
    	m_oImgAutoLogin = null;
    	m_oBtnConfirm = null;
    }
    public void requestLogin()
	{
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_LOGIN);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("id",m_oEmail.getText().toString()));
		loginParams.add(new Params("pwd",m_oPassword.getText().toString()));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		startProgressDialog();
	}
	
	public void startProgressDialog() 
	{
		if( loading == null )
		{
			loading = new LoadingPopup(this);
			loading.start();
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
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.btn_autologin :
			{
				if(checkAutoLogin)
				{
					m_oImgAutoLogin.setBackgroundResource(R.drawable.checkbox);
					checkAutoLogin = false;
				}
				else
				{
					
					m_oImgAutoLogin.setBackgroundResource(R.drawable.checkbox_o);
					checkAutoLogin = true;
				}
			}
			case R.id.btn_facebook_login : 
			{
				break;
			}
			case R.id.btn_login : 
			{
				if(m_oEmail.getText().toString().equals(""))
				{
					Toast.makeText(this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
				}
				else if(m_oPassword.getText().toString().equals("") )
				{
					Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
				}
				else
				{
					requestLogin();
				}
				break;
			}
		}
		
	}

	@Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		try{
		Object o = resContent;
		JSONObject object = new JSONObject(resContent);
		if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_LOGIN"))
		{
			String result = object.getJSONObject("data").optString("result","");
			String reason = object.getJSONObject("data").optString("reason","");
			if(result.equals("true"))
			{
				String id = object.getJSONObject("data").optString("id","");
				String pwd = object.getJSONObject("data").optString("pwd","");
				String name = object.getJSONObject("data").optString("name","");
				String phone = object.getJSONObject("data").optString("phone","");
				String myImg = object.getJSONObject("data").optString("myImg","");
				String likeTeamList = object.getJSONObject("data").optString("likeTeamList","");
 				String myteam = object.getJSONObject("data").optString("myteam","");
				String[] likeTeam = likeTeamList.split(",");
				ArrayList<String> likeTeamArrayList = new ArrayList<String>();
				if(!likeTeam[0].equals(""))
				{
					for(int i = 0 ; i < likeTeam.length ; i++)
					{
						likeTeamArrayList.add(likeTeam[i]);
					}
				}
				LoginInfoObject.getInstance().setLogin(id, pwd, name, phone, myImg, likeTeamArrayList, myteam);
				Intent intent =  new Intent(this.getApplicationContext(), MainActivity.class);
				startActivity(intent);
				LoginJoinActivity.getInstance().finish();
				finish();
				ArrayList<TeamObject> obj = MainActivity.getTeamObject();
				if(checkAutoLogin)
				{
					SharedPreferences sp = getSharedPreferences("autologin", MODE_PRIVATE);
					SharedPreferences.Editor editer = sp.edit();
					editer.putBoolean("autologinboolean", true);
					editer.putString("id", id);
					editer.putString("pwd", pwd);
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
   
}
