package com.cultureshock.buskingbook.page;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.cultureshock.buskingbook.R;



public class ThreePage extends LinearLayout{
	
	private Context mContext;
	private View v = null;
	
	Dialog dialog;
	public ThreePage(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
		mContext = context;
		initView();
	}
	
	public void show() {
		dialog.show();
	}
	
	public void dismissPopup(){
		dialog.dismiss();
	}
	

	private void initView() {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(R.layout.three, null);
		this.addView(v);
//		dialog = new Dialog(mContext, R.style.Dialog);
//		dialog.addContentView(this, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//		
//		dialog.setCanceledOnTouchOutside(false);

	}
	

}
