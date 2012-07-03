package com.jczhou.kingcai.messageservice;

import java.util.HashMap;

public class ActiveMessageManager {
	public HashMap<String, ActiveFunctor> mActiveMsgMap = new HashMap<String, ActiveFunctor>();
	public ActiveMessageManager(){
		mActiveMsgMap.put(QueryRequestMessage.s_MsgTag, new QueryRequestMessage.QueryRequestFunctor());		
		mActiveMsgMap.put(LoginRequestMessage.s_MsgTag, new LoginRequestMessage.LoginRequestFunctor());
		mActiveMsgMap.put(LoginFinishedRequestMessage.s_MsgTag, new LoginFinishedRequestMessage.LoginFinishedRequestFunctor());
		mActiveMsgMap.put(PaperReadyMessage.s_MsgTag, new PaperReadyMessage.PaperReadyFunctor());		
	}
	
	public static abstract class ActiveFunctor {
		public abstract ActiveMessage OnReceiveMessage(String peer, String param);
	}
}
