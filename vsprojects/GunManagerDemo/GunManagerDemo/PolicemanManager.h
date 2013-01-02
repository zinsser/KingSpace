#pragma once
#include <vector>
using namespace std;

struct CPoliceman;
class CPolicemanManager
{
private:
	CPolicemanManager(void);
	CPolicemanManager(const CPolicemanManager&);
	CPolicemanManager& operator=(const CPolicemanManager&);
public:
	~CPolicemanManager(void);
	static CPolicemanManager& GetInstance()
	{
		static CPolicemanManager s_policemanMgr;
		return s_policemanMgr;
	}
public:
	CPoliceman* GetPolicemanById(CString id);

private:
	vector<CPoliceman*> mPolicemans;
};

