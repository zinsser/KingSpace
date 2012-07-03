package com.jczhou.kingcai.messageservice;

import com.jczhou.kingcai.messageservice.ActiveMessageManager.ActiveFunctor;
import com.jczhou.kingcai.socketservice.SocketService;

public class LoginFinishedRequestMessage extends ActiveMessage{
	public final static String  s_MsgTag = "[LoginFinished]";
	private String mMsgPack;
	private String mPeerIP;
	protected LoginFinishedRequestMessage(String peer, String msgContent) {
		super(s_MsgTag);
		mMsgPack = msgContent;
		mPeerIP = peer;
	}

	@Override
	public void Execute() {
		SocketService.GetInstance().GetWaiter().onLoginFinished();		
	}
	
	public static class LoginFinishedRequestFunctor extends ActiveFunctor{

		@Override
		public ActiveMessage OnReceiveMessage(String peer, String param){
			return new LoginFinishedRequestMessage(peer, param);
		}
	}
}