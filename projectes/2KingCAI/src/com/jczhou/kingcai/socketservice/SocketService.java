package com.jczhou.kingcai.socketservice;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Set;

//import android.app.Service;
import android.content.Context;
//import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
//import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.jczhou.kingcai.utils.KingCAIConfig;
import com.jczhou.kingcai.messageservice.ActiveMessage;
import com.jczhou.kingcai.messageservice.ActiveMessageManager;
import com.jczhou.kingcai.messageservice.LoginFinishedMessage;
import com.jczhou.kingcai.messageservice.LoginRequestMessage;
import com.jczhou.kingcai.messageservice.QueryServerMessage;
import com.jczhou.kingcai.messageservice.RequestMessage;
import com.jczhou.kingcai.socketservice.UDPSocketReceiver;
import com.jczhou.kingcai.socketservice.MulticastSocketReceiver;

public class SocketService/* extends Service */{
	private final static SocketService s_instance = new SocketService();
	private String mLocalIP = "";
	private SocketEventWaiter mWaiter = null;
	private String mServerIP;
	private Thread mUDPReceiver = null;
	private Thread mMulticastCommonReceiver = null;
	private Thread mMulticastImageReceiver = null;	
	/* 
    @Override
    public void onCreate() {
        super.onCreate();
	}
   
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.hasExtra("LocalIP")){
            Bundle extra = intent.getExtras();
        	mLocalIP = extra.getString("LocalIP");
        }
        
		return super.onStartCommand(intent, flags, startId);
    }

    @Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	*/
	private SocketService(){
		mUDPReceiver = new Thread(new UDPSocketReceiver(mHandler, KingCAIConfig.mUDPPort));
		mMulticastCommonReceiver = new Thread(new MulticastSocketReceiver(mHandler, 
							KingCAIConfig.mMulticastClientGroupIP, KingCAIConfig.mMulticastClientCommonPort));
/*		mMulticastImageReceiver =  new Thread(new MulticastSocketReceiver(mHandler, 
							KingCAIConfig.mMulticastClientImageGroupIP, KingCAIConfig.mMulticastClientImagePort));
							*/
		mMulticastImageReceiver = new Thread(new UDPSocketReceiver(mHandler, KingCAIConfig.mMulticastClientImagePort));
	}
	
	public static SocketService GetInstance(){
		return s_instance;
	}
	
	private Handler mHandler = new Handler(){
		private ActiveMessageManager mActiveMsgMgr = new ActiveMessageManager();
		
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle bundle = msg.getData();
			
			switch (msg.what){
				case KingCAIConfig.EVENT_RECEIVE_MESSAGE:
				{
					String peerip = bundle.getString("PEER");
					String msgData = bundle.getString("CONTENT");
					OnReceiveMessage(peerip, msgData);
					break;
				}	
			}
		}
		
		protected void OnReceiveMessage(String peer, String MsgData){
			Log.d("MessageHandler", "Raw Msg Data:" + MsgData);
			ActiveMessage activeMsgExecutor = null;
			Set<String> keysets = mActiveMsgMgr.mActiveMsgMap.keySet();
			String[] keys = (String[])keysets.toArray(new String[keysets.size()]);
			for (int i = 0; i < keys.length; ++i){
				if (MsgData.contains(keys[i])){
					activeMsgExecutor = mActiveMsgMgr.mActiveMsgMap.get(keys[i]).OnReceiveMessage(peer, MsgData);
					break;
				}
			}
			
			if (activeMsgExecutor != null){
				activeMsgExecutor.Execute();
			}	
		}		
	};

	public interface SocketEventWaiter{
		public abstract void  onTalkingFinished(final String serverip);
		public abstract void  onLoginSuccess(final String studentinfo);
		public abstract void  onLoginFail();	
		public abstract void  onPaperTitleReceived(String title);	
		public abstract void  onNewQuestion(String answer, int type, String content);
		public abstract void  onCleanPaper();
		public abstract void  onNewImage(Integer id, ByteBuffer buf);
	}

	public void StartService(SocketEventWaiter waiter, String localip){
		mWaiter = waiter;
		mLocalIP = localip;
		
		if (mUDPReceiver != null && !mUDPReceiver.isAlive()){
			mUDPReceiver.start();
		}
		
		if (mMulticastCommonReceiver != null && mMulticastCommonReceiver.isAlive()){
			mMulticastCommonReceiver.start();
		}
		
		if (mMulticastImageReceiver != null && mMulticastImageReceiver.isAlive()){
			mMulticastImageReceiver.start();
		}
	}

	public void StopService(){
		if (mUDPReceiver != null && !mUDPReceiver.isAlive()){
			mUDPReceiver.stop();
		}
		
		if (mMulticastCommonReceiver != null && mMulticastCommonReceiver.isAlive()){
			mMulticastCommonReceiver.stop();
		}
		
		if (mMulticastImageReceiver != null && mMulticastImageReceiver.isAlive()){
			mMulticastImageReceiver.stop();
		}
	}	
	
	public void QueryServer(){
		//1～2 见
		//3：UDPReceiver：ReciveMessage
		//4，消息接收线程收到服务器的响应消息后，将消息转发给主线程
		//5，发送协商完成消息[FinishIPTalking]到对方
		SendMessage(new QueryServerMessage(mLocalIP));
	}
	
	public void ConnectServer(String number, String password){
		SendMessage(new LoginRequestMessage(number, password), mServerIP);
	}
	
	public void AnswerReady(String msg){
		SendMessage(new LoginFinishedMessage(msg), mServerIP);
	}
	
	//UDP Socket use this function
	public static void SendMessage(RequestMessage msg, String destip){
		try {
			String rawMsg = msg.ToPack();
			DatagramSocket s = new DatagramSocket();
			InetAddress	local = InetAddress.getByName(destip);			
			DatagramPacket p = new DatagramPacket(rawMsg.getBytes(), rawMsg.getBytes().length,
								local, KingCAIConfig.mUDPPort);
			s.send(p);
			s.close();
		} catch (SocketException e){
			e.printStackTrace();
		} catch (UnknownHostException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	//multicast socket Socket use this function		
	public static void SendMessage(RequestMessage msg) {
	    try{
	    	String rawMsg = msg.ToPack();
	        int TTL = 4;
	        MulticastSocket multiSocket = new MulticastSocket();  
	        multiSocket.setTimeToLive(TTL);
	        InetAddress  GroupAddress = InetAddress.getByName(KingCAIConfig.mMulticastServerGroupIP);
	        DatagramPacket dp = new DatagramPacket(rawMsg.getBytes(), rawMsg.getBytes().length, 
	        					GroupAddress, KingCAIConfig.mMulticastServerCommonPort);
	        multiSocket.send(dp);
	        multiSocket.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	//客户机向服务器点对点发送图片
	public static void SendImage(RequestMessage msg, String destip) {
	    try{
	    	String rawMsg = msg.ToPack();
	        int TTL = 4;
	        MulticastSocket multiSocket = new MulticastSocket();  
	        multiSocket.setTimeToLive(TTL);
	        InetAddress  GroupAddress = InetAddress.getByName(destip);
	        DatagramPacket dp = new DatagramPacket(rawMsg.getBytes(), rawMsg.getBytes().length, 
	        					GroupAddress, KingCAIConfig.mUDPServerImagePort);
	        multiSocket.send(dp);
	        multiSocket.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	//服务器向客户机广播发送图片
	public static void SendImage(RequestMessage msg) {
	    try{
	    	String rawMsg = msg.ToPack();
	        int TTL = 4;
	        MulticastSocket multiSocket = new MulticastSocket();  
	        multiSocket.setTimeToLive(TTL);
	        InetAddress  GroupAddress = InetAddress.getByName(KingCAIConfig.mMulticastClientImageGroupIP);
	        DatagramPacket dp = new DatagramPacket(rawMsg.getBytes(), rawMsg.getBytes().length, 
	        					GroupAddress, KingCAIConfig.mMulticastClientImagePort);
	        multiSocket.send(dp);
	        multiSocket.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
    public static String getLocalIPAddress(Context ctx){
    	String ipAddr = null;
    	
    	WifiManager mWifiMgr = (WifiManager)ctx.getSystemService(Context.WIFI_SERVICE);   
    	if (mWifiMgr != null){
        	DhcpInfo dhcpInfo = mWifiMgr.getDhcpInfo();
        	if (dhcpInfo != null){
	        	ipAddr = ((dhcpInfo.ipAddress) & 0xff) + "."
	        			+ ((dhcpInfo.ipAddress >> 8) & 0xff) + "."
	        			+ ((dhcpInfo.ipAddress >> 16) & 0xff) + "."
	        			+ ((dhcpInfo.ipAddress >> 24) & 0xff);	        	
        	}
    	}
    	
    	return ipAddr;
    }	
    
    public void SetServerIPAddr(String ServerIP){
    	mServerIP = ServerIP;
    }

    public String GetServerIPAddr(){
    	return mServerIP;
    }
    
    public void ResetServerAddr(){
    	mServerIP = null;
    }
    
    public SocketEventWaiter GetWaiter(){
    	return mWaiter;
    }
    
    public void SetWaiter(SocketEventWaiter waiter){
    	mWaiter = null;
    	mWaiter = waiter;
    }
}
