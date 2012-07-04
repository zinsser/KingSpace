package com.king.notification;

import android.app.Activity;  
import android.app.Notification;  
import android.app.NotificationManager;  
import android.app.PendingIntent;  
import android.content.Context;  
import android.content.Intent;  
import android.os.Bundle;  
import android.view.View;  
import android.widget.RemoteViews;

public class NotificationActivity extends Activity {  
      
    //注意,如果不想覆盖前一个通知,需设置不同的ID  
    private static final int NORMAL_NOTIFY_ID = 1;  
    private static final int CUSTOM_NOTIFY_ID = 2;  
      
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.notify);  
    }  
      
    // 普通通知事件  
    public void normalNotify(View view) {  
        Context context = this;  
  
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);  
  
        int icon = android.R.drawable.stat_notify_voicemail;  
        CharSequence tickerText = "普通通知";  
        long when = System.currentTimeMillis();  
        Notification notification = new Notification(icon, tickerText, when);  
  
        // 设定声音  
        notification.defaults |= Notification.DEFAULT_SOUND;  
          
        //设定震动(需加VIBRATE权限)  
        notification.defaults |= Notification.DEFAULT_VIBRATE;  
          
        // 设定LED灯提醒  
        notification.defaults |= Notification.DEFAULT_LIGHTS;  
          
        // 设置点击此通知后自动清除  
        notification.flags |= Notification.FLAG_AUTO_CANCEL;  
  
        CharSequence contentTitle = "普通通知的标题";  
        CharSequence contentText = "通知的内容部分,一段长长的文字...";  
        Intent intent = new Intent(context, TargetActivity.class);  
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);  
  
        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);  
  
        mNotificationManager.notify(NORMAL_NOTIFY_ID, notification);  
    }  
  
    // 个性化通知点击事件  
    public void customNotify(View view) {  
        Context context = this;  
  
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);  
  
        int icon = android.R.drawable.stat_notify_voicemail;  
        CharSequence tickerText = "自定义通知";  
        long when = System.currentTimeMillis();  
        Notification notification = new Notification(icon, tickerText, when);  
  
        notification.flags |= Notification.FLAG_AUTO_CANCEL;  
  
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_notification_layout);  
        contentView.setImageViewResource(R.id.imageView, R.drawable.ic_launcher);  
        contentView.setTextViewText(R.id.textView, "这是一个个性化的通知视图,代替了系统默认的通知视图.");  
        // 指定个性化视图  
        notification.contentView = contentView;  
  
        Intent intent = new Intent(context, TargetActivity.class);  
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);  
        // 指定内容意图  
        notification.contentIntent = contentIntent;  
  
        mNotificationManager.notify(CUSTOM_NOTIFY_ID, notification);  
    }  
}  

