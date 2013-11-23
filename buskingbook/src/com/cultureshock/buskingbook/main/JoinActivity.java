package com.cultureshock.buskingbook.main;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.component.LoadingPopup;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.page.MainHomeFragment;
import com.cultureshock.buskingbook.service.ServiceType;

public class JoinActivity extends Activity implements View.OnClickListener , HttpClientNet.OnResponseListener{
    private static final int REQ_PICK_IMAGE = 0;
    private static final String TEMP_PHOTO_FILE = "tmp_profile.jpg";
    
    private static Uri m_oImageCropUri;

    private Context mContext;
    
    private LinearLayout m_oImageUpload;
    private ImageView m_oImage;
    private EditText m_oUserName;
    private EditText m_oEmail;
    private EditText m_oPassword;
    private EditText m_oPasswordConfirm;
    private LinearLayout m_oBtnConfirm;
    private ImageView m_oImgDoubleCheck;
    
    private String m_oStrImgThum = "";
    private boolean checkDouble = false; 

    private LoadingPopup loading;
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);
        mContext = this;
        setUi();
       
    }
    public void setUi()
    {
    	m_oImageUpload = (LinearLayout)findViewById(R.id.img_upload);
    	m_oImage = (ImageView)findViewById(R.id.join_image);
    	m_oUserName = (EditText)findViewById(R.id.join_username);
    	m_oEmail = (EditText)findViewById(R.id.join_email);
    	m_oPassword = (EditText)findViewById(R.id.join_password);
    	m_oPasswordConfirm = (EditText)findViewById(R.id.join_password_confirm);
    	m_oBtnConfirm = (LinearLayout)findViewById(R.id.btn_confirm);
    	m_oImgDoubleCheck = (ImageView)findViewById(R.id.double_check_img);
    	m_oEmail.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus==false)
				{
					if((m_oEmail.getText().toString().equals("")))
					{
						//아이디 입력해주세요
						
						Toast.makeText(JoinActivity.this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
					}
					else
					{
						reqeustDoubleId();
					}
				}
				
			}
		});
    	
    	m_oImageUpload.setOnClickListener(this);
    	m_oBtnConfirm.setOnClickListener(this);
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
        m_oImageUpload = null;
    	m_oImage = null;
    	m_oUserName = null;
    	m_oEmail = null;
    	m_oPassword = null;
    	m_oPasswordConfirm = null;
    	m_oBtnConfirm  = null;
    	m_oImgDoubleCheck = null;
    	m_oEmail = null;
    }
    public void reqeustDoubleId()
	{
		//중복체크 요청
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_JOIN_DOUBLE_ID);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("id", m_oEmail.getText().toString()));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		startProgressDialog();
	}
    public void requestJoin()
	{
		//회원가입 요청
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_JOIN);
		loginService.setCheck(true);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("id", m_oEmail.getText().toString()));
		loginParams.add(new Params("pwd", m_oPassword.getText().toString()));
		loginParams.add(new Params("name", m_oUserName.getText().toString()));
		loginParams.add(new Params("phone", ""));
		if(m_oImageCropUri != null)
		{
			
			loginParams.add(new Params("thum", m_oImageCropUri.getPath()));
		}
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		startProgressDialog();
	}
   
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.img_upload : 
			{
			    Intent intent = new Intent(Intent.ACTION_PICK);
			    intent.setType("image/*");
			    intent.putExtra("crop", "true");
			    intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
			    intent.putExtra("output", m_oImageCropUri);
			    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
			    startActivityForResult(intent, REQ_PICK_IMAGE);
			    break;
			}
			case R.id.btn_confirm : 
			{
				if(!checkDouble)
				{
					Toast.makeText(this, "중복체크를 다시해주세요", Toast.LENGTH_SHORT).show();
					//중복체크해주세요
				}
				else if(m_oPassword.getText().toString().equals(""))
				{
					Toast.makeText(this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
					//비밀번호 입력하세요
				}
				else if(m_oPassword.getText().toString().length() < 4)
				{
					Toast.makeText(this, "비밀번호는 4자리 이상 입력해주세요", Toast.LENGTH_SHORT).show();
					//비밀번호는 8자리이상
				}
				else if(!m_oPassword.getText().toString().equals(m_oPasswordConfirm.getText().toString()))
				{
					Toast.makeText(this, "비밀번호와 비밀번호확인 값이 다릅니다.", Toast.LENGTH_SHORT).show();
				}
				else if(m_oUserName.getText().toString().equals(""))
				{
					Toast.makeText(this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
					//이름을 입력해주세요
				}
				else
				{
					requestJoin();
				}
				break;
				
			}
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
					//중복되는아이디가 없을시
//					m_oTxtDoble.setText("사용 가능한 아이디 입니다.");
					Toast.makeText(this, "사용 가능한 이메일 입니다.", Toast.LENGTH_SHORT).show();
					checkDouble = true;
					m_oImgDoubleCheck.setBackgroundResource(R.drawable.check_double);
				}
				else
				{
//					m_oTxtDoble.setText("중복된 아이디가 있습니다.");
					Toast.makeText(this, "중복된 이메일이 있습니다.", Toast.LENGTH_SHORT).show();
					checkDouble = false;
					//얼랏 중복되니 다시 입력바랍니다.
					m_oImgDoubleCheck.setBackgroundResource(R.drawable.check_double_no);
				}
			}
			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG.JOIN"))
			{
				String result = object.getJSONObject("data").optString("result","");
				if(result.equals("true"))
				{
					//성공
					Toast.makeText(this, "회원가입 되셨습니다.", Toast.LENGTH_SHORT).show();
					finish();
				}
				else
				{
					//실패
					Toast.makeText(this, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
					finish();
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

	private Uri getTempUri() {
	    return Uri.fromFile(getProfileImage());
	}

	private File getProfileImage() {
	    File file = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE);
	    try {
	        file.createNewFile();
	    } catch (IOException e) {
	        
	    }
	    return file;
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
        case REQ_PICK_IMAGE:
            if (resultCode == RESULT_OK) {
                if (data != null) {
                	
                    Bitmap bitmap;
                    String fileName = Environment.getExternalStorageDirectory() + "/" + TEMP_PHOTO_FILE;
                    File outFile = new File(fileName);
    				m_oImageCropUri = Uri.fromFile(outFile);
                    try {
                        bitmap = BitmapFactory.decodeFile(fileName);
                        m_oImage.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    
                }
            }
            break;
        }
    }

	
}
