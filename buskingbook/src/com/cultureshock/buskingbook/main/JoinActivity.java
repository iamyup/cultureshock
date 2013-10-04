package com.cultureshock.buskingbook.main;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.framework.BaseActivity;
import com.cultureshock.buskingbook.page.MainHomeFragment;

public class JoinActivity extends Activity implements View.OnClickListener {
    private Context mContext;
    private static JoinActivity mInstance;
    
    private LinearLayout m_oImageUpload;
    private ImageView m_oImage;
    private EditText m_oUserName;
    private EditText m_oEmail;
    private EditText m_oPassword;
    private EditText m_oPasswordConfirm;
    private LinearLayout m_oBtnConfirm;
    
    
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
        mInstance = this;
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
    	
    	m_oImageUpload.setOnClickListener(this);
    	m_oBtnConfirm.setOnClickListener(this);
    }
    public static JoinActivity getInstance() {
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
			case R.id.img_upload : 
			{
				break;
			}
			case R.id.btn_confirm : 
			{
				Intent intent =  new Intent(this.getApplicationContext(), LoginActivity.class);
				startActivity(intent);
				finish();
				break;
			}
		}
		
	}
   
}
