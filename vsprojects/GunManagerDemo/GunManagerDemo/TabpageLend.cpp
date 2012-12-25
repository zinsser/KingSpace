// TabpageLend.cpp : implementation file
//

#include "stdafx.h"
#include "GunManagerDemo.h"
#include "TabpageLend.h"
#include "afxdialogex.h"


// CTabpageLend dialog
IMPLEMENT_DYNAMIC(CTabpageLend, CDialogEx)

CTabpageLend::CTabpageLend(CWnd* pParent, CTabCtrl* pTabContainer)
	: CTabpageBase(CTabpageLend::IDD, L"Lend", pParent, pTabContainer)
{
}

CTabpageLend::~CTabpageLend()
{
}

void CTabpageLend::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(CTabpageLend, CDialogEx)
END_MESSAGE_MAP()


// CTabpageLend message handlers
