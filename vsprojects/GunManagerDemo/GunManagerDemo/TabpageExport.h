#pragma once
#include "afxwin.h"
#include "afxcmn.h"

// CTabpageExport dialog

class CTabpageExport : public CDialogEx
{
	DECLARE_DYNAMIC(CTabpageExport)

public:
	CTabpageExport(CWnd* pParent = NULL);   // standard constructor
	virtual ~CTabpageExport();

// Dialog Data
	enum { IDD = IDD_TABPAGE_EXPORT };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

	DECLARE_MESSAGE_MAP()

private:
	void UpdatePersonData();
	void UpdateBulletLibData();
	void ExportExcelFromList(CListCtrl* listCtrl);
public:
	virtual BOOL OnInitDialog();
	afx_msg void OnLbnSelchangeList2();
	afx_msg void OnBnClickedButtonExportPerson();

	CListCtrl mListPerson;
	CListCtrl mListGunBorrow;
	CListCtrl mListBulletBorrow;
	CListCtrl mListBulletLib;
	afx_msg void OnBnClickedButtonBulletLib();
	afx_msg void OnBnClickedButtonGunBorrow();
	afx_msg void OnBnClickedButtonBulletBorrow();
};
