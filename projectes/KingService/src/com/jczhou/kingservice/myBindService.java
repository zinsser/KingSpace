package com.jczhou.kingservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class myBindService extends Service{
	 //�����һ��Tag��ǩ  
    private static final String TAG = "myBindService";  

    @Override  
    public void onCreate() {  
        Log.e(TAG, "start onCreate~~~");  
        mPlayer = MediaPlayer.create(myBindService.this, R.raw.spring);        
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
    
    //���ﶨ���һ��Binder�࣬����onBind()�з��������Activity�Ǳ߿��Ի�ȡ��        
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	public MediaPlayer mPlayer;
    private final IMusicControlService.Stub mBinder = new IMusicControlService.Stub() { 
        @Override 
        public void playMusic() throws RemoteException { 
            Log.i("myBindService", "Play music..."); 
            mPlayer.start(); 
        }

        @Override 
        public void stopMusic() throws RemoteException { 
            Log.i("myBindService", "Stop music..."); 
            if (mPlayer.isPlaying()) { 
            	mPlayer.stop(); 
            } 
        } 
    };
}
