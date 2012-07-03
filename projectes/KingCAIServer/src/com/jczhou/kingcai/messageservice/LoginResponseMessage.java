package com.jczhou.kingcai.messageservice;

public class LoginResponseMessage  extends RequestMessage{
	private final static String  s_MsgTag = "[LoginResponse]";
	private String mMsgContent;
	
	public LoginResponseMessage(String msgContent) {
		super(s_MsgTag);
		mMsgContent = msgContent;
	}
	
	@Override
	public String Pack(){
		return mMsgContent;
	}
}