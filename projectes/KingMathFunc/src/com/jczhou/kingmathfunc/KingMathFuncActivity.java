package com.jczhou.kingmathfunc;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class KingMathFuncActivity extends Activity {
	LinearLayout mRootView = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LayoutInflater factory = LayoutInflater.from(this);
        mRootView = (LinearLayout) factory.inflate(R.layout.main, null);
        setContentView(mRootView);
        
//		LinearLayout layout =(LinearLayout) findViewById(R.id.root);//在根界面创建一个线性布局管理器  
		final LineView l=new LineView(this);//实例一个画线实例  
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 
				 										LinearLayout.LayoutParams.MATCH_PARENT);
		l.setLayoutParams(lp);
		mRootView.addView(l);//画线实例添加进线性布局
    }
}