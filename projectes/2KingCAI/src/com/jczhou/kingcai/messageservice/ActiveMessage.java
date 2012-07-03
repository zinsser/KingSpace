package com.jczhou.kingcai.messageservice;

public abstract class ActiveMessage {
	private String mMsgTag;

	protected ActiveMessage(String msgTag){
		mMsgTag = msgTag;
	}

	public abstract void Execute();
	
	protected String FromPack(String msgPack){
		return msgPack.substring(mMsgTag.length(), msgPack.length());
	}	
	
	protected String GetMessageTag(){
		return mMsgTag;
	}

}
