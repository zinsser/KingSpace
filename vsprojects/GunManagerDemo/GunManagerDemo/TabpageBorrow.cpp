// TabpageBorrow.cpp : implementation file
//

#include "stdafx.h"
#include "GunManagerDemo.h"
#include "TabpageBorrow.h"
#include "afxdialogex.h"


// CTabpageBorrow dialog

IMPLEMENT_DYNAMIC(CTabpageBorrow, CDialogEx)

CTabpageBorrow::CTabpageBorrow(CWnd* pParent, CTabCtrl* pTabContainer)
	: CTabpageBase(CTabpageBorrow::IDD, L"Borrow", pParent, pTabContainer)
	, m_bApprovalPass(FALSE)
{
}

CTabpageBorrow::~CTabpageBorrow()
{
}

void CTabpageBorrow::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Radio(pDX, IDC_RADIO_PASS, m_bApprovalPass);
}


BEGIN_MESSAGE_MAP(CTabpageBorrow, CDialogEx)
	ON_BN_CLICKED(IDC_RADIO_PASS, &CTabpageBorrow::OnBnClickedRadioPass)
	ON_WM_CTLCOLOR()
	ON_BN_CLICKED(IDC_RADIO_FAIL, &CTabpageBorrow::OnBnClickedRadioFail)
END_MESSAGE_MAP()


// CTabpageBorrow message handlers



BOOL CTabpageBorrow::OnInitDialog()
{
	CTabpageBase::OnInitDialog();

	m_RedBrush.CreateSolidBrush(RGB(255, 0, 0));
	m_GreenBrush.CreateSolidBrush(RGB(0, 255, 0));
	return TRUE;  // return TRUE unless you set the focus to a control
	// EXCEPTION: OCX Property Pages should return FALSE
}


void CTabpageBorrow::OnBnClickedRadioFail()
{
	// TODO: Add your control notification handler code here
	((CButton *)GetDlgItem(IDC_RADIO_PASS))->RedrawWindow();
	((CButton *)GetDlgItem(IDC_RADIO_FAIL))->RedrawWindow();
	m_bApprovalPass = !m_bApprovalPass;
	TRACE("m_bApprovalPass:"+m_bApprovalPass);
}

void CTabpageBorrow::OnBnClickedRadioPass()
{
	// TODO: Add your control notification handler code here
	((CButton *)GetDlgItem(IDC_RADIO_PASS))->RedrawWindow();
	((CButton *)GetDlgItem(IDC_RADIO_FAIL))->RedrawWindow();
	m_bApprovalPass = !m_bApprovalPass;
	TRACE("m_bApprovalPass:"+m_bApprovalPass);
}

HBRUSH CTabpageBorrow::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CTabpageBase::OnCtlColor(pDC, pWnd, nCtlColor);
    enum STATIC_BKCOLOR
    {
        NULL_COLOR,
        RED_COLOR,
        GREEN_COLOR,
    };
	STATIC_BKCOLOR static_BkColor = NULL_COLOR;
	HBRUSH return_hbr = hbr;
	if(pWnd->GetDlgCtrlID()== IDC_RADIO_PASS
		|| pWnd->GetDlgCtrlID() == IDC_RADIO_FAIL)
	{
		if (!m_bApprovalPass)
		{
			static_BkColor = RED_COLOR;
		}else{
			static_BkColor = GREEN_COLOR;
		}
	}else{
		static_BkColor = RED_COLOR;
	}
    switch (static_BkColor)
    {
    case RED_COLOR:
        pDC->SetTextColor(RGB(255,255,255));
        pDC->SetBkColor(RGB(255,0,0));
        return_hbr = (HBRUSH)m_RedBrush.GetSafeHandle();
        break;
	case GREEN_COLOR:
        pDC->SetTextColor(RGB(255,255,255));
        pDC->SetBkColor(RGB(0,255,0));
        return_hbr = (HBRUSH)m_GreenBrush.GetSafeHandle();
		break;
	case NULL_COLOR:
	default:
        return_hbr = hbr;
		break;
	}
	return return_hbr;
}
