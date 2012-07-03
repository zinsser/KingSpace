package com.jczhou.kingcai.socketservice;

import com.jczhou.kingcai.KingCAIConfig;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public abstract class FireMessageReceiver implements Runnable{
	private Handler mHandler = null;
	protected FireMessageReceiver(Handler handler){
		mHandler = handler;
	}

	protected void FireMessage(String peerip, String msgData){
		Bundle bundle = new Bundle();
		bundle.putString("PEER", peerip);
		bundle.putString("CONTENT", msgData);
		Message msg = Message.obtain(mHandler, KingCAIConfig.EVENT_RECEIVE_MESSAGE);
		msg.setData(bundle);
		msg.sendToTarget();
	}
};