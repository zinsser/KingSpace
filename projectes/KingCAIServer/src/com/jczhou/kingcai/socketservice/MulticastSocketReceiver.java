package com.jczhou.kingcai.socketservice;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;

import android.os.Handler;

import com.jczhou.kingcai.socketservice.FireMessageReceiver;
import com.jczhou.kingcai.KingCAIConfig;

public class MulticastSocketReceiver extends FireMessageReceiver{
	private boolean mStopMultiReceiver = false;
    private MulticastSocket mMulticastSocket = null;
    private DatagramPacket mDatagramPacket = null;
	private ByteBuffer mMsgBuffer = ByteBuffer.allocate(9216);

	public MulticastSocketReceiver(Handler handler){
		super(handler);
        try{
        	mMulticastSocket = new MulticastSocket(KingCAIConfig.mMulticastServerCommonPort);  
        	mMulticastSocket.joinGroup(InetAddress.getByName(KingCAIConfig.mMulticastServerCommonGroupIP));
        	mDatagramPacket = new DatagramPacket(mMsgBuffer.array(), mMsgBuffer.array().length);
        }catch (IOException e){
    		e.printStackTrace();
    	} 
	}
	
	@Override
	public void run(){
		do{
			try{
				if (mMsgBuffer != null) mMsgBuffer.clear();
				mMulticastSocket.receive(mDatagramPacket);
				String remoteip = mDatagramPacket.getAddress().getHostAddress().toString();
				FireMessage(remoteip, new String(mDatagramPacket.getData(), KingCAIConfig.mCharterSet).trim());
			}catch (IOException e){
				e.printStackTrace();
			}
		}while(!mStopMultiReceiver);
		
		mMulticastSocket.close();
	}		
};
