#pragma once

#include "TabpageBase.h"
#include "afxcmn.h"
#include "afxwin.h"
// CTabpageBorrow dialog

class CTabpageBorrow : public CDialogEx //CTabpageBase
{
	DECLARE_DYNAMIC(CTabpageBorrow)

public:
	CTabpageBorrow(CWnd* pParent = NULL, CTabCtrl* pTabContainer = NULL);   // standard constructor
	virtual ~CTabpageBorrow();

// Dialog Data
	enum { IDD = IDD_TABPAGE_BORROW };
protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

	DECLARE_MESSAGE_MAP()

private:
	void InitControlPtrs();
	void InitListCtrl();
	void InitLights();
	void DoIDInput(CString id);
	void DoGunIDInput(CString gunId);

public:
	afx_msg void OnCbnSelchangeCombo2();
	afx_msg void OnBnClickedRadioPass();
	afx_msg void OnBnClickedRadioFail();
//	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);

public:
	virtual BOOL OnInitDialog();
	virtual BOOL PreTranslateMessage(MSG* pMsg);

private:
	CString mNameValue;
	CString mSexValue;
	CString mPoliceIdValue;
	CString mRankValue;
	BOOL mIDPass;
	BOOL m_bApprovalPass;

	CString mGunStyleValue;
	CString mGunCountValue;
	CString mBulletStyleValue;
	CString mBulletCountValue;

private:
	CStatic* mHeadPhoto;
	CStatic* mApprovalLight;
	CStatic* mIDLight;
	CStatic* mGunLight;
	CStatic* mGunPhoto;
	CListCtrl* m_ListBorrowRecord;

	CBitmap mBmpNormal;
	CBitmap mBmpPass;
	CBitmap mBmpFail;

	CBitmap mBmpHead;
	CBitmap mBmpGun;
	CBitmap mBmpHeadDef;
};
