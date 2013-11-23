package com.cultureshock.buskingbook;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View.OnClickListener;

import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.service.ServiceType;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class FacebookInfo  {

	// android market
	// public static Facebook facebook = new Facebook("209505859130902");
	// olleh market
//	public static Facebook facebook = new Facebook("256937297704300");

	static FacebookInfo mInstace = null;
	public static boolean isPost = false;
	public Context mCurContext = null;
	public static final String EMAIL = "email";
	private String mThum = "";
	private String mEmail = "";
	private String mUserName = "";
	private String mUserId = "";
	private static Session.StatusCallback statusCallback;

	public FacebookInfo(Context context) {
		init(context);
		if (statusCallback == null) {
			statusCallback = new SessionStatusCallback();
			Session.getActiveSession().addCallback(statusCallback);
		}
	}

	public static boolean isConnect(Activity context, Bundle savedInstanceState) {
		Session session = Session.getActiveSession();
		if (session == null) {
			if (savedInstanceState != null) {
				session = Session.restoreSession(context, null, statusCallback, savedInstanceState);
			}
			if (session == null) {
				session = new Session(context);
			}
			Session.setActiveSession(session);
			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
				session.openForRead(new Session.OpenRequest(context).setCallback(statusCallback));
			}
		}

		if (session.isOpened()) {
			return true;
		} else {
			return false;
		}
	}

	public void setCurContext(Context context) {
		mCurContext = context;
	}

	public static FacebookInfo getInstance(Context context) {
		if (mInstace == null)
			mInstace = new FacebookInfo(context);

		return mInstace;
	}

	public void init(Context context) {
		
		Session session = Session.getActiveSession();
		if (session == null) {
			session = new Session(context);
			Session.setActiveSession(session);
		}
		
	}

	public void Authrize(Activity mActivity) {
		Session session = Session.getActiveSession();
		if (!session.isOpened() && !session.isClosed()) {
			session.openForPublish(new Session.OpenRequest(mActivity).setCallback(statusCallback).setPermissions(Arrays.asList("read_stream", "email", "publish_stream")).setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO));
		} else {
			Session.openActiveSession2(mActivity, true, statusCallback);
		}
	}

	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			if (state == SessionState.OPENED_TOKEN_UPDATED) {

			}

			if (state == SessionState.OPENED) {
				Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user, Response response) {
						if (user != null) {
							try
							{
								mEmail = user.asMap().get("email").toString();
								mUserId = user.getId();
								mUserName = user.getName();
								mThum = "https://graph.facebook.com/" + mUserId  + "/picture";
								SharedPreferences sp = mCurContext.getSharedPreferences("autologin", mCurContext.MODE_PRIVATE);
								SharedPreferences.Editor editer = sp.edit();
								editer.putBoolean("autologinboolean", true);
								editer.putBoolean("facebook", true);
								editer.putString("id", mEmail);
								editer.putString("pwd", "");
								editer.commit();
								
//								setEmail(mEmail);
//								setUserId(mUserId);
//								SystemConfig.getInstance().setFacebookInfo(mEmail, mUserId);
//								mCurContext.sendBroadcast(new Intent("fb_login_complete"));
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
						}

					}
				});
				Request.executeBatchAsync(request);
			}
			
			
		}
	}

	public void Logout(Context context) {
		Session session = Session.getActiveSession();
		if (!session.isClosed()) {
			session.closeAndClearTokenInformation();
		}
	}

	public void setEmail(String email) {

		mEmail = email;
	}

	public String getEmail() {
		return mEmail;
	}

	public String getUserName() {
		return mUserName;
	}

	public void setUserName(String mUserName) {
		this.mUserName = mUserName;
	}

	public String getUserId() {
		return mUserId;
	}

	public void setUserId(String mUserId) {
		this.mUserId = mUserId;
	}
}
