#include "StdAfx.h"
#include "TabpageInfo.h"


CTabpageInfo::CTabpageInfo(CDialogEx* pDialog, int index, CString caption, UINT idd)
	:mDialog(pDialog), mIndex(index), mCaption(caption), mIDD(idd)
{
}

CTabpageInfo::~CTabpageInfo(void)
{
	if (mDialog)
	{
		delete mDialog;
		mDialog = NULL;
	}
}

BOOL CTabpageInfo::CreatePage(CWnd* tabCtrl)
{
	BOOL bRet = FALSE;
	if (mDialog)
	{
		bRet = mDialog->Create(mIDD, tabCtrl);

		CRect rs;
		tabCtrl->GetClientRect(&rs);
		rs.top += 30;
		rs.bottom -= 1;
		rs.left -= 2;
		rs.right -= 1;
	
		mDialog->SetParent(tabCtrl);
		mDialog->MoveWindow(rs);

		((CTabCtrl*)tabCtrl)->InsertItem(mIndex, mCaption);
	}

	return bRet;
}

BOOL CTabpageInfo::ShowPage(int nCmdShow)
{
	BOOL bRet = FALSE;
	if (mDialog)
	{
		bRet = mDialog->ShowWindow(nCmdShow);
	}
	return bRet;
}
