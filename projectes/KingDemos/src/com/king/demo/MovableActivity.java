package com.jczhou.mindsnap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MovableActivity extends Activity {
	private TextView mWrapControl = null;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movablelayout);
        
        mWrapControl = (TextView)findViewById(R.id.SnapNodeContent);
    }

	@Override
	public boolean onTouchEvent (MotionEvent event){
		boolean bRet = super.onTouchEvent(event);
		if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
			Log.d("MovableTeveView", "Down");
			bRet = true;
		}else if (event.getActionMasked() == MotionEvent.ACTION_MOVE){
			// flag为true即控件被点到时，执行移动控件操作
			int x = (int) event.getRawX();
			int y = (int) event.getRawY();
			// 得到X，Y座标
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 
												x, y);
			// 四参数分别为宽，高，X，Y座标，wrap_conent为根据内容自动调整
			// 后面-10,-40是我自己多次调试的结果，因为我发现如果不减，那个座标并不是在指头下，而是在指头的右下角
			// 暂时不知道什么原因
			mWrapControl.setLayoutParams(params);

			Log.d("MovableTeveView", "Moving");
		}else if (event.getActionMasked() == MotionEvent.ACTION_UP){
			Log.d("MovableTeveView", "UP");
		}
		return bRet;
	}     
    
}
