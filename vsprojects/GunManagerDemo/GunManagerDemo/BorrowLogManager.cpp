#include "StdAfx.h"
#include "BorrowLogManager.h"
#include "BorrowRecord.h"

BorrowLogManager::BorrowLogManager(void)
{
	mRecords.push_back(new CBorrowRecord(L"123456789098765432", L"123456", L"2013-1-3_17:20:59", L"2013-1-4_17:20:59", FALSE));
}


BorrowLogManager::~BorrowLogManager(void)
{
	vector<CBorrowRecord*>::const_iterator iter = mRecords.begin();
	for (; iter != mRecords.end(); ++iter)
	{
		delete *iter;
	}
	mRecords.clear();
}

void BorrowLogManager::GetRecordById(CString id, vector<CBorrowRecord*>& results)
{
	results.clear();
	vector<CBorrowRecord*>::const_iterator iter = mRecords.begin();
	for (; iter != mRecords.end(); ++iter)
	{
		if ((*iter)->mId == id)
		{
			results.push_back(*iter);
		}
	}
}

void BorrowLogManager::GetRecordByGunId(CString gunId, vector<CBorrowRecord*>& results)
{
	results.clear();
	vector<CBorrowRecord*>::const_iterator iter = mRecords.begin();
	for (; iter != mRecords.end(); ++iter)
	{
		if ((*iter)->mGunId == gunId)
		{
			results.push_back(*iter);
		}
	}
}
