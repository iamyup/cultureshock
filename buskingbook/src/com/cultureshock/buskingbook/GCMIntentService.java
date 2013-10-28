package com.cultureshock.buskingbook;

import java.util.Iterator;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.cultureshock.buskingbook.main.GCMPopupActivity;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.page.BuskerJoinFragment;
import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {
	private static final String tag = "GCMIntentService";
	public static final String SEND_ID = "949552179833";
	public static boolean isPopup = true;
	public GCMIntentService(){ this(SEND_ID); }
	
	public GCMIntentService(String senderId) { super(senderId); }

	@Override
	protected void onMessage(Context context, Intent intent) {
		if(isPopup)
		{
			Bundle b = intent.getExtras();
			String value = null ;
			String key = "msg";
			value = b.get(key).toString();
	//		Iterator<String> iterator = b.keySet().iterator();
	//		while(iterator.hasNext()) {
	//			String key = iterator.next();
	//			value = b.get(key).toString();
	//			Log.d(tag, "onMessage. "+key+" : "+value);
	//		}
			 Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
			 long milliseconds = 1000;
			 vibrator.vibrate(milliseconds);
			 
			 PowerManager powerManager = (PowerManager) context.getSystemService( Context.POWER_SERVICE );
			    
			 PowerManager.WakeLock wakeLock = powerManager.newWakeLock(
			 PowerManager.SCREEN_DIM_WAKE_LOCK |
			 PowerManager.ACQUIRE_CAUSES_WAKEUP , "TEST_1" );
			 
		     /* 10초 동안 화면 및 cpu 활성화 */
		     wakeLock.acquire( 10000 );
			    
		    //상태바 공지
		     
	//	     if (isPopup) {
	//	         // 팝업으로 사용할 액티비티를 호출할 인텐트를 작성한다.
	//	         Intent popupIntent = new Intent(context, GCMPopupActivity.class)
	//	                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	//	         // 그리고 호출한다.
	//	         context.startActivity(popupIntent);
	//	     }
		    NotificationManager nm = (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE );
		    Notification notification = new Notification(R.drawable.ic_stat_36, value, System.currentTimeMillis());
		    PendingIntent pendingIntent = null;
		    
		    pendingIntent = PendingIntent.getActivity(context, 0, new Intent( context, FirstStartActivity.class ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET), PendingIntent.FLAG_UPDATE_CURRENT);
		   
		    
		    notification.setLatestEventInfo(context, "버스킹알림!", value, pendingIntent);
		    //해당 메세지 선택시 상태바 아이콘 삭제
		    notification.flags = Notification.FLAG_AUTO_CANCEL;
		    nm.cancel( 0 );
		    nm.notify( 0, notification );
		}
	}

	@Override
	protected void onError(Context context, String errorId) {
		Log.d(tag, "onError. errorId : "+errorId);
	}

	@Override
	protected void onRegistered(Context context, String regId) {
		Log.d(tag, "onRegistered. regId : "+regId);
		FirstStartActivity.regId = regId;
	}

	@Override
	protected void onUnregistered(Context context, String regId) {
		Log.d(tag, "onUnregistered. regId : "+regId);
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		Log.d(tag, "onRecoverableError. errorId : "+errorId);
		return super.onRecoverableError(context, errorId);
	}
}