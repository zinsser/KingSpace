#include "StdAfx.h"
#include "BorrowLogManager.h"
#include "BorrowRecord.h"

CBorrowLogManager::CBorrowLogManager(void)
{
	mRecords.push_back(new CBorrowRecord(L"123456789098765432", L"123456", 1357218227, 1357218227, FALSE));

	mRecords[0]->mEndTime = 1357218230;
}

CBorrowLogManager::~CBorrowLogManager(void)
{
	vector<CBorrowRecord*>::const_iterator iter = mRecords.begin();
	for (; iter != mRecords.end(); ++iter)
	{
		delete *iter;
	}
	mRecords.clear();
}

void CBorrowLogManager::GetRecordById(CString id, vector<CBorrowRecord*>& results)
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

void CBorrowLogManager::GetRecordByGunId(CString gunId, vector<CBorrowRecord*>& results)
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

void CBorrowLogManager::AddRecord(CBorrowRecord* record)
{
	mRecords.push_back(record);
}