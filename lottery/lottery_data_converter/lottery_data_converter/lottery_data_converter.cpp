// lottery_data_converter.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <iostream>
using std::cout;
using std::endl;

#include "AccessDBHelper.h"

int _tmain(int argc, _TCHAR* argv[])
{
	if (argc < 3)
	{
		cout << "请输入正确的参数，例如" << endl
			 << "lottery_data_converter SOURCE_DATA_FILE<.txt> TARGET_DATA_BASE<.mdb>" << endl;
		return -1;
	}

	CAccessDBHelper dbHelper(argv[2], cout);
	dbHelper.Convert(argv[1]);
	return 0;
}

