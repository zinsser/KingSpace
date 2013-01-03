#pragma once

#include <vector>
using namespace std;

struct CBorrowRecord;
class CBorrowLogManager
{
private:
	CBorrowLogManager(void);
	CBorrowLogManager(const CBorrowLogManager&);
	CBorrowLogManager& operator=(const CBorrowLogManager&);
public:
	~CBorrowLogManager(void);

	static CBorrowLogManager& GetInstance()
	{
		static CBorrowLogManager s_BorrowLogManager;
		return s_BorrowLogManager;
	}

	void GetRecordById(CString id, vector<CBorrowRecord*>& results);
	void GetRecordByGunId(CString gunId, vector<CBorrowRecord*>& results);
	void AddRecord(CBorrowRecord* record);
private:
	vector<CBorrowRecord*> mRecords;
};

