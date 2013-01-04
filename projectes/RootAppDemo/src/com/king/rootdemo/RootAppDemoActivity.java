package com.king.rootdemo;

import java.io.IOException;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

public class RootAppDemoActivity extends Activity {
	private final ComponentName mAppDetailInfoComponent = new  ComponentName("com.android.settings", 
			"com.android.settings.applications.InstalledAppDetails");
	private PackageManager mPackageMgr = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mPackageMgr = getPackageManager();
        if (mPackageMgr != null){
        	mPackageMgr.setComponentEnabledSetting(mAppDetailInfoComponent, 
				PackageManager.COMPONENT_ENABLED_STATE_DISABLED, 
				PackageManager.DONT_KILL_APP);
    	}else{
    		((TextView)findViewById(R.id.textViewHello)).setText("package manager create error!");
    	}
    }
/*    
    private void forkRootProcess(){
    	Process process = null;
    	try {
			process = Runtime.getRuntime().exec("su");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }*/
}