package com.jczhou.kingcai.messageservice;

public class PaperTitleMessage  extends RequestMessage{
	private final static String  s_MsgTag = "[DispatchTitle]";
	private String mMsgTitle;
	
	public PaperTitleMessage(String title) {
		super(s_MsgTag);
		mMsgTitle = title;
	}
	
	@Override
	public String Pack(){
		return mMsgTitle;
	}
}