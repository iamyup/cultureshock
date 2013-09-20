package com.cultureshock.buskingbook.framework;


import com.cultureshock.buskingbook.component.LoadingPopup;
import com.cultureshock.buskingbook.net.HttpClientNet;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
public abstract class BaseActivity extends Activity implements View.OnClickListener, HttpClientNet.OnResponseListener{
	
	private LoadingPopup loading;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setUi(); //유아이 셋팅 
		initValue(); // 이닛 벨류 
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//destory 하고 함수호출
		clearUi(); 
	}
	
	/**
	 * 통신 이후 이쪽으로 데이터 넘어온다
	 * resContent - > json값 
	 */
	@Override
	public void onResponseReceived(String resContent)  {
		// TODO Auto-generated method stub
	}
	
	/**
	 * ui 셋팅
	 */
	public abstract void setUi();
	/**
	 * value 셋팅
	 */
	public abstract void initValue();
	
	/**
	 * ui 정리
	 */
	public abstract void clearUi();
	
	
	//통신시에 프로그래스바 돌리기
	public void startProgressDialog() 
	{
		if( loading == null )
		{
			loading = new LoadingPopup(this);
			loading.start();
		}
	}
	//통신시에 프로그래스바 멈추기
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
		/**
		 * 온클릭 사용법 
		 */
//			case R.id.base_menu_home : 
//			{
//				
//				finish();
//				Intent intent =  new Intent(this.getApplicationContext(), MainActivity.class);
//				startActivity(intent);
//				check_Btn = 0;
//				
//				break;
//			}
		}
		
	}

	
	

}
