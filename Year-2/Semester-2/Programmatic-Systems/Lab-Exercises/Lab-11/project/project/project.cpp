// project.cpp : Defines the entry point for the application.
//
#include "stdafx.h"
#include "project.h"
#include <string>
#define MAX_LOADSTRING 100

// Global Variables:
HINSTANCE hInst;	// current instance
TCHAR szTitle[MAX_LOADSTRING];	// The title bar text
TCHAR szWindowClass[MAX_LOADSTRING];	// the main window class name

// Forward declarations of functions included in this code module:
ATOM MyRegisterClass(HINSTANCE hInstance);
BOOL InitInstance(HINSTANCE, int);
LRESULT CALLBACK WndProc(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK About(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK Task(HWND, UINT, WPARAM, LPARAM);

int APIENTRY _tWinMain(_In_ HINSTANCE hInstance,
	_In_opt_ HINSTANCE hPrevInstance,
	_In_ LPTSTR lpCmdLine,
	_In_ int nCmdShow)
{
	UNREFERENCED_PARAMETER(hPrevInstance);
	UNREFERENCED_PARAMETER(lpCmdLine);

	// TODO: Place code here.
	MSG msg;
	HACCEL hAccelTable;

	// Initialize global strings
	LoadString(hInstance, IDS_APP_TITLE, szTitle, MAX_LOADSTRING);
	LoadString(hInstance, IDC_PROJECT, szWindowClass, MAX_LOADSTRING);
	MyRegisterClass(hInstance);

	// Perform application initialization:
	if (!InitInstance(hInstance, nCmdShow))
	{
		return FALSE;
	}

	hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_PROJECT));

	// Main message loop:
	while (GetMessage(&msg, NULL, 0, 0))
	{
		if (!TranslateAccelerator(msg.hwnd, hAccelTable, &msg))
		{
			TranslateMessage(&msg);
			DispatchMessage(&msg);
		}
	}

	return (int) msg.wParam;
}

//
//  FUNCTION: MyRegisterClass()
//
//  PURPOSE: Registers the window class.
//
ATOM MyRegisterClass(HINSTANCE hInstance)
{
	WNDCLASSEX wcex;

	wcex.cbSize = sizeof(WNDCLASSEX);

	wcex.style = CS_HREDRAW | CS_VREDRAW;
	wcex.lpfnWndProc = WndProc;
	wcex.cbClsExtra = 0;
	wcex.cbWndExtra = 0;
	wcex.hInstance = hInstance;
	wcex.hIcon = LoadIcon(hInstance, MAKEINTRESOURCE(IDI_PROJECT));
	wcex.hCursor = LoadCursor(NULL, IDC_ARROW);
	wcex.hbrBackground = (HBRUSH)(COLOR_WINDOW + 1);
	wcex.lpszMenuName = MAKEINTRESOURCE(IDC_PROJECT);
	wcex.lpszClassName = szWindowClass;
	wcex.hIconSm = LoadIcon(wcex.hInstance, MAKEINTRESOURCE(IDI_SMALL));

	return RegisterClassEx(&wcex);
}

//
//   FUNCTION: InitInstance(HINSTANCE, int)
//
//   PURPOSE: Saves instance handle and creates main window
//
//   COMMENTS:
//
//        In this function, we save the instance handle in a global variable and
//        create and display the main program window.
//
BOOL InitInstance(HINSTANCE hInstance, int nCmdShow)
{
	HWND hWnd;

	hInst = hInstance;	// Store instance handle in our global variable

	hWnd = CreateWindow(szWindowClass, szTitle, WS_OVERLAPPEDWINDOW,
		CW_USEDEFAULT, 0, CW_USEDEFAULT, 0, NULL, NULL, hInstance, NULL);

	if (!hWnd)
	{
		return FALSE;
	}

	ShowWindow(hWnd, nCmdShow);
	UpdateWindow(hWnd);

	return TRUE;
}

//
//  FUNCTION: WndProc(HWND, UINT, WPARAM, LPARAM)
//
//  PURPOSE:  Processes messages for the main window.
//
//  WM_COMMAND	- process the application menu
//  WM_PAINT	- Paint the main window
//  WM_DESTROY	- post a quit message and return
//
//
LRESULT CALLBACK WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
	int wmId, wmEvent;
	PAINTSTRUCT ps;
	HDC hdc;

	switch (message)
	{
		case WM_COMMAND:
			wmId = LOWORD(wParam);
			wmEvent = HIWORD(wParam);
			// Parse the menu selections:
			switch (wmId)
			{
				case IDM_ABOUT:
					DialogBox(hInst, MAKEINTRESOURCE(IDD_ABOUTBOX), hWnd, About);
					break;
				case IDM_EXIT:
					DestroyWindow(hWnd);
					break;
				case IDM_TASK:
					DialogBox(hInst, MAKEINTRESOURCE(IDD_DIALOG1), hWnd, Task);
					break;
				default:
					return DefWindowProc(hWnd, message, wParam, lParam);
			}

			break;
		case WM_PAINT:
			hdc = BeginPaint(hWnd, &ps);
			// TODO: Add any drawing code here...
			EndPaint(hWnd, &ps);
			break;
		case WM_DESTROY:
			PostQuitMessage(0);
			break;
		default:
			return DefWindowProc(hWnd, message, wParam, lParam);
	}

	return 0;
}

// Message handler for about box.
INT_PTR CALLBACK About(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
{
	UNREFERENCED_PARAMETER(lParam);
	switch (message)
	{
		case WM_INITDIALOG:
			return (INT_PTR) TRUE;

		case WM_COMMAND:
			if (LOWORD(wParam) == IDOK || LOWORD(wParam) == IDCANCEL)
			{
				EndDialog(hDlg, LOWORD(wParam));
				return (INT_PTR) TRUE;
			}

			break;
	}

	return (INT_PTR) FALSE;
}

// Message handler for task box.
INT_PTR CALLBACK Task(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
{
	char data[20];
	int c_index, l_index;
	int mID;
	int cbIndex;
	int lbIndex;
	UNREFERENCED_PARAMETER(lParam);
	switch (message)
	{
		case WM_INITDIALOG:
			SetDlgItemText(hDlg, IDC_EDIT1, "2.33");
			SetDlgItemText(hDlg, IDC_EDIT2, "8.1");
			SetDlgItemText(hDlg, IDC_EDIT3, "5.4");
			CheckDlgButton(hDlg, IDC_CHECK1, MFS_CHECKED);
			CheckDlgButton(hDlg, IDC_CHECK2, MFS_CHECKED);
			SendDlgItemMessage(hDlg, IDC_COMBO1, CB_ADDSTRING, 0, (LPARAM)
				"-1.1");
			SendDlgItemMessage(hDlg, IDC_COMBO1, CB_ADDSTRING, 0, (LPARAM)
				"24.5");
			SendDlgItemMessage(hDlg, IDC_COMBO1, CB_ADDSTRING, 0, (LPARAM)
				"3.2");
			SendDlgItemMessage(hDlg, IDC_COMBO1, CB_SETCURSEL, 0, 0);
			return (INT_PTR) TRUE;

		case WM_COMMAND:
			if (LOWORD(wParam) == IDOK || LOWORD(wParam) == IDCANCEL)
			{
				EndDialog(hDlg, LOWORD(wParam));
				return (INT_PTR) TRUE;
			}
			else if (LOWORD(wParam) == IDC_BUTTON1)
			{
				GetDlgItemText(hDlg, IDC_EDIT1, data, sizeof(data));

				if (!(SendDlgItemMessage(hDlg, IDC_COMBO1, CB_FINDSTRING, 0, (LPARAM) data) > -1))
				{
					c_index = SendDlgItemMessage(hDlg, IDC_COMBO1, CB_ADDSTRING, 0, (LPARAM) data);
					SendDlgItemMessage(hDlg, IDC_COMBO1, CB_SETCURSEL, c_index, 0);
				}
			}
			else if (LOWORD(wParam) == IDC_BUTTON4)
			{
				char inputStringNumbers[10], *stopstring;

				GetDlgItemText(hDlg, IDC_EDIT1, inputStringNumbers, MAX_LOADSTRING);

				double a = 0;
				if (inputStringNumbers[0] != '\0')
				{
					a = strtod(inputStringNumbers, &stopstring);
				}
				else
				{
					SetDlgItemText(hDlg, IDC_EDIT1, "0");
				}

				double b = 0;

				if (IsDlgButtonChecked(hDlg, IDC_CHECK1))
				{
					GetDlgItemText(hDlg, IDC_EDIT2, inputStringNumbers, MAX_LOADSTRING);
					b = strtod(inputStringNumbers, &stopstring);
				}

				GetDlgItemText(hDlg, IDC_EDIT3, inputStringNumbers, MAX_LOADSTRING);
				double c = strtod(inputStringNumbers, &stopstring);

				double d = 0;
				if (IsDlgButtonChecked(hDlg, IDC_CHECK2))
				{
					GetDlgItemText(hDlg, IDC_COMBO1, inputStringNumbers, MAX_LOADSTRING);
					d = strtod(inputStringNumbers, &stopstring);
				}

				if (d + b == 0)
				{
					MessageBox(hDlg, "Divisor cannot be 0!", "ERROR!", MB_OK | MB_ICONERROR);
					SetDlgItemInt(hDlg, IDC_EDIT4, 0, TRUE);
				}
				else
				{
					double res = (a + c) / (d + b);
					sprintf_s(inputStringNumbers, "%8.3f", res);
					SetDlgItemText(hDlg, IDC_EDIT4, inputStringNumbers);
				}
			}

			break;
	}

	return (INT_PTR) FALSE;
}