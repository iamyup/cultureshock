package com.cultureshock.buskingbook;

import java.util.ArrayList;

import org.json.JSONArray;
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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.component.LoadingPopup;
import com.cultureshock.buskingbook.main.LoginJoinActivity;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.object.TeamObject;
import com.cultureshock.buskingbook.page.MainHomeFragment;
import com.cultureshock.buskingbook.service.ServiceType;
import com.google.android.gcm.GCMRegistrar;

public class FirstStartActivity extends Activity implements
		View.OnClickListener, HttpClientNet.OnResponseListener {
	private Context mContext;
	public static String regId;
	private boolean netCheck = false;
	private LoadingPopup loading;

	@Override
	public View onCreateView(View parent, String name, Context context,
			AttributeSet attrs) {
		return super.onCreateView(parent, name, context, attrs);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_start);
		registGCM();
		mContext = this;
		setUi();
		requestTeam();

	}

	private void registGCM() {
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);

		regId = GCMRegistrar.getRegistrationId(this);

		if ("".equals(regId)) // 구글 가이드에는 regId.equals("")로 되어 있는데 Exception을
								// 피하기 위해 수정
			GCMRegistrar.register(this, GCMIntentService.SEND_ID);
		else
			Log.d("==============", regId);
	}

	public void requestTeam() {
		HttpClientNet loginService = new HttpClientNet(
				ServiceType.MSG_TEAM_SELECT);
		loginService.doAsyncExecute(this);
		startProgressDialog();
	}

	public void setUi() {

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
		switch (v.getId()) {

		}

	}

	@Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		try {
			netCheck = true;
			Object o = resContent;
			JSONObject object = new JSONObject(resContent);
			if (object.getJSONObject("result").optString("type")
					.equals("ServiceType.MSG_TEAM_SELECT")) {
				JSONArray top12 = object.getJSONArray("data");
				for (int i = 0; i < top12.length(); i++) {
					JSONObject jsonObject = top12.getJSONObject(i);
					String teamName = jsonObject.optString("teamname");
					String teamThum = jsonObject.optString("teamthum");
					int likeCount = Integer.parseInt(jsonObject
							.optString("likecount"));
					String teamComent = jsonObject.optString("teamcoment");
					String teamSong = jsonObject.optString("teamsong");
					String teamMember = jsonObject.optString("teammember");
					String likeman = jsonObject.optString("likeman");
					String notice = jsonObject.optString("notice");
					String[] p = likeman.split(",");
					ArrayList<String> likeMans = new ArrayList<String>();
					if (!p[0].equals("")) {
						for (int j = 0; j < p.length; j++) {
							likeMans.add(p[j]);
						}
					}
					MainActivity.getTeamObject().add(
							new TeamObject(teamName, teamMember, teamThum,
									likeCount, teamComent, teamSong, likeMans,notice));
				}
				for (int i = 0; i < MainActivity.getTeamObject().size() - 1; i++) {
					for (int j = i + 1; j < MainActivity.getTeamObject().size(); j++) {
						if (MainActivity.getTeamObject().get(i).getLikeCount() < MainActivity
								.getTeamObject().get(j).getLikeCount()) {
							TeamObject tempobejct = MainActivity
									.getTeamObject().get(i);
							MainActivity.getTeamObject().set(i,
									MainActivity.getTeamObject().get(j));
							MainActivity.getTeamObject().set(j, tempobejct);
						}
					}
				}
				SharedPreferences sp = getSharedPreferences("autologin",
						MODE_PRIVATE);
				if (sp.getBoolean("autologinboolean", false)) {
					if (sp.getString("id", "").equals("")) {
						Intent intent = new Intent(
								FirstStartActivity.this.getApplicationContext(),
								LoginJoinActivity.class);
						startActivity(intent);
						finish();
					} else {
						netCheck = false;
						requestLogin(sp.getString("id", ""),
								sp.getString("pwd", ""));
					}
				} else {
					Intent intent = new Intent(
							FirstStartActivity.this.getApplicationContext(),
							LoginJoinActivity.class);
					startActivity(intent);
					finish();
				}

			} else if (object.getJSONObject("result").optString("type")
					.equals("ServiceType.MSG_LOGIN")) {
				String result = object.getJSONObject("data").optString(
						"result", "");
				String reason = object.getJSONObject("data").optString(
						"reason", "");
				if (result.equals("true")) {
					String id = object.getJSONObject("data")
							.optString("id", "");
					String pwd = object.getJSONObject("data").optString("pwd",
							"");
					String name = object.getJSONObject("data").optString(
							"name", "");
					String phone = object.getJSONObject("data").optString(
							"phone", "");
					String myImg = object.getJSONObject("data").optString(
							"myImg", "");
					String likeTeamList = object.getJSONObject("data")
							.optString("likeTeamList", "");
					String myteam = object.getJSONObject("data").optString(
							"myteam", "");
					String[] likeTeam = likeTeamList.split(",");
					ArrayList<String> likeTeamArrayList = new ArrayList<String>();
					if (!likeTeam[0].equals("")) {
						for (int i = 0; i < likeTeam.length; i++) {
							likeTeamArrayList.add(likeTeam[i]);
						}
					}
					LoginInfoObject.getInstance().setLogin(id, pwd, name,
							phone, myImg, likeTeamArrayList, myteam);
					Intent intent = new Intent(this.getApplicationContext(),
							MainActivity.class);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(this, reason, Toast.LENGTH_LONG).show();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (netCheck) {
				stopProgressDialog();
			}
		}

	}

	public void startProgressDialog() {
		if (loading == null) {
			loading = new LoadingPopup(this);
			loading.start();
		}
	}

	public void stopProgressDialog() {
		if (loading != null) {
			loading.stop();
			loading = null;
		}
	}

	public void requestLogin(String id, String pwd) {
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_LOGIN);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("id", id));
		loginParams.add(new Params("pwd", pwd));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		startProgressDialog();
	}
}
