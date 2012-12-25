#pragma once

#include "TabpageBase.h"
// CTabpageBorrow dialog

class CTabpageBorrow : public CTabpageBase
{
	DECLARE_DYNAMIC(CTabpageBorrow)

public:
	CTabpageBorrow(CWnd* pParent, CTabCtrl* pTabContainer);   // standard constructor
	virtual ~CTabpageBorrow();

// Dialog Data
	enum { IDD = IDD_TABPAGE_BORROW };
protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

	DECLARE_MESSAGE_MAP()
public:
	afx_msg void OnCbnSelchangeCombo2();
	afx_msg void OnBnClickedRadioPass();
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
private:
	CBrush m_RedBrush;
	CBrush m_GreenBrush;
public:
	virtual BOOL OnInitDialog();
	BOOL m_bApprovalPass;
	afx_msg void OnBnClickedRadioFail();
};
