
package com.cultureshock.buskingbook.framework;

import android.os.Bundle;

import com.cultureshock.buskingbook.main.BuskingMainActivity;


public class BaseActivity extends BuskingMainActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    	
  
}
