package com.jczhou.mindsnap;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class KingMindSnapActivity extends Activity {
	private final static int GROUP_NORMAL = 0;
	private final static int MENU_ADD_SIBLING_SNAP = 0;
	private final static int MENU_ADD_SUB_SNAP = 1;	

	private FrameLayout mRootView = null;
	private ArrayList<FrameLayout> mMindSnapTree = new ArrayList<FrameLayout>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LayoutInflater factory = LayoutInflater.from(this);
        mRootView = (FrameLayout) factory.inflate(R.layout.main, null);
        setContentView(mRootView);
        
        mMindSnapTree.add((FrameLayout)findViewById(R.id.RootWrapper));
        ((UnblockableEditText)findViewById(R.id.RootContent)).setText(GetDateTimeString());
    }
    
    private String GetDateTimeString(){
    	  final Calendar c = Calendar.getInstance(); 
          int mYear = c.get(Calendar.YEAR); //��ȡ��ǰ��� 
          int mMonth = c.get(Calendar.MONTH);//��ȡ��ǰ�·� 
          int mDay = c.get(Calendar.DAY_OF_MONTH);//��ȡ��ǰ�·ݵ����ں��� 
          int mHour = c.get(Calendar.HOUR_OF_DAY);//��ȡ��ǰ��Сʱ�� 
          int mMinute = c.get(Calendar.MINUTE);//��ȡ��ǰ�ķ�����   
          return mYear+"-"+mMonth+"-"+mDay+"_"+mHour+"-"+mMinute;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	menu.add(GROUP_NORMAL, MENU_ADD_SIBLING_SNAP, 0, "Add as Sibling");
    	menu.add(GROUP_NORMAL, MENU_ADD_SUB_SNAP, 0, "Add as Child");

    	return super.onCreateOptionsMenu(menu);
    }
    
    private FrameLayout MakeOneNode(){
		MovableFrameLayout mfl = new MovableFrameLayout(this);
		mfl.addView(new UnblockableEditText(this));
		return mfl;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()){
    	case MENU_ADD_SIBLING_SNAP:
    		mRootView.addView(MakeOneNode());
    		break;
    	case MENU_ADD_SUB_SNAP:
    		mRootView.addView(MakeOneNode());
    		break;
    	}
    	
    	return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onAttachedToWindow (){
    	//TODO:
    	Log.d("Activity", "onAttachedToWindow");
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus){
    	Log.d("Activity", "onWindowFocusChanged");    	
    }
}