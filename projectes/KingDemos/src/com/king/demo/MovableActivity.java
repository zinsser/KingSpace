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
			// flagΪtrue���ؼ����㵽ʱ��ִ���ƶ��ؼ�����
			int x = (int) event.getRawX();
			int y = (int) event.getRawY();
			// �õ�X��Y����
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 
												x, y);
			// �Ĳ����ֱ�Ϊ���ߣ�X��Y���꣬wrap_conentΪ���������Զ�����
			// ����-10,-40�����Լ���ε��ԵĽ������Ϊ�ҷ�������������Ǹ����겢������ָͷ�£�������ָͷ�����½�
			// ��ʱ��֪��ʲôԭ��
			mWrapControl.setLayoutParams(params);

			Log.d("MovableTeveView", "Moving");
		}else if (event.getActionMasked() == MotionEvent.ACTION_UP){
			Log.d("MovableTeveView", "UP");
		}
		return bRet;
	}     
    
}
