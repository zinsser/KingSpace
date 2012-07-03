package com.jczhou.mindsnap;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/* MovableFrameLayout (可移动的FrameLayout)
 * 该Layout中所含的所有控件将可拖拽移动
 * 应用时，MovableFrameLayout需要由普通的FrameLayout包含，否则会引起TypeCastException
 * 包含多控件时，需要在内部使用LinearLayout进行布局
 * 示例如下：
 * <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android" 
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <com.jczhou.mindsnap.MovableFrameLayout 
		android:layout_width="wrap_content" 
		android:layout_height="wrap_content" 
		android:id="@+id/Board" 
		android:layout_gravity="top"> 
		<LinearLayout
		    android:id="@+id/linearLayout1"
		    android:layout_width="match_parent"
		    android:layout_height="match_parent"
		    android:orientation="vertical"  >

		    <TextView
		        android:id="@+id/SnapNodeContent"
		        android:layout_width="wrap_content"
		        android:layout_height="wrap_content"
		        android:background="#F4EFC5"
		        android:gravity="center"
		        android:text="Hello world" />
		    <TextView
		        android:id="@+id/SnapNodeContent3"
		        android:layout_width="wrap_content"
		        android:layout_height="wrap_content"
		        android:background="#F4EFC5"
		        android:gravity="center"
		        android:text="Hello world" />	    
		    
		</LinearLayout>
	</com.jczhou.mindsnap.MovableFrameLayout >
   </FrameLayout>
 * 注意，在TouchListener中，对于Action_Down一定要返回true，否则接收不到Action_Move事件
 * lp.gravity = Gravity.TOP; 
 * 
 * */
public class MovableFrameLayout extends FrameLayout {
	private boolean mIsActive = false;
	
	public MovableFrameLayout(Context context) {
		super(context);
		setOnTouchListener(new TouchListener());
		setExtraLayoutParam();		
	}

	public MovableFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnTouchListener(new TouchListener());
		setExtraLayoutParam();
	}

	public MovableFrameLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setOnTouchListener(new TouchListener());
		setExtraLayoutParam();
	}
	
	private void setExtraLayoutParam(){
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.TOP;
		setLayoutParams(lp);
	}
	
	public class TouchListener implements View.OnTouchListener{
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			boolean bRet = event.getAction() == MotionEvent.ACTION_DOWN;
			if (!bRet && event.getAction() == MotionEvent.ACTION_MOVE) {
				FrameLayout.LayoutParams par = (LayoutParams) v.getLayoutParams();
				par.height = v.getHeight();
				par.width = v.getWidth();
				par.topMargin = (int)event.getRawY() - (v.getHeight()); 
				par.leftMargin = (int)event.getRawX() - (v.getWidth()/2); 
				v.setLayoutParams(par); 
				bRet = true;
			}
			
			return bRet;
		}
	}

	public boolean isActive(){
		return mIsActive;
	}
}
