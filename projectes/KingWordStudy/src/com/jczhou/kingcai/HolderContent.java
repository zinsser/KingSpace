package com.jczhou.kingcai;

//被容纳到按钮的对象
//可以使英文单词、中文解释、音标、读音等
public class HolderContent{
	private String mWrapper = null;
	private String mKey = null;
	private String mSound = null; //发音单词
	
	public HolderContent(String content, String key){
		this(content, key, null);
	}

	public HolderContent(String content, String key, String sound){
		mKey = key;
		mWrapper = content;
		mSound = sound;
	}
	
	public static String MakeKeyString(String dictName, int id){
		return new String(dictName + ":" + id);
	}
	
	//如果被容纳对象可发音（mSound不为空），那么被容纳无就不可显示了
	//本函数用于判断是否显示字符串或发音的小喇叭图标
	public boolean IsShowable(){
		return mSound == null;
	}
	
	//如果key值相同，那么为同一个单词
	public boolean ConnectTo(HolderContent another){
		return mKey != null && another != null && this != another ? mKey.equals(another.mKey) : false;
	}
	
	public String GetWrapper(){
		return IsShowable() ? mWrapper : "点击我收听读音";
	}
	
	public void PlaySound(){
		
	}
}
