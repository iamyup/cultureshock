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

public class LoginActivity extends Activity implements View.OnClickListener {
    private Context mContext;
    private static LoginActivity mInstance;
    
    private EditText m_oEmail;
    private EditText m_oPassword;
    private LinearLayout m_oBtnFacebookConfirm;
    private LinearLayout m_oBtnConfirm;
    
    
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login2);
        mContext = this;
        mInstance = this;
        setUi();
       
    }
    public void setUi()
    {
    	m_oEmail = (EditText)findViewById(R.id.login_email);
    	m_oPassword = (EditText)findViewById(R.id.login_password);
    	m_oBtnConfirm = (LinearLayout)findViewById(R.id.btn_login);
    	m_oBtnFacebookConfirm = (LinearLayout)findViewById(R.id.btn_facebook_login);
    	
    	m_oBtnFacebookConfirm.setOnClickListener(this);
    	m_oBtnConfirm.setOnClickListener(this);
    }
    public static LoginActivity getInstance() {
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
			case R.id.btn_facebook_login : 
			{
				break;
			}
			case R.id.btn_login : 
			{
				Intent intent =  new Intent(this.getApplicationContext(), MainActivity.class);
				startActivity(intent);
				finish();
				break;
			}
		}
		
	}
   
}
