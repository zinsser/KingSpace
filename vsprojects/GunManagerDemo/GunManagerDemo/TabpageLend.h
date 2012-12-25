#pragma once

#include "TabpageBase.h"
// CTabpageLend dialog

class CTabpageLend : public CTabpageBase
{
	DECLARE_DYNAMIC(CTabpageLend)

public:
	CTabpageLend(CWnd* pParent, CTabCtrl* pTabContainer);   // standard constructor
	virtual ~CTabpageLend();

// Dialog Data
	enum { IDD = IDD_TABPAGE_LEND };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

	DECLARE_MESSAGE_MAP()
};
