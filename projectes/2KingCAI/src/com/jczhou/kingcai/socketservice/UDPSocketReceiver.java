package com.jczhou.kingcai.socketservice;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;

import android.os.Handler;
import android.util.Log;

import com.jczhou.kingcai.socketservice.FireMessageReceiver;
import com.jczhou.kingcai.utils.KingCAIConfig;


public class UDPSocketReceiver extends FireMessageReceiver{
	private DatagramSocket mDatagramSocket = null;
	private DatagramPacket mDatagramPacket = null;
	// ���յ��ֽڴ�С���ͻ��˷��͵����ݲ��ܳ��������С
	private ByteBuffer mMsgBuffer = ByteBuffer.allocate(20480);
	
	public UDPSocketReceiver(Handler handler, int port){
		super(handler);
		try {
			// ����Socket����
			mDatagramSocket = new DatagramSocket(port/*KingCAIConfig.mUDPPort*/);
			mDatagramPacket = new DatagramPacket(mMsgBuffer.array(), mMsgBuffer.array().length);
		}catch (SocketException e){
			e.printStackTrace();
		}
	}
	
	public void run(){
		String result = "[Starting]";
		do{
			try {
				mMsgBuffer.clear();
				mDatagramSocket.receive(mDatagramPacket);
				String remoteip = mDatagramPacket.getAddress().getHostAddress().toString();
				Log.d("UDPReceiver", mDatagramPacket.getAddress().getHostAddress().toString()
						+ ":"+ new String(mDatagramPacket.getData(), KingCAIConfig.mCharterSet));
				String rawmsg = new String(mDatagramPacket.getData(), mDatagramPacket.getOffset(), 
						mDatagramPacket.getLength(), KingCAIConfig.mCharterSet);
				FireMessage(remoteip, rawmsg.trim());
			}catch(IOException e) {
				e.printStackTrace();
			}
		}while (!result.contains("[NetLogout]")
				|| !"".equals(result));
		
		mDatagramSocket.close();
	}
}