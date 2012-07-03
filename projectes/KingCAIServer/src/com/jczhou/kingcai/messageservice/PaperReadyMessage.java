package com.jczhou.kingcai.messageservice;

import com.jczhou.kingcai.messageservice.ActiveMessageManager.ActiveFunctor;
import com.jczhou.kingcai.socketservice.SocketService;

public class PaperReadyMessage extends ActiveMessage{
	public final static String  s_MsgTag = "[PaperRequest]";
	private String mMsgPack;
	private String mPeerIP;
	protected PaperReadyMessage(String peer, String msgContent) {
		super(s_MsgTag);
		mMsgPack = msgContent;
		mPeerIP = peer;
	}

	@Override
	public void Execute() {
		SocketService.GetInstance().GetWaiter().onPaperReady();		
	}
	
	public static class PaperReadyFunctor extends ActiveFunctor{

		@Override
		public ActiveMessage OnReceiveMessage(String peer, String param){
			return new PaperReadyMessage(peer, param);
		}
	}	
}