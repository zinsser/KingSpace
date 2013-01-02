#pragma once
struct CPoliceman
{
	CString mID;			//身份证
	CString mNumber;		//警号
	CString mName;			//名称
	CString mSex;			//性别
	CString mRank;			//警衔
	CString mAgency;		//单位
	CString mOfficiate;		//职务
	CString mJob;			//岗位
	CString mGunHoldID;		//持枪号码
	CString mTelephone;		//电话
	BOOL    mIsInstructor;  //是否教官
	BOOL    mIsStudent;		//是否学员
public:
	CPoliceman(CString id, CString number, CString name, CString sex, CString rank,
		CString agency, CString officiate, CString job, CString gunHoldId,
		CString telephone, BOOL isInstructor, BOOL isStudent)
		:mID(id), mNumber(number), mName(name), mSex(sex),
		mRank(rank), mAgency(agency), mOfficiate(officiate),
		mJob(job), mGunHoldID(gunHoldId), mTelephone(telephone),
		mIsInstructor(isInstructor), mIsStudent(isStudent)
	{
	}
};

