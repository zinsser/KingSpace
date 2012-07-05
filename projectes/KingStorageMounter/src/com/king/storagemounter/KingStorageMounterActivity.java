package com.king.storagemounter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.os.storage.OnObbStateChangeListener;
import android.view.View;
import android.widget.Button;

public class KingStorageMounterActivity extends Activity {
	private StorageManager mStorageManager = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        if (mStorageManager == null){
        	mStorageManager = (StorageManager)getSystemService(Context.STORAGE_SERVICE);
        }
        Button mount = (Button)findViewById(R.id.buttonMounte);
        mount.setEnabled(mStorageManager.isObbMounted(filename));
        mount.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mStorageManager.mountObb(filename, key, mStorageListener);
			}
		});
        Button unmount = (Button)findViewById(R.id.buttonUnmounte);
        unmount.setEnabled(mStorageManager.isObbMounted(filename))
        unmount.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mStorageManager.unmountObb(filename, force, mStorageListener);
				
			}
		});
    }
    public void onResume(){
    	super.onResume();
    }
    private OnObbStateChangeListener mStorageListener = new OnObbStateChangeListener(){
    	public void onObbStateChange(String path, int state){
    		
    	}
    };
}