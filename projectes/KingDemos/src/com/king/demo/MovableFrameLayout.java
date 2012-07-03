package com.jczhou.mindsnap;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/* MovableFrameLayout (���ƶ���FrameLayout)
 * ��Layout�����������пؼ�������ק�ƶ�
 * Ӧ��ʱ��MovableFrameLayout��Ҫ����ͨ��FrameLayout���������������TypeCastException
 * ������ؼ�ʱ����Ҫ���ڲ�ʹ��LinearLayout���в���
 * ʾ�����£�
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
 * ע�⣬��TouchListener�У�����Action_Downһ��Ҫ����true��������ղ���Action_Move�¼�
 * */
public class MovableFrameLayout extends FrameLayout {

	public MovableFrameLayout(Context context) {
		super(context);
		setOnTouchListener(new TouchListener());
	}

	public MovableFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnTouchListener(new TouchListener());
	}

	public MovableFrameLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setOnTouchListener(new TouchListener());
	}
	
	public class TouchListener implements View.OnTouchListener{
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			boolean bRet = event.getAction() == MotionEvent.ACTION_DOWN
							|| event.getAction() == MotionEvent.ACTION_UP;
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
}
