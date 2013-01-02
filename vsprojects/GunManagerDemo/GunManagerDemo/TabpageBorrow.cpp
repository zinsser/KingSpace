// TabpageBorrow.cpp : implementation file
//

#include "stdafx.h"
#include "GunManagerDemo.h"
#include "TabpageBorrow.h"
#include "afxdialogex.h"


#include "PolicemanManager.h"
#include "Policeman.h"
#include "GunManager.h"
#include "Gun.h"

// CTabpageBorrow dialog

IMPLEMENT_DYNAMIC(CTabpageBorrow, CDialogEx)

CTabpageBorrow::CTabpageBorrow(CWnd* pParent, CTabCtrl* pTabContainer)
//	: CTabpageBase(CTabpageBorrow::IDD, L"借用枪支", pParent, pTabContainer)
	: CDialogEx(CTabpageBorrow::IDD, NULL)
	, m_bApprovalPass(FALSE)
	, mSexValue(_T("性别："))
	, mPoliceIdValue(_T("警号："))
	, mRankValue(_T("警衔："))
	, mNameValue(_T("姓名："))
	, mGunStyleValue(_T("枪支型号："))
	, mGunCountValue(_T("枪支数量："))
	, mBulletStyleValue(_T("子弹型号："))
	, mBulletCountValue(_T("子弹数量："))
{
	mBmpNormal.LoadOEMBitmap(IDB_BITMAP_CLOSE);
	mBmpPass.LoadBitmapW(IDB_BITMAP_GREEN);
	mBmpFail.LoadBitmapW(IDB_BITMAP_RED);
	mBmpHead.LoadBitmapW(IDB_BITMAP_HEAD);
	mBmpGun.LoadBitmapW(IDB_BITMAP_GUN_1);
	mBmpHeadDef.LoadBitmapW(IDB_BITMAP_HEAD_DEF);
}

CTabpageBorrow::~CTabpageBorrow()
{
}

void CTabpageBorrow::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);

	DDX_Text(pDX, IDC_STATIC_SEX, mSexValue);
	DDX_Text(pDX, IDC_STATIC_POLICE_ID, mPoliceIdValue);
	DDX_Text(pDX, IDC_STATIC_POLICE_RANK2, mRankValue);
	DDX_Text(pDX, IDC_STATIC_NAME, mNameValue);
	DDX_Text(pDX, IDC_STATIC_GUN_STYLE, mGunStyleValue);
	DDX_Text(pDX, IDC_STATIC_GUN_COUNT, mGunCountValue);
	DDX_Text(pDX, IDC_STATIC_BULLET_STYLE, mBulletStyleValue);
	DDX_Text(pDX, IDC_STATIC_BULLET_COUNT, mBulletCountValue);
}


BEGIN_MESSAGE_MAP(CTabpageBorrow, CDialogEx)
	ON_BN_CLICKED(IDC_RADIO_PASS, &CTabpageBorrow::OnBnClickedRadioPass)
	ON_WM_CTLCOLOR()
	ON_BN_CLICKED(IDC_RADIO_FAIL, &CTabpageBorrow::OnBnClickedRadioFail)
END_MESSAGE_MAP()


// CTabpageBorrow message handlers
BOOL CTabpageBorrow::OnInitDialog()
{
	CDialogEx::OnInitDialog();
	InitControlPtrs();
	InitListCtrl();
//	InitLights();

	return TRUE;  // return TRUE unless you set the focus to a control
	// EXCEPTION: OCX Property Pages should return FALSE
}

void CTabpageBorrow::InitControlPtrs()
{
	mHeadPhoto = (CStatic* )GetDlgItem(IDC_STATIC_HEAD_PHOTO);
	mApprovalLight = (CStatic* )GetDlgItem(IDC_STATIC_APPROVAL_LIGHT);
	mIDLight = (CStatic* )GetDlgItem(IDC_STATIC_ID_LIGHT);
	mGunLight = (CStatic* )GetDlgItem(IDC_STATIC_GUN_LIGHT);
	m_ListBorrowRecord = (CListCtrl* )GetDlgItem(IDC_LIST_RECORD);
	mGunPhoto = (CStatic*)GetDlgItem(IDC_STATIC_GUN_PHOTO);

	ASSERT(mHeadPhoto != NULL && mApprovalLight != NULL
		&& mIDLight != NULL && mGunLight != NULL
		&& m_ListBorrowRecord != NULL && mGunPhoto);
}

void CTabpageBorrow::InitListCtrl()
{
	wchar_t *szColumn[]={L"昵称", L"IP地址", L"登陆时间", L"状态"};
	int widths[]={100, 98, 70, 55};
	LV_COLUMN lvc;
	lvc.mask = LVCF_FMT | LVCF_WIDTH | LVCF_TEXT | LVCF_SUBITEM;
	lvc.fmt = LVCFMT_LEFT;
	for(int i = 0; i < 4; i++) 
	{//插入各列
		lvc.pszText = szColumn[i];
		lvc.cx = widths[i];
		lvc.iSubItem = i;
		((CListCtrl* )GetDlgItem(IDC_LIST_RECORD))->InsertColumn(i, &lvc);
	}
}

void CTabpageBorrow::OnBnClickedRadioFail()
{
	// TODO: Add your control notification handler code here
	m_bApprovalPass = FALSE;
	HBITMAP hLight = (HBITMAP)mBmpFail.GetSafeHandle();
//	((CStatic*)GetDlgItem(IDC_STATIC_APPROVAL_LIGHT))->SetBitmap(hLight);
	mApprovalLight->SetBitmap(hLight);
}

void CTabpageBorrow::OnBnClickedRadioPass()
{
	// TODO: Add your control notification handler code here
	m_bApprovalPass = TRUE;
	HBITMAP hLight = (HBITMAP)mBmpPass.GetSafeHandle();
//	((CStatic*)GetDlgItem(IDC_STATIC_APPROVAL_LIGHT))->SetBitmap(hLight);
	mApprovalLight->SetBitmap(hLight);
}

void CTabpageBorrow::DoIDInput(CString id)
{
	CPoliceman* policeman = CPolicemanManager::GetInstance().GetPolicemanById(id);
	if (policeman)
	{
		mIDPass = TRUE;
		HBITMAP hLight = (HBITMAP)mBmpPass.GetSafeHandle();
		//((CStatic*)GetDlgItem(IDC_STATIC_ID_LIGHT))->SetBitmap(hLight);
		mIDLight->SetBitmap(hLight);

		HBITMAP hHead = (HBITMAP)mBmpHead.GetSafeHandle();
		//((CStatic*)GetDlgItem(IDC_STATIC_HEAD_PHOTO))->SetBitmap(hHead);
		mHeadPhoto->SetBitmap(hHead);		
		((CStatic*)GetDlgItem(IDC_STATIC_BASE_INFO))->SetWindowTextW(L"基本信息："+id);
		mNameValue = L"姓名：   " + policeman->mName;
		mSexValue = L"性别：   " + policeman->mSex;
		mPoliceIdValue = L"警号：   " + policeman->mNumber;
		mRankValue = L"警衔：   " + policeman->mRank;	

		UpdateData(FALSE);
	}
	else
	{
		mIDPass = FALSE;

		HBITMAP hLight = (HBITMAP)mBmpFail.GetSafeHandle();
		((CStatic*)GetDlgItem(IDC_STATIC_ID_LIGHT))->SetBitmap(hLight);
		HBITMAP hHead = (HBITMAP)mBmpHeadDef.GetSafeHandle();
		mHeadPhoto->SetBitmap(hHead);		
		((CStatic*)GetDlgItem(IDC_STATIC_BASE_INFO))->SetWindowTextW(L"基本信息：查无此人");
	//	((CStatic*)GetDlgItem(IDC_STATIC_HEAD_PHOTO))->SetBitmap(hLight);

		mNameValue = L"姓名：   ";
		mSexValue = L"性别：   ";
		mPoliceIdValue = L"警号：   ";
		mRankValue = L"警衔：   ";

		UpdateData(FALSE);
	}
}

void CTabpageBorrow::DoGunIDInput(CString gunId)
{
	CGun* gun = CGunManager::GetInstance().GetGunById(gunId);
	if (gun)
	{
		((CStatic*)GetDlgItem(IDC_STATIC_GUN_INFO))->SetWindowTextW(L"枪支信息："+gunId);
		HBITMAP hGunPhoto = (HBITMAP)mBmpGun.GetSafeHandle();
		mGunPhoto->SetBitmap(hGunPhoto);

		HBITMAP hLight = (HBITMAP)mBmpPass.GetSafeHandle();
		mGunLight->SetBitmap(hLight);

		mGunStyleValue = L"枪支型号：  " + gun->mStyle;
		mGunCountValue = L"枪支数量：  1" ;
		mBulletStyleValue = L"子弹型号：  " + gun->mBullet;
		mBulletCountValue = L"子弹数量：  4" + gun->mCountBullet;
		UpdateData(FALSE);
	}
	else
	{
		((CStatic*)GetDlgItem(IDC_STATIC_GUN_INFO))->SetWindowTextW(L"枪支信息：尚未入库");
		mGunPhoto->SetBitmap(NULL);

		HBITMAP hLight = (HBITMAP)mBmpFail.GetSafeHandle();
		mGunLight->SetBitmap(hLight);

		mGunStyleValue = L"枪支型号：  ";
		mGunCountValue = L"枪支数量：  1" ;
		mBulletStyleValue = L"子弹型号：  ";
		mBulletCountValue = L"子弹数量：  ";
		UpdateData(FALSE);
	}
}

BOOL CTabpageBorrow::PreTranslateMessage(MSG* pMsg)
{
	// TODO: Add your specialized code here and/or call the base class
	CEdit* pEditId = (CEdit*)GetDlgItem(IDC_EDIT_ID_IN_BORROW);
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
