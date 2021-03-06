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
#include "BorrowRecord.h"
#include "BorrowLogManager.h"

// CTabpageBorrow dialog

IMPLEMENT_DYNAMIC(CTabpageBorrow, CDialogEx)

CTabpageBorrow::CTabpageBorrow(CWnd* pParent)
	: CDialogEx(CTabpageBorrow::IDD, pParent)
	, mApprovalPass(FALSE)
	, mIDPass(FALSE)
	, mGunPass(FALSE)
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

	mCurrentPoliceman = NULL;
	mCurrentGun = NULL;
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
	DDX_Control(pDX, IDC_DATETIMEPICKER1, mExpectDateCtrl);
	DDX_Control(pDX, IDC_DATETIMEPICKER2, mExpectTimeCtrl);
	DDX_Control(pDX, IDC_STATIC_BASE_INFO, mGroupboxBaseinfo);
	DDX_Control(pDX, IDC_STATIC_GUN_INFO, mGroupboxGuninfo);
	DDX_Control(pDX, IDC_STATIC_BORROW_INFO, mGroupboxBorrowinfo);
	DDX_Control(pDX, IDC_STATIC_DOER, mGroupboxDoer);
	DDX_Control(pDX, IDC_RADIO_PASS, mRadioButtonPass);
	DDX_Control(pDX, IDC_RADIO_FAIL, mRadioButtonFail);
}


BEGIN_MESSAGE_MAP(CTabpageBorrow, CDialogEx)
	ON_BN_CLICKED(IDC_RADIO_PASS, &CTabpageBorrow::OnBnClickedRadioPass)
	ON_WM_CTLCOLOR()
	ON_BN_CLICKED(IDC_RADIO_FAIL, &CTabpageBorrow::OnBnClickedRadioFail)
	ON_BN_CLICKED(IDC_BUTTON_OK, &CTabpageBorrow::OnBnClickedButtonOk)
	ON_NOTIFY(DTN_DATETIMECHANGE, IDC_DATETIMEPICKER1, &CTabpageBorrow::OnDtnDatetimechangeDatetimepicker1)
	ON_NOTIFY(DTN_DATETIMECHANGE, IDC_DATETIMEPICKER2, &CTabpageBorrow::OnDtnDatetimechangeDatetimepicker2)
	ON_WM_PAINT()
	ON_WM_CTLCOLOR()
	ON_WM_DRAWITEM()
END_MESSAGE_MAP()


// CTabpageBorrow message handlers
BOOL CTabpageBorrow::OnInitDialog()
{
	CDialogEx::OnInitDialog();
	InitControlPtrs();
	InitListCtrl();

	COLORREF bkColor = RGB(41, 22, 111);
	COLORREF fontColor = RGB(255, 255, 255);
	mBlueBrush.CreateSolidBrush(bkColor);
	mGroupboxBaseinfo.SetBackgroundColor(bkColor, bkColor);
	mGroupboxBaseinfo.SetCatptionTextColor(fontColor);

	mGroupboxGuninfo.SetBackgroundColor(bkColor, bkColor);
	mGroupboxGuninfo.SetCatptionTextColor(fontColor);
	mGroupboxBorrowinfo.SetBackgroundColor(bkColor, bkColor);
	mGroupboxBorrowinfo.SetCatptionTextColor(fontColor);
	mGroupboxDoer.SetBackgroundColor(bkColor, bkColor);
	mGroupboxDoer.SetCatptionTextColor(fontColor);

	GetDlgItem(IDC_RADIO_PASS)->Invalidate(FALSE);
	return TRUE;  // return TRUE unless you set the focus to a control
	// EXCEPTION: OCX Property Pages should return FALSE
}

void CTabpageBorrow::InitListCtrl( )
{
    //初始化列
    m_ListBorrowRecord->InsertColumn(0, L"枪支型号", LVCFMT_LEFT, 150 );
    m_ListBorrowRecord->InsertColumn(1, L"借用日期", LVCFMT_LEFT, 250 );
	m_ListBorrowRecord->InsertColumn(2, L"归还日期", LVCFMT_LEFT, 250 );
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

void CTabpageBorrow::OnBnClickedRadioFail()
{
	mApprovalPass = FALSE;
	HBITMAP hLight = (HBITMAP)mBmpFail.GetSafeHandle();
	mApprovalLight->SetBitmap(hLight);
}

void CTabpageBorrow::OnBnClickedRadioPass()
{
	mApprovalPass = TRUE;
	HBITMAP hLight = (HBITMAP)mBmpPass.GetSafeHandle();
	mApprovalLight->SetBitmap(hLight);
}

void CTabpageBorrow::LoadBorrowRecords(CString id)
{
	vector<CBorrowRecord*> records;
	CBorrowLogManager::GetInstance().GetRecordById(id, records);
	if (records.size() > 0)
	{
		vector<CBorrowRecord*>::const_iterator iter = records.begin();
		for (; iter != records.end(); ++iter)
		{
			CString gunId = (*iter)->mGunId;

			CGun* gun = CGunManager::GetInstance().GetGunById(gunId);
			int nItem = m_ListBorrowRecord->InsertItem(0, gun->mStyle);
			
			m_ListBorrowRecord->SetItemText(nItem, 1, CBorrowRecord::FormatTimeString((*iter)->mStartTime));
			if ((*iter)->mEndTime == 0)
			{
				m_ListBorrowRecord->SetItemText(nItem, 2, L"尚未归还");
			}
			else
			{
				m_ListBorrowRecord->SetItemText(nItem, 2, CBorrowRecord::FormatTimeString((*iter)->mEndTime));
			}
		}
	}
}

void CTabpageBorrow::DoIDInput(CString id)
{
	mCurrentPoliceman = CPolicemanManager::GetInstance().GetPolicemanById(id);
	if (mCurrentPoliceman)
	{
		mIDPass = TRUE;
		HBITMAP hLight = (HBITMAP)mBmpPass.GetSafeHandle();
		mIDLight->SetBitmap(hLight);

		HBITMAP hHead = (HBITMAP)mBmpHead.GetSafeHandle();
		mHeadPhoto->SetBitmap(hHead);		
		mGroupboxBaseinfo.SetWindowTextW(L"基本信息："+id);
		mNameValue = L"姓名：   " + mCurrentPoliceman->mName;
		mSexValue = L"性别：   " + mCurrentPoliceman->mSex;
		mPoliceIdValue = L"警号：   " + mCurrentPoliceman->mNumber;
		mRankValue = L"警衔：   " + mCurrentPoliceman->mRank;	

		LoadBorrowRecords(id);

		UpdateData(FALSE);
	}
	else
	{
		mIDPass = FALSE;

		HBITMAP hLight = (HBITMAP)mBmpFail.GetSafeHandle();
		((CStatic*)GetDlgItem(IDC_STATIC_ID_LIGHT))->SetBitmap(hLight);
		HBITMAP hHead = (HBITMAP)mBmpHeadDef.GetSafeHandle();
		mHeadPhoto->SetBitmap(hHead);		
		mGroupboxBaseinfo.SetWindowTextW(L"基本信息："+id);

		mNameValue = L"姓名：   查无此人";
		mSexValue = L"性别：   ";
		mPoliceIdValue = L"警号：   ";
		mRankValue = L"警衔：   ";

		UpdateData(FALSE);
	}
}

void CTabpageBorrow::DoGunIDInput(CString gunId)
{
	mCurrentGun = CGunManager::GetInstance().GetGunById(gunId);
	if (mCurrentGun)
	{
		mGunPass = TRUE;
		mGroupboxGuninfo.SetWindowTextW(L"枪支信息："+gunId);

		HBITMAP hLight = (HBITMAP)mBmpPass.GetSafeHandle();
		mGunLight->SetBitmap(hLight);

		HBITMAP hGunPhoto = (HBITMAP)mBmpGun.GetSafeHandle();
		mGunPhoto->SetBitmap(hGunPhoto);

		mGunStyleValue = L"枪支型号：  " + mCurrentGun->mStyle;
		mGunCountValue = L"枪支数量：  1" ;
		mBulletStyleValue = L"子弹型号：  " + mCurrentGun->mBullet;
		mBulletCountValue.Format(L"子弹数量：  %d", mCurrentGun->mCountBullet);
		UpdateData(FALSE);
	}
	else
	{
		mGunPass = FALSE;
		mGroupboxGuninfo.SetWindowTextW(L"枪支信息："+gunId);
		mGunPhoto->SetBitmap(NULL);

		HBITMAP hLight = (HBITMAP)mBmpFail.GetSafeHandle();
		mGunLight->SetBitmap(hLight);

		mGunStyleValue = L"枪支型号：  尚未入库";
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


void CTabpageBorrow::OnBnClickedButtonOk()
{
	// TODO: Add your control notification handler code here
	do{
		if (!mApprovalPass)
		{
			::AfxMessageBox(L"领导审批未通过，不允许借用！");
			break;
		}
	
		if (!mIDPass)
		{
			::AfxMessageBox(L"身份证无效，不允许借用！");
			break;
		}
		
		if (!mGunPass)
		{
			::AfxMessageBox(L"枪支未入库，不允许借用！");
			break;
		}

		CTime startTime = CTime::GetCurrentTime();
		CTime expectTime;
		mExpectTimeCtrl.GetTime(expectTime);
		CBorrowLogManager::GetInstance().AddRecord(new CBorrowRecord(mCurrentPoliceman->mID, mCurrentGun->mGunId, startTime.GetTime(), expectTime.GetTime(), FALSE));
		::AfxMessageBox(L"    借用成功！\n是否打印回执?（是/否）");
	}while (0);
}


void CTabpageBorrow::OnDtnDatetimechangeDatetimepicker1(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMDATETIMECHANGE pDTChange = reinterpret_cast<LPNMDATETIMECHANGE>(pNMHDR);

	CTime tm;
	mExpectDateCtrl.GetTime(tm);
	mExpectTimeCtrl.SetTime(&tm);
	*pResult = 0;
}


void CTabpageBorrow::OnDtnDatetimechangeDatetimepicker2(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMDATETIMECHANGE pDTChange = reinterpret_cast<LPNMDATETIMECHANGE>(pNMHDR);

	CTime tm;
	mExpectTimeCtrl.GetTime(tm);
	mExpectDateCtrl.SetTime(&tm);
	*pResult = 0;
}


void CTabpageBorrow::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	// TODO: Add your message handler code here
	// Do not call CDialogEx::OnPaint() for painting messages
	CRect   rect;
	GetClientRect(rect);
	dc.FillSolidRect(rect,RGB(41,22,111));   //设置为绿色背景
}


HBRUSH CTabpageBorrow::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CDialogEx::OnCtlColor(pDC, pWnd, nCtlColor);

	// TODO:  Change any attributes of the DC here
	if (nCtlColor != CTLCOLOR_EDIT)
	{
		pDC->SetBkColor(RGB(41, 22, 111));
		pDC->SetTextColor(RGB(255,255,255));
		pDC->SetBkMode(TRANSPARENT);
	}

	if (pWnd->GetDlgCtrlID() == IDC_RADIO_PASS)
	{
		pDC->SetBkColor(RGB(41, 22, 111));
		pDC->SetTextColor(RGB(255,255,255));	
	}
	// TODO:  Return a different brush if the default is not desired
	return (HBRUSH)mBlueBrush.GetSafeHandle();;
}


void CTabpageBorrow::OnDrawItem(int nIDCtl, LPDRAWITEMSTRUCT lpDrawItemStruct)
{
	// TODO: Add your message handler code here and/or call default
	if (nIDCtl == IDC_RADIO_PASS || nIDCtl == IDC_RADIO_FAIL)
	{
		CDC dc;
		RECT rc;
		dc.Attach(lpDrawItemStruct->hDC);
		dc.SetTextColor(RGB(255, 255, 255));
		dc.Detach();
	}
	CDialogEx::OnDrawItem(nIDCtl, lpDrawItemStruct);
}
