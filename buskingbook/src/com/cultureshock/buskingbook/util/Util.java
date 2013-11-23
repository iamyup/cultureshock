package com.cultureshock.buskingbook.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

public class Util {
	public static AlertDialog m_oDialog = null;

	public static void showAlertMsg(Context poContext, String strTitle,
			String strMsg, String strOkMsg,
			DialogInterface.OnClickListener poOkOnClick) {
		try {
			AlertDialog.Builder oMsgBox = new AlertDialog.Builder(poContext);
			if (strTitle != null) {
				oMsgBox.setTitle(strTitle);
			}
			oMsgBox.setMessage(strMsg);
			if (poOkOnClick == null) {
				oMsgBox.setNeutralButton(strOkMsg,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});
			} else {
				oMsgBox.setNeutralButton(strOkMsg, poOkOnClick);
			}
			oMsgBox.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {
					if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
						return true;
					}
					return false;
				}
			});
			m_oDialog = oMsgBox.show();
			m_oDialog.setCanceledOnTouchOutside(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showAlertMsg(Context poContext, String strTitle,
			String strMsg, String strOkMsg, String strNOkMsg,
			DialogInterface.OnClickListener poOkOnClick,
			DialogInterface.OnClickListener poNOkOnClick) {
		try {
			AlertDialog.Builder oMsgBox = new AlertDialog.Builder(poContext);
			if (strTitle != null) {
				oMsgBox.setTitle(strTitle);
			}
			oMsgBox.setMessage(strMsg);
			if (poOkOnClick == null) {
				oMsgBox.setNegativeButton(strOkMsg,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});
			} else {
				oMsgBox.setNegativeButton(strOkMsg, poOkOnClick);
			}
			if (poNOkOnClick == null) {
				oMsgBox.setPositiveButton(strNOkMsg,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});
			} else {
				oMsgBox.setPositiveButton(strNOkMsg, poNOkOnClick);
			}
			oMsgBox.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {
					if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
						return true;
					}
					return false;
				}
			});
			m_oDialog = oMsgBox.show();
			m_oDialog.setCanceledOnTouchOutside(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void dissmissAlertMsg() {
		try {
			if (m_oDialog != null && m_oDialog.isShowing()) {
				m_oDialog.dismiss();
				m_oDialog = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void recursiveRecycle(View root) {
		if (root == null)
			return;
		root.setBackgroundDrawable(null);
		if (root instanceof ViewGroup) {
			ViewGroup group = (ViewGroup) root;
			int count = group.getChildCount();
			for (int i = 0; i < count; i++) {
				recursiveRecycle(group.getChildAt(i));
			}

			if (!(root instanceof AdapterView)) {
				try {
					group.removeAllViews();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

		if (root instanceof ImageView) {

			((ImageView) root).setImageDrawable(null);
		}
		root = null;

		return;
	}
	public static void unbindDrawables(View view)
    {
        if ( view != null )
        {
            if ( view.getBackground() != null )
            {
                view.getBackground().setCallback(null);
            }
            if ( view instanceof ViewGroup )
            {
                for ( int i = 0; i < ((ViewGroup) view).getChildCount(); i++ )
                {
                    unbindDrawables(((ViewGroup) view).getChildAt(i));
                }
                try
                {
                	((ViewGroup) view).removeAllViews();
                }
                catch( Exception e)
                {
                	e.printStackTrace();
                }
            }
        }
    }
    
	public static void recursiveRecycle(View root, boolean setNull) {
        if (root == null)
            return;
        root.setBackgroundDrawable(null);
        if (root instanceof ViewGroup) {
            ViewGroup group = (ViewGroup)root;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                recursiveRecycle(group.getChildAt(i));
            }
        }
       
        if (root instanceof ImageView) {

    		((ImageView)root).setImageDrawable(null);
        }
        
        if(setNull)
        	root = null;
 
        return;
    }

}
