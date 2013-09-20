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
     * 통신형태 
     * 서비스타입 으로 구분 후 
     * 파라미터 집어넣고
     * loginservice.doAsyncExecute(this)실행하면 뒷단에서 모든 처리하고 
     * onResponseReceived(String resContent) 여기로 제이슨 값 string 형태로 넘어옴 
     * 그리고 스타트 프로그래스바 시작하면 프로그래스바 돌아감 
     */
//    public void requestJoin()
//	{
//		//회원가입 요청
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
     * 여기서 스톱프로그래스바 하면됨 통신 이후 이쪽으로 옴 
     */
    @Override
	public void onResponseReceived(String resContent)  {
		// TODO Auto-generated method stub
    	/**
    	 * 대충 통신 하는법 예시 
    	 */
//    	try{
//			Object o = resContent;
//			JSONObject object = new JSONObject(resContent);
//			if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_JOIN_DOUBLE_ID"))
//			{
//				String result = object.getJSONObject("data").optString("result","");
//				if(result.equals("true"))
//				{
//					//중복되는아이디가 없을시
//					m_oTxtDoble.setText("사용 가능한 아이디 입니다.");
//					Toast.makeText(this, "사용 가능한 아이디 입니다.", Toast.LENGTH_SHORT).show();
//					checkDouble = true;
//				}
//				else
//				{
//					m_oTxtDoble.setText("중복된 아이디가 있습니다.");
//					Toast.makeText(this, "중복된 아이디가 있습니다.", Toast.LENGTH_SHORT).show();
//					checkDouble = false;
//					//얼랏 중복되니 다시 입력바랍니다.
//				}
//			}
//			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG.JOIN"))
//			{
//				String result = object.getJSONObject("data").optString("result","");
//				if(result.equals("true"))
//				{
//					//성공
//					Toast.makeText(this, "회원가입 되셨습니다.", Toast.LENGTH_SHORT).show();
//					finish();
//				}
//				else
//				{
//					//실패
//					Toast.makeText(this, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
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
