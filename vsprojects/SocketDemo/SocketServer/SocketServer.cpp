// SocketServer.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <WinSock.h>
#include <iostream>
#include <fstream>
#include <time.h>
using namespace std;

#define PORT 4000
#define IP_ADDR "192.168.0.102"

int _tmain(int argc, _TCHAR* argv[])
{
	WSADATA dat;
	//初始化Socket
	if (WSAStartup(MAKEWORD(2, 2), &dat) != 0)
	{
		cout << "Init Windows Socket Failed: " << GetLastError() << endl;
		return -1;
	}
	//Socket返回值
	SOCKET serverSocket, clientSocket;
	int ret = 0;
	//创建Socket
	serverSocket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
	if (serverSocket == INVALID_SOCKET)
	{
		cout << "Create Socket Failed: " << GetLastError() << endl;
		return -1;
	}

	SOCKADDR_IN localAddr, clientAddr; 
	localAddr.sin_family = AF_INET;
	localAddr.sin_addr.s_addr = inet_addr(IP_ADDR);
	localAddr.sin_port = htons(PORT);
	memset(localAddr.sin_zero, 0, sizeof(localAddr.sin_zero));

	//服务器建立
	ret = bind(serverSocket, (struct sockaddr*)&localAddr, sizeof(localAddr));
	if (ret != 0)
	{
		cout << "Bind Socket Failed: " << GetLastError() << endl;
		return -1;
	}
	//服务器监听
	ret = listen(serverSocket, 10);
	if (ret != 0)
	{
		cout << "Listen Failed: " << GetLastError() << endl;
		return -1;
	}
	cout << "Server is On." << endl;
	while(1)
	{
		//接收信息
		int addLen = sizeof(clientAddr);
		clientSocket = accept(serverSocket, (struct sockaddr*)&clientAddr, &addLen);
		cout << "connect is success." << endl;
		ifstream ifs("..\\res\\chuanqi.mp3", fstream::in|fstream::binary);
		char buf[4096] = {0};
		cout << "Sending... at:" << time(NULL) << endl;

		do
		{
			ifs.read(buf, sizeof(buf));
			ret = send(clientSocket, buf, ifs.gcount(),0);
		}while (!ifs.eof() && ret != SOCKET_ERROR);

		ifs.close();
		cout << "end. at:" << time(NULL) << endl;

		if (ret == SOCKET_ERROR)
		{
			cout << "Send Into error: " << GetLastError() << endl;
		}
		closesocket(clientSocket);
	}

	closesocket(serverSocket);
	WSACleanup();
	return 0;
}

