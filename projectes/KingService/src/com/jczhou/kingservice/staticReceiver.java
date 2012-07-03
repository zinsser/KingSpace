package com.jczhou.kingservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/* 
 * ���վ�̬ע��㲥��BroadcastReceiver�� 
 * step1:Ҫ��AndroidManifest.xml����ע����Ϣ 
 *      <receiver android:name="clsReceiver2"> 
            <intent-filter> 
                <action 
                    android:name="com.testBroadcastReceiver.Internal_2"/> 
            </intent-filter> 
        </receiver> 
    step2:������Ϣ���ַ��� 
    step3:ͨ��Intent������Ϣ����ʹBroadcastReceiver���� 
 */ 

public class staticReceiver extends BroadcastReceiver{
	
	@Override
	public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Toast.makeText(context, "static :" + action, 2000).show();
	} 

}
