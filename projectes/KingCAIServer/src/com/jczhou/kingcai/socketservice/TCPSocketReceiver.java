package com.jczhou.kingcai.socketservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import com.jczhou.kingcai.KingCAIConfig;

import android.os.Handler;
import android.util.Log;

public class TCPSocketReceiver extends FireMessageReceiver{
    private ServerSocket mTcpSocket = null;
    private boolean mStopped = false;
	protected TCPSocketReceiver(Handler handler) {
		super(handler);
		try{
        // 创建一个serversocket对象，并让他在Port端口监听  
			mTcpSocket = new ServerSocket(KingCAIConfig.mTCPPort);
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (!mStopped) {
			try{
			    // 调用serversocket的accept()方法，接收客户端发送的请求  
			    Socket socket = mTcpSocket.accept();
			    BufferedReader buffer = new BufferedReader(  
			             new InputStreamReader(socket.getInputStream()));  
			    // 读取数据  
			    String msg = buffer.readLine();
			    Log.d("TCPSocket", "msg:" + msg);
		        FireMessage(null, msg);
			} catch (IOException e) {  
				e.printStackTrace();  
			}  
		}
 
		try {
			mTcpSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
}