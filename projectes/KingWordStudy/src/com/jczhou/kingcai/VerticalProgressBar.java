package com.jczhou.kingcai;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class VerticalProgressBar extends ProgressBar{
	public VerticalProgressBar(Context context) {
		super(context);
	}

	public VerticalProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}	
	
	public VerticalProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}


	@Override
    protected void onDraw(Canvas c)
    {
        c.rotate(-90);
        super.onDraw(c);
    }
}
