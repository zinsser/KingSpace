package com.jczhou.kingcai;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class ScrollableTextView extends TextView {

    private float step =0f; 
    private float width;
    private List<String> textList = new ArrayList<String>();    //���б���textview����ʾ��Ϣ��
    public ScrollableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollableTextView(Context context) {
        super(context);       
    }
     
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {       
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);  
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec); 
 /*       if (widthMode != MeasureSpec.EXACTLY) {  
            throw new IllegalStateException("ScrollLayout only canmCurScreen run at EXACTLY mode!");
        }     
*/
        float length = 0;   
        String text = this.getText().toString();
        if(text==null|text.length()==0){
                return ;
        }

        //����Ĵ����Ǹ��ݿ�Ⱥ������С��������textview��ʾ��������
        textList.clear();
        StringBuilder builder = new StringBuilder();
        Paint paint = getPaint();
        for(int i=0;i<text.length();i++){
            Log.e("textviewscroll",""+i+text.charAt(i));
            if(length<width){
                builder.append(text.charAt(i));
                length += paint.measureText(text.substring(i, i+1));
                if(i==text.length()-1){
                    Log.e("textviewscroll",""+i+text.charAt(i));
                    textList.add(builder.toString());
                }
            }else{
                textList.add(builder.toString().substring(0,builder.toString().length()-1));
	            builder.delete(0, builder.length()-1) ;
	            length= paint.measureText(text.substring(i, i+1));
	            i--;
            }
        }
    }

    //�����������������������ʾ�����������ֻ��ڻ����ϣ�ʵʱ���¡�
	@Override
    public void onDraw(Canvas canvas) {
        Paint paint = getPaint();
        for (int i = 0; i < textList.size(); i++) {
            canvas.drawText(textList.get(i), 0, this.getHeight()+(i+1)*paint.getTextSize()-step, getPaint());
        }
        invalidate();   
        step = step+0.3f;
        if (step >= this.getHeight()+textList.size()*paint.getTextSize()) {
            step = 0;
        }       
    }
}