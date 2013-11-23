package com.cultureshock.buskingbook.main;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.*;
import com.facebook.model.*;
import com.cultureshock.buskingbook.FacebookInfo;
import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.component.LoadingPopup;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.object.TeamObject;
import com.cultureshock.buskingbook.service.ServiceType;

public class LoginJoinActivity extends Activity implements View.OnClickListener  , HttpClientNet.OnResponseListener {
    private Context mContext;
    private static LoginJoinActivity mInstance;
    private LinearLayout m_oBtnJoinFacefook;
    private LinearLayout m_oBtnJoinEmail;
    private LinearLayout m_oBtnLogin;
    private LoadingPopup loading;
    private boolean check = false;
    private String id = "";
    private String mUserName = "";
    private String mThum = "" ;
    @Override
    public View onCreateView(View parent, String name, Context context,
            AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_join);
        mContext = this;
        mInstance = this;
        setUi();

    }

    public void setUi() {
        m_oBtnJoinEmail = (LinearLayout) findViewById(R.id.btn_join_email);
        m_oBtnJoinFacefook = (LinearLayout) findViewById(R.id.btn_join_facebook);
        m_oBtnLogin = (LinearLayout) findViewById(R.id.btn_login);
        m_oBtnJoinEmail.setOnClickListener(this);
        m_oBtnJoinFacefook.setOnClickListener(this);
        m_oBtnLogin.setOnClickListener(this);
    }

    public static LoginJoinActivity getInstance() {
        return mInstance;
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
        m_oBtnJoinFacefook = null;
        m_oBtnJoinEmail = null;
        m_oBtnLogin = null;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
        case R.id.btn_join_facebook: {
            // start Facebook Login
//        	FacebookInfo.getInstance(mContext).setCurContext(mContext);
//			FacebookInfo.getInstance(mContext).Authrize((Activity)mContext);
            Session.openActiveSession(this, true, new Session.StatusCallback() {

              // callback when session changes state
              @Override
              public void call(Session session, SessionState state, Exception exception) {
                  if (session.isOpened()) {
                      Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
                        @Override
                        public void onCompleted(GraphUser user, Response response) {
                        	if (user != null) {
    							try
    							{
    								id = user.asMap().get("email").toString();
    								String mUserId = user.getId();
    							    mUserName = user.getName();
    								mThum = "https://graph.facebook.com/" + mUserId  + "/picture";
    								SharedPreferences sp = getSharedPreferences("autologin", MODE_PRIVATE);
    								SharedPreferences.Editor editer = sp.edit();
    								editer.putBoolean("autologinboolean", true);
    								editer.putBoolean("facebook", true);
    								editer.putString("id", id);
    								editer.putString("pwd", "");
    								editer.commit();
    								reqeustDoubleId();
    							}
    							catch(Exception e)
    							{
    								e.printStackTrace();
    							}
    						}
        					
                        }
                    });
                  } else {
                      
                  }
              }
            });
            break;
        }
        case R.id.btn_join_email: {
            Intent intent = new Intent(this.getApplicationContext(),
                    JoinActivity.class);
            startActivity(intent);
            break;
        }
        case R.id.btn_login: {
            Intent intent = new Intent(this.getApplicationContext(),
                    LoginActivity.class);
            startActivity(intent);
            break;
        }
        }

    }
    public void reqeustDoubleId()
	{
		//중복체크 요청
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_JOIN_DOUBLE_ID);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("id", id));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		startProgressDialog();
	}
    public void requestJoin(String email, String pwd, String name,String thum)
	{
		//회원가입 요청
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_JOIN);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		SharedPreferences sp = getSharedPreferences("autologin", MODE_PRIVATE);
		SharedPreferences.Editor editer = sp.edit();
		loginParams.add(new Params("facebook", sp.getBoolean("facebook", true)+""));
		loginParams.add(new Params("id", email));
		loginParams.add(new Params("pwd", pwd));
		loginParams.add(new Params("name", name));
		loginParams.add(new Params("phone", ""));
		loginParams.add(new Params("thum", thum));
		
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		startProgressDialog();
	}
    public void requestLogin(String id, String pwd) {
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_LOGIN);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		SharedPreferences sp = getSharedPreferences("autologin", MODE_PRIVATE);
		SharedPreferences.Editor editer = sp.edit();
		loginParams.add(new Params("facebook", sp.getBoolean("facebook", true)+""));
		loginParams.add(new Params("id", id));
		loginParams.add(new Params("pwd", pwd));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		startProgressDialog();
	}
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
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
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		try{
			Object o = resContent;
			JSONObject object = new JSONObject(resContent);
			if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_JOIN_DOUBLE_ID"))
			{
				String result = object.getJSONObject("data").optString("result","");
				if(result.equals("true"))
				{
					check = false;
					//중복되는아이디가 없을시
//					m_oTxtDoble.setText("사용 가능한 아이디 입니다.");
					requestJoin(id, "", mUserName, mThum);
				}
				else
				{
					check = false;
//					m_oTxtDoble.setText("중복된 아이디가 있습니다.");
					requestLogin(id, "");
				}
			}
			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG.JOIN"))
			{
				String result = object.getJSONObject("data").optString("result","");
				if(result.equals("true"))
				{
					check = false;
					requestLogin(id,"");
				}
				else
				{
					check = false;
					Toast.makeText(mContext, "회원가입을 다시해주세요", Toast.LENGTH_SHORT).show();
				}
			}
			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_LOGIN"))
			{
				String result = object.getJSONObject("data").optString("result","");
				String reason = object.getJSONObject("data").optString("reason","");
				if(result.equals("true"))
				{
					check = true;
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
					
					SharedPreferences sp = getSharedPreferences("autologin", MODE_PRIVATE);
					SharedPreferences.Editor editer = sp.edit();
					
					editer.putBoolean("autologinboolean", true);
					editer.putBoolean("facebook", true);
					editer.putString("id", id);
					editer.putString("pwd", pwd);
					editer.commit();
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
			if(check == true)
				stopProgressDialog() ;
		}
	}

}
