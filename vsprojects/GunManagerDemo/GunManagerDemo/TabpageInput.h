#pragma once

#include "TabpageBase.h"
// CTabpageInput dialog

class CTabpageInput : public CTabpageBase
{
	DECLARE_DYNAMIC(CTabpageInput)

public:
	CTabpageInput(CWnd* pParent, CTabCtrl* pTabContainer);   // standard constructor
	virtual ~CTabpageInput();

// Dialog Data
	enum { IDD = IDD_TAGPAGE_INPUT };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

	DECLARE_MESSAGE_MAP()
};
