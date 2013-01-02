#include "StdAfx.h"
#include "GunManager.h"
#include "Gun.h"

CGunManager::CGunManager(void)
{
	mGuns.push_back(new CGun(L"VY_AK", L"123456", L"KK_xK3", 4));
	mGuns.push_back(new CGun(L"VX_M4", L"654321", L"KK_aZ7", 1));
}


CGunManager::~CGunManager(void)
{
	vector<CGun*>::const_iterator iter = mGuns.begin();
	for (; iter != mGuns.end(); ++iter)
	{
		delete *iter;
	}
	mGuns.clear();
}

CGun* CGunManager::GetGunById(CString gunId)
{
	vector<CGun*>::const_iterator iter = mGuns.begin();
	for (; iter != mGuns.end(); ++iter)
	{
		if ((*iter)->mGunId == gunId)
		{
			return *iter;
		}
	}

	return NULL;
}