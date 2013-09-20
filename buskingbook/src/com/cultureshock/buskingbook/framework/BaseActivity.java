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
		setUi(); //������ ���� 
		initValue(); // �̴� ���� 
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
		//destory �ϰ� �Լ�ȣ��
		clearUi(); 
	}
	
	/**
	 * ��� ���� �������� ������ �Ѿ�´�
	 * resContent - > json�� 
	 */
	@Override
	public void onResponseReceived(String resContent)  {
		// TODO Auto-generated method stub
	}
	
	/**
	 * ui ����
	 */
	public abstract void setUi();
	/**
	 * value ����
	 */
	public abstract void initValue();
	
	/**
	 * ui ����
	 */
	public abstract void clearUi();
	
	
	//��Žÿ� ���α׷����� ������
	public void startProgressDialog() 
	{
		if( loading == null )
		{
			loading = new LoadingPopup(this);
			loading.start();
		}
	}
	//��Žÿ� ���α׷����� ���߱�
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
		 * ��Ŭ�� ���� 
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
