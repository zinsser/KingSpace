package com.jczhou.kingcai.socketservice;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Set;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.jczhou.kingcai.messageservice.ActiveMessage;
import com.jczhou.kingcai.messageservice.ActiveMessageManager;
import com.jczhou.kingcai.messageservice.PaperTitleMessage;
import com.jczhou.kingcai.messageservice.QuestionMessage;
import com.jczhou.kingcai.messageservice.RequestMessage;
import com.jczhou.kingcai.socketservice.MulticastSocketReceiver;
import com.jczhou.kingcai.socketservice.UDPSocketReceiver;
import com.jczhou.kingcai.DatabaseImpl;
import com.jczhou.kingcai.IDatabase;
import com.jczhou.kingcai.KingCAIConfig;

public class SocketService {
	private final static SocketService s_instance = new SocketService();
	private ArrayList<String> mRegistedClients = new ArrayList<String>();
	private SocketEventWaiter mWaiter = null;	
	private IDatabase mDB = null;
	private Thread mMulticastThread = null;
	private Thread mUDPThread = null;
	private String mLocalIP = null;
	private SocketService(){
		mMulticastThread = new Thread(new MulticastSocketReceiver(mHandler));
		mUDPThread = new Thread(new UDPSocketReceiver(mHandler));		
	}
	public static SocketService GetInstance(){
		return s_instance;
	}
	
	private Context mContext = null;
	
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
		public abstract void  onServiceStart(final String localip);
		public abstract void  onQueryRequest(final String peerip);
		public abstract void  onQueryFinished();
		public abstract void  onLoginRequest(final String number, final String peerip);
		public abstract void  onLoginFinished();
		public abstract void  onPaperReady();
	}		

	
	public void StartService(Context ctx, SocketEventWaiter waiter){
		mWaiter = waiter;
		mContext = ctx;
		mDB = new DatabaseImpl();
		
		if (mMulticastThread != null && !mMulticastThread.isAlive()){
			mMulticastThread.start();
		}
		
		if (mUDPThread != null && !mUDPThread.isAlive()){
			mUDPThread.start();
		}
	}
	public void DispatchPaperTitle(String Title){
		SendMessage(new PaperTitleMessage(Title));
	}
	
	public void BroadcastPaper(String paper){
		SendMessage(new QuestionMessage(paper));
	}
	
	//multicast socket Socket use this function		
	public static void SendMessage(RequestMessage msg) {
	    try{
	    	String rawMsg = new String(msg.ToPack().getBytes(KingCAIConfig.mCharterSet), "UTF-8");
//	    	String rawMsg = msg.ToPack();
//		        int TTL = TTLTime;
	        MulticastSocket multiSocket = new MulticastSocket();  
	        //multiSocket.setTimeToLive(TTL);
	        InetAddress  GroupAddress = InetAddress.getByName(KingCAIConfig.mMulticastClientCommonGroupIP);
	        DatagramPacket dp = new DatagramPacket(rawMsg.getBytes(), rawMsg.getBytes().length, 
	        					GroupAddress, KingCAIConfig.mMulticastClientCommonPort);  
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

	//服务器向客户机广播发送图片
	public static void SendImage(RequestMessage msg, String destip) {
		try {
//	    	String rawMsg = new String(msg.ToPack().getBytes(KingCAIConfig.mCharterSet), KingCAIConfig.mCharterSet);
			String rawMsg = msg.ToPack();
			DatagramSocket s = new DatagramSocket();
			InetAddress	local = InetAddress.getByName(destip);
			
			DatagramPacket p = new DatagramPacket(rawMsg.getBytes(), rawMsg.getBytes().length,
								local, KingCAIConfig.mMulticastClientImagePort);
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
	
	public static void SendMessage(RequestMessage msg, String destip, int destport){
	    // 发送信息  
        try {  
            // 创建socket对象，指定服务器端地址和端口号  
        	Socket socket = new Socket(destip, destport);  
            // 获取 Client 端的输出流  
            PrintWriter out = new PrintWriter(new BufferedWriter(  
                    new OutputStreamWriter(socket.getOutputStream())), true);  
            // 填充信息  
            out.println(msg.ToPack());  
            // 关闭  
            socket.close();
            
        } catch (UnknownHostException e1) {  
            e1.printStackTrace();  
        } catch (IOException e1) {  
            e1.printStackTrace();  
        }  
	}

	
	//UDP Socket use this function
	public static void SendMessage(RequestMessage msg, String destip){
		try {
	    	String rawMsg = new String(msg.ToPack().getBytes(KingCAIConfig.mCharterSet), KingCAIConfig.mCharterSet);
//			String rawMsg = msg.ToPack();
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

	public String getLocalIPAddress(){
    	if (mLocalIP == null){
	    	WifiManager mWifiMgr = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);   
	    	if (mWifiMgr != null){
	        	DhcpInfo dhcpInfo = mWifiMgr.getDhcpInfo();
	        	if (dhcpInfo != null){
	        		mLocalIP = new String(((dhcpInfo.ipAddress) & 0xff) + "."
		        			+ ((dhcpInfo.ipAddress >> 8) & 0xff) + "."
		        			+ ((dhcpInfo.ipAddress >> 16) & 0xff) + "."
		        			+ ((dhcpInfo.ipAddress >> 24) & 0xff));	        	
	        	}
	    	}
    	}
    	
    	return mLocalIP;
    }
    
    public void AddClient(String ClientIP){
    	mRegistedClients.add(ClientIP);
    }
    
    public void RemoveClient(String ClientIP){
    	mRegistedClients.remove(ClientIP);
    }
    
    public String GetStudentInfoByID(String number, String password){
    	return mDB.GetStudentInfoByID(number, password);
    }
    
    public SocketEventWaiter  GetWaiter(){
    	return mWaiter;
    }
}

