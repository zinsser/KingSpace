package com.jczhou.mindsnap;

import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;

public class ViewMovableDecorator implements View.OnTouchListener{
	private View mViewDecoratee = null;
	private int mResID = 0;
	public ViewMovableDecorator(View v){
		mViewDecoratee = v;
	}
	
	public ViewMovableDecorator(int resID){
		mResID = resID;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (v.getId() == mResID || 
				(mViewDecoratee != null && v == mViewDecoratee)){
			LayoutParams par = (LayoutParams) v.getLayoutParams();
			switch(event.getAction()){
			case MotionEvent.ACTION_MOVE: 

				par.height = v.getHeight(); 
				par.width = v.getWidth();				
				par.topMargin = (int)event.getRawY() - (v.getHeight()); 
				par.leftMargin = (int)event.getRawX() - (v.getWidth()/2); 
				v.setLayoutParams(par); 
				break; 
 
			case MotionEvent.ACTION_UP: 
	 
				par.height = v.getHeight(); 
				par.width = v.getWidth();
				par.topMargin = (int)event.getRawY() - (v.getHeight()); 
				par.leftMargin = (int)event.getRawX() - (v.getWidth()/2); 
				v.setLayoutParams(par); 
				break; 
 
			case MotionEvent.ACTION_DOWN: 
//				int h = v.getHeight();
//				int w = v.getWidth();

				par.height = v.getHeight();//v.getHeight(); 
				par.width =  v.getWidth();//v.getWidth(); 
				v.setLayoutParams(par); 
				break; 
			}
		} 
		return true; 
	}
}
