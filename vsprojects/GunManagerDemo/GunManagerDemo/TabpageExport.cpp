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
    mListPerson.InsertColumn(0, L"身份证", LVCFMT_CENTER, 200);
    mListPerson.InsertColumn(1, L"名称", LVCFMT_CENTER, 200);
	mListPerson.InsertColumn(2, L"警号", LVCFMT_CENTER, 200);
    mListPerson.InsertColumn(3, L"性别", LVCFMT_CENTER, 200);
    mListPerson.InsertColumn(4, L"警衔", LVCFMT_CENTER, 200);
	mListPerson.InsertColumn(5, L"单位", LVCFMT_CENTER, 200);
    mListPerson.InsertColumn(6, L"职务", LVCFMT_CENTER, 200);
    mListPerson.InsertColumn(7, L"岗位", LVCFMT_CENTER, 200);
	mListPerson.InsertColumn(8, L"持枪号码", LVCFMT_CENTER, 200);
	mListPerson.InsertColumn(9, L"电话", LVCFMT_CENTER, 200);
    mListPerson.InsertColumn(10, L"是否教官", LVCFMT_CENTER, 200);
	mListPerson.InsertColumn(11, L"是否学员", LVCFMT_CENTER, 200);

	mListGunBorrow.InsertColumn(0, L"领用时间", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(1, L"训练年份", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(2, L"参训期数", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(3, L"培训类型", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(4, L"训练大队", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(5, L"训练中队", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(6, L"枪型", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(7, L"领用枪支数量", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(8, L"领用枪支ID", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(9, L"领用教官签名", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(10, L"审批领导签名", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(11, L"发枪人签名", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(12, L"还枪时间", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(13, L"还枪人签名", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(14, L"收枪人签名", LVCFMT_CENTER, 200);
	mListGunBorrow.InsertColumn(15, L"在校时间", LVCFMT_CENTER, 200);

	mListBulletBorrow.InsertColumn(0, L"警号", LVCFMT_CENTER, 200);
	mListBulletBorrow.InsertColumn(1, L"领用时间", LVCFMT_CENTER, 200);
	mListBulletBorrow.InsertColumn(2, L"训练大队", LVCFMT_CENTER, 200);
	mListBulletBorrow.InsertColumn(3, L"训练中队", LVCFMT_CENTER, 200);
	mListBulletBorrow.InsertColumn(4, L"弹型", LVCFMT_CENTER, 200);
	mListBulletBorrow.InsertColumn(5, L"领用实弹数量", LVCFMT_CENTER, 200);
	mListBulletBorrow.InsertColumn(6, L"发单人签名", LVCFMT_CENTER, 200);
	mListBulletBorrow.InsertColumn(7, L"备注（子弹使用情况）", LVCFMT_CENTER, 200);

	mListBulletLib.InsertColumn(0, L"库存弹型", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(1, L"仓库管理员", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(2, L"库存数量", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(3, L"数量大写", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(4, L"备注", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(5, L"入库时间", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(6, L"调入方式", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(7, L"调入原因", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(8, L"调入时间", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(9, L"调入数量", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(10, L"调出方式", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(11, L"调出原因", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(12, L"调出时间", LVCFMT_CENTER, 200);
	mListBulletLib.InsertColumn(13, L"调出数量", LVCFMT_CENTER, 200);

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
		mListPerson.SetItemText(row, 10, (*iter)->mIsInstructor ? L"是" : L"否");
		mListPerson.SetItemText(row, 11, (*iter)->mIsStudent ? L"是" : L"否");
	}
}

void CTabpageExport::UpdateBulletLibData()
{
	mListBulletLib.InsertItem(1, L"六四弹");
	mListBulletLib.InsertItem(1, L"五一弹");
	mListBulletLib.InsertItem(1, L"九二弹");
	mListBulletLib.InsertItem(1, L"六四标记弹");
	mListBulletLib.InsertItem(1, L"38毫米二用烟雾弹");
	mListBulletLib.InsertItem(1, L"18.4毫米烟雾弹");
	mListBulletLib.InsertItem(1, L"9毫米转轮手枪弹");
	mListBulletLib.InsertItem(1, L"六四空包弹");
	mListBulletLib.InsertItem(1, L"五三式7.62步枪弹普（钢）");
	mListBulletLib.InsertItem(1, L"五六式7.62步枪弹普（钢）");
	mListBulletLib.InsertItem(1, L"7.62自选步枪专用弹（钢）");
	mListBulletLib.InsertItem(1, L"五六式7.62空包弹（钢）");
	mListBulletLib.InsertItem(1, L"五一B式7.62手枪弹（钢）");
	mListBulletLib.InsertItem(1, L"五一式手枪弹空（钢）");
	mListBulletLib.InsertItem(1, L"八八式步枪弹");
	mListBulletLib.InsertItem(1, L"38毫米二用催泪弹");
	mListBulletLib.InsertItem(1, L"9毫米转轮手枪橡皮弹");
	mListBulletLib.InsertItem(1, L"38毫米（16头）橡胶弹");
	mListBulletLib.InsertItem(1, L"10毫米转轮用布袋弹");
	mListBulletLib.InsertItem(1, L"10毫米转轮用催泪弹");
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
		_T("Excel文件 (*.xls)|*.xls||"), this); 
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
				MessageBox(L"保存文件格式错误，后缀名应该为“.xls”！", L"错误信息",
					MB_OK|MB_ICONERROR);
				return;
			}
		}

		//删除替换表格内容，这里防止替换文件的处理方法 
		CFileFind fFind;
		if(fFind.FindFile(strPathName))
		{
			if(MessageBox(strFileName+L"文件已经存在，是否替换原文件？",L"提示信息",MB_OKCANCEL|MB_ICONQUESTION) == IDCANCEL)
			{
				return;
			}
			DeleteFile(strFileName);
		}

		//写入EXCEL表格内容
		BeginWaitCursor();
		CExcelCtrl SS(strPathName, L"导出查询表格信息");  
		
		CStringArray sampleArray; 
		SS.BeginTransaction();
		
		// 加入标题
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

		//加入数据		
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
		AfxMessageBox(L"数据保存成功，保存文件为："+strPathName);
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
