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
#include "BorrowRecord.h"
#include "BorrowLogManager.h"

// CTabpageLend dialog
IMPLEMENT_DYNAMIC(CTabpageLend, CDialogEx)

CTabpageLend::CTabpageLend(CWnd* pParent)
	: CDialogEx(CTabpageLend::IDD, pParent)
	, mIDPass(FALSE)
	, mGunPass(FALSE)
	, mTimePass(FALSE)
	, mNameValue(_T("姓名："))
	, mSexValue(_T("性别："))
	, mRankValue(_T("警衔："))
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

	mCurrentRecord = NULL;
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
	DDX_Control(pDX, IDC_LIST_BORROW_LOG, mListBorrowLog);
	DDX_Control(pDX, IDC_DATETIMEPICKER2, mEndDateCtrl);
	DDX_Control(pDX, IDC_DATETIMEPICKER4, mEndTimeCtrl);
	DDX_Control(pDX, IDC_DATETIMEPICKER1, mExpectDateCtrl);
	DDX_Control(pDX, IDC_DATETIMEPICKER3, mExpectTimeCtrl);
}


BEGIN_MESSAGE_MAP(CTabpageLend, CDialogEx)
	ON_BN_CLICKED(IDC_BUTTON_RETURN_OK, &CTabpageLend::OnBnClickedButtonReturnOk)
END_MESSAGE_MAP()


// CTabpageLend message handlers

void CTabpageLend::InitListCtrl( )
{
    //初始化列
    mListBorrowLog.InsertColumn(0, L"枪支型号", LVCFMT_LEFT, 150 );
    mListBorrowLog.InsertColumn(1, L"借用日期", LVCFMT_LEFT, 250 );
	mListBorrowLog.InsertColumn(2, L"归还日期", LVCFMT_LEFT, 250 );
}

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

void CTabpageLend::LoadBorrowRecords(CString id)
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
			int nItem = mListBorrowLog.InsertItem(0, gun->mStyle);
			mListBorrowLog.SetItemText(nItem, 1, CBorrowRecord::FormatTimeString((*iter)->mStartTime));
			if ((*iter)->mEndTime == 0)
			{
				mListBorrowLog.SetItemText(nItem, 2, L"尚未归还");
			}
			else
			{
				mListBorrowLog.SetItemText(nItem, 2, CBorrowRecord::FormatTimeString((*iter)->mEndTime));
			}
		}
	}
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
		((CStatic*)GetDlgItem(IDC_STATIC_BASE_INFO))->SetWindowTextW(L"基本信息："+id);
		mNameValue = L"姓名：   " + policeman->mName;
		mSexValue = L"性别：   " + policeman->mSex;
		mRankValue = L"警衔：   " + policeman->mRank;	

		LoadBorrowRecords(id);

		UpdateData(FALSE);
	}
	else
	{
		mIDPass = FALSE;

		HBITMAP hLight = (HBITMAP)mBmpFail.GetSafeHandle();
		mIDLight.SetBitmap(hLight);
		HBITMAP hHead = (HBITMAP)mBmpHeadDef.GetSafeHandle();
		mHeadPhoto.SetBitmap(hHead);		
		((CStatic*)GetDlgItem(IDC_STATIC_BASE_INFO))->SetWindowTextW(L"基本信息："+id);

		mNameValue = L"姓名：   查无此人";
		mSexValue = L"性别：   ";
		mRankValue = L"警衔：   ";

		UpdateData(FALSE);
	}
}

void CTabpageLend::UpdateTimeLight(CString gunId)
{		
	vector<CBorrowRecord*> records;
	CBorrowLogManager::GetInstance().GetRecordByGunId(gunId, records);
	for (vector<CBorrowRecord*>::const_iterator iter = records.begin();
		iter != records.end(); ++iter)
	{
		if ((*iter)->mEndTime == 0)
		{
			mCurrentRecord = *iter;
			break;
		}
	}
	__time64_t expectSeconds = 0;
	CTime now = CTime::GetCurrentTime();

	if (mCurrentRecord){
		expectSeconds = mCurrentRecord->mExpectTime;
		mCurrentRecord->mEndTime = now.GetTime();
	}

	CTime expectTime(expectSeconds);
	mExpectDateCtrl.SetTime(&expectTime);
	mExpectTimeCtrl.SetTime(&expectTime);

	mEndDateCtrl.SetTime(&now);
	mEndTimeCtrl.SetTime(&now);

	if (expectTime.GetTime() > now.GetTime())
	{
		HBITMAP hTimeLight = (HBITMAP)mBmpPass.GetSafeHandle();
		mTimeLight.SetBitmap(hTimeLight);
	}else{
		HBITMAP hTimeLight = (HBITMAP)mBmpFail.GetSafeHandle();
		mTimeLight.SetBitmap(hTimeLight);
	}
}

void CTabpageLend::DoGunIDInput(CString gunId)
{
	CGun* gun = CGunManager::GetInstance().GetGunById(gunId);
	if (gun)
	{
		mGunPass = TRUE;
		((CStatic*)GetDlgItem(IDC_STATIC_GUN_INFO))->SetWindowTextW(L"枪支信息："+gunId);
		HBITMAP hGunPhoto = (HBITMAP)mBmpGun.GetSafeHandle();
		mGunPhoto.SetBitmap(hGunPhoto);

		HBITMAP hLight = (HBITMAP)mBmpPass.GetSafeHandle();
		mGunLight.SetBitmap(hLight);

		mGunStyleValue = L"枪支型号：  " + gun->mStyle;
		mGunCountValue = L"枪支数量：  1" ;
		mBulletStyleValue = L"子弹型号：  " + gun->mBullet;
		mBulletCountValue.Format(L"子弹数量：  %d", gun->mCountBullet);

		UpdateTimeLight(gunId);

		UpdateData(FALSE);
	}
	else
	{
		mGunPass = FALSE;
		((CStatic*)GetDlgItem(IDC_STATIC_GUN_INFO))->SetWindowTextW(L"枪支信息："+gunId);
		mGunPhoto.SetBitmap(NULL);

		HBITMAP hLight = (HBITMAP)mBmpFail.GetSafeHandle();
		mGunLight.SetBitmap(hLight);

		mGunStyleValue = L"枪支型号：  尚未入库";
		mGunCountValue = L"枪支数量：  1" ;
		mBulletStyleValue = L"子弹型号：  ";
		mBulletCountValue = L"子弹数量：  ";
		UpdateData(FALSE);
	}
}


BOOL CTabpageLend::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	// TODO:  Add extra initialization here
	InitListCtrl();
	return TRUE;  // return TRUE unless you set the focus to a control
	// EXCEPTION: OCX Property Pages should return FALSE
}


void CTabpageLend::OnBnClickedButtonReturnOk()
{
	// TODO: Add your control notification handler code here
	if (mCurrentRecord)
	{
		mCurrentRecord->mEndTime = CTime::GetCurrentTime().GetTime();
		LoadBorrowRecords(mCurrentRecord->mId);
		::AfxMessageBox(L"还枪成功！");
		mCurrentRecord = NULL;
	}
}
