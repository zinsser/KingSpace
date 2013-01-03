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

CTabpageBorrow::CTabpageBorrow(CWnd* pParent)
	: CDialogEx(CTabpageBorrow::IDD, pParent)
	, mApprovalPass(FALSE)
	, mIDPass(FALSE)
	, mGunPass(FALSE)
	, mSexValue(_T("�Ա�"))
	, mPoliceIdValue(_T("���ţ�"))
	, mRankValue(_T("���Σ�"))
	, mNameValue(_T("������"))
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
	ON_BN_CLICKED(IDC_BUTTON_OK, &CTabpageBorrow::OnBnClickedButtonOk)
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
	wchar_t *szColumn[]={L"�ǳ�", L"IP��ַ", L"��½ʱ��", L"״̬"};
	int widths[]={100, 98, 70, 55};
	LV_COLUMN lvc;
	lvc.mask = LVCF_FMT | LVCF_WIDTH | LVCF_TEXT | LVCF_SUBITEM;
	lvc.fmt = LVCFMT_LEFT;
	for(int i = 0; i < 4; i++) 
	{//�������
		lvc.pszText = szColumn[i];
		lvc.cx = widths[i];
		lvc.iSubItem = i;
		((CListCtrl* )GetDlgItem(IDC_LIST_RECORD))->InsertColumn(i, &lvc);
	}
}

void CTabpageBorrow::OnBnClickedRadioFail()
{
	// TODO: Add your control notification handler code here
	mApprovalPass = FALSE;
	HBITMAP hLight = (HBITMAP)mBmpFail.GetSafeHandle();
	mApprovalLight->SetBitmap(hLight);
}

void CTabpageBorrow::OnBnClickedRadioPass()
{
	// TODO: Add your control notification handler code here
	mApprovalPass = TRUE;
	HBITMAP hLight = (HBITMAP)mBmpPass.GetSafeHandle();
	mApprovalLight->SetBitmap(hLight);
}

void CTabpageBorrow::DoIDInput(CString id)
{
	CPoliceman* policeman = CPolicemanManager::GetInstance().GetPolicemanById(id);
	if (policeman)
	{
		mIDPass = TRUE;
		HBITMAP hLight = (HBITMAP)mBmpPass.GetSafeHandle();
		mIDLight->SetBitmap(hLight);

		HBITMAP hHead = (HBITMAP)mBmpHead.GetSafeHandle();
		mHeadPhoto->SetBitmap(hHead);		
		((CStatic*)GetDlgItem(IDC_STATIC_BASE_INFO))->SetWindowTextW(L"������Ϣ��"+id);
		mNameValue = L"������   " + policeman->mName;
		mSexValue = L"�Ա�   " + policeman->mSex;
		mPoliceIdValue = L"���ţ�   " + policeman->mNumber;
		mRankValue = L"���Σ�   " + policeman->mRank;	

		UpdateData(FALSE);
	}
	else
	{
		mIDPass = FALSE;

		HBITMAP hLight = (HBITMAP)mBmpFail.GetSafeHandle();
		((CStatic*)GetDlgItem(IDC_STATIC_ID_LIGHT))->SetBitmap(hLight);
		HBITMAP hHead = (HBITMAP)mBmpHeadDef.GetSafeHandle();
		mHeadPhoto->SetBitmap(hHead);		
		((CStatic*)GetDlgItem(IDC_STATIC_BASE_INFO))->SetWindowTextW(L"������Ϣ�����޴���");

		mNameValue = L"������   ";
		mSexValue = L"�Ա�   ";
		mPoliceIdValue = L"���ţ�   ";
		mRankValue = L"���Σ�   ";

		UpdateData(FALSE);
	}
}

void CTabpageBorrow::DoGunIDInput(CString gunId)
{
	CGun* gun = CGunManager::GetInstance().GetGunById(gunId);
	if (gun)
	{
		mGunPass = TRUE;
		((CStatic*)GetDlgItem(IDC_STATIC_GUN_INFO))->SetWindowTextW(L"ǹ֧��Ϣ��"+gunId);
		HBITMAP hGunPhoto = (HBITMAP)mBmpGun.GetSafeHandle();
		mGunPhoto->SetBitmap(hGunPhoto);

		HBITMAP hLight = (HBITMAP)mBmpPass.GetSafeHandle();
		mGunLight->SetBitmap(hLight);

		mGunStyleValue = L"ǹ֧�ͺţ�  " + gun->mStyle;
		mGunCountValue = L"ǹ֧������  1" ;
		mBulletStyleValue = L"�ӵ��ͺţ�  " + gun->mBullet;
		mBulletCountValue.Format(L"�ӵ�������  %d", gun->mCountBullet);
		UpdateData(FALSE);
	}
	else
	{
		mGunPass = FALSE;
		((CStatic*)GetDlgItem(IDC_STATIC_GUN_INFO))->SetWindowTextW(L"ǹ֧��Ϣ����δ���");
		mGunPhoto->SetBitmap(NULL);

		HBITMAP hLight = (HBITMAP)mBmpFail.GetSafeHandle();
		mGunLight->SetBitmap(hLight);

		mGunStyleValue = L"ǹ֧�ͺţ�  ";
		mGunCountValue = L"ǹ֧������  1" ;
		mBulletStyleValue = L"�ӵ��ͺţ�  ";
		mBulletCountValue = L"�ӵ�������  ";
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


void CTabpageBorrow::OnBnClickedButtonOk()
{
	// TODO: Add your control notification handler code here
	do{
		if (!mApprovalPass)
		{
			::AfxMessageBox(L"�쵼����δͨ������������ã�");
			break;
		}
	
		if (!mIDPass)
		{
			::AfxMessageBox(L"���֤��Ч����������ã�");
			break;
		}
		if (!mGunPass)
		{
			::AfxMessageBox(L"ǹ֧δ��⣬��������ã�");
			break;
		}

		//DoBorrowGun();
	}while (0);
}
