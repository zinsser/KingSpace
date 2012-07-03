package com.jczhou.kingcai.messageservice;

import com.jczhou.kingcai.messageservice.ActiveMessageManager.ActiveFunctor;
import com.jczhou.kingcai.socketservice.SocketService;

public class LoginRequestMessage extends ActiveMessage{
	public final static String  s_MsgTag = "[LoginRequest]";
	private String mMsgPack;
	private String mPeerIP;
	protected LoginRequestMessage(String peer, String msgContent) {
		super(s_MsgTag);
		mMsgPack = msgContent;
		mPeerIP = peer;
	}

	@Override
	public void Execute() {
		SocketService.GetInstance().GetWaiter().onQueryFinished();		
		String pack = super.FromPack(mMsgPack);
		if (pack.contains("[Password]")){
			int idxPassword = pack.indexOf("[Password]");
			String id = pack.substring("[Number]".length(), idxPassword);
			String password = pack.substring(idxPassword+"[Password]".length(), pack.length());
	
			String result = SocketService.GetInstance().GetStudentInfoByID(id, password);
			String retPack = "";
			if (result == null){
				retPack = "[fail]";
			}else{
				retPack = "[pass]" + result;
			}
			
			SocketService.SendMessage(new LoginResponseMessage(retPack), mPeerIP);
			SocketService.GetInstance().GetWaiter().onLoginRequest(id, mPeerIP);
		}
	}
	
	public static class LoginRequestFunctor extends ActiveFunctor{

		@Override
		public ActiveMessage OnReceiveMessage(String peer, String param){
			return new LoginRequestMessage(peer, param);
		}
	}
}
