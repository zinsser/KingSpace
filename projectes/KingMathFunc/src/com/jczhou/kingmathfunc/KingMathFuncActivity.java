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
        
//		LinearLayout layout =(LinearLayout) findViewById(R.id.root);//�ڸ����洴��һ�����Բ��ֹ�����  
		final LineView l=new LineView(this);//ʵ��һ������ʵ��  
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 
				 										LinearLayout.LayoutParams.MATCH_PARENT);
		l.setLayoutParams(lp);
		mRootView.addView(l);//����ʵ����ӽ����Բ���
    }
}