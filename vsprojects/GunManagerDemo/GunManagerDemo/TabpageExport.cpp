// TabpageExport.cpp : implementation file
//

#include "stdafx.h"
#include "GunManagerDemo.h"
#include "TabpageExport.h"
#include "afxdialogex.h"

#include "Policeman.h"
#include "PolicemanManager.h"
#include "ExcelCtrl.h"

#include <vector>
using namespace std;

// CTabpageExport dialog

IMPLEMENT_DYNAMIC(CTabpageExport, CDialogEx)

CTabpageExport::CTabpageExport(CWnd* pParent)
	: CDialogEx(CTabpageExport::IDD, NULL)
{
}

CTabpageExport::~CTabpageExport()
{
}

void CTabpageExport::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_LIST_PERSON, mListPerson);
	DDX_Control(pDX, IDC_LIST_GUN_BORROW, mListGunBorrow);
	DDX_Control(pDX, IDC_LIST_BULLET_BORROW, mListBulletBorrow);
	DDX_Control(pDX, IDC_LIST_BULLET_LIB, mListBulletLib);
}


BEGIN_MESSAGE_MAP(CTabpageExport, CDialogEx)
	ON_BN_CLICKED(IDC_BUTTON_EXPORT_PERSON, &CTabpageExport::OnBnClickedButtonExportPerson)
	ON_BN_CLICKED(IDC_BUTTON_BULLET_LIB, &CTabpageExport::OnBnClickedButtonBulletLib)
	ON_BN_CLICKED(IDC_BUTTON_GUN_BORROW, &CTabpageExport::OnBnClickedButtonGunBorrow)
	ON_BN_CLICKED(IDC_BUTTON_BULLET_BORROW, &CTabpageExport::OnBnClickedButtonBulletBorrow)
END_MESSAGE_MAP()


// CTabpageExport message handlers


BOOL CTabpageExport::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	// TODO:  Add extra initialization here
    mListPerson.InsertColumn(0, L"���֤", LVCFMT_CENTER, 200);
    mListPerson.InsertColumn(1, L"����", LVCFMT_CENTER, 200);
	mListPerson.InsertColumn(2, L"����", LVCFMT_CENTER, 200);
    mListPerson.InsertColumn(3, L"�Ա�", LVCFMT_CENTER, 200);
    mListPerson.InsertColumn(4, L"����", LVCFMT_CENTER, 200);
	mListPerson.InsertColumn(5, L"��λ", LVCFMT_CENTER, 200);
    mListPerson.InsertColumn(6, L"ְ��", LVCFMT_CENTER, 200);
    mListPerson.InsertColumn(7, L"��λ", LVCFMT_CENTER, 200);
	mListPerson.InsertColumn(8, L"��ǹ����", LVCFMT_CENTER, 200);
	mListPerson.InsertColumn(9, L"�绰", LVCFMT_CENTER, 200);
    mListPerson.InsertColumn(10, L"�Ƿ�̹�", LVCFMT_CENTER, 200);
	mListPerson.InsertColumn(11, L"�Ƿ�ѧԱ", LVCFMT_CENTER, 200);

	mListGunBorrow.InsertColumn(0, L"����ʱ��", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(1, L"ѵ�����", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(2, L"��ѵ����", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(3, L"��ѵ����", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(4, L"ѵ�����", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(5, L"ѵ���ж�", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(6, L"ǹ��", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(7, L"����ǹ֧����", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(8, L"����ǹ֧ID", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(9, L"���ý̹�ǩ��", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(10, L"�����쵼ǩ��", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(11, L"��ǹ��ǩ��", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(12, L"��ǹʱ��", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(13, L"��ǹ��ǩ��", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(14, L"��ǹ��ǩ��", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(15, L"��Уʱ��", LVCFMT_CENTER, 200);

	mListBulletBorrow.InsertColumn(0, L"����", LVCFMT_CENTER, 200);
	mListBulletBorrow.InsertColumn(1, L"����ʱ��", LVCFMT_CENTER, 200);
	mListBulletBorrow.InsertColumn(2, L"ѵ�����", LVCFMT_CENTER, 200);
	mListBulletBorrow.InsertColumn(3, L"ѵ���ж�", LVCFMT_CENTER, 200);
	mListBulletBorrow.InsertColumn(4, L"����", LVCFMT_CENTER, 200);
	mListBulletBorrow.InsertColumn(5, L"����ʵ������", LVCFMT_CENTER, 200);
	mListBulletBorrow.InsertColumn(6, L"������ǩ��", LVCFMT_CENTER, 200);
	mListBulletBorrow.InsertColumn(7, L"��ע���ӵ�ʹ�������", LVCFMT_CENTER, 200);

	mListBulletLib.InsertColumn(0, L"��浯��", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(1, L"�ֿ����Ա", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(2, L"�������", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(3, L"������д", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(4, L"��ע", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(5, L"���ʱ��", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(6, L"���뷽ʽ", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(7, L"����ԭ��", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(8, L"����ʱ��", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(9, L"��������", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(10, L"������ʽ", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(11, L"����ԭ��", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(12, L"����ʱ��", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(13, L"��������", LVCFMT_CENTER, 200);

	UpdatePersonData();
	UpdateBulletLibData();
	return TRUE;  // return TRUE unless you set the focus to a control
	// EXCEPTION: OCX Property Pages should return FALSE
}

void CTabpageExport::UpdatePersonData()
{
	vector<CPoliceman*> policemans = CPolicemanManager::GetInstance().GetAllPolice();

	vector<CPoliceman*>::const_iterator iter = policemans.begin();
	for (; iter != policemans.end(); ++iter)
	{
		int row = mListPerson.InsertItem(0, (*iter)->mID);

		mListPerson.SetItemText(row, 1, (*iter)->mName);
		mListPerson.SetItemText(row, 2, (*iter)->mNumber);
		mListPerson.SetItemText(row, 3, (*iter)->mSex);
		mListPerson.SetItemText(row, 4, (*iter)->mRank);
		mListPerson.SetItemText(row, 5, (*iter)->mAgency);
		mListPerson.SetItemText(row, 6, (*iter)->mOfficiate);
		mListPerson.SetItemText(row, 7, (*iter)->mJob);
		mListPerson.SetItemText(row, 8, (*iter)->mGunHoldID);
		mListPerson.SetItemText(row, 9, (*iter)->mTelephone);
		mListPerson.SetItemText(row, 10, (*iter)->mIsInstructor ? L"��" : L"��");
		mListPerson.SetItemText(row, 11, (*iter)->mIsStudent ? L"��" : L"��");
	}
}

void CTabpageExport::UpdateBulletLibData()
{
	mListBulletLib.InsertItem(1, L"���ĵ�");
	mListBulletLib.InsertItem(1, L"��һ��");
	mListBulletLib.InsertItem(1, L"�Ŷ���");
	mListBulletLib.InsertItem(1, L"���ı�ǵ�");
	mListBulletLib.InsertItem(1, L"38���׶�������");
	mListBulletLib.InsertItem(1, L"18.4��������");
	mListBulletLib.InsertItem(1, L"9����ת����ǹ��");
	mListBulletLib.InsertItem(1, L"���Ŀհ���");
	mListBulletLib.InsertItem(1, L"����ʽ7.62��ǹ���գ��֣�");
	mListBulletLib.InsertItem(1, L"����ʽ7.62��ǹ���գ��֣�");
	mListBulletLib.InsertItem(1, L"7.62��ѡ��ǹר�õ����֣�");
	mListBulletLib.InsertItem(1, L"����ʽ7.62�հ������֣�");
	mListBulletLib.InsertItem(1, L"��һBʽ7.62��ǹ�����֣�");
	mListBulletLib.InsertItem(1, L"��һʽ��ǹ���գ��֣�");
	mListBulletLib.InsertItem(1, L"�˰�ʽ��ǹ��");
	mListBulletLib.InsertItem(1, L"38���׶��ô��ᵯ");
	mListBulletLib.InsertItem(1, L"9����ת����ǹ��Ƥ��");
	mListBulletLib.InsertItem(1, L"38���ף�16ͷ���𽺵�");
	mListBulletLib.InsertItem(1, L"10����ת���ò�����");
	mListBulletLib.InsertItem(1, L"10����ת���ô��ᵯ");
}

void CTabpageExport::OnBnClickedButtonExportPerson()
{
	// TODO: Add your control notification handler code here
	ExportExcelFromList(&mListPerson);
}


void CTabpageExport::OnBnClickedButtonBulletLib()
{
	// TODO: Add your control notification handler code here
	ExportExcelFromList(&mListBulletLib);
}

void CTabpageExport::ExportExcelFromList(CListCtrl* listCtrl)
{
	CFileDialog mFileDlg(FALSE, NULL, NULL, OFN_ALLOWMULTISELECT, 
		_T("Excel�ļ� (*.xls)|*.xls||"), this); 
	if(mFileDlg.DoModal() == IDOK)
	{
		CString strPathName, strFileName;
		strPathName = mFileDlg.GetPathName();
		strFileName = mFileDlg.GetFileName();
		if(strFileName.Find('.') == -1)
		{
			strPathName += L".xls";
		}
		else
		{
			if(strFileName.Right(4) != L".xls" && strFileName.Right(4) != L".XLS")
			{
				MessageBox(L"�����ļ���ʽ���󣬺�׺��Ӧ��Ϊ��.xls����", L"������Ϣ",
					MB_OK|MB_ICONERROR);
				return;
			}
		}

		//ɾ���滻������ݣ������ֹ�滻�ļ��Ĵ����� 
		CFileFind fFind;
		if(fFind.FindFile(strPathName))
		{
			if(MessageBox(strFileName+L"�ļ��Ѿ����ڣ��Ƿ��滻ԭ�ļ���",L"��ʾ��Ϣ",MB_OKCANCEL|MB_ICONQUESTION) == IDCANCEL)
			{
				return;
			}
			DeleteFile(strFileName);
		}

		//д��EXCEL�������
		BeginWaitCursor();
		CExcelCtrl SS(strPathName, L"������ѯ�����Ϣ");  
		
		CStringArray sampleArray; 
		SS.BeginTransaction();
		
		// �������
		sampleArray.RemoveAll();
		for(int i=0; i<listCtrl->GetHeaderCtrl()->GetItemCount(); i++)
		{
			HDITEM hdi;
			wchar_t lpBuffer[256];
			hdi.mask = HDI_TEXT;
			hdi.pszText = lpBuffer;
			hdi.cchTextMax = 256;

			listCtrl->GetHeaderCtrl()->GetItem(i, &hdi);
			sampleArray.Add(hdi.pszText);
		}
		SS.AddHeaders(sampleArray);

		//��������		
		for(int i=0; i<listCtrl->GetItemCount(); i++)
		{
			sampleArray.RemoveAll();
			for(int j=0; j<listCtrl->GetHeaderCtrl()->GetItemCount(); j++)
			{
				sampleArray.Add(listCtrl->GetItemText(i, j));
			}
			SS.AddRow(sampleArray);
		}		
		SS.Commit();
		AfxMessageBox(L"���ݱ���ɹ��������ļ�Ϊ��"+strPathName);
		EndWaitCursor(); 
	}
}


void CTabpageExport::OnBnClickedButtonGunBorrow()
{
	// TODO: Add your control notification handler code here
	ExportExcelFromList(&mListGunBorrow);
}


void CTabpageExport::OnBnClickedButtonBulletBorrow()
{
	// TODO: Add your control notification handler code here
	ExportExcelFromList(&mListBulletBorrow);
}
