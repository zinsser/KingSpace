package com.king.ftpdemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FtpDemoActivity extends Activity {
	private static final int DATA_RECEIVED = 0;
	private static final String mServerAddr = "192.168.0.102";
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
			
			public void onClick(View v) {
				mTextViewReceivedInfo.setText("");
				mTcpClient = new ClientObject(mServerAddr, mPort);
				mButtonReceived.setEnabled(false);
			}
		});
        
        mButtonStop = (Button)findViewById(R.id.buttonStop);
        mButtonStop.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				mTcpClient.stopRunner();
				mTextViewReceivedInfo.append("Client Object Disconnected!\n");
				mButtonReceived.setEnabled(true);				
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
				int  size = bundle.getInt("SIZE");
				StringBuilder builder = new StringBuilder();
				builder.append(datas);
				builder.append("\n"+size+"\n");
    			mTextViewReceivedInfo.append(builder);
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
			} finally{
				if (mSocket != null && mInputStream != null){
					mTextViewReceivedInfo.append("Client Object Connected!\n");
					mReceiveRunner = new TCPReceiveRunner(mHandler, mInputStream);
					new Thread(mReceiveRunner).start();
				}else{
					mTextViewReceivedInfo.append("Client Object Connected Failure!\n");
				}
			}
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
		private ByteBuffer mReceiveBuf = ByteBuffer.allocate(4096);
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
			String rootPath = Environment.getExternalStorageDirectory().getPath() + "/KingCAI";
			File  rootDir = new File(rootPath);
			if (!rootDir.exists()) {
				rootDir.mkdirs();
			}
			File file = new File(rootDir.getPath()+ '/', "kkk.mp3");
			if (file.exists()) return;
			FileOutputStream outStream = null;
			try {
				outStream = new FileOutputStream(file);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			do {
				try {
					sigleReadSize = mInputStream.read(mReceiveBuf.array());
					if (sigleReadSize > 0){
						outStream.write(mReceiveBuf.array(), mReceiveBuf.arrayOffset(), sigleReadSize);
						Message msg = mHandler.obtainMessage(DATA_RECEIVED);
						Bundle bundle = new Bundle();
						bundle.putByteArray("DATA", mReceiveBuf.array());
						bundle.putInt("SIZE", sigleReadSize);
						msg.setData(bundle);
						msg.sendToTarget();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}while (sigleReadSize > 0);
			try {
				if (outStream != null) outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		
		protected void onExit() {
			mReceiveBuf = null;
		}		
	}
}