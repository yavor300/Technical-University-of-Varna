// yyc577.cpp : Defines the entry point for the application.
//
#include "stdafx.h"
#include "yyc577.h"
#include <vector>
#include <string>
#include "commctrl.h"
#define MAX_LOADSTRING 100
typedef tagLOGBRUSH LOGBRUSH;

// Global Variables:
HINSTANCE hInst;	// current instance
TCHAR szTitle[MAX_LOADSTRING];	// The title bar text
TCHAR szWindowClass[MAX_LOADSTRING];	// the main window class name

// Forward declarations of functions included in this code module:
ATOM MyRegisterClass(HINSTANCE hInstance);
BOOL InitInstance(HINSTANCE, int);
LRESULT CALLBACK WndProc(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK About(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK Test(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK ProgressBar(HWND, UINT, WPARAM, LPARAM);

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
	LoadString(hInstance, IDC_YYC577, szWindowClass, MAX_LOADSTRING);
	MyRegisterClass(hInstance);

	// Perform application initialization:
	if (!InitInstance(hInstance, nCmdShow))
	{
		return FALSE;
	}

	hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_YYC577));

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
	wcex.hIcon = LoadIcon(hInstance, MAKEINTRESOURCE(IDI_YYC577));
	wcex.hCursor = LoadCursor(NULL, IDC_ARROW);
	wcex.hbrBackground = (HBRUSH)(COLOR_WINDOW + 1);
	wcex.lpszMenuName = MAKEINTRESOURCE(IDC_YYC577);
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
				case ID_TEST_2:
					DialogBox(hInst, MAKEINTRESOURCE(IDD_DIALOG1), hWnd, Test);
					break;
				case ID_TEST_PROGRESSBAR:
					DialogBox(hInst, MAKEINTRESOURCE(IDD_DIALOG2), hWnd, ProgressBar);
					break;
				default:
					return DefWindowProc(hWnd, message, wParam, lParam);
			}

			break;
		case WM_PAINT:
			{
				hdc = BeginPaint(hWnd, &ps);
				// TODO: Add any drawing code here...
				HPEN hpen = CreatePen(PS_SOLID, 2, RGB(0, 0, 0));
				HPEN whitehpen = CreatePen(PS_SOLID, 1, RGB(255, 255, 255));

				int centerX = 200;
            int centerY = 280;
            int radius = 80;
						SelectObject(hdc, hpen);
            Ellipse(hdc, centerX - radius, centerY - radius, centerX + radius, centerY + radius);

						int centerX2 = 200;
            int centerY2 = 280;
            int radius2 = 65;
						Ellipse(hdc, centerX2 - radius2, centerY2 - radius2, centerX2 + radius2, centerY2 + radius2);



												SelectObject(hdc, whitehpen);

						Rectangle(hdc, 120, 200, 280, 300);

				POINT pp[3];
				pp[0].x = 200;
				pp[0].y = 200;
				pp[1].x = 120;
				pp[1].y = 300;
				pp[2].x = 140;
				pp[2].y = 300;
				SelectObject(hdc, hpen);
				Polygon(hdc, pp, 3);

				pp[0].x = 200;
				pp[0].y = 200;
				pp[1].x = 280;
				pp[1].y = 300;
				pp[2].x = 260;
				pp[2].y = 300;
				SelectObject(hdc, hpen);
				Polygon(hdc, pp, 3);

				

			

				EndPaint(hWnd, &ps);
				break;
			}

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

INT_PTR CALLBACK Test(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
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
			SendDlgItemMessage(hDlg, IDC_COMBO2, CB_ADDSTRING, 0, (LPARAM)
				"17.4");
			SendDlgItemMessage(hDlg, IDC_COMBO2, CB_ADDSTRING, 0, (LPARAM)
				"-9.1");
			SendDlgItemMessage(hDlg, IDC_COMBO2, CB_SETCURSEL, 0, 0);
			return (INT_PTR) TRUE;

		case WM_COMMAND:
			if (LOWORD(wParam) == IDOK || LOWORD(wParam) == IDCANCEL)
			{
				EndDialog(hDlg, LOWORD(wParam));
				return (INT_PTR) TRUE;
			}

			if (LOWORD(wParam) == IDC_BUTTON1)
			{
				GetDlgItemText(hDlg, IDC_EDIT1, data, sizeof(data));

				if (!(SendDlgItemMessage(hDlg, IDC_COMBO1, CB_FINDSTRING, 0, (LPARAM) data) > -1))
				{
					c_index = SendDlgItemMessage(hDlg, IDC_COMBO1, CB_ADDSTRING, 0, (LPARAM) data);
					SendDlgItemMessage(hDlg, IDC_COMBO1, CB_SETCURSEL, c_index, 0);
				}
			}

			if (LOWORD(wParam) == IDC_BUTTON2)
			{
				char inputStringNumbers[10], *stopstring;

				double a = 0, b = 0, c = 0;

				if (IsDlgButtonChecked(hDlg, IDC_CHECK1))
				{
					GetDlgItemText(hDlg, IDC_EDIT2, inputStringNumbers, MAX_LOADSTRING);
					a = strtod(inputStringNumbers, &stopstring);
				}

				if (IsDlgButtonChecked(hDlg, IDC_CHECK2))
				{
					GetDlgItemText(hDlg, IDC_EDIT3, inputStringNumbers, MAX_LOADSTRING);
					b = strtod(inputStringNumbers, &stopstring);
				}

				if (IsDlgButtonChecked(hDlg, IDC_CHECK3))
				{
					lbIndex = SendDlgItemMessage(hDlg, IDC_COMBO2, CB_GETCURSEL, 0, 0);
					if (lbIndex > -1)
					{
						SendDlgItemMessage(hDlg, IDC_COMBO2, CB_GETLBTEXT, lbIndex, (LPARAM) inputStringNumbers);

						c = strtod(inputStringNumbers, &stopstring);
					}
				}

				double sum = c + b - a;
				sprintf_s(inputStringNumbers, "%f", sum);
				SetDlgItemText(hDlg, IDC_EDIT5, inputStringNumbers);
			}

			break;
	}

	return FALSE;
}
#define TIMER1 500
INT_PTR CALLBACK ProgressBar(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
{
	static int step = -10;
	UNREFERENCED_PARAMETER(lParam);
	switch (message)
	{
		case WM_INITDIALOG:
			SetDlgItemInt(hDlg, IDC_TIMER, 0, NULL);
			SendDlgItemMessage(hDlg, IDC_PROGRESS1, PBM_SETRANGE, 0, MAKELPARAM(0, 1000));
			SendDlgItemMessage(hDlg, IDC_PROGRESS2, PBM_SETRANGE, 0, MAKELPARAM(0, 1000));
			SendDlgItemMessage(hDlg, IDC_PROGRESS1, PBM_DELTAPOS, 1000, 0);
			SendDlgItemMessage(hDlg, IDC_PROGRESS2, PBM_DELTAPOS, 1000, 0);
			return (INT_PTR) TRUE;

		case WM_COMMAND:
			if (LOWORD(wParam) == IDOK || LOWORD(wParam) == IDCANCEL)
			{
				EndDialog(hDlg, LOWORD(wParam));
				return (INT_PTR) TRUE;
			}

			if (LOWORD(wParam) == IDC_BUTTON1)
			{
				if (SendDlgItemMessage(hDlg, IDC_PROGRESS1, PBM_GETPOS, 0, 0) <= 0 &&
					SendDlgItemMessage(hDlg, IDC_PROGRESS2, PBM_GETPOS, 0, 0) <= 0)
				{
					break;
				}

				SetTimer(hDlg, TIMER1, 20, NULL);
			}

			break;

		case WM_TIMER:
			{
				int counter = GetDlgItemInt(hDlg, IDC_TIMER, NULL, FALSE);

				if (SendDlgItemMessage(hDlg, IDC_PROGRESS2, PBM_GETPOS, 0, 0) > 0)
				{
					counter += 5;
					SetDlgItemInt(hDlg, IDC_TIMER, counter, NULL);
					SendDlgItemMessage(hDlg, IDC_PROGRESS2, PBM_SETSTEP, step, 0);
					SendDlgItemMessage(hDlg, IDC_PROGRESS2, PBM_STEPIT, 0, 0);
				}
				else
				{
					if (SendDlgItemMessage(hDlg, IDC_PROGRESS1, PBM_GETPOS, 0, 0) > 0)
					{
						counter += 5;
						SetDlgItemInt(hDlg, IDC_TIMER, counter, NULL);
						SendDlgItemMessage(hDlg, IDC_PROGRESS1, PBM_SETSTEP, step, 0);
						SendDlgItemMessage(hDlg, IDC_PROGRESS1, PBM_STEPIT, 0, 0);
					}
					else
					{
						KillTimer(hDlg, TIMER1);
					}
				}
			}
	}

	return FALSE;
}