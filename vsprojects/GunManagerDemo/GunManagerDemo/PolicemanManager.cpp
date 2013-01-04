#include "StdAfx.h"
#include "PolicemanManager.h"
#include "Policeman.h"

CPolicemanManager::CPolicemanManager(void)
{
	mPolicemans.push_back(new CPoliceman(L"123456789098765432", L"9527", L"King", L"男", L"督察", L"科技园", L"探长", L"CID", L"CA001", L"10086", TRUE, FALSE));
	mPolicemans.push_back(new CPoliceman(L"987654321234567890", L"9528", L"King", L"女", L"督察", L"科技园", L"探长", L"CID", L"CA001", L"10086", TRUE, FALSE));
}

CPolicemanManager::~CPolicemanManager(void)
{
	vector<CPoliceman*>::const_iterator iter = mPolicemans.begin();
	for (; iter != mPolicemans.end(); ++iter)
	{
		delete *iter;
	}
	mPolicemans.clear();
}

CPoliceman* CPolicemanManager::GetPolicemanById(CString id)
{
	vector<CPoliceman*>::const_iterator iter = mPolicemans.begin();
	for (; iter != mPolicemans.end(); ++iter)
	{
		if ((*iter)->mID == id)
		{
			return *iter;
		}
	}

	return NULL;
}

void CPolicemanManager::AddPoliceman(CString id, CString number, CString name, CString sex, CString rank,
		CString agency, CString officiate, CString job, CString gunHoldId,
		CString telephone, BOOL isInstructor, BOOL isStudent)
{
	mPolicemans.push_back(new CPoliceman(id, number, name, sex, rank, agency, officiate,
		job, gunHoldId, telephone, isInstructor, isStudent));
}
