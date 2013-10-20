package com.cultureshock.buskingbook.main;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.component.LoadingPopup;
import com.cultureshock.buskingbook.framework.BaseActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.TeamObject;
import com.cultureshock.buskingbook.page.MainHomeFragment;
import com.cultureshock.buskingbook.service.ServiceType;
import com.google.android.gcm.GCMRegistrar;

public class FirstStartActivity extends Activity implements View.OnClickListener , HttpClientNet.OnResponseListener{
    private Context mContext;
    private static FirstStartActivity mInstance;
    public static String regId;

    private LoadingPopup loading;
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_start);
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        regId = GCMRegistrar.getRegistrationId(this);
        if("".equals(regId))  
        {//구글 가이드에는 regId.equals("")로 되어 있는데 Exception을 피하기 위해 수정
              GCMRegistrar.register(this, "949552179833");
        }
        else
              Log.d("==============", regId);
        mContext = this;
        mInstance = this;
        setUi();
        requestTeam();
       
    }
	public void requestTeam()
	{
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_TEAM_SELECT);
		loginService.doAsyncExecute(this);
		startProgressDialog();
	}
    public void setUi()
    {
    	
    }
    public static FirstStartActivity getInstance() {
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
    }
  
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			
		}
		
	}

	@Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		try{
			Object o = resContent;
			JSONObject object = new JSONObject(resContent);
			if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_TEAM_SELECT"))
			{
				JSONArray top12 = object.getJSONArray("data");
				for(int i=0; i<top12.length(); i++)
				{
					JSONObject jsonObject = top12.getJSONObject(i);
					String teamName = jsonObject.optString("teamname");
					String teamThum = jsonObject.optString("teamthum");
					int likeCount = Integer.parseInt(jsonObject.optString("likecount"));
					String teamComent = jsonObject.optString("teamcoment");
					String teamSong = jsonObject.optString("teamsong");
					String teamMember = jsonObject.optString("teammember");
					String likeman = jsonObject.optString("likeman");
					String [] p = likeman.split(",");
					ArrayList<String> likeMans = new ArrayList<String>();
					if(!p[0].equals(""))
					{	
						for(int j = 0 ; j < p.length ; j++)
						{
							likeMans.add(p[j]);
						}
					}
					BaseActivity.getTeamObject().add(new TeamObject(teamName,teamMember,teamThum,likeCount,teamComent,teamSong,likeMans));
				}
				Intent intent =  new Intent(FirstStartActivity.this.getApplicationContext(), LoginJoinActivity.class);
				startActivity(intent);
				finish();
			}
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			stopProgressDialog();
		}
		
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
   
}
