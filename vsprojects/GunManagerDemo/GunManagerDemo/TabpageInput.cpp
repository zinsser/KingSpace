// TabpageInput.cpp : implementation file
//

#include "stdafx.h"
#include "GunManagerDemo.h"
#include "TabpageInput.h"
#include "afxdialogex.h"

#include "PolicemanManager.h"
#include "Policeman.h"

// CTabpageInput dialog

IMPLEMENT_DYNAMIC(CTabpageInput, CDialogEx)

CTabpageInput::CTabpageInput(CWnd* pParent)
	: CDialogEx(CTabpageInput::IDD, pParent)
	, mEditNameValue(_T(""))
	, mEditID(_T(""))
	, mEditPhoneValue(_T(""))
	, mEditAgency(_T(""))
	, mEditOfficate(_T(""))
	, mEditPoliceNumber(_T(""))
	, mEditPoliceRank(_T(""))
	, mEditHolderID(_T(""))
	, mEditJob(_T(""))
{

}

CTabpageInput::~CTabpageInput()
{
}

void CTabpageInput::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Text(pDX, IDC_EDIT_NAME, mEditNameValue);
	DDX_Text(pDX, IDC_EDIT_ID, mEditID);
	DDX_Text(pDX, IDC_EDIT_PHONE, mEditPhoneValue);
	DDX_Text(pDX, IDC_EDIT_AGENCY, mEditAgency);
	DDX_Text(pDX, IDC_EDIT_OFFICATE, mEditOfficate);
	DDX_Text(pDX, IDC_EDIT_POLICE_NUMBER, mEditPoliceNumber);
	DDX_Text(pDX, IDC_EDIT_POLICE_RANK, mEditPoliceRank);
	DDX_Text(pDX, IDC_EDIT_HOLDER_ID, mEditHolderID);
	DDX_Text(pDX, IDC_EDIT_JOB, mEditJob);
	DDX_Control(pDX, IDC_COMBO_SEX, mComboSex);
	DDX_Control(pDX, IDC_COMBO_INSTRUCTOR, mComboInstructor);
	DDX_Control(pDX, IDC_COMBO_STUDENT, mComboStudent);
}


BEGIN_MESSAGE_MAP(CTabpageInput, CDialogEx)
	ON_BN_CLICKED(IDC_BUTTON_OK, &CTabpageInput::OnBnClickedButtonOk)
	ON_BN_CLICKED(IDC_BUTTON_CANCEL, &CTabpageInput::OnBnClickedButtonCancel)
END_MESSAGE_MAP()


// CTabpageInput message handlers


void CTabpageInput::OnBnClickedButtonOk()
{
	// TODO: Add your control notification handler code here
	UpdateData(TRUE);

	CPolicemanManager::GetInstance().AddPoliceman(mEditID, mEditPoliceNumber, mEditNameValue, 
		L"男", mEditPoliceRank, mEditAgency, mEditOfficate, mEditJob, mEditHolderID, 
		mEditPhoneValue, TRUE, FALSE);
}


void CTabpageInput::OnBnClickedButtonCancel()
{
	// TODO: Add your control notification handler code here
	mEditID = L"";
	mEditPoliceNumber = L""; 
	mEditNameValue = L""; 
	mEditPoliceRank = L""; 
	mEditAgency = L""; 
	mEditOfficate = L"";
	mEditJob = L"";
	mEditHolderID = L"";
	mEditPhoneValue = L"";

	UpdateData(FALSE);
}


BOOL CTabpageInput::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	// TODO:  Add extra initialization here
	mComboSex.AddString(L"请选择性别");
	mComboSex.AddString(L"男");
	mComboSex.AddString(L"女");
	mComboSex.SetCurSel(0);

	mComboInstructor.AddString(L"请选择");
	mComboInstructor.AddString(L"是");
	mComboInstructor.AddString(L"否");
	mComboInstructor.SetCurSel(0);

	mComboStudent.AddString(L"请选择");
	mComboStudent.AddString(L"是");
	mComboStudent.AddString(L"否");
	mComboStudent.SetCurSel(0);
	return TRUE;  // return TRUE unless you set the focus to a control
	// EXCEPTION: OCX Property Pages should return FALSE
}
