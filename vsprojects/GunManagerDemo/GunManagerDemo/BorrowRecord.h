#pragma once

struct CBorrowRecord
{
	CString mId;			//借用人身份证号码
	CString mGunId;         //被借用枪支编号
	__time64_t mStartTime;     //借用时间
	__time64_t mExpectTime;    //预计时间
	__time64_t mEndTime;		//归还时间
	BOOL mException;		//是否异常借用

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