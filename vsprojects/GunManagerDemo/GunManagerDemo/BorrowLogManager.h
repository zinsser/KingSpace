#pragma once

#include <vector>
using namespace std;

struct BorrowRecord;
class BorrowLogManager
{
private:
	BorrowLogManager(void);
	BorrowLogManager(const BorrowLogManager&);
	BorrowLogManager& operator=(const BorrowLogManager&);
public:
	~BorrowLogManager(void);

	static BorrowLogManager& GetInstance()
	{
		static BorrowLogManager s_BorrowLogManager;
		return s_BorrowLogManager;
	}

	void GetRecordById(CString id, vector<CBorrowRecord*>& results);
	void GetRecordByGunId(CString gunId, vector<CBorrowRecord*>& results);
private:
	vector<BorrowRecord*> mRecords;
};

