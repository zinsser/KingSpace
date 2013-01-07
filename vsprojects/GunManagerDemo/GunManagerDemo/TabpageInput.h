#pragma once
#include "afxwin.h"

// CTabpageInput dialog

class CTabpageInput : public CDialogEx
{
	DECLARE_DYNAMIC(CTabpageInput)

public:
	CTabpageInput(CWnd* pParent = NULL);   // standard constructor
	virtual ~CTabpageInput();

// Dialog Data
	enum { IDD = IDD_TAGPAGE_INPUT };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

	DECLARE_MESSAGE_MAP()
private:
	
	afx_msg void OnBnClickedButtonOk();
	afx_msg void OnBnClickedButtonCancel();
	afx_msg void OnPaint();
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
	virtual BOOL OnInitDialog();
	
private:
	CString mEditNameValue;
	CString mEditID;
	CString mEditPhoneValue;
	CString mEditAgency;
	CString mEditOfficate;
	CString mEditPoliceNumber;
	CString mEditPoliceRank;
	CString mEditHolderID;
	CString mEditJob;

	CBrush   mBlueBrush;

	CComboBox mComboSex;
	CComboBox mComboInstructor;
	CComboBox mComboStudent;
};
