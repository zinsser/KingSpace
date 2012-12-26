// SocketClient.cpp : Defines the entry point for the console application.
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
	//初始化
	if (WSAStartup(MAKEWORD(2,2), &dat)!=0)
	{
		cout<<"Init Falied: "<< GetLastError() << endl;
		return -1;
	}

	SOCKET clientSocket;
	int ret(0);
	//创建Socket
	clientSocket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
	if (clientSocket == INVALID_SOCKET)
	{
		cout << "Create Falied: " << GetLastError() << endl;
		return -1;
	}
	SOCKADDR_IN serverAddr;
	serverAddr.sin_family = AF_INET;
	serverAddr.sin_addr.s_addr = inet_addr(IP_ADDR);
	serverAddr.sin_port = htons(PORT);
	memset(serverAddr.sin_zero,0,sizeof(serverAddr.sin_zero));

	ret = connect(clientSocket, (struct sockaddr*)&serverAddr, sizeof(serverAddr));
	if (ret == SOCKET_ERROR)
	{
		cout << "Connect Falied: " << GetLastError() << endl;
		return -1;
	}
	cout << "Connect is success." << endl;

	ofstream out("..\\res_rx\\IMG_0007.JPG", fstream::out | fstream::binary);
	char buf[1024] = {0};
	cout << "Receiving...at:" << time(NULL) << endl;
	do
	{
		ret = recv(clientSocket, buf, sizeof(buf), 0);
		if (ret > 0)
		{
			out.write(buf,sizeof(char)*ret);
		}
	}while(ret > 0);

	out.close();
	cout << "end at:" << time(NULL) << endl;
	closesocket(clientSocket);
	WSACleanup();
	return 0;
}

