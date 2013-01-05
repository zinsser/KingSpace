
// GunManagerDemoDlg.cpp : implementation file
//

#include "stdafx.h"
#include "GunManagerDemo.h"
#include "GunManagerDemoDlg.h"
#include "afxdialogex.h"

#include "TabpageInfo.h"
#include "TabpageLend.h"
#include "TabpageBorrow.h"
#include "TabpageInput.h"
#include "TabpageExport.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// CAboutDlg dialog used for App About

class CAboutDlg : public CDialogEx
{
public:
	CAboutDlg();

// Dialog Data
	enum { IDD = IDD_ABOUTBOX };

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

// Implementation
protected:
	DECLARE_MESSAGE_MAP()
};

CAboutDlg::CAboutDlg() : CDialogEx(CAboutDlg::IDD)
{
}

void CAboutDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CAboutDlg, CDialogEx)
END_MESSAGE_MAP()


// CGunManagerDemoDlg dialog
CGunManagerDemoDlg::CGunManagerDemoDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(CGunManagerDemoDlg::IDD, pParent)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

CGunManagerDemoDlg::~CGunManagerDemoDlg()
{
	for (vector<CTabpageInfo*>::iterator iter = mPageItems.begin();
		iter != mPageItems.end(); ++iter)
	{
		delete *iter;
	}

	mPageItems.clear();
}

void CGunManagerDemoDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_OP_STAGE, mTabCtrlOpStage);
}

BEGIN_MESSAGE_MAP(CGunManagerDemoDlg, CDialogEx)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_NOTIFY(TCN_SELCHANGE, IDC_OP_STAGE, &CGunManagerDemoDlg::OnTcnSelchangeOpStage)
END_MESSAGE_MAP()


// CGunManagerDemoDlg message handlers
BOOL CGunManagerDemoDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	// Add "About..." menu item to system menu.

	// IDM_ABOUTBOX must be in the system command range.
	ASSERT((IDM_ABOUTBOX & 0xFFF0) == IDM_ABOUTBOX);
	ASSERT(IDM_ABOUTBOX < 0xF000);

	CMenu* pSysMenu = GetSystemMenu(FALSE);
	if (pSysMenu != NULL)
	{
		BOOL bNameValid;
		CString strAboutMenu;
		bNameValid = strAboutMenu.LoadString(IDS_ABOUTBOX);
		ASSERT(bNameValid);
		if (!strAboutMenu.IsEmpty())
		{
			pSysMenu->AppendMenu(MF_SEPARATOR);
			pSysMenu->AppendMenu(MF_STRING, IDM_ABOUTBOX, strAboutMenu);
		}
	}

	// Set the icon for this dialog.  The framework does this automatically
	//  when the application's main window is not a dialog
	SetIcon(m_hIcon, TRUE);			// Set big icon
	SetIcon(m_hIcon, FALSE);		// Set small icon

	mPageItems.push_back(new CTabpageInfo(new CTabpageLend(), mPageItems.size(), L"归还枪支", CTabpageLend::IDD));
	mPageItems.push_back(new CTabpageInfo(new CTabpageBorrow(), mPageItems.size(), L"枪支借用", CTabpageBorrow::IDD));
	mPageItems.push_back(new CTabpageInfo(new CTabpageInput(), mPageItems.size(), L"信息录入", CTabpageInput::IDD));
	mPageItems.push_back(new CTabpageInfo(new CTabpageExport(), mPageItems.size(), L"数据导出", CTabpageExport::IDD));

	for (vector<CTabpageInfo*>::iterator iter = mPageItems.begin();
		iter != mPageItems.end(); ++iter)
	{
		(*iter)->CreatePage(&mTabCtrlOpStage);
	}

	OnInitTabpage();
	ShowWindow(SW_MAXIMIZE);
	return TRUE;  // return TRUE  unless you set the focus to a control
}

void CGunManagerDemoDlg::OnSysCommand(UINT nID, LPARAM lParam)
{
	if ((nID & 0xFFF0) == IDM_ABOUTBOX)
	{
		CAboutDlg dlgAbout;
		dlgAbout.DoModal();
	}
	else
	{
		CDialogEx::OnSysCommand(nID, lParam);
	}
}

// If you add a minimize button to your dialog, you will need the code below
//  to draw the icon.  For MFC applications using the document/view model,
//  this is automatically done for you by the framework.

void CGunManagerDemoDlg::OnPaint()
{
	if (IsIconic())
	{
		CPaintDC dc(this); // device context for painting

		SendMessage(WM_ICONERASEBKGND, reinterpret_cast<WPARAM>(dc.GetSafeHdc()), 0);

		// Center icon in client rectangle
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// Draw the icon
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialogEx::OnPaint();
	}
}

// The system calls this function to obtain the cursor to display while the user drags
//  the minimized window.
HCURSOR CGunManagerDemoDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}

void CGunManagerDemoDlg::OnInitTabpage()
{
	UINT defaultIndex = 0;
	mTabCtrlOpStage.SetCurSel(defaultIndex);
	ShowPage(defaultIndex);
}

void CGunManagerDemoDlg::OnTcnSelchangeOpStage(NMHDR *pNMHDR, LRESULT *pResult)
{
	ShowPage(mTabCtrlOpStage.GetCurSel());
	*pResult = 0;
}

void CGunManagerDemoDlg::ShowPage(int index)
{
	for (vector<CTabpageInfo*>::iterator iter = mPageItems.begin();
		iter != mPageItems.end(); ++iter)
	{
		(*iter)->ShowPage((*iter)->GetIndex() == index ? SW_SHOW : SW_HIDE);
	}
}

