// TabpageBase.cpp : implementation file
//

#include "stdafx.h"
#include "GunManagerDemo.h"
#include "TabpageBase.h"
#include "afxdialogex.h"


// CTabpageBase dialog
UINT CTabpageBase::mMaxItem = 0;
CTabpageBase::CTabpageBase(UINT idd, LPWSTR title, CWnd* pParent, CTabCtrl* pTabContainer)
	: CDialogEx(idd, pParent), mIndex(0)
{
	CreatePageContent(idd, title, pParent, pTabContainer);
}

CTabpageBase::~CTabpageBase()
{
}

// CTabpageBase message handlers
void CTabpageBase::CreatePageContent(UINT idd, LPWSTR title, CWnd* pWndParent, CTabCtrl* pTabContainer)
{
	this->Create(idd, pWndParent);

	CRect rs;
	pWndParent->GetClientRect(&rs);
	rs.top += 30;
	rs.bottom -= 1;
	rs.left -= 2;
	rs.right -= 1;

	SetParent(pWndParent);
	MoveWindow(rs);

	mIndex = mMaxItem;
	pTabContainer->InsertItem(mMaxItem++, title);
}

UINT CTabpageBase::GetIndex()
{
	return mIndex;
}
