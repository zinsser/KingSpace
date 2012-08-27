#ifndef ACCESS_DBHELPER_H
#define ACCESS_DBHELPER_H

#include <iostream>
#include <string>
using std::string;
using std::ostream;

class CAccessDBHelper
{
private:
	struct CRecord
	{
		string mRed[6];
		string mBlue;
	};
public:
	CAccessDBHelper(const string& dbFile, ostream& logger);
	~CAccessDBHelper();

	void Convert(const string& srcFile);

public:
	void OpenDB();
	void CloseDB();
	void InsertRecord(const CRecord& record);

private:
	void OpenTextFile(const string& file);

private:
	string mDBFile;
	ostream& mLogger;
    _ConnectionPtr m_pConnection;
    _variant_t RecordsAffected;
    _RecordsetPtr m_pRecordset;

};

#endif
