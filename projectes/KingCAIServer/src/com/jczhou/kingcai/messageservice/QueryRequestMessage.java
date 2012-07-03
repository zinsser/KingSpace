package com.jczhou.kingcai.messageservice;

import com.jczhou.kingcai.messageservice.ActiveMessageManager.ActiveFunctor;
import com.jczhou.kingcai.socketservice.SocketService;

public class QueryRequestMessage extends ActiveMessage{
	public final static String  s_MsgTag = "[QueryRequest]";
	private String mMsgPack;
	private String mPeerIP;
	protected QueryRequestMessage(String peer, String msgContent) {
		super(s_MsgTag);
		mMsgPack = msgContent;
		mPeerIP = peer;
	}

	@Override
	public void Execute() {
		String IpAddr = SocketService.GetInstance().getLocalIPAddress();
		SocketService.GetInstance().AddClient(mPeerIP);
		SocketService.SendMessage(new QueryResponseMessage(IpAddr), mPeerIP);
		SocketService.GetInstance().GetWaiter().onQueryRequest(mPeerIP);	
	}
	
	public static class QueryRequestFunctor extends ActiveFunctor{

		@Override
		public ActiveMessage OnReceiveMessage(String peer, String param){
			return new QueryRequestMessage(peer, param);//peer=null
		}
	}	
}
