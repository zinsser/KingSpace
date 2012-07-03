package com.king.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
 

/*
 * eBook继承FrameLayout，好处在于FrameLayout有图层效果，后添加的View类能覆盖前面的View。
初始化：定义一个LinearLayout的成员变量mView，将page.xml inflate 成View分别用leftPage，rightPage引用，并初始化其数据，将leftPage，rightPage通过addView添加到mView，然后将mView添加到eBook。在eBook里定义一个私有类BookView extends View。 并定义成员变量 BookView mBookView； 最后将mBookView添加的eBook中，这样，mView中的内容为书面内容，mBookView中的内容为特效内容。
后续手势动作：可将各种手势的特效动作画于mBookView的画布中。
*/
public class eBook extends FrameLayout{
 public static final String LOG_TAG = "eBook";
 List<Integer> myRecPages;
 int totalPageNum;
 Context mContext;
 boolean hasInit = false;
 final int defaultWidth = 600 , defaultHeight = 400;
 int contentWidth = 0;
 int contentHeight = 0;
 View leftPage,rightPage,llPage,lrPage,rrPage,rlPage;
 LinearLayout mView;
 bookView mBookView;
 boolean closeBook = false;
 
 private enum Corner {
  LeftTop,
  RightTop,
  LeftBottom,
  RightBottom,
  None
 };
 private Corner mSelectCorner;
 final int clickCornerLen = 250*250; //50dip
 float scrollX = 0,scrollY = 0;
 int indexPage = 0;
 
 private enum State {
  ABOUT_TO_ANIMATE,
  ANIMATING,
  ANIMATE_END,
  READY,
  TRACKING
 };
 private State mState;
 private Point aniStartPos;
 private Point aniStopPos;
 private Date aniStartTime;
 private long aniTime = 2000;
 private long timeOffset = 900;
 
 Listener mListener;
 
 private GestureDetector mGestureDetector;
 private BookOnGestureListener mGestureListener;
 
 public eBook(Context context) {
  super(context);
  Init(context);
 }
 public eBook(Context context, AttributeSet attrs) {
  super(context, attrs);
  Init(context);
 }

}
