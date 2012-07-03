package com.jczhou.kingservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class dynamicReceiver extends BroadcastReceiver{
	
	@Override
	public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Toast.makeText(context, "dynamic :" + action, 2000).show();
	} 
}
