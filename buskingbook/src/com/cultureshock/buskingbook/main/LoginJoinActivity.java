package com.cultureshock.buskingbook.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.*;
import com.facebook.model.*;
import com.cultureshock.buskingbook.R;

public class LoginJoinActivity extends Activity implements View.OnClickListener {
    private Context mContext;
    private static LoginJoinActivity mInstance;
    private LinearLayout m_oBtnJoinFacefook;
    private LinearLayout m_oBtnJoinEmail;
    private LinearLayout m_oBtnLogin;

    @Override
    public View onCreateView(View parent, String name, Context context,
            AttributeSet attrs) {
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

    public void setUi() {
        m_oBtnJoinEmail = (LinearLayout) findViewById(R.id.btn_join_email);
        m_oBtnJoinFacefook = (LinearLayout) findViewById(R.id.btn_join_facebook);
        m_oBtnLogin = (LinearLayout) findViewById(R.id.btn_login);
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
        switch (v.getId()) {
        case R.id.btn_join_facebook: {
            // start Facebook Login
            Session.openActiveSession(this, true, new Session.StatusCallback() {

              // callback when session changes state
              @Override
              public void call(Session session, SessionState state, Exception exception) {
                  if (session.isOpened()) {
                      Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
                        @Override
                        public void onCompleted(GraphUser user, Response response) {
                        }
                    });
                  } else {
                      
                  }
              }
            });
            break;
        }
        case R.id.btn_join_email: {
            Intent intent = new Intent(this.getApplicationContext(),
                    JoinActivity.class);
            startActivity(intent);
            break;
        }
        case R.id.btn_login: {
            Intent intent = new Intent(this.getApplicationContext(),
                    LoginActivity.class);
            startActivity(intent);
            break;
        }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }

}
