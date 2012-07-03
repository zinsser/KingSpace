package com.jczhou.kingservice;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Toast;

public class testServiceActivity extends Activity {
	public static final String SERVICE_CONTROL_ACTION = "com.jczhou.kingservice.myStartService";
	public static final String STATIC_RECEIVER_ACTION = "com.jczhou.kingservice.staticreceiver";
	public static final String DYNAMIC_RECEIVER_ACTION = "com.jczhou.kingservice.dynamicreceiver";
	
	private dynamicReceiver mDynamicReceiver = new dynamicReceiver();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        StartService();
//        BindService();
        BindStartedService();
        
        findViewById(R.id.buttonSendBroadcast).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mServiceChannel.sendBroadcastMessage();
			}
		});
        
    }
    
    @Override
    public void onStart(){
        super.onStart();
    	registerReceiver(mDynamicReceiver, new IntentFilter(DYNAMIC_RECEIVER_ACTION));       
    }

    @Override
    public void onStop(){
    	unregisterReceiver(mDynamicReceiver);    	
    	super.onStop();
    }

    public void StartService(){
        findViewById(R.id.buttonStartService).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startService(new Intent(SERVICE_CONTROL_ACTION));
			}
		});
        
        findViewById(R.id.buttonStopService).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				stopService(new Intent(SERVICE_CONTROL_ACTION));
			}
		});    	
    }

    public void BindStartedService(){
    	findViewById(R.id.buttonBindService).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
                Intent intent = new Intent(); 
				intent.setClass(testServiceActivity.this, myStartService.class); 
				bindService(intent, mServiceChannel, Context.BIND_AUTO_CREATE);
			}
		});
    	
    	findViewById(R.id.buttonUnbindService).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//mServiceConnection.StopService();
				unbindService(mServiceChannel);
			}
		});
    }

    //这里需要用到ServiceConnection在Context.bindService和context.unBindService()里用到  
    private myServiceChannel mServiceChannel = new myServiceChannel();
    private class myServiceChannel implements ServiceConnection{     
    	myStartService mMyService = null;
        public void onServiceConnected(ComponentName name, IBinder service) { 
        	mMyService = ((myStartService.MyBinder)service).getService();  
        }  
        
        public void onServiceDisconnected(ComponentName name) {  
        	mMyService = null;
        }
        
        public void sendBroadcastMessage(){
        	Intent intent = new Intent(STATIC_RECEIVER_ACTION);
        	mMyService.sendBroadcast(intent);

        	Intent intentDynamic = new Intent(DYNAMIC_RECEIVER_ACTION);
        	mMyService.sendBroadcast(intentDynamic);        	
        }
    };     
    
    
    
    public void BindService(){	
    	findViewById(R.id.buttonBindService).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
                Intent intent = new Intent(); 
				intent.setClass(testServiceActivity.this, myBindService.class); 
				bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
			}
		});
    	
    	findViewById(R.id.buttonUnbindService).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mServiceConnection.StopService();
			}
		});
    }
    private myServiceConnection mServiceConnection = new myServiceConnection();
    private class myServiceConnection implements ServiceConnection{ 
    
        public IMusicControlService mMusicControlService;
        // 第一次连接service时会调用这个方法 
		@Override
		public void onServiceConnected(ComponentName arg0, IBinder service) {
			mMusicControlService = IMusicControlService.Stub.asInterface(service);	
        	try {
				mMusicControlService.playMusic();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
        // service断开的时候会调用这个方法 
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
		
			mMusicControlService = null; 
		}
		
		public void StopService(){
        	try {
				mMusicControlService.stopMusic();
			} catch (RemoteException e) {
				e.printStackTrace();
			}		
			unbindService(this);
		}

    };  
    
}