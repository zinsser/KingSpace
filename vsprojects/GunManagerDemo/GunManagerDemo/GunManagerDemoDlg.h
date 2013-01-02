
// GunManagerDemoDlg.h : header file
//

#pragma once
#include "afxcmn.h"
#include <vector>
using namespace std;

#include "TabpageExport.h"
#include "TabpageBorrow.h"
// CGunManagerDemoDlg dialog
class CTabpageBase;
class CGunManagerDemoDlg : public CDialogEx
{
// Construction
public:
	CGunManagerDemoDlg(CWnd* pParent = NULL);	// standard constructor
	~CGunManagerDemoDlg();
// Dialog Data
	enum { IDD = IDD_GUNMANAGERDEMO_DIALOG };

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support


// Implementation
protected:
	HICON m_hIcon;

	// Generated message map functions
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	DECLARE_MESSAGE_MAP()

private :
	void OnInitTabpage();

private:
	CTabCtrl mTabCtrlOpStage;
	vector<CTabpageBase*>  mPageItems;
	CTabpageExport mTabpageExport;
	CTabpageBorrow mTabpageBorrow;
public:
	afx_msg void OnTcnSelchangeOpStage(NMHDR *pNMHDR, LRESULT *pResult);

};
