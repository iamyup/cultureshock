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
import android.widget.LinearLayout;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.framework.BaseActivity;
import com.cultureshock.buskingbook.page.MainHomeFragment;

public class LoginJoinActivity extends Activity implements View.OnClickListener {
    private Context mContext;
    private static LoginJoinActivity mInstance;
    private LinearLayout m_oBtnJoinFacefook;
    private LinearLayout m_oBtnJoinEmail;
    private LinearLayout m_oBtnLogin;
    
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_join);
        mContext = this;
        mInstance = this;
        setUi();
       
    }
    public void setUi()
    {
    	m_oBtnJoinEmail = (LinearLayout)findViewById(R.id.btn_join_email);
    	m_oBtnJoinFacefook = (LinearLayout)findViewById(R.id.btn_join_facebook);
    	m_oBtnLogin = (LinearLayout)findViewById(R.id.btn_login);
    	m_oBtnJoinEmail.setOnClickListener(this);
    	m_oBtnJoinFacefook.setOnClickListener(this);
    	m_oBtnLogin.setOnClickListener(this);
    }
    public static LoginJoinActivity getInstance() {
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
		case R.id.btn_join_facebook : 
		{
			break;
		}
		case R.id.btn_join_email : 
		{
			Intent intent =  new Intent(this.getApplicationContext(), JoinActivity.class);
			startActivity(intent);
			break;
		}
		case R.id.btn_login : 
		{
			Intent intent =  new Intent(this.getApplicationContext(), LoginActivity.class);
			startActivity(intent);
			break;
		}
		}
		
	}
   
}
