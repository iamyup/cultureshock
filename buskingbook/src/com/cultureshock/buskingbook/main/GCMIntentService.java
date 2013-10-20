package com.cultureshock.buskingbook.main;

import java.util.Iterator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cultureshock.buskingbook.main.FirstStartActivity;
import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {
	private static final String PROJECT_ID = "949552179833";

	// 구글 api 페이지 주소 [https://code.google.com/apis/console/#project:긴 번호]
	// #project: 이후의 숫자가 위의 PROJECT_ID 값에 해당한다
	// public 기본 생성자를 무조건 만들어야 한다.

	public GCMIntentService()
	{
		this(PROJECT_ID);
	}

	public GCMIntentService(String project_id) {
		super(project_id);
	}

	/** 푸시로 받은 메시지 */
	@Override
	protected void onMessage(Context context, Intent intent) {
		String title = intent.getStringExtra("title");
		String message = intent.getStringExtra("msg");
	}
	/** 단말에서 GCM 서비스 등록 했을 때 등록 id를 받는다 */
	@Override
	protected void onRegistered(Context context, String regId) {   
		Log.d("regIdregIdregIdregId",regId);	
	}

	@Override
	protected void onError(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}


}
