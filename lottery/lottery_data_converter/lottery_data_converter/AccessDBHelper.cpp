#include "stdafx.h"
#include <afxdisp.h>
#include "AccessDBHelper.h"
#include "windows.h"

CAccessDBHelper::CAccessDBHelper(const string& dbFile, ostream& logger)
	: mDBFile(dbFile), mLogger(logger)
{
	//使用ADO访问数据库时注意一定要初始化OLE/COM库环境－－有了这个语句就不会创建数据库时出现指针错误
	::CoInitialize(NULL);
	::AfxOleInit();
	OpenDB();
}

CAccessDBHelper::~CAccessDBHelper()
{
	::CoUninitialize();//释放占用的COM资源
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
			m_pConnection->ConnectionTimeout = 600;	//设置连接超时时间
			m_pConnection->CommandTimeout = 120;	//设置执行命令超时时间		
			m_pConnection->Open("Provider=Microsoft.Jet.OLEDB.4.0;Data Source=" + mDBFile, "", "",
								adModeUnknown);
		}
    }
    catch(_com_error e)
    {
		mLogger << "连接数据库失败! " << std::endl 
				<< "错误信息:" << e.ErrorMessage() << std::endl; 
    }
}

void writeDB()
{

    try
    {
        m_pRecordset.CreateInstance("ADODB.Recordset"); //为Recordset对象创建实例
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
            //取得第1列的值，从0开始计数，你也可以直接列出列的名称，如下一行
 
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
 
    WriteTableFile(CString("\\AccessDB.txt"),strTableContent);//将查询到的内容写到文件中
 
    m_pRecordset->Close();
    m_pRecordset=NULL;
    m_pConnection->Close();  
    m_pConnection=NULL;
}

void CAccessDBHelper::OpenTextFile(const string& file)
{
	CFile txtFile = new CFile(file, CFile.modeRead);
}


