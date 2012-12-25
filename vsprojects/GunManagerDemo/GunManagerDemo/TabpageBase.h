#pragma once


// CTabpageBase dialog
class CTabpageBase : public CDialogEx
{
public:
	CTabpageBase(UINT idd, LPWSTR title, CWnd* pParent, CTabCtrl* pTabContainer);
	virtual ~CTabpageBase();

public:
	UINT   GetIndex();

private:
	void CreatePageContent(UINT idd, LPWSTR title, CWnd* pWndParent, CTabCtrl* pTabContainer);

private:
	static UINT mMaxItem;
	UINT mIndex;
};
