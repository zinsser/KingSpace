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
	// ���յ��ֽڴ�С���ͻ��˷��͵����ݲ��ܳ��������С
//	private byte[] mMsgBuffer = new byte[1024];
	private DatagramSocket mDatagramSocket = null;
	private DatagramPacket mDatagramPacket = null;
	private ByteBuffer mMsgBuffer = ByteBuffer.allocate(9216);
	public UDPSocketReceiver(Handler handler){
		super(handler);
		try {
			// ����Socket����
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
			//1��2��IPTalking:StartTalking
	    	//3�����������̽��յ��ù㲥��Ϣ��ʹ��UDP���IP��Ӧ������IP��ַ[ResponseClient]xxx.xxx.xxx.xxx
			//4��IPTalking:Handler
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
