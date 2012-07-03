package com.jczhou.kingcai;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

public class KingMThreadActivity extends Activity {
	private Handler handler2 = null;
	private Handler handler1 = new Handler();
			
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initHandlerThread2();
        //        initHandlerThread1();
    }

	private void initHandlerThread1(){
		handler1.post(new MyRunnable1());
		Log.d("KingMThread", "Oncreate--Thread id is £º" + Thread.currentThread().getId());
	}    

	
	private class MyRunnable1 implements Runnable {
		public void run() {
			Log.d("MyRunnable1", "Runnable--Thread is running");
			Log.d("MyRunnable1", "Runnable--Thread id is £º"+Thread.currentThread().getId());
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}	
	
    private void initHandlerThread2(){
    	HandlerThread handlerThread = new HandlerThread("myHandlerThread");
    	handlerThread.start();
    	handler2 = new Handler(handlerThread.getLooper());
    	handler2.post(new MyRunnable2());
    	Log.d("KingMThread", "OnCreate--Thread id is:"+ Thread.currentThread().getId());
    }

	private class MyRunnable2 implements Runnable {
		public void run(){
			Log.d("MyRunnalbe", "Runnable--Thread is running");
			Log.d("MyRunnalbe", "Runnable--Thread id is £º" + Thread.currentThread().getId());
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
