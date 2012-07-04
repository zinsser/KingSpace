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
      
    //ע��,������븲��ǰһ��֪ͨ,�����ò�ͬ��ID  
    private static final int NORMAL_NOTIFY_ID = 1;  
    private static final int CUSTOM_NOTIFY_ID = 2;  
      
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.notify);  
    }  
      
    // ��֪ͨͨ�¼�  
    public void normalNotify(View view) {  
        Context context = this;  
  
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);  
  
        int icon = android.R.drawable.stat_notify_voicemail;  
        CharSequence tickerText = "��֪ͨͨ";  
        long when = System.currentTimeMillis();  
        Notification notification = new Notification(icon, tickerText, when);  
  
        // �趨����  
        notification.defaults |= Notification.DEFAULT_SOUND;  
          
        //�趨��(���VIBRATEȨ��)  
        notification.defaults |= Notification.DEFAULT_VIBRATE;  
          
        // �趨LED������  
        notification.defaults |= Notification.DEFAULT_LIGHTS;  
          
        // ���õ����֪ͨ���Զ����  
        notification.flags |= Notification.FLAG_AUTO_CANCEL;  
  
        CharSequence contentTitle = "��֪ͨͨ�ı���";  
        CharSequence contentText = "֪ͨ�����ݲ���,һ�γ���������...";  
        Intent intent = new Intent(context, TargetActivity.class);  
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);  
  
        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);  
  
        mNotificationManager.notify(NORMAL_NOTIFY_ID, notification);  
    }  
  
    // ���Ի�֪ͨ����¼�  
    public void customNotify(View view) {  
        Context context = this;  
  
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);  
  
        int icon = android.R.drawable.stat_notify_voicemail;  
        CharSequence tickerText = "�Զ���֪ͨ";  
        long when = System.currentTimeMillis();  
        Notification notification = new Notification(icon, tickerText, when);  
  
        notification.flags |= Notification.FLAG_AUTO_CANCEL;  
  
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_notification_layout);  
        contentView.setImageViewResource(R.id.imageView, R.drawable.ic_launcher);  
        contentView.setTextViewText(R.id.textView, "����һ�����Ի���֪ͨ��ͼ,������ϵͳĬ�ϵ�֪ͨ��ͼ.");  
        // ָ�����Ի���ͼ  
        notification.contentView = contentView;  
  
        Intent intent = new Intent(context, TargetActivity.class);  
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);  
        // ָ��������ͼ  
        notification.contentIntent = contentIntent;  
  
        mNotificationManager.notify(CUSTOM_NOTIFY_ID, notification);  
    }  
}  

