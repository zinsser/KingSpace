package com.jczhou.kingcai;

public class KingCAIConfig {
	public final static int EVENT_CLIENT_IP_REQUEST = 0;
	public final static int EVENT_CLIENT_IP_FINISH = 1;	
	public final static int EVENT_CLIENT_LOGIN = 2;
	public final static int EVENT_LOGIN_FINISHED = 3;
	public final static int EVENT_RECEIVE_MESSAGE = 4;

	public final static String mCharterSet = "GBK";
	
	public final static String ExtraParamOpenSheet = "ExtraParam";

	public final static int mTCPPort = 2011;
	public final static int mUDPPort = 2012;
	public final static String mMulticastServerCommonGroupIP = "224.0.0.100";
	public final static int mMulticastServerCommonPort = 2013;
	public final static String mMulticastClientCommonGroupIP = "224.0.0.101";	
	public final static int mMulticastClientCommonPort = 2014;
	public final static String mMulticastClientImageGroupIP = "224.0.0.102";	
	public final static int mMulticastClientImagePort = 2015;
	public final static int mUDPServerImagePort = 2016;	
}
