#pragma once

#include "TabpageBase.h"
// CTabpageExport dialog

class CTabpageExport : public CTabpageBase
{
	DECLARE_DYNAMIC(CTabpageExport)

public:
	CTabpageExport(CWnd* pParent, CTabCtrl* pTabContainer);   // standard constructor
	virtual ~CTabpageExport();

// Dialog Data
	enum { IDD = IDD_TABPAGE_EXPORT };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

	DECLARE_MESSAGE_MAP()
};
