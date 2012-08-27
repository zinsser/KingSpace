#include "stdafx.h"
#include <afxdisp.h>
#include "AccessDBHelper.h"
#include "windows.h"

CAccessDBHelper::CAccessDBHelper(const string& dbFile, ostream& logger)
	: mDBFile(dbFile), mLogger(logger)
{
	//ʹ��ADO�������ݿ�ʱע��һ��Ҫ��ʼ��OLE/COM�⻷����������������Ͳ��ᴴ�����ݿ�ʱ����ָ�����
	::CoInitialize(NULL);
	::AfxOleInit();
	OpenDB();
}

CAccessDBHelper::~CAccessDBHelper()
{
	::CoUninitialize();//�ͷ�ռ�õ�COM��Դ
}

void CAccessDBHelper::Convert(const string& srcFile)
{
}

void CAccessDBHelper::OpenDB()
{
    try
    {
        HRESULT hr = m_pConnection.CreateInstance("ADODB.Connection");//__uuidof(Connection)
		if (SUCCEEDED(hr))
		{
			m_pConnection->ConnectionTimeout = 600;	//�������ӳ�ʱʱ��
			m_pConnection->CommandTimeout = 120;	//����ִ�����ʱʱ��		
			m_pConnection->Open("Provider=Microsoft.Jet.OLEDB.4.0;Data Source=" + mDBFile, "", "",
								adModeUnknown);
		}
    }
    catch(_com_error e)
    {
		mLogger << "�������ݿ�ʧ��! " << std::endl 
				<< "������Ϣ:" << e.ErrorMessage() << std::endl; 
    }
}

void writeDB()
{

    try
    {
        m_pRecordset.CreateInstance("ADODB.Recordset"); //ΪRecordset���󴴽�ʵ��
        _bstr_t strCmd = "SELECT ID, Red1, Red2, Red3, Red4, Red5, Red6, Blue FROM rawdata";
        m_pRecordset = m_pConnection->Execute(strCmd,&RecordsAffected,adCmdText);
 
    }
    catch(_com_error &e)
    {
        AfxMessageBox(e.Description());
    }
 
    _variant_t vEmployeeID,vFirstName,vLastName,vHireDate,vCity;
 
    try
    {
        while(!m_pRecordset->adoEOF)
        {
            vEmployeeID=m_pRecordset->GetCollect(_variant_t((long)0));
            //ȡ�õ�1�е�ֵ����0��ʼ��������Ҳ����ֱ���г��е����ƣ�����һ��
 
            vFirstName=m_pRecordset->GetCollect("FirstName");
            vLastName=m_pRecordset->GetCollect("LastName");
            vHireDate=m_pRecordset->GetCollect("HireDate");
            vCity=m_pRecordset->GetCollect("City");
 
            CString strtemp;
            if(vEmployeeID.vt!=VT_NULL)
            {
                strtemp.Format(CString("%d"),vEmployeeID.lVal);
            }
 
            if(vFirstName.vt!=VT_NULL)
            {
                strtemp+="      ";
                strtemp+=(LPCTSTR)(_bstr_t)vFirstName;
            }
 
            if(vLastName.vt!=VT_NULL)
            {
                strtemp+="      ";
                strtemp+=(LPCTSTR)(_bstr_t)vLastName;
            }
 
            if(vHireDate.vt!=VT_NULL)
            {
                strtemp+="      ";
                strtemp+=(LPCTSTR)(_bstr_t)vHireDate;
            }
 
            if(vCity.vt!=VT_NULL)
            {
                strtemp+="      ";
                strtemp+=(LPCTSTR)(_bstr_t)vCity;
            }
 
         
            strTableContent+=strtemp;
            strTableContent+=CString("\r\n");
 
             
 
            m_pRecordset->MoveNext();
        }
 
    }
    catch(_com_error &e)
    {
        AfxMessageBox(e.Description());
    }
 
    WriteTableFile(CString("\\AccessDB.txt"),strTableContent);//����ѯ��������д���ļ���
 
    m_pRecordset->Close();
    m_pRecordset=NULL;
    m_pConnection->Close();  
    m_pConnection=NULL;
}

void CAccessDBHelper::OpenTextFile(const string& file)
{
	CFile txtFile = new CFile(file, CFile.modeRead);
}


