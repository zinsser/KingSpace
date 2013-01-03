#pragma once

#include "afxcmn.h"
#include "afxwin.h"
#include "afxdtctl.h"
// CTabpageBorrow dialog
struct CPoliceman;
struct CGun;
class CTabpageBorrow : public CDialogEx 
{
	DECLARE_DYNAMIC(CTabpageBorrow)

public:
	CTabpageBorrow(CWnd* pParent = NULL);   // standard constructor
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
	void LoadBorrowRecords(CString id);

public:
	afx_msg void OnCbnSelchangeCombo2();
	afx_msg void OnBnClickedRadioPass();
	afx_msg void OnBnClickedRadioFail();

public:
	virtual BOOL OnInitDialog();
	virtual BOOL PreTranslateMessage(MSG* pMsg);

private:
	CString mNameValue;
	CString mSexValue;
	CString mPoliceIdValue;
	CString mRankValue;
	BOOL mIDPass;
	BOOL mApprovalPass;
	BOOL mGunPass;

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

	CPoliceman* mCurrentPoliceman;
	CGun* mCurrentGun;
public:
	afx_msg void OnBnClickedButtonOk();
	CDateTimeCtrl mExpectDateCtrl;
	CDateTimeCtrl mExpectTimeCtrl;
	afx_msg void OnDtnDatetimechangeDatetimepicker1(NMHDR *pNMHDR, LRESULT *pResult);
	afx_msg void OnDtnDatetimechangeDatetimepicker2(NMHDR *pNMHDR, LRESULT *pResult);
};
