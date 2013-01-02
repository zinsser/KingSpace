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

public:
	static UINT mMaxItem;
private:
	UINT mIndex;
};
