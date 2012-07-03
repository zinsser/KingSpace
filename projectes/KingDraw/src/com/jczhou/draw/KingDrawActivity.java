package com.jczhou.draw;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class KingDrawActivity extends Activity {
	private final static int GROUP_NORMAL = 0;
	private final static int MENU_SAVE_IMAGE = 0;
	
	private LineView mDrawableView = null;
	private LinearLayout mRootView = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LayoutInflater factory = LayoutInflater.from(this);
        mRootView = (LinearLayout) factory.inflate(R.layout.main, null);
        setContentView(mRootView);
        
        mDrawableView = new LineView(this);//实例一个画线实例  
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 
				 										LinearLayout.LayoutParams.MATCH_PARENT);
		mDrawableView.setLayoutParams(lp);
		mRootView.addView(mDrawableView);//画线实例添加进线性布局
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	menu.add(GROUP_NORMAL, MENU_SAVE_IMAGE, 0, "Save as image");

    	return super.onCreateOptionsMenu(menu);
    }   
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()){
    	case MENU_SAVE_IMAGE:
    		mDrawableView.SaveToImage("/sdcard/kingdemo_image.png");
    		break;
    	}
    	
    	return super.onOptionsItemSelected(item);
    }    
}