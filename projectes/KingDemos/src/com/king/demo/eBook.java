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
 * eBook�̳�FrameLayout���ô�����FrameLayout��ͼ��Ч��������ӵ�View���ܸ���ǰ���View��
��ʼ��������һ��LinearLayout�ĳ�Ա����mView����page.xml inflate ��View�ֱ���leftPage��rightPage���ã�����ʼ�������ݣ���leftPage��rightPageͨ��addView��ӵ�mView��Ȼ��mView��ӵ�eBook����eBook�ﶨ��һ��˽����BookView extends View�� �������Ա���� BookView mBookView�� ���mBookView��ӵ�eBook�У�������mView�е�����Ϊ�������ݣ�mBookView�е�����Ϊ��Ч���ݡ�
�������ƶ������ɽ��������Ƶ���Ч��������mBookView�Ļ����С�
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
