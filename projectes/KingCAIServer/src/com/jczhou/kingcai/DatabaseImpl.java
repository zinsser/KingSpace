package com.jczhou.kingcai;

public class DatabaseImpl implements IDatabase{

	@Override
	public String GetStudentInfoByID(String ID, String password) {
		return "00001".equals(ID) && "000000".equals(password) ? "���� ����" : null;
		//		return "00001".equals(ID) && "000000".equals(password) ? "wealth" : null;
	}
}
