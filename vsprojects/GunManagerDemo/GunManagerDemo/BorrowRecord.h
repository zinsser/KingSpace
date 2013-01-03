#pragma once

struct CBorrowRecord
{
	CString mId;			//借用人身份证号码
	CString mGunId;         //被借用枪支编号
	CString mStartTime;     //借用时间
	CString mExpectTime;    //预计时间
	CString mEndTime;		//归还时间
	BOOL mException;		//是否异常借用

	CBorrowRecord(CString id, CString gunId, 
		CString startTime, CString expectTime, 
		BOOL exception)
		: mId(id), mGunId(gunId), mStartTime(startTime), 
		mExpectTime(expectTime), mException(exception)
	{
	}
};