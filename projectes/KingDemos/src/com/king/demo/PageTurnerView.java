package com.king.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

public class PageTurnerView extends RelativeLayout {    

	private static final int CORNER_RIGHT_MASK = 1;    
    private static final int CORNER_TOP_MASK = 2;    
    public static final int CORNER_BOTTOM_LEFT = 0;    
    public static final int CORNER_BOTTOM_RIGHT = 1;    
    public static final int CORNER_TOP_LEFT = 2;    
    public static final int CORNER_TOP_RIGHT = 3;    
    private static final int INVALIDATE = 1;    
    private static final int INITIAL_TIME_DELAY = 100;    
    private static final int TIME_DELAY = 10;    
//  private static final int TIME_STEPS = 30;    
    private boolean mPageTurning;    
    private boolean mStepping;    
    public long mNextTime;    
    private int mTimeStep;    
    private int mDrawnTimeStep;    
    private int mCorner;    
    private Drawable mBackPage;    
    private Drawable mPageBackground;    
    private Path mForegroundPath;    
    private Path mBackPagePath;    
    private Path mBackgroundPath;    
    private float mRotation;    
    private Rect mChildRect = new Rect();    
    private int mOuterOffsetX;    
    private int mOuterOffsetY;    
    public float mPivotX;    
    private int mPageId;    
    private PageView mPage;    
    private PointF mPageTurnCorner = new PointF();    
    private PointF mOppositeCorner = new PointF();    
    private PointF mPageDim = new PointF();    
    private int mStepLen = 1;    
    public static final int KEEP = 0;    
    public static final int NEXT = 1;    
    public static final int LAST = 2;    
    public int mWhere = KEEP;    
    public boolean isBgInit = true;    
    public boolean isBackInit = true;    
    private  float ax,ay,bx,by,cx,cy,dx,dy,ex,ey,c0x,c0y;    
    private int mMaxStep=30;    
    public final Handler mHandler = new Handler() {    
        public void handleMessage(Message msg) {    
            if (msg.what != 1) { return; }    
//          PageTurnerViewP1.this.invalidate();    
            refreshUI();    
//          PageTurnerViewP1.this.invalidate((int)bx, (int)ay, (int)dx, (int)dy);    
            if (PageTurnerView.this.mStepping) { return; }    
            msg = obtainMessage(1);    
            long current = SystemClock.uptimeMillis();    
            if (PageTurnerView.this.mNextTime < current) {    
                //PageTurnerViewP1.access$102(PageTurnerViewP1.this, current + 10L);    
            	PageTurnerView.this.mNextTime= current + 5L;    
            }    
            sendMessageAtTime(msg, PageTurnerView.this.mNextTime);    
            //PageTurnerViewP1.access$114(PageTurnerViewP1.this, 10L);    
            PageTurnerView.this.mNextTime+= 5L;    
        }    
    };    
      
 
    public PageTurnerView(Context context) {    
        super(context);    
        Log.i("==================== PageTurnerViewP1(Context context) =================", "" + this);    
    }    
 
    public PageTurnerView(Context context, AttributeSet attrs) {    
        super(context, attrs);    
 
//      TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PageTurner);    
 
//      this.mBackPage = a.getDrawable(1);    
//      this.mPageBackground = a.getDrawable(0);    
//  
//      this.mPageId = a.getResourceId(3, -1);    
//  
//      this.mCorner = a.getInt(2, 0);    
//        this.mBackPage = this.getResources().getDrawable(com.tom.clip.R.drawable.a5);    
//        this.mPageBackground =this.getResources().getDrawable(com.tom.clip.R.drawable.a6);    
              
        this.mPageId = -1;    
          this.mCorner = -1;    
    }    
 
    protected void onFinishInflate() {    
        super.onFinishInflate();    
        if (this.mPageId != -1) {    
            this.mPage = ((PageView)findViewById(this.mPageId));    
            if (this.mPage != null)    
                this.mPage.setPageTurner(this);    
        }    
    }    
 
    public void setPageId(int pageId) {    
        this.mPageId = pageId;    
        this.mPage = ((PageView)findViewById(this.mPageId));    
        if (this.mPage != null)    
            this.mPage.setPageTurner(this);    
    }    
 
    public int getPageId() {    
        return this.mPageId;    
    }    
 
    public void setPage(PageView pageViewP1) {    
        this.mPage = pageViewP1;    
    }    
 
    public PageView getPage() {    
        return this.mPage;    
    }    
 
    public void setCorner(int corner) {    
        this.mCorner = corner;    
    }    
 
    public int getCorner() {    
        return this.mCorner;    
    }    
 
    protected void dispatchDraw(Canvas canvas) {    
            
          Log.v("log dispatchDraw:", "drawing back page"+mPageTurning);    
//      if ((this.mPageTurning) && (this.mPage != null) && (computePageTurn())) {    
        if ((computePageTurn())&& (this.mPageTurning) && (this.mPage != null) ) {    
            this.mPage.setClipPath(this.mForegroundPath);    
        }    
 
        super.dispatchDraw(canvas);    
 
        if (this.mPageTurning) {    
            drawBackground(canvas);    
            drawBackPage(canvas);    
                
            if (!updateTimeStep()) {    
                this.mHandler.removeMessages(1);    
                if (this.mPage != null) {    
                    this.mPage.onPageTurnFinished(canvas);    
                }    
                this.mPageTurning = false;    
                this.mStepping = false;    
                invalidate();    
            }    
        }    
    }    
 
    public void startPageTurn(int mTimeStep) {    
        if ((this.mPage == null) && (this.mPageId != -1)) {    
            this.mPage = ((PageView)findViewById(this.mPageId));    
        }    
        if (this.mPage == null) {    
            return;    
        }    
        this.mPage.setPageTurner(this);    
        Drawable d = this.mPage.getPageBackground();    
        if (d != null) {    
            this.mPageBackground = d;    
        }    
 
        d = this.mPage.getBackPage();    
        if (d != null) {    
            this.mBackPage = d;    
        }    
 
        int corner = this.mPage.getCorner();    
        if (corner != -1) {    
            this.mCorner = corner;    
        }    
//      this.mStepping=false;    
        this.mPageTurning = true;    
        this.mTimeStep = mTimeStep;    
        this.mDrawnTimeStep = -1;    
        Message msg = this.mHandler.obtainMessage(1);    
        this.mNextTime = (SystemClock.uptimeMillis() + 5L);    
        this.mHandler.sendMessageAtTime(msg, this.mNextTime);    
    }    
 
    public void stepPageTurn() {    
        if (!this.mStepping) {    
            this.mStepping = true;    
            startPageTurn(this.mTimeStep);    
        } else {    
//        te((int)bx, 0, (int)mPageTurnCorner.x, (int)mPageTurnCorner.y);    
//          sendUIhandler();    
            refreshUI();    
        }    
    }    
    public void refreshUI(){    
        computePageTurn();    
//    
        this.invalidate();    
    }    
    private void sendUIhandler(){    
        Message msg = this.mHandler.obtainMessage(1);    
        this.mNextTime = (SystemClock.uptimeMillis() + 30L);    
        this.mHandler.sendMessageAtTime(msg, this.mNextTime);    
    }    
    private boolean updateTimeStep() {    
        if (this.mTimeStep >mMaxStep || this.mTimeStep<0) {    
            return false;    
        }    
        this.mTimeStep +=mStepLen;    
        return true;    
    }    
        
    private boolean computePageTurn() {    
//      if ((this.mDrawnTimeStep == this.mTimeStep) ||(this.mTimeStep <0)||(this.mTimeStep >this.mMaxStep)) {    
        if ( (this.mTimeStep <0)||(this.mTimeStep >this.mMaxStep)) {    
            return false;    
        }    
        if (this.mPage == null) {    
            return false;    
        }    
        this.mDrawnTimeStep = this.mTimeStep;    
        View child = this.mPage.getChildAt(0);    
        child.getDrawingRect(this.mChildRect);    
//      this.mOuterOffsetX = (child.getLeft() - getLeft());    
//      this.mOuterOffsetY = (child.getTop() - getTop());    
        this.mOuterOffsetX = 0;    
        this.mOuterOffsetY = 0;    
 
        float width = this.mChildRect.right;    
        float height = this.mChildRect.bottom;    
        if(!mStepping){    
            this.mPivotX = (this.mTimeStep / 30.0f * width);    
        }    
        this.mForegroundPath = new Path();    
        this.mBackPagePath = new Path();    
        this.mBackgroundPath = new Path();    
        float slope = width / (this.mPivotX - width);    
        float y = this.mPivotX * slope;    
 
        this.mPageTurnCorner.x = 0.0F;    
        this.mPageTurnCorner.y = height;    
        this.mOppositeCorner.x = width;    
        this.mOppositeCorner.y = 0.0F;    
        float x0 = this.mPivotX;    
        float cornerIntersect = height * width / (height + width);    
        if ((this.mCorner & 0x1) != 0) {    
            this.mPageTurnCorner.x = width;    
            this.mOppositeCorner.x = 0.0F;    
            x0 = width - x0;    
        }    
 
        if ((this.mCorner & 0x2) != 0) {    
            this.mPageTurnCorner.y = 0.0F;    
            this.mOppositeCorner.y = height;    
        }    
 
        this.mPageDim.x = width;    
        this.mPageDim.y = height;    
        float page_slope;    
 
        if (this.mPivotX <= cornerIntersect) {    
            page_slope = firstHalfPageTurn(this.mPageDim, this.mPageTurnCorner, this.mOppositeCorner, x0, slope, y);    
        } else {    
            page_slope = secondHalfPageTurn(this.mPageDim, this.mPageTurnCorner, this.mOppositeCorner, x0, slope, y);    
        }    
 
        this.mRotation = (float)Math.atan(page_slope);    
 
        this.mRotation = (float)(-this.mRotation * 180.0D / 3.141592653589793D);    
 
        return true;    
    }    
 
    private float firstHalfPageTurn(PointF pageDim, PointF pageTurnCorner, PointF oppositeCorner, float xPivot, float slope, float y) {    
        float width = pageDim.x;    
        float height = pageDim.y;    
        View child = this.mPage.getChildAt(0);    
        int innerOffsetX = child.getLeft() - this.mPage.getLeft();    
        int innerOffsetY = child.getTop() - this.mPage.getTop();    
 
        float y1 = height + y;    
 
        float x2 = (float)(2.0D * y / (slope + 1.0D / slope));    
        float y2 = height + x2 / slope;    
        float page_slope = (height - y2) / (x2 - this.mPivotX);    
        if ((this.mCorner & 0x1) != 0) {    
            x2 = width - x2;    
            page_slope = -page_slope;    
        }    
        if ((this.mCorner & 0x2) != 0) {    
            y1 = height - y1;    
            y2 = height - y2;    
            page_slope = -page_slope;    
        }    
 
        float x0 = xPivot;    
        float x1 = pageTurnCorner.x;    
 
            
        //a点是右上角，b点位左下角，c点为右下角，d点为固定的右下角    
        ax=this.mOuterOffsetX + x1;    
        ay=this.mOuterOffsetY + y1;    
            
        bx=this.mOuterOffsetX + x2;    
        by=this.mOuterOffsetY + y2;    
            
        cx=this.mOuterOffsetX + x0;    
        cy=this.mOuterOffsetY + pageTurnCorner.y;    
            
        dx=this.mOuterOffsetX + pageTurnCorner.x;    
        dy=this.mOuterOffsetY + pageTurnCorner.y;    
            
        ex=(dx+bx)/2;    
        ey=(dy+by)/2;    
            
        this.mForegroundPath.moveTo(innerOffsetX + x1, innerOffsetY + y1);    
        this.mForegroundPath.lineTo(innerOffsetX + pageTurnCorner.x, innerOffsetY + oppositeCorner.y);    
        this.mForegroundPath.lineTo(innerOffsetX + oppositeCorner.x, innerOffsetY + oppositeCorner.y);    
        this.mForegroundPath.lineTo(innerOffsetX + oppositeCorner.x, innerOffsetY + pageTurnCorner.y);    
        this.mForegroundPath.lineTo(innerOffsetX + x0, innerOffsetY + pageTurnCorner.y);    
        this.mForegroundPath.lineTo(innerOffsetX + x2, innerOffsetY + y2);    
//      this.mForegroundPath.quadTo(ex, ey, bx, by);    
        this.mForegroundPath.lineTo(innerOffsetX + x1, innerOffsetY + y1);    
 
        this.mBackPagePath.moveTo(this.mOuterOffsetX + x1, this.mOuterOffsetY + y1);    
        this.mBackPagePath.lineTo(this.mOuterOffsetX + x2, this.mOuterOffsetY + y2);    
        //b到c的那条线，可以考虑画个弧线    
        this.mBackPagePath.lineTo(this.mOuterOffsetX + x0, this.mOuterOffsetY + pageTurnCorner.y);    
//      this.mBackPagePath.arcTo(new RectF(bx,by,cx,cy), 220, 180,false);    
//      this.mBackPagePath.quadTo(ex, ey, cx, cy);    
//      this.mBackPagePath.cubicTo(100, -50, 200, 50, cx, cy);//贝赛尔曲线    
        this.mBackPagePath.lineTo(this.mOuterOffsetX + x1, this.mOuterOffsetY + y1);    
 
            
        this.mBackgroundPath.moveTo(this.mOuterOffsetX + x1, this.mOuterOffsetY + y1);    
        this.mBackgroundPath.lineTo(this.mOuterOffsetX + x0, this.mOuterOffsetY + pageTurnCorner.y);    
        this.mBackgroundPath.lineTo(this.mOuterOffsetX + pageTurnCorner.x, this.mOuterOffsetY + pageTurnCorner.y);    
        this.mBackgroundPath.lineTo(this.mOuterOffsetX + x1, this.mOuterOffsetY + y1);    
 
        return page_slope;    
    }    
        
 
    private float secondHalfPageTurn(PointF pageDim, PointF pageTurnCorner, PointF oppositeCorner, float xPivot, float slope, float y) {    
        float width = pageDim.x;    
        float height = pageDim.y;    
        View child = this.mPage.getChildAt(0);    
        int xOffset = child.getLeft() - this.mPage.getLeft();    
        int yOffset = child.getTop() - this.mPage.getTop();    
 
        float y1 = 0.0F;    
        float x1 = width - (height + width) * (width - this.mPivotX) / width;    
 
        float x3 = (float)(2.0D * y / (slope + 1.0D / slope));    
        float y3 = height + x3 / slope;    
        float page_slope = (height - y3) / (x3 - this.mPivotX);    
        float b = height - x1 * page_slope;    
        float x2 = (float)((-y - b) / (page_slope + 1.0D / page_slope));    
        float y2 = (x1 - x2) * page_slope;    
 
        if ((this.mCorner & 0x1) != 0) {    
            x1 = width - x1;    
            x2 = width - x2;    
            x3 = width - x3;    
            page_slope = -page_slope;    
        }    
        if ((this.mCorner & 0x2) != 0) {    
            y1 = height - y1;    
            y2 = height - y2;    
            y3 = height - y3;    
            page_slope = -page_slope;    
        }    
 
        float x0 = xPivot;    
 
        this.mForegroundPath.moveTo(xOffset + x1, yOffset + y1);    
        this.mForegroundPath.lineTo(xOffset + oppositeCorner.x, yOffset + oppositeCorner.y);    
        this.mForegroundPath.lineTo(xOffset + oppositeCorner.x, yOffset + pageTurnCorner.y);    
        this.mForegroundPath.lineTo(xOffset + x0, yOffset + pageTurnCorner.y);    
        this.mForegroundPath.lineTo(xOffset + x1, yOffset + y1);    
 
      //a点是右上角，c0点位左上角，b点为左下角，d点为固定的右下角    
        ax=this.mOuterOffsetX + x1;    
        ay=this.mOuterOffsetY + y1;    
            
        c0x=this.mOuterOffsetX + x2;    
        c0y=this.mOuterOffsetY + y2;    
            
        bx=this.mOuterOffsetX + x3;    
        by=this.mOuterOffsetY + y3;    
        cx=this.mOuterOffsetX + x0;    
        cy=this.mOuterOffsetY + pageTurnCorner.y;    
            
        dx=this.mOuterOffsetX + pageTurnCorner.x;    
        dy=this.mOuterOffsetY + pageTurnCorner.y;    
            
        ex=(dx+bx)/2;    
        ey=(dy+by)/2;    
            
        this.mBackPagePath.moveTo(this.mOuterOffsetX + x1, this.mOuterOffsetY + y1);    
        this.mBackPagePath.lineTo(this.mOuterOffsetX + x2, this.mOuterOffsetY + y2);    
        this.mBackPagePath.lineTo(this.mOuterOffsetX + x3, this.mOuterOffsetY + y3);    
        this.mBackPagePath.lineTo(this.mOuterOffsetX + x0, this.mOuterOffsetY + pageTurnCorner.y);    
//      this.mBackPagePath.quadTo(ex, ey, cx, cy);    
        this.mBackPagePath.lineTo(this.mOuterOffsetX + x1, this.mOuterOffsetY + y1);    
          
            
            
        this.mBackgroundPath.moveTo(this.mOuterOffsetX + x1, this.mOuterOffsetY + y1);    
        this.mBackgroundPath.lineTo(this.mOuterOffsetX + x0, this.mOuterOffsetY + pageTurnCorner.y);    
        this.mBackgroundPath.lineTo(this.mOuterOffsetX + pageTurnCorner.x, this.mOuterOffsetY + pageTurnCorner.y);    
        this.mBackgroundPath.lineTo(this.mOuterOffsetX + pageTurnCorner.x, this.mOuterOffsetY + oppositeCorner.y);    
        this.mBackgroundPath.lineTo(this.mOuterOffsetX + x1, this.mOuterOffsetY + y1);    
 
        return page_slope;    
    }    
 
    private void drawBackground(Canvas canvas) {    
        canvas.save();    
//      canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));    
        canvas.clipPath(this.mBackgroundPath, Region.Op.INTERSECT);    
        canvas.translate(this.mOuterOffsetX, this.mOuterOffsetY);    
            
        if (this.mPageBackground != null) {    
            this.mPageBackground.setBounds(0, 0, this.mChildRect.right, this.mChildRect.bottom);    
 
            this.mPageBackground.draw(canvas);    
        }    
        if (this.mPage != null) {    
            this.mPage.drawBackground(canvas);    
        }    
            
      // add drop shadow start test    
          if(mTimeStep<=29&&ex>2){    
          LinearGradient grad = new LinearGradient(    
                  ex,ey,ex+(dx-ex)/4,ey+(dy-ey)/4,R.color.gray3,Color.TRANSPARENT,Shader.TileMode.CLAMP);    
          Paint p=new Paint();    
          p.setShader(grad);    
//        p.setAlpha(120);    
          canvas.drawPath(this.mBackgroundPath,p);    
          //end test    
          }    
        canvas.restore();    
            
          
    }    
 
    private void drawBackPage(Canvas canvas) {    
        float width = this.mChildRect.right;    
        float height = this.mChildRect.bottom;    
        canvas.save();    
//      canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));    
        canvas.clipPath(this.mBackPagePath, Region.Op.INTERSECT);    
        float xShift = 2.0F * this.mPivotX - width;    
        float xRotate = width - this.mPivotX;    
        float yRotate = height;    
        if ((this.mCorner & 0x1) != 0) {    
            xShift = width - 2.0F * this.mPivotX;    
            xRotate = this.mPivotX;    
        }    
        if ((this.mCorner & 0x2) != 0) {    
            yRotate = 0.0F;    
        }    
        canvas.translate(this.mOuterOffsetX + xShift, this.mOuterOffsetY);    
        canvas.rotate(this.mRotation, xRotate, yRotate);    
            
        //画原本的背面    
        if (this.mBackPage != null) {    
            this.mBackPage.setBounds(0, 0, this.mChildRect.right, this.mChildRect.bottom);    
            this.mBackPage.draw(canvas);    
        }    
        //画回调函数中的画背面    
        if (this.mPage != null) {    
              Log.v("log2 drawBackPage2:", "drawing back page");    
            this.mPage.drawBackPage(canvas);    
        }    
            
        canvas.restore();    
        canvas.save();    
//      canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));    
//      int[] colors={Color.BLACK,Color.GRAY,Color.BLACK};    
//        LinearGradient grad = new LinearGradient(    
//                ex,ey,bx,by,colors,null,Shader.TileMode.CLAMP);    
//        Paint p=new Paint();    
//        p.setShader(grad);    
//        p.setAlpha(120);    
//        canvas.drawPath(this.mBackPagePath,p);    
          LinearGradient grad = new LinearGradient(    
                  ex,ey,ex-(ex-bx)/4,ey-(ey-by)/4,R.color.gray3,0xC9C9C9,Shader.TileMode.CLAMP);    
          Paint p=new Paint();    
          p.setShader(grad);    
//        p.setAlpha(120);    
          canvas.drawPath(this.mBackPagePath,p);    
          canvas.restore();    
          //中间阴影问题，起点蓝色-》 白色-》 蓝色终点，这样起点前和终点后的区域也为蓝色了。    
          canvas.save();    
//        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));    
          LinearGradient grad1 = new LinearGradient(    
                  ex-(ex-bx)/4,ey-(ey-by)/4,bx,by,0xC9C9C9,R.color.gray3,Shader.TileMode.CLAMP);    
          Paint p1=new Paint();    
          p1.setShader(grad1);    
//        p1.setAlpha(120);    
          canvas.drawPath(this.mBackPagePath,p1);    
          canvas.restore();    
//  
              
    }    
        
    public int getmTimeStep(){    
            
        return mTimeStep;    
    }    
    public void setmTimeStep(int mTimeStep ){    
            
        this.mTimeStep= mTimeStep;    
    }    
    public boolean getmStepping(){    
            
        return mStepping;    
    }    
    public void setmStepping(boolean mStepping ){    
            
        this.mStepping= mStepping;    
    }    
        
    public void setmStepLen(int mStepLen){    
        this.mStepLen=mStepLen;    
    }    
    public int getmStepLen(){    
        return this.mStepLen;    
    }    
        
    public void setmMaxStep(int mMaxStep){    
        this.mMaxStep=mMaxStep;    
    }    
    public int getmMaxStep(){    
        return this.mMaxStep;    
    }    
 
  public void setPreStart(int where)    
  {    
      switch (where)     
      {    
          case NEXT:    
          case LAST:    
              mWhere = where;    
              break;    
                  
          default:    
              mWhere = KEEP;    
              break;    
      }    
          
      isBgInit = true;    
      isBackInit = true;    
          
  }    
}
