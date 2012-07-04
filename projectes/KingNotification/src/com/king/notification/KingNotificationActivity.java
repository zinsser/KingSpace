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
		CharSequence tickerText = "程序已启动";  
		long when = System.currentTimeMillis();  
		//新建一个Notification实例  
		Notification notification = new Notification(icon, tickerText, when);  
		  
		// 把通知放置在"正在运行"栏目中  
		notification.flags |= Notification.FLAG_ONGOING_EVENT;  
		  
		CharSequence contentTitle = "Notification示例";  
		CharSequence contentText = "程序正在运行中,点击此处跳到演示界面";  
		          
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
		//取消一个通知  
		mNotificationManager.cancel(ONGOING_ID);  
		//结束进程  
		android.os.Process.killProcess(android.os.Process.myPid());  
		System.exit(0);  
	}  
}