#pragma once
#include "afxwin.h"

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

private:
	void DoIDInput(CString id);
	void DoGunIDInput(CString gunId);

public:
	virtual BOOL PreTranslateMessage(MSG* pMsg);

private:
	BOOL mIDPass;
	BOOL mGunPass;
	BOOL mTimePass;

	CBitmap mBmpNormal;
	CBitmap mBmpPass;
	CBitmap mBmpFail;
	CBitmap mBmpHead;
	CBitmap mBmpGun;
	CBitmap mBmpHeadDef;
private:
	CStatic mTimeLight;
	CStatic mHeadPhoto;
	CStatic mGunPhoto;
	CStatic mGunLight;
	CStatic mIDLight;

	CString mNameValue;
	CString mSexValue;
	CString mRankValue;
	CString mGunStyleValue;
	CString mGunCountValue;
	CString mBulletStyleValue;
	CString mBulletCountValue;
};
