#pragma once

struct CBorrowRecord
{
	CString mId;			//���������֤����
	CString mGunId;         //������ǹ֧���
	__time64_t mStartTime;     //����ʱ��
	__time64_t mExpectTime;    //Ԥ��ʱ��
	__time64_t mEndTime;		//�黹ʱ��
	BOOL mException;		//�Ƿ��쳣����

	CBorrowRecord(CString id, CString gunId, 
		__time64_t startTime, __time64_t expectTime, 
		BOOL exception)
		: mId(id), mGunId(gunId), mStartTime(startTime), 
		mExpectTime(expectTime), mException(exception)
	{
		mEndTime = 0;
	}

	
	static CString FormatTimeString(__time64_t t)
	{
		CTime time(t);
		CString timeString;
		timeString.Format(L"%d-%d-%d_%d:%d:%d", time.GetYear(), time.GetMonth(), time.GetDay(),
			time.GetHour(), time.GetMinute(), time.GetSecond());

		return timeString;
	}
};