package com.jczhou.kingcai.messageservice;

public class QuestionMessage extends RequestMessage{
	private final static String  s_MsgTag = "[QuestionBC]";
	private String mMsgContent;
	
	public QuestionMessage(String msgContent) {
		super(s_MsgTag);
		mMsgContent = msgContent;
	}
	
	@Override
	public String Pack(){
		return mMsgContent;
	}
}