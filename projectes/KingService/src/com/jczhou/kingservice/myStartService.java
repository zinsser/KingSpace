package com.jczhou.kingservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class myStartService extends Service{
	 //定义个一个Tag标签  
    private static final String TAG = "myStartService";  

    @Override  
    public void onCreate() {  
        Log.e(TAG, "start onCreate~~~");  
        super.onCreate();  
    }  
      
    @Override  
    public void onStart(Intent intent, int startId) {  
        Log.e(TAG, "start onStart~~~");  
        super.onStart(intent, startId);   
    }  
      
    @Override  
    public void onDestroy() {  
        Log.e(TAG, "start onDestroy~~~");  
        super.onDestroy();  
    }  
      
      
    @Override  
    public boolean onUnbind(Intent intent) {  
        Log.e(TAG, "start onUnbind~~~");  
        return super.onUnbind(intent);  
    }  
    
    //这里定义吧一个Binder类，用在onBind()有方法里，这样Activity那边可以获取到 
    private MyBinder mBinder = new MyBinder();      
	@Override
	public IBinder onBind(Intent intent) {
        Log.e(TAG, "start IBinder~~~");  
        return mBinder;
	}
	
	public class MyBinder extends Binder{
		myStartService getService(){  
            return myStartService.this;  
        }  
    }
}
