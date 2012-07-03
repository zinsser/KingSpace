package com.jczhou.kingcai.socketservice;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;

import android.os.Handler;

import com.jczhou.kingcai.socketservice.FireMessageReceiver;
import com.jczhou.kingcai.KingCAIConfig;


public class UDPSocketReceiver extends FireMessageReceiver{
	// 接收的字节大小，客户端发送的数据不能超过这个大小
//	private byte[] mMsgBuffer = new byte[1024];
	private DatagramSocket mDatagramSocket = null;
	private DatagramPacket mDatagramPacket = null;
	private ByteBuffer mMsgBuffer = ByteBuffer.allocate(9216);
	public UDPSocketReceiver(Handler handler){
		super(handler);
		try {
			// 建立Socket连接
			mDatagramSocket = new DatagramSocket(KingCAIConfig.mUDPPort);
			mDatagramPacket = new DatagramPacket(mMsgBuffer.array(), mMsgBuffer.array().length);
		}catch (SocketException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		String result = "[Starting]";
		do{
			//1～2：IPTalking:StartTalking
	    	//3，服务器进程接收到该广播消息后，使用UDP向该IP响应服务器IP地址[ResponseClient]xxx.xxx.xxx.xxx
			//4：IPTalking:Handler
			try {
				mMsgBuffer.clear();
				mDatagramSocket.receive(mDatagramPacket);
				String remoteip = mDatagramPacket.getAddress().getHostAddress().toString();
				FireMessage(remoteip, new String(mDatagramPacket.getData(), mDatagramPacket.getOffset(), 
						mDatagramPacket.getLength(), KingCAIConfig.mCharterSet).trim());
//				FireMessage(remoteip, new String(mDatagramPacket.getData(), KingCAIConfig.mCharterSet).trim());
			}catch(IOException e) {
				e.printStackTrace();
			}
		}while (!result.contains("[NetLogout]")
				|| !"".equals(result));
		
		mDatagramSocket.close();
	}
}
