// TabpageExport.cpp : implementation file
//

#include "stdafx.h"
#include "GunManagerDemo.h"
#include "TabpageExport.h"
#include "afxdialogex.h"


// CTabpageExport dialog

IMPLEMENT_DYNAMIC(CTabpageExport, CDialogEx)

CTabpageExport::CTabpageExport(CWnd* pParent, CTabCtrl* pTabContainer)
//	: CTabpageBase(CTabpageExport::IDD, L"表格导出", pParent, pTabContainer)
	: CDialogEx(CTabpageExport::IDD, NULL)
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


BOOL CTabpageExport::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	// TODO:  Add extra initialization here

	return TRUE;  // return TRUE unless you set the focus to a control
	// EXCEPTION: OCX Property Pages should return FALSE
}
