package com.cultureshock.buskingbook.main;

import java.util.ArrayList;

import org.json.JSONObject;

import com.cultureshock.buskingbook.framework.BaseActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    /**
     * ������� 
     * ����Ÿ�� ���� ���� �� 
     * �Ķ���� ����ְ�
     * loginservice.doAsyncExecute(this)�����ϸ� �޴ܿ��� ��� ó���ϰ� 
     * onResponseReceived(String resContent) ����� ���̽� �� string ���·� �Ѿ�� 
     * �׸��� ��ŸƮ ���α׷����� �����ϸ� ���α׷����� ���ư� 
     */
//    public void requestJoin()
//	{
//		//ȸ������ ��û
//		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_JOIN);
//		ArrayList<Params> loginParams = new ArrayList<Params>();
//		loginParams.add(new Params("id", m_oEditTextId.getText().toString()));
//		loginParams.add(new Params("pwd", m_oEditTextPwd.getText().toString()));
//		loginParams.add(new Params("name", m_oEditTextName.getText().toString()));
//		loginParams.add(new Params("phone", m_oEditTextPhone.getText().toString()));
//		loginParams.add(new Params("thum", m_oStrImgThum));
//		loginService.setParam(loginParams);
//		loginService.doAsyncExecute(this);
//		startProgressDialog();
//	}
    /**
     * ���⼭ �������α׷����� �ϸ�� ��� ���� �������� �� 
     */
    @Override
	public void onResponseReceived(String resContent)  {
		// TODO Auto-generated method stub
    	/**
    	 * ���� ��� �ϴ¹� ���� 
    	 */
//    	try{
//			Object o = resContent;
//			JSONObject object = new JSONObject(resContent);
//			if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_JOIN_DOUBLE_ID"))
//			{
//				String result = object.getJSONObject("data").optString("result","");
//				if(result.equals("true"))
//				{
//					//�ߺ��Ǵ¾��̵� ������
//					m_oTxtDoble.setText("��� ������ ���̵� �Դϴ�.");
//					Toast.makeText(this, "��� ������ ���̵� �Դϴ�.", Toast.LENGTH_SHORT).show();
//					checkDouble = true;
//				}
//				else
//				{
//					m_oTxtDoble.setText("�ߺ��� ���̵� �ֽ��ϴ�.");
//					Toast.makeText(this, "�ߺ��� ���̵� �ֽ��ϴ�.", Toast.LENGTH_SHORT).show();
//					checkDouble = false;
//					//��� �ߺ��Ǵ� �ٽ� �Է¹ٶ��ϴ�.
//				}
//			}
//			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG.JOIN"))
//			{
//				String result = object.getJSONObject("data").optString("result","");
//				if(result.equals("true"))
//				{
//					//����
//					Toast.makeText(this, "ȸ������ �Ǽ̽��ϴ�.", Toast.LENGTH_SHORT).show();
//					finish();
//				}
//				else
//				{
//					//����
//					Toast.makeText(this, "ȸ�����Կ� �����Ͽ����ϴ�.", Toast.LENGTH_SHORT).show();
//					finish();
//				}
//			}
//		}
//		catch(Exception e )
//		{
//			e.printStackTrace();
//		}
//		finally
//		{
//			stopProgressDialog() ;
//		}
	}
	@Override
	public void setUi() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initValue() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearUi() {
		// TODO Auto-generated method stub
		
	}
}
