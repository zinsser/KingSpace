class SvcDispatch
{
private:
	static CONST WORD THREADCOUNT = 1;
	static HANDLE hStopEvent;
	static HANDLE hThreads[THREADCOUNT];
	static HDEVNOTIFY hDevNotifyCdrom;
	static HDEVNOTIFY hDevNotifyModem;
	static LPTSTR lpszServiceName;
	static SERVICE_STATUS_HANDLE ssh;
private:
	static void ErrorStopService(const string& szFunName);
	static void SetServiceStatus();
	static DWORD WINAPI ThreadProc(LPVOID lpParameter);
	static DWORD WINAPI ModemInsertedProc(LPVOID lpParameter);
	static void WINAPI  MyServiceMain(DWORD dwArgc, LPTSTR* lpszArgv);
	static void WINAPI  MyServiceCtrl(DWORD dwCtrlCode, DWORD dwEventType, LPVOID lpEventData, LPVOID lpContext);
public:
	static void Dispatch();
	static void SvcMain(int argc, char* argv[]);
};