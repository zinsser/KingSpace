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
	ON_BN_CLICKED(IDC_BUTTON_OK, &CTabpageBorrow::OnBnClickedButtonOk)
END_MESSAGE_MAP()


// CTabpageBorrow message handlers
BOOL CTabpageBorrow::OnInitDialog()
{
	CDialogEx::OnInitDialog();
	InitControlPtrs();
	InitListCtrl();
	return TRUE;  // return TRUE unless you set the focus to a control
	// EXCEPTION: OCX Property Pages should return FALSE
}

void CTabpageBorrow::InitListCtrl( )
{
    //设置图标
//    m_imgNormal.Create( MAKEINTRESOURCE(IDB_BITMAP_NORMAL),
//        48, 48, RGB( 0, 0, 0 ) );
//    m_imgSmall.Create( MAKEINTRESOURCE(IDB_BITMAP_SMALL),
        //16, 16, RGB( 0, 0, 0 ) );
//    m_ListBorrowRecord->SetImageList( &m_imgNormal, LVSIL_NORMAL );
    //m_ListBorrowRecord->SetImageList( &m_imgSmall, LVSIL_SMALL );
    //初始化列
    m_ListBorrowRecord->InsertColumn( 0, L"名称", LVCFMT_LEFT, 150 );
    m_ListBorrowRecord->InsertColumn( 1, L"长度", LVCFMT_LEFT, 150 );

    CString strPath = L"C:\\*.*";
    CFileFind find;
    BOOL bFind = find.FindFile( strPath );
    while( bFind )
    {
        bFind = find.FindNextFile( );
        if( find.IsDirectory( ) )
        {   //插入项
            m_ListBorrowRecord->InsertItem( 0, find.GetFileName(), 0 );
        }
        else
        {   //插入项
            int nItem = m_ListBorrowRecord->InsertItem( 0, find.GetFileName(), 1 );
            DWORD dwLen = find.GetLength( );
            CString strLen;
            strLen.Format( L"%d", dwLen );
            //设置SubItem的字符串
            m_ListBorrowRecord->SetItemText( nItem, 1, strLen );
        }
    }
    find.Close( );
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
/*
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
		m_ListBorrowRecord->InsertColumn(i, &lvc);
	}
}
*/
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
		((CStatic*)GetDlgItem(IDC_STATIC_BASE_INFO))->SetWindowTextW(L"基本信息："+id);

		mNameValue = L"姓名：   查无此人";
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
		mGunPass = TRUE;
		((CStatic*)GetDlgItem(IDC_STATIC_GUN_INFO))->SetWindowTextW(L"枪支信息："+gunId);

		HBITMAP hLight = (HBITMAP)mBmpPass.GetSafeHandle();
		mGunLight->SetBitmap(hLight);

		HBITMAP hGunPhoto = (HBITMAP)mBmpGun.GetSafeHandle();
		mGunPhoto->SetBitmap(hGunPhoto);

		mGunStyleValue = L"枪支型号：  " + gun->mStyle;
		mGunCountValue = L"枪支数量：  1" ;
		mBulletStyleValue = L"子弹型号：  " + gun->mBullet;
		mBulletCountValue.Format(L"子弹数量：  %d", gun->mCountBullet);
		UpdateData(FALSE);
	}
	else
	{
		mGunPass = FALSE;
		((CStatic*)GetDlgItem(IDC_STATIC_GUN_INFO))->SetWindowTextW(L"枪支信息："+gunId);
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

		//DoBorrowGun();
		::AfxMessageBox(L"    借用成功！\n是否打印回执?（是/否）");
	}while (0);
}
