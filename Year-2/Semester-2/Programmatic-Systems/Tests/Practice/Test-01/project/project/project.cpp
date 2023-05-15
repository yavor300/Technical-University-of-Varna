// project.cpp : Defines the entry point for the application.
//
#include "stdafx.h"
#include "project.h"
#include <vector>
#include <string>
#include "commctrl.h"
#define MAX_LOADSTRING 100
typedef tagLOGBRUSH LOGBRUSH;

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
INT_PTR CALLBACK Test(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK Timer(HWND, UINT, WPARAM, LPARAM);

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
				case ID_TEST_1:
					DialogBox(hInst, MAKEINTRESOURCE(IDD_DIALOG1), hWnd, Test);
					break;
				case ID_TEST_TIMER:
					DialogBox(hInst, MAKEINTRESOURCE(IDD_DIALOG2), hWnd, Timer);
					break;
				default:
					return DefWindowProc(hWnd, message, wParam, lParam);
			}

			break;
		case WM_PAINT:
			{
				hdc = BeginPaint(hWnd, &ps);
				RECT rt;
				GetClientRect(hWnd, &rt);

				HPEN hpen = CreatePen(PS_SOLID, 1, RGB(0, 0, 0));
				POINT pp[3];
				pp[0].x = 200;
				pp[0].y = 200;
				pp[1].x = 100;
				pp[1].y = 350;
				pp[2].x = 250;
				pp[2].y = 390;
				SelectObject(hdc, hpen);
				Polygon(hdc, pp, 3);

				pp[0].x = 250;
				pp[0].y = 390;
				pp[1].x = 350;
				pp[1].y = 340;
				pp[2].x = 200;
				pp[2].y = 200;
				SelectObject(hdc, hpen);
				Polygon(hdc, pp, 3);

				HPEN hpenDot = CreatePen(PS_DOT, 1, RGB(0, 0, 0));
				MoveToEx(hdc, 100, 350, nullptr);
				SelectObject(hdc, hpenDot);
				LineTo(hdc, 350, 340);

				SelectObject(hdc, hpen);
				Rectangle(hdc, 100, 100, 300, 300);
				MoveToEx(hdc, 100, 100, NULL);
				LineTo(hdc, 150, 50);
				MoveToEx(hdc, 300, 100, NULL);
				LineTo(hdc, 350, 50);
				MoveToEx(hdc, 300, 300, NULL);
				LineTo(hdc, 350, 250);
				MoveToEx(hdc, 100, 300, NULL);
				LineTo(hdc, 150, 250);

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
#define TIMER1 500
INT_PTR CALLBACK Test(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
{
	BOOL success = FALSE;
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

			if (LOWORD(wParam) == IDC_BUTTON3)
			{
				int f1InputNumber = GetDlgItemInt(hDlg, IDC_EDIT1, &success, TRUE);
				std::string f1InputString = std::to_string(f1InputNumber);

				if (!success)
				{
					MessageBox(hDlg, "Enter valid integer number!", "ERROR!", MB_OK | MB_ICONERROR);
					SetFocus(GetDlgItem(hDlg, IDC_EDIT1));
				}
				else
				{
					if (!(SendDlgItemMessage(hDlg, IDC_LIST2, LB_FINDSTRING, 0, reinterpret_cast<LPARAM> (f1InputString.c_str())) > -1))
					{
						int l_index = SendDlgItemMessage(hDlg, IDC_LIST2, LB_ADDSTRING, 0, reinterpret_cast<LPARAM> (f1InputString.c_str()));
						SetDlgItemText(hDlg, IDC_EDIT1, "");
						SetFocus(GetDlgItem(hDlg, IDC_EDIT1));
						//SendDlgItemMessage(hDlg, IDC_LIST2, LB_SETCURSEL, l_index, 0);
					}
				}
			}

			if (LOWORD(wParam) == IDC_BUTTON2)
			{
				int f1InputNumber = GetDlgItemInt(hDlg, IDC_EDIT1, &success, TRUE);
				std::string f1InputString = std::to_string(f1InputNumber);

				if (!success)
				{
					MessageBox(hDlg, "Enter valid integer number!", "ERROR!", MB_OK | MB_ICONERROR);
					SetFocus(GetDlgItem(hDlg, IDC_EDIT1));
				}
				else
				{
					if (!(SendDlgItemMessage(hDlg, IDC_LIST1, LB_FINDSTRING, 0, reinterpret_cast<LPARAM> (f1InputString.c_str())) > -1))
					{
						int l_index = SendDlgItemMessage(hDlg, IDC_LIST1, LB_ADDSTRING, 0, reinterpret_cast<LPARAM> (f1InputString.c_str()));
						SetDlgItemText(hDlg, IDC_EDIT1, "");
						SetFocus(GetDlgItem(hDlg, IDC_EDIT1));
						//SendDlgItemMessage(hDlg, IDC_LIST1, LB_SETCURSEL, l_index, 0);
					}
				}
			}

			if (LOWORD(wParam) == IDC_BUTTON1)
			{
				char inputStringNumbers[20], *stopstring;
				double f1InputNumber = 0, lbIndex;
				double sum = 0;
				if (IsDlgButtonChecked(hDlg, IDC_CHECK1))
				{
					f1InputNumber = GetDlgItemInt(hDlg, IDC_EDIT1, &success, TRUE);
					if (!success)
					{
						MessageBox(hDlg, "Enter valid integer number!", "ERROR!", MB_OK | MB_ICONERROR);
						SetFocus(GetDlgItem(hDlg, IDC_EDIT1));
						SetDlgItemInt(hDlg, IDC_EDIT2, sum, TRUE);
						break;
					}
				}

				double f2InputNumber;
				lbIndex = SendDlgItemMessage(hDlg, IDC_LIST2, LB_GETCURSEL, 0, 0);
				if (lbIndex > -1)
				{
					SendDlgItemMessage(hDlg, IDC_LIST2, LB_GETTEXT, lbIndex, (LPARAM) inputStringNumbers);
					f2InputNumber = strtod(inputStringNumbers, &stopstring);
				}
				else
				{
					MessageBox(hDlg, "Select a number from F2 list box!", "Error!", MB_OK | MB_ICONERROR);
					SetDlgItemInt(hDlg, IDC_EDIT2, sum, TRUE);
					break;
				}

				double f3InputNumber;
				lbIndex = SendDlgItemMessage(hDlg, IDC_LIST1, LB_GETCURSEL, 0, 0);
				if (lbIndex > -1)
				{
					SendDlgItemMessage(hDlg, IDC_LIST1, LB_GETTEXT, lbIndex, (LPARAM) inputStringNumbers);
					f3InputNumber = strtod(inputStringNumbers, &stopstring);
				}
				else
				{
					MessageBox(hDlg, "Select a number from F3 list box!", "Error!", MB_OK | MB_ICONERROR);
					SetDlgItemInt(hDlg, IDC_EDIT2, sum, TRUE);
					break;
				}

				if (!IsDlgButtonChecked(hDlg, IDC_CHECK2))
				{
					f2InputNumber = 0;
				}

				if (!IsDlgButtonChecked(hDlg, IDC_CHECK3))
				{
					f3InputNumber = 0;
				}

				if (f2InputNumber + f3InputNumber == 0)
				{
					MessageBox(hDlg, "Divisor cannot be 0!", "Error!", MB_OK | MB_ICONERROR);
					SetDlgItemInt(hDlg, IDC_EDIT2, sum, TRUE);
					break;
				}

				sum = f1InputNumber / (f2InputNumber + f3InputNumber);
				sprintf_s(inputStringNumbers, "%f", sum);
				SetDlgItemText(hDlg, IDC_EDIT2, inputStringNumbers);
			}

			break;
	}

	return false;
}

INT_PTR CALLBACK Timer(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)	// pri startirane na about box
{
	static int step = -100;

	switch (message)
	{
		case WM_INITDIALOG:
			{
				SetDlgItemInt(hDlg, IDC_TIMER, 0, NULL);
				SendDlgItemMessage(hDlg, IDC_PROGRESS1, PBM_SETRANGE, 0, MAKELPARAM(0, 1000));
				SendDlgItemMessage(hDlg, IDC_PROGRESS1, PBM_DELTAPOS, 1000, 0);
				return TRUE;
			}

		case WM_COMMAND:
			{
				if (LOWORD(wParam) == IDOK || LOWORD(wParam) == IDCANCEL)
				{
					EndDialog(hDlg, LOWORD(wParam));
					return TRUE;
				}

				if (LOWORD(wParam) == IDC_BUTTON1)
				{
					if (SendDlgItemMessage(hDlg, IDC_PROGRESS1, PBM_GETPOS, 0, 0) <= 0)
					{
						SetDlgItemInt(hDlg, IDC_TIMER, 0, NULL);
						SendDlgItemMessage(hDlg, IDC_PROGRESS1, PBM_DELTAPOS, 1000, 0);
					}

					SetTimer(hDlg, TIMER1, 1000, NULL);
					break;
				}

				if (LOWORD(wParam) == IDC_BUTTON2)
				{
					KillTimer(hDlg, TIMER1);
				}

				break;
			}

		case WM_TIMER:
			{
				int counter = GetDlgItemInt(hDlg, IDC_TIMER, NULL, FALSE);

				if (SendDlgItemMessage(hDlg, IDC_PROGRESS1, PBM_GETPOS, 0, 0) > 0)
				{
					counter += 1;
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

	return FALSE;
}