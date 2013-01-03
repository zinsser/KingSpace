#pragma once

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
};
