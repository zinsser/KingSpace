package com.jczhou.kingcai;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

public class KingMouseControlActivity extends Activity {
	private final static int GROUP_NORMAL = 0;
	private final static int MENU_CONNECT_TO_PC = 0;

	private int mTargetWidth = 0;
	private int mTargetHeight = 0;
	private String mTargetAddress = "192.168.1.100";
	private int mTargetPort = 2012;
	
	private EditTextPreference mConnectInfoEditor = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	menu.add(GROUP_NORMAL, MENU_CONNECT_TO_PC, 0, "Á¬½Óµ½PC");
    	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()){
    	case MENU_CONNECT_TO_PC:
    		mConnectInfoEditor = new EditTextPreference(this);
    		break;
    	}
    	
    	return super.onOptionsItemSelected(item);
    }
    
	public void SendMessage(String pointerInfo, String destip){
		try {
			DatagramSocket s = new DatagramSocket();
			InetAddress	target = InetAddress.getByName(destip);			
			DatagramPacket p = new DatagramPacket(pointerInfo.getBytes(), pointerInfo.getBytes().length,
										target, mTargetPort);
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

	@Override
	public boolean onTouchEvent(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_MOVE) {     
        	SendMessage("[x]"+event.getX()+"[y]"+event.getY(), mTargetAddress);
        }  
 
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
        	SendMessage("[x]"+event.getX()+"[y]"+event.getY(), mTargetAddress);
        }
        
        if (event.getAction() == MotionEvent.ACTION_UP) {
        	SendMessage("[x]-1[y]-1", mTargetAddress);
        }

        return true;
	}
}