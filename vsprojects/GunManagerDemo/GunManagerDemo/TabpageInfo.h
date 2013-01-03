#pragma once
class CTabpageInfo
{
public:
	CTabpageInfo(CDialogEx* pDialog, int index, CString caption, UINT idd);
	~CTabpageInfo(void);

public:
	int GetIndex(){return mIndex;}
	BOOL CreatePage(CWnd* tabCtrl);
	BOOL ShowPage(int nCmdShow);
	
private:
	CDialogEx* mDialog;
	int mIndex;
	CString mCaption;
	UINT mIDD;
};

