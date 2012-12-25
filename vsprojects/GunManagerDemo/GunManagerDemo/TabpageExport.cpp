// TabpageExport.cpp : implementation file
//

#include "stdafx.h"
#include "GunManagerDemo.h"
#include "TabpageExport.h"
#include "afxdialogex.h"


// CTabpageExport dialog

IMPLEMENT_DYNAMIC(CTabpageExport, CDialogEx)

CTabpageExport::CTabpageExport(CWnd* pParent, CTabCtrl* pTabContainer)
	: CTabpageBase(CTabpageExport::IDD, L"Export", pParent, pTabContainer)
{
}

CTabpageExport::~CTabpageExport()
{
}

void CTabpageExport::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(CTabpageExport, CDialogEx)
END_MESSAGE_MAP()


// CTabpageExport message handlers
