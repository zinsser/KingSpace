package com.jczhou.kingcai;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Button;

//用于容纳单词对象的button
//具有激活、非激活态两种状态
//同时具有连接到另外一个按钮的功能（如果另外一个按钮的key值和本按钮的key值相同，那么连接成功，否则连接失败）
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
		//TODO:显示不同状态的背景图片
		if (down){
			setBackgroundResource(R.drawable.btn_default_pressed);
		}else{
			setBackgroundResource(android.R.drawable.btn_default);		
		}
	}

	public void SetMindedStatus(boolean minded){
		//TODO:显示在提醒状态下的背景图片
		if (minded){
			setTextColor(Color.RED);
		}
	}	
	
	@Override
	public void setText(CharSequence text, BufferType type){
		super.setText(text, type);
		//TODO: 保持按钮矩形宽高不随文本的长短而变
	}
	
	@Override
	public void setVisibility(int visibility){
		super.setVisibility(visibility);
		//TODO: 保持按钮在父窗口的位置空间不随visibility而改变
		
	}

}
