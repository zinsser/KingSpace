package com.jczhou.kingmathfunc;

import android.content.Context;  
import android.graphics.Bitmap;  
import android.graphics.Canvas;  
import android.graphics.Color;  
import android.graphics.Paint;  
import android.util.Log;
import android.view.MotionEvent;  
import android.view.View;  
 
public class LineView extends View {  
 
    public Canvas canvas;  
    public Paint p;  
    private Bitmap bitmap;  
    float x,y;  
    int bgColor;  
 
    public LineView(Context context) {  
        super(context);  

        bgColor = Color.RED;        //设置颜色                   
        bitmap = Bitmap.createBitmap(320, 400, Bitmap.Config.ARGB_8888);      
        canvas = new Canvas();        //建立画布    
        canvas.setBitmap(bitmap);        //设置位图           
        p = new Paint(Paint.DITHER_FLAG);        //设置图层    
        p.setAntiAlias(true);        //可以更平滑                    
        p.setColor(Color.RED);    
        p.setStrokeCap(Paint.Cap.ROUND);        //样式    
        p.setStrokeWidth(3);        //线条宽       
        DrawCoordinate();
    }  
      
    private void DrawCoordinate(){
        Log.d("LineView", "width:"+getWidth()+"height:"+getHeight()); 
        Log.d("LineView", "X:"+this.getLeft()+"y:"+this.getTop()); 
        canvas.drawLine(160, 0, 160, 400, p);
        canvas.drawLine(0, 170, 320, 170, p);
    }
    
    public void DrawLine(int a, int b){
    	if (a != 0){
    		if (b == 0){
    			
    			return ;
    		}
    		
    		int yPixel = 170 / (b * 2);
	    	int pixel = 160 / (2* b / a);
	    	if (pixel > yPixel){
	    		pixel = yPixel;
	    	}
	    	
	    	
    	}
    }
    
    @Override 
    public boolean onTouchEvent(MotionEvent event) {  
 
        if (event.getAction() == MotionEvent.ACTION_MOVE) {     
            //画线  
            canvas.drawLine(x, y, event.getX(), event.getY(), p);     
            invalidate();  
        }  
 
        if (event.getAction() == MotionEvent.ACTION_DOWN) {     
            x = event.getX();                 
            y = event.getY();  
            //画一个点  

            canvas.drawPoint(x, y, p);
            invalidate();  
        }  
        if (event.getAction() == MotionEvent.ACTION_UP) {      
          
        }  
        //记录当前的鼠标坐标  
        x = event.getX();    
        y = event.getY();  
        return true;  
    }  

    @Override
    public void onDraw(Canvas c) {                        
    	super.onDraw(c);
    	c.drawBitmap(bitmap, 0, 0, null);    
    }  
 }