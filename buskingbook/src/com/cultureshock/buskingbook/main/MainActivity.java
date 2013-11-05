
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
import android.widget.Toast;

import com.cultureshock.buskingbook.FirstStartActivity;
import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.component.LoadingPopup;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.object.TeamObject;
import com.cultureshock.buskingbook.page.MainHomeFragment;
import com.cultureshock.buskingbook.page.PaperEditFragment;
import com.cultureshock.buskingbook.page.PartnerSearchFragment;
import com.cultureshock.buskingbook.page.TeamPageSettingFragment;
import com.cultureshock.buskingbook.service.ServiceType;


public class MainActivity extends BuskingMainActivity implements HttpClientNet.OnResponseListener {
	
	private static ArrayList<TeamObject> teamObject = new ArrayList<TeamObject>();
	private Context mContext;
    private static MainActivity mInstance;
    private LoadingPopup loading;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	 super.onCreate(savedInstanceState);
         setContentView(R.layout.main);
         mContext = this;
         mInstance = this;
         requestRegId();
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
  
}
