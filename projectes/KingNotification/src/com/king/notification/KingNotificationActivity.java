package com.king.notification;

import com.king.notification.NotificationActivity;

import android.app.Activity;
import android.os.Bundle;
import android.app.NotificationManager; 
import android.content.Context;
import android.app.Notification;
import android.content.Intent; 
import android.app.PendingIntent;
import android.view.View;

public class KingNotificationActivity extends Activity {
    /** Called when the activity is first created. */
	private static final int ONGOING_ID = 0;  
	private NotificationManager mNotificationManager;  
	       
	public void onCreate(Bundle savedInstanceState) {  
	super.onCreate(savedInstanceState);  
	setContentView(R.layout.main);  
	setUpNotification();  
	}  
	  
	private void setUpNotification() {  
		Context context = this;  
		  
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);  
		  
		int icon = R.drawable.ic_launcher;  
		CharSequence tickerText = "����������";  
		long when = System.currentTimeMillis();  
		//�½�һ��Notificationʵ��  
		Notification notification = new Notification(icon, tickerText, when);  
		  
		// ��֪ͨ������"��������"��Ŀ��  
		notification.flags |= Notification.FLAG_ONGOING_EVENT;  
		  
		CharSequence contentTitle = "Notificationʾ��";  
		CharSequence contentText = "��������������,����˴�������ʾ����";  
		          
		Intent intent = new Intent(context, NotificationActivity.class);  
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);  

		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);  
		  
		mNotificationManager.notify(ONGOING_ID, notification);  
	}  
	  
	public void notify(View view) {  
		Intent intent = new Intent(this, NotificationActivity.class);  
		startActivity(intent);  
	}  
 
	public void onBackPressed() {  
		super.onBackPressed();  
		finish();  
		//ȡ��һ��֪ͨ  
		mNotificationManager.cancel(ONGOING_ID);  
		//��������  
		android.os.Process.killProcess(android.os.Process.myPid());  
		System.exit(0);  
	}  
}