#pragma once

#include <vector>
using namespace std;
struct CGun;
class CGunManager
{
private:
	CGunManager(void);
	CGunManager(const CGunManager&);
	CGunManager& operator=(const CGunManager&);

public:
	~CGunManager(void);
	static CGunManager& GetInstance()
	{
		static CGunManager s_GunManager;
		return s_GunManager;
	}

public:
	CGun* GetGunById(CString gunId);

private:
	vector<CGun*> mGuns;
};

