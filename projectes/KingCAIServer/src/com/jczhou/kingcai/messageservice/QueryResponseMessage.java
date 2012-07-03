package com.jczhou.kingcai.messageservice;

public class QueryResponseMessage extends RequestMessage{
	private final static String  s_MsgTag = "[QueryResponse]";
	private String mMsgContent;
	
	public QueryResponseMessage(String msgContent) {
		super(s_MsgTag);
		mMsgContent = msgContent;
	}
	
	@Override
	public String Pack(){
		return mMsgContent;
	}
}
