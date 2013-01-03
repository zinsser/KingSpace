#pragma once

struct CBorrowRecord
{
	CString mId;			//���������֤����
	CString mGunId;         //������ǹ֧���
	CString mStartTime;     //����ʱ��
	CString mExpectTime;    //Ԥ��ʱ��
	CString mEndTime;		//�黹ʱ��
	BOOL mException;		//�Ƿ��쳣����

	CBorrowRecord(CString id, CString gunId, 
		CString startTime, CString expectTime, 
		BOOL exception)
		: mId(id), mGunId(gunId), mStartTime(startTime), 
		mExpectTime(expectTime), mException(exception)
	{
	}
};