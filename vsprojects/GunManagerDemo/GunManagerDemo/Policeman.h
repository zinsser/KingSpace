#pragma once
struct CPoliceman
{
	CString mID;			//���֤
	CString mNumber;		//����
	CString mName;			//����
	CString mSex;			//�Ա�
	CString mRank;			//����
	CString mAgency;		//��λ
	CString mOfficiate;		//ְ��
	CString mJob;			//��λ
	CString mGunHoldID;		//��ǹ����
	CString mTelephone;		//�绰
	BOOL    mIsInstructor;  //�Ƿ�̹�
	BOOL    mIsStudent;		//�Ƿ�ѧԱ
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

