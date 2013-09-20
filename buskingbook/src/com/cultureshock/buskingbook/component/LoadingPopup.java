package com.cultureshock.buskingbook.component;



import com.cultureshock.buskingbook.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class LoadingPopup extends LinearLayout {
	
	private Context mContext;
	
	private Dialog loading;
	
	private Handler mHandler = new Handler();
	
	private View parent = null;
	
	private boolean canCanceled = true;
	
	private Handler cancelHandler = null;

	private AnimationDrawable loadAniFrame = null;
	private LoadAnimationRunable loadAniTask;
	
	
	class LoadAnimationRunable implements Runnable
	{
		@Override
		public void run()
		{
			loadAniFrame.start();
		}
	}
	
	public LoadingPopup(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		initView();
	}

	private void initView() 
	{
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		boolean blFlag = false;
		try
		{
			parent = inflater.inflate(R.layout.loading_layout, null);
			blFlag = true;
		}
		catch(Exception e)
		{
			System.gc();
			blFlag = false;
			e.printStackTrace();
		}
		
		if(blFlag)
		{
			ImageView imageView = (ImageView) parent.findViewById(R.id.loading);
			imageView.setImageResource(R.anim.ani_loading);
			loadAniFrame = (AnimationDrawable) imageView.getDrawable();
			loadAniTask =  new LoadAnimationRunable();
			loading = new Dialog(mContext, R.style.Theme_TransParent);		
			loading.setOnKeyListener(keylistener);
		}

	}
	
	public void start()
	{
		try
		{
			if( !isShowing() ) 
			{
				if( loading != null )
				{
					mHandler.removeCallbacks(loadAniTask);
					mHandler.postDelayed(loadAniTask, 100);
					loading.getWindow().getDecorView().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					loading.setContentView(parent);
					loading.show();
				}
			}
		}
		catch( Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void stop()
	{
		try
		{
			if( isShowing() )
			{
				loadAniFrame.stop();
				loading.dismiss();
				canCanceled = true;
				cancelHandler = null;
			}
	
	        recursiveRecycle(this, false);
			unbindDrawables(this);
			loading = null;
			mContext = null;
		}
		catch( Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean isShowing()
	{
		if( loading != null)
			return loading.isShowing();
		else
			return false;
	}
	
	public void setCanceled(boolean value)
	{
		canCanceled = value;
	}
	
	public void setHandler(Handler handler)
	{
		cancelHandler = handler;
	}
	
	private final DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {

		@Override
		public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
			// TODO Auto-generated method stub
			 if (arg2.getKeyCode()==KeyEvent.KEYCODE_BACK) {
				 if( canCanceled )
				 {
					 if( isShowing() )
					 {
				 		loadAniFrame.stop();
			 			loading.dismiss();
			 			cancelHandler = null;
					 }
				 }
				 return true;
		     }
			 return false;
		}
	};
	 public static void recursiveRecycle(View root) {
	        if (root == null)
	            return;
	        root.setBackgroundDrawable(null);
	        if (root instanceof ViewGroup) {
	            ViewGroup group = (ViewGroup)root;
	            int count = group.getChildCount();
	            for (int i = 0; i < count; i++) {
	                recursiveRecycle(group.getChildAt(i));
	            }
	 
	            if (!(root instanceof AdapterView)) {
	            	try
	            	{
	                    group.removeAllViews();
	            	}
	            	catch(Exception e)
	            	{
	            		e.printStackTrace();
	            	}
	            }
	 
	        }
	       
	        if (root instanceof ImageView) {

	    		((ImageView)root).setImageDrawable(null);
	        }
	        root = null;
	 
	        return;
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
    
}
