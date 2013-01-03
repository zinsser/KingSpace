#pragma once

// CTabpageLend dialog

class CTabpageLend : public CDialogEx
{
	DECLARE_DYNAMIC(CTabpageLend)

public:
	CTabpageLend(CWnd* pParent = NULL);   // standard constructor
	virtual ~CTabpageLend();

// Dialog Data
	enum { IDD = IDD_TABPAGE_LEND };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

	DECLARE_MESSAGE_MAP()
public:
	afx_msg void OnEnChangeEdit1();
};
