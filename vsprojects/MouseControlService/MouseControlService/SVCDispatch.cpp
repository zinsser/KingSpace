#include "stdafx.h"
#include "SVCDispatch.h"
void SVCDispatch::SvcMain(int argc, char* argv[])
{
	char szSvcExe[256] = {0};
	GetModuleFileName(NULL, szSvcExe, 256);
}

void SVCDispatch::Dispatch()
{
	SERVICE_TABLE_ENTRY ste[] = {{"", (LPSERVICE_MAIN_FUNCTION)MyServiceMain}, {NULL, NULL}};
	if (!StartServiceCtrlDispatcher(ste))
	{
	}
	else
	{
	}
}