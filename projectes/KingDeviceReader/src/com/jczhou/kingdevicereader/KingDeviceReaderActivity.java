package com.jczhou.kingdevicereader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class KingDeviceReaderActivity extends Activity {
	private TextView mTxtDeviceInfo = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mTxtDeviceInfo = (TextView)findViewById(R.id.textDeviceInfo);
        
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mTxtDeviceInfo.setText(QueryVersionInfo()+"\n");
			}
		});
        
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		    	mTxtDeviceInfo.setText(QueryCPUInfo());
			}
		});

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		    	mTxtDeviceInfo.setText(QueryMemInfo());
			}
		}) ; 
        
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		    	mTxtDeviceInfo.setText(QueryDiskInfo());
			}
		}) ; 
        
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mTxtDeviceInfo.setText(QueryDisplayInfo());
			}
		}) ;      
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mTxtDeviceInfo.setText(QueryNetInfo());
			}
		}) ;          
    }
    
    public class CMDExecute {
    	public String run(String [] cmd, String workdirectory) throws IOException {
	    	String result = "";	
	    	try {
	    		ProcessBuilder builder = new ProcessBuilder( cmd );
		    	//设置一个路径
		    	if ( workdirectory != null )
		    		builder.directory(new File(workdirectory));
		    	builder.redirectErrorStream (true);
		    	Process process = builder.start();
		    	InputStream in = process.getInputStream();
		    	byte[] re = new byte[1024] ;
		    	while (in.read(re) != -1) {
		    		result = result + new String(re).trim();
		    	}
		    	in.close();
	    	} catch ( Exception ex ) {
	    		ex.printStackTrace();
	    	}
	    	return result ;
    	}
   	}    
    
	private String QueryVersionInfo(){
		String result = null;
		CMDExecute cmdexe = new CMDExecute ( );
		try {
			String[ ] args = {"/system/bin/cat", "/proc/version" };
			result = cmdexe.run(args, "system/bin/");
		} catch (IOException ex) {
			ex.printStackTrace( );
		}
		return result;
	}    
    
    private String QueryCPUInfo(){
    	String result = null;
    	CMDExecute cmdexe = new CMDExecute();
    	try {
    	String[ ] args = { "/system/bin/cat", "/proc/cpuinfo" };
    	result = cmdexe.run(args, "/system/bin/");
    	Log.i("result","result=" + result);
    	} catch (IOException ex) {
    	ex.printStackTrace();
    	}
    	return result;    	
    }
    
    private String QueryMemInfo(){
		StringBuffer memoryInfo = new StringBuffer();
		final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager. MemoryInfo outInfo = new ActivityManager. MemoryInfo();
		activityManager.getMemoryInfo(outInfo);
		memoryInfo.append("\nTotal Available Memory :").append(outInfo.availMem >> 10).append("k");
		memoryInfo.append("\nTotal Available Memory :").append(outInfo.availMem >> 20).append("k");
		memoryInfo.append("\nIn low memory situation:").append(outInfo.lowMemory);
		String result = null;
		CMDExecute cmdexe = new CMDExecute();
		try {
			String[ ] args = { "/system/bin/cat", "/proc/meminfo" };
			result = cmdexe.run(args, "/system/bin/");
		} catch (IOException ex) {
			Log.i("fetch_process_info","ex=" + ex.toString());
		}
		return memoryInfo.toString()+"\n\n"+result;
    }
    
    private String QueryDiskInfo(){
		String result = null;
		CMDExecute cmdexe = new CMDExecute();
		try {
			String[] args = { "/system/bin/df"};
			result = cmdexe.run(args, "/system/bin/");
			Log.i("result","result=" + result);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return result;
    }
    
    private String QueryDisplayInfo(){
		String str = " ";
		DisplayMetrics dm = new DisplayMetrics();
		dm = getApplicationContext().getResources().getDisplayMetrics();
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		float density = dm.density;
		float xdpi = dm.xdpi;
		float ydpi = dm.ydpi;
		int dpi = dm.densityDpi;
		float scaldensity = dm.scaledDensity;
		str += "The absolute width: " + String.valueOf(screenWidth) + " pixels\n";
		str += "The absolute heightin: " + String.valueOf(screenHeight) + " pixels\n";
		str += "The logical density of the display: " + String.valueOf(density) + "\n";
		str += "X dimension : " + String.valueOf(xdpi) +" pixels per inch\n";
		str += "Y dimension : " + String.valueOf(ydpi) +" pixels per inch\n";
		str += "DPI:" + String.valueOf(dpi) + "\n";
		str += "scale density:" + String.valueOf(scaldensity) + "\n";
		return str;
    }
    
    private String QueryNetInfo(){
    	String str = "";
    	CMDExecute cmdexe = new CMDExecute();
    	try{
    		String[] args = {"/system/bin/netcfg"};
    		str = cmdexe.run(args, "/system/bin/");
    	}catch (IOException ex){
    		ex.printStackTrace();
    	}
    	return str;
    }    
}