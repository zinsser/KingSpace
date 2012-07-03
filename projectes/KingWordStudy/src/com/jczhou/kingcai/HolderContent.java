package com.jczhou.kingcai;

//�����ɵ���ť�Ķ���
//����ʹӢ�ĵ��ʡ����Ľ��͡����ꡢ������
public class HolderContent{
	private String mWrapper = null;
	private String mKey = null;
	private String mSound = null; //��������
	
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
	
	//��������ɶ���ɷ�����mSound��Ϊ�գ�����ô�������޾Ͳ�����ʾ��
	//�����������ж��Ƿ���ʾ�ַ���������С����ͼ��
	public boolean IsShowable(){
		return mSound == null;
	}
	
	//���keyֵ��ͬ����ôΪͬһ������
	public boolean ConnectTo(HolderContent another){
		return mKey != null && another != null && this != another ? mKey.equals(another.mKey) : false;
	}
	
	public String GetWrapper(){
		return IsShowable() ? mWrapper : "�������������";
	}
	
	public void PlaySound(){
		
	}
}
