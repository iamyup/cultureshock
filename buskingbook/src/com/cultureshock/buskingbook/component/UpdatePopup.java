package com.cultureshock.buskingbook.component;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.main.LoginActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class UpdatePopup extends LinearLayout {

	private Context mContext;
	Dialog dialog;
	private View v = null;
	private ImageView m_oBtnConfirm;
	//private ImageView m_oBtnCancel;

	public UpdatePopup(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
		mContext = context;
		initView();
	}

	public void show() {
		dialog.show();
	}

	public void dismissPopup() {
		dialog.dismiss();
	}

	private void initView() {
		// TODO Auto-generated method stub

		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(R.layout.popup_update, null);
		this.addView(v);
		dialog = new Dialog(mContext, R.style.Dialog);
		dialog.addContentView(this, new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		dialog.setCanceledOnTouchOutside(false);

		m_oBtnConfirm = (ImageView) v
				.findViewById(R.id.popup_modify_myalbum_btn_confirm);
		m_oBtnConfirm.setOnClickListener(BtnConfirm);
//		m_oBtnCancel = (ImageView) v
//				.findViewById(R.id.popup_modify_myalbum_btn_cancel);
//		m_oBtnCancel.setOnClickListener(BtnCancel);
//		show();

	}

	private final OnClickListener BtnConfirm = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			Intent intent = new Intent(mContext, LoginActivity.class);
			mContext.startActivity(intent);
			dialog.dismiss();
		}
	};
	private final OnClickListener BtnCancel = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			dialog.dismiss();
		}
	};
}
