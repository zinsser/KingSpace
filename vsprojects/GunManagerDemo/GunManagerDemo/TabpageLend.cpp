// TabpageLend.cpp : implementation file
//

#include "stdafx.h"
#include "GunManagerDemo.h"
#include "TabpageLend.h"
#include "afxdialogex.h"

#include "PolicemanManager.h"
#include "Policeman.h"
#include "GunManager.h"
#include "Gun.h"

// CTabpageLend dialog
IMPLEMENT_DYNAMIC(CTabpageLend, CDialogEx)

CTabpageLend::CTabpageLend(CWnd* pParent)
	: CDialogEx(CTabpageLend::IDD, pParent)
	, mIDPass(FALSE)
	, mGunPass(FALSE)
	, mTimePass(FALSE)
	, mNameValue(_T("������"))
	, mSexValue(_T("�Ա�"))
	, mRankValue(_T("���Σ�"))
	, mGunStyleValue(_T("ǹ֧�ͺţ�"))
	, mGunCountValue(_T("ǹ֧������"))
	, mBulletStyleValue(_T("�ӵ��ͺţ�"))
	, mBulletCountValue(_T("�ӵ�������"))
{
	mBmpNormal.LoadOEMBitmap(IDB_BITMAP_CLOSE);
	mBmpPass.LoadBitmapW(IDB_BITMAP_GREEN);
	mBmpFail.LoadBitmapW(IDB_BITMAP_RED);
	mBmpHead.LoadBitmapW(IDB_BITMAP_HEAD);
	mBmpGun.LoadBitmapW(IDB_BITMAP_GUN_1);
	mBmpHeadDef.LoadBitmapW(IDB_BITMAP_HEAD_DEF);
}

CTabpageLend::~CTabpageLend()
{
}

void CTabpageLend::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_STATIC_TIME_LIGHT, mTimeLight);
	DDX_Control(pDX, IDC_STATIC_HEAD_PHOTO, mHeadPhoto);
	DDX_Control(pDX, IDC_STATIC_GUN_PHOTO, mGunPhoto);
	DDX_Control(pDX, IDC_STATIC_GUN_LIGHT, mGunLight);
	DDX_Control(pDX, IDC_STATIC_ID_LIGHT, mIDLight);
	DDX_Text(pDX, IDC_STATIC_NAME, mNameValue);
	DDX_Text(pDX, IDC_STATIC_RANK, mRankValue);
	DDX_Text(pDX, IDC_STATIC_GUN_STYLE, mGunStyleValue);
	DDX_Text(pDX, IDC_STATIC_BULLET_STYLE, mBulletStyleValue);
	DDX_Text(pDX, IDC_STATIC_BULLET_COUNT, mBulletCountValue);
	DDX_Text(pDX, IDC_STATIC_GUN_COUNT, mGunCountValue);
	DDX_Text(pDX, IDC_STATIC_SEX, mSexValue);
}


BEGIN_MESSAGE_MAP(CTabpageLend, CDialogEx)
END_MESSAGE_MAP()


// CTabpageLend message handlers


BOOL CTabpageLend::PreTranslateMessage(MSG* pMsg)
{
	CEdit* pEditId = (CEdit*)GetDlgItem(IDC_EDIT_ID);
	CEdit* pEditGunId = (CEdit*)GetDlgItem(IDC_EDIT_GUN);
	if (pEditId != NULL && pMsg->hwnd == pEditId->GetSafeHwnd()
		&& VK_RETURN == pMsg->wParam)
	{
		CString id;
		pEditId->GetWindowTextW(id);
		DoIDInput(id);
		return TRUE;
	}
	else if (pEditGunId != NULL && pMsg->hwnd == pEditGunId->GetSafeHwnd()
		&& VK_RETURN == pMsg->wParam)
	{
		CString gunId;
		pEditGunId->GetWindowTextW(gunId);
		DoGunIDInput(gunId);
		return TRUE;
	}
	return CDialogEx::PreTranslateMessage(pMsg);
}

void CTabpageLend::DoIDInput(CString id)
{
	CPoliceman* policeman = CPolicemanManager::GetInstance().GetPolicemanById(id);
	if (policeman)
	{
		mIDPass = TRUE;
		HBITMAP hLight = (HBITMAP)mBmpPass.GetSafeHandle();
		mIDLight.SetBitmap(hLight);

		HBITMAP hHead = (HBITMAP)mBmpHead.GetSafeHandle();
		mHeadPhoto.SetBitmap(hHead);		
		((CStatic*)GetDlgItem(IDC_STATIC_BASE_INFO))->SetWindowTextW(L"������Ϣ��"+id);
		mNameValue = L"������   " + policeman->mName;
		mSexValue = L"�Ա�   " + policeman->mSex;
		mRankValue = L"���Σ�   " + policeman->mRank;	

		UpdateData(FALSE);
	}
	else
	{
		mIDPass = FALSE;

		HBITMAP hLight = (HBITMAP)mBmpFail.GetSafeHandle();
		mIDLight.SetBitmap(hLight);
		HBITMAP hHead = (HBITMAP)mBmpHeadDef.GetSafeHandle();
		mHeadPhoto.SetBitmap(hHead);		
		((CStatic*)GetDlgItem(IDC_STATIC_BASE_INFO))->SetWindowTextW(L"������Ϣ��"+id);

		mNameValue = L"������   ���޴���";
		mSexValue = L"�Ա�   ";
		mRankValue = L"���Σ�   ";

		UpdateData(FALSE);
	}
}

void CTabpageLend::DoGunIDInput(CString gunId)
{
	CGun* gun = CGunManager::GetInstance().GetGunById(gunId);
	if (gun)
	{
		mGunPass = TRUE;
		((CStatic*)GetDlgItem(IDC_STATIC_GUN_INFO))->SetWindowTextW(L"ǹ֧��Ϣ��"+gunId);
		HBITMAP hGunPhoto = (HBITMAP)mBmpGun.GetSafeHandle();
		mGunPhoto.SetBitmap(hGunPhoto);

		HBITMAP hLight = (HBITMAP)mBmpPass.GetSafeHandle();
		mGunLight.SetBitmap(hLight);

		mGunStyleValue = L"ǹ֧�ͺţ�  " + gun->mStyle;
		mGunCountValue = L"ǹ֧������  1" ;
		mBulletStyleValue = L"�ӵ��ͺţ�  " + gun->mBullet;
		mBulletCountValue.Format(L"�ӵ�������  %d", gun->mCountBullet);
		UpdateData(FALSE);
	}
	else
	{
		mGunPass = FALSE;
		((CStatic*)GetDlgItem(IDC_STATIC_GUN_INFO))->SetWindowTextW(L"ǹ֧��Ϣ��"+gunId);
		mGunPhoto.SetBitmap(NULL);

		HBITMAP hLight = (HBITMAP)mBmpFail.GetSafeHandle();
		mGunLight.SetBitmap(hLight);

		mGunStyleValue = L"ǹ֧�ͺţ�  ��δ���";
		mGunCountValue = L"ǹ֧������  1" ;
		mBulletStyleValue = L"�ӵ��ͺţ�  ";
		mBulletCountValue = L"�ӵ�������  ";
		UpdateData(FALSE);
	}
}
