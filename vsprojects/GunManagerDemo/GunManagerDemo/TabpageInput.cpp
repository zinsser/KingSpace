// TabpageInput.cpp : implementation file
//

#include "stdafx.h"
#include "GunManagerDemo.h"
#include "TabpageInput.h"
#include "afxdialogex.h"


// CTabpageInput dialog

IMPLEMENT_DYNAMIC(CTabpageInput, CDialogEx)

CTabpageInput::CTabpageInput(CWnd* pParent)
	: CDialogEx(CTabpageInput::IDD, pParent)
{

}

CTabpageInput::~CTabpageInput()
{
}

void CTabpageInput::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(CTabpageInput, CDialogEx)
END_MESSAGE_MAP()


// CTabpageInput message handlers
