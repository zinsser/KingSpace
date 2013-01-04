#pragma once

// CTabpageExport dialog

class CTabpageExport : public CDialogEx
{
	DECLARE_DYNAMIC(CTabpageExport)

public:
	CTabpageExport(CWnd* pParent = NULL);   // standard constructor
	virtual ~CTabpageExport();

// Dialog Data
	enum { IDD = IDD_TABPAGE_EXPORT };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

	DECLARE_MESSAGE_MAP()
public:
	virtual BOOL OnInitDialog();
	afx_msg void OnLbnSelchangeList2();
};
