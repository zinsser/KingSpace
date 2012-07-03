package com.jczhou.kingcai;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Button;

//�������ɵ��ʶ����button
//���м���Ǽ���̬����״̬
//ͬʱ�������ӵ�����һ����ť�Ĺ��ܣ��������һ����ť��keyֵ�ͱ���ť��keyֵ��ͬ����ô���ӳɹ�����������ʧ�ܣ�
public class HolderButton extends Button{
	private HolderContent mContent = null;	
	public static final int MAX_LINES = 4;	

	public HolderButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		DynamicButtonStyle();
	}

	public HolderButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		DynamicButtonStyle();
	}

	public HolderButton(Context context) {
		super(context);
		DynamicButtonStyle();
	}
	
	public void SetContent(HolderContent content){
		mContent = content;
	}
	
	public HolderContent GetContent(){
		return mContent;
	}

	
	public void DynamicButtonStyle(){
		setLines(MAX_LINES);
		setWidth(120);
//		setHeight(100);
		setBackgroundResource(android.R.drawable.btn_default);		
	}
	
	public boolean ConnectTo(HolderButton targetBtn){
		return mContent != null ? mContent.ConnectTo(targetBtn.mContent) : false;
	}
	
	public void PlaySound(){
		mContent.PlaySound();
	}	
	
	public void SetToggle(boolean down){
		//TODO:��ʾ��ͬ״̬�ı���ͼƬ
		if (down){
			setBackgroundResource(R.drawable.btn_default_pressed);
		}else{
			setBackgroundResource(android.R.drawable.btn_default);		
		}
	}

	public void SetMindedStatus(boolean minded){
		//TODO:��ʾ������״̬�µı���ͼƬ
		if (minded){
			setTextColor(Color.RED);
		}
	}	
	
	@Override
	public void setText(CharSequence text, BufferType type){
		super.setText(text, type);
		//TODO: ���ְ�ť���ο�߲����ı��ĳ��̶���
	}
	
	@Override
	public void setVisibility(int visibility){
		super.setVisibility(visibility);
		//TODO: ���ְ�ť�ڸ����ڵ�λ�ÿռ䲻��visibility���ı�
		
	}

}
