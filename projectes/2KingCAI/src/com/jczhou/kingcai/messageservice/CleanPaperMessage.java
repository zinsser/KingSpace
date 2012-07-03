package com.jczhou.kingcai.messageservice;

import com.jczhou.kingcai.messageservice.ActiveMessageManager.ActiveFunctor;
import com.jczhou.kingcai.socketservice.SocketService;

public class CleanPaperMessage  extends ActiveMessage{
	public final static String s_MsgTag = "[CleanPaper]";
	public CleanPaperMessage(){
		super(s_MsgTag);
	}
	
	public static class CleanPaperFunctor extends ActiveFunctor{

		@Override
		public ActiveMessage OnReceiveMessage(String peer, String param){
			return new CleanPaperMessage();
		}
	}

	@Override
	public void Execute() {
		SocketService.GetInstance().GetWaiter().onCleanPaper();
	}
}