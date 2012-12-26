package com.king.ftpdemo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FtpDemoActivity extends Activity {
	private static final int DATA_RECEIVED = 0;
	private static final String mServerAddr = "192.168.0.2";
	private static final int mPort = 4000;
	
	private Button mButtonReceived = null;
	private Button mButtonStop = null;	
	private TextView mTextViewReceivedInfo = null;
	private ClientObject mTcpClient = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mButtonReceived = (Button)findViewById(R.id.buttonReceived);
        mButtonReceived.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mTextViewReceivedInfo.setText("");
				mTcpClient = new ClientObject(mServerAddr, mPort);
				mTextViewReceivedInfo.append("Client Object Connected!\n");
			}
		});
        
        mButtonStop = (Button)findViewById(R.id.buttonStop);
        mButtonStop.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mTcpClient.stopRunner();
			}
		});
        mTextViewReceivedInfo = (TextView)findViewById(R.id.textviewReceivedInfo);
    }
    
    private Handler mHandler = new Handler(){
    	@Override
    	public void handleMessage(Message msg){
    		switch (msg.what){
    		case DATA_RECEIVED:
				Bundle bundle = msg.getData();
				byte[] datas = bundle.getByteArray("DATA");
				StringBuilder builder = new StringBuilder();
				builder.append(datas);
    			mTextViewReceivedInfo.append(builder.toString()+"\n");
    			break;
    		}
    	}
    };
    
	public class ClientObject{
		private Socket mSocket = null;
		private InputStream mInputStream = null;
		private OutputStream mOutputStream = null;
		private TCPReceiveRunner mReceiveRunner = null;
		
		public ClientObject(String addr, int port){
			try {
				mSocket = new Socket(addr, port);
				mInputStream = mSocket.getInputStream();
				mOutputStream = mSocket.getOutputStream();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
			mReceiveRunner = new TCPReceiveRunner(mHandler, mInputStream);
			new Thread(mReceiveRunner).start();
		}
		
		public void sendMessage(String outterMessage){
			TCPSenderThread tcpSendThread = new TCPSenderThread(mOutputStream, outterMessage);
			tcpSendThread.start();
		}
		
		public void stopRunner(){
			mReceiveRunner.stopRunner();
			//TODO: let sender thread run over
			try {
				mInputStream.close();
				mOutputStream.close();
				mSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public class TCPSenderThread extends Thread{
		private OutputStream mOutputStream = null;
		private String mOutterMessage = null;
		public TCPSenderThread(OutputStream os, String msg){
			mOutputStream = os;
			mOutterMessage = msg;
		}
		
		@Override
		public void run(){
			try {
				mOutputStream.write(mOutterMessage.getBytes());
				mOutputStream.flush();
			} catch (IOException e) {		
				e.printStackTrace();
			}
		}
	}
	
	public class TCPReceiveRunner implements Runnable{
		private InputStream mInputStream = null;
		private ByteBuffer mReceiveBuf = ByteBuffer.allocate(4 * 1024);
		private boolean mRunning = false;
		private Handler mHandler = null;
		public TCPReceiveRunner(Handler innerHandler, InputStream is) {
			mHandler = innerHandler;
			mInputStream = is;
		}

		public void stopRunner(){
			mRunning = false;
		} 
		
		public void run(){
			onStart();
			do{
				doRun();
			}while (mRunning);
			onExit();
		}
		
		protected void onStart() {		
			mRunning = true;
		}
		
		protected void doRun() {
			int sigleReadSize = 0;
			do {
				try {
					sigleReadSize = mInputStream.read(mReceiveBuf.array());
					Message msg = mHandler.obtainMessage(DATA_RECEIVED);
					Bundle bundle = new Bundle();
					bundle.putByteArray("DATA", mReceiveBuf.array());
					msg.setData(bundle);
					msg.sendToTarget();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}while (sigleReadSize > 0);
		}
		
		protected void onExit() {
			mReceiveBuf = null;
		}		
	}
}