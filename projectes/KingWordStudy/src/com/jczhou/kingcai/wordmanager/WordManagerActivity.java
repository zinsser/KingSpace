package com.jczhou.kingcai.wordmanager;

import com.jczhou.kingcai.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TabHost;

public class WordManagerActivity extends TabActivity implements TabHost.OnTabChangeListener {
    /** Called when the activity is first created. */
	private TabHost mTabHost;
	private static final int TAB_INDEX_QUERY = 0;
	private static final int TAB_INDEX_INSERT = 1;
	private WordDBAdapter wordDBAdapter;
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.word_manager);
        
        mTabHost = getTabHost();
        mTabHost.setOnTabChangedListener(this);
        
        setupQueryTab();
        setupInsertTab();
        wordDBAdapter = new WordDBAdapter(this);
        wordDBAdapter.open();
    }
    
    public void onTabChanged(String tabId){
    	
    }
 /*   
    private final TabListener mTabListener = new TabListener(){

		@Override
		public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub
			
		}
    	
    };
   */ 
    private void setupQueryTab(){
    	Intent intent = new Intent(this,WordQueryActivity.class);
    	mTabHost.addTab(mTabHost.newTabSpec("query").setIndicator(getString(R.string.word_query)).setContent(intent));
    }
    
    private void setupInsertTab(){
    	Intent intent = new Intent(this,WordInsertActivity.class);
    	mTabHost.addTab(mTabHost.newTabSpec("insert").setIndicator(getString(R.string.word_insert)).setContent(intent));
    }
}