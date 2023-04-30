// yyc577.cpp : Defines the entry point for the application.
//

#include "stdafx.h"
#include "yyc577.h"
#include  <string>

#define MAX_LOADSTRING 100

// Global Variables:
HINSTANCE hInst;								// current instance
TCHAR szTitle[MAX_LOADSTRING];					// The title bar text
TCHAR szWindowClass[MAX_LOADSTRING];			// the main window class name

// Forward declarations of functions included in this code module:
ATOM				MyRegisterClass(HINSTANCE hInstance);
BOOL				InitInstance(HINSTANCE, int);
LRESULT CALLBACK	WndProc(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK	About(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK	TestOne(HWND, UINT, WPARAM, LPARAM);

int APIENTRY _tWinMain(_In_ HINSTANCE hInstance,
                     _In_opt_ HINSTANCE hPrevInstance,
                     _In_ LPTSTR    lpCmdLine,
                     _In_ int       nCmdShow)
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
	if (!InitInstance (hInstance, nCmdShow))
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

	wcex.style			= CS_HREDRAW | CS_VREDRAW;
	wcex.lpfnWndProc	= WndProc;
	wcex.cbClsExtra		= 0;
	wcex.cbWndExtra		= 0;
	wcex.hInstance		= hInstance;
	wcex.hIcon			= LoadIcon(hInstance, MAKEINTRESOURCE(IDI_YYC577));
	wcex.hCursor		= LoadCursor(NULL, IDC_ARROW);
	wcex.hbrBackground	= (HBRUSH)(COLOR_WINDOW+1);
	wcex.lpszMenuName	= MAKEINTRESOURCE(IDC_YYC577);
	wcex.lpszClassName	= szWindowClass;
	wcex.hIconSm		= LoadIcon(wcex.hInstance, MAKEINTRESOURCE(IDI_SMALL));

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

   hInst = hInstance; // Store instance handle in our global variable

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
		case WM_RBUTTONDOWN: {
    HMENU hMenu = LoadMenu(hInst, MAKEINTRESOURCE(IDR_MENU1));
    HMENU hSubMenu = GetSubMenu(hMenu, 0);
    POINT pt = {
      LOWORD(lParam),
      HIWORD(lParam)
    };
    ClientToScreen(hWnd, & pt);
    TrackPopupMenu(hSubMenu, TPM_RIGHTBUTTON, pt.x, pt.y, 0, hWnd, NULL);
    DestroyMenu(hMenu);

    break;
  }

	case WM_COMMAND:
		wmId    = LOWORD(wParam);
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

		case ID_TEST1A_Y12: {
			HMENU hMenu = GetMenu(hWnd);

      if (GetMenuState(hMenu, 7, MF_BYCOMMAND) == -1) // Проверка за вече добавен елемент
      {
        MENUITEMINFO mii;
        ZeroMemory( & mii, sizeof(mii));
        mii.cbSize = sizeof(mii);
        mii.fMask = MIIM_ID | MIIM_TYPE | MIIM_STATE;
        mii.wID = 7;
        mii.fType = MFT_STRING;
        mii.dwTypeData = TEXT("y7");
        mii.fState = MFS_ENABLED;
        //InsertMenuItem(hMenu, IDM_ADD, TRUE, &mii);
        InsertMenuItem(hMenu, ID_TEST1A_Y4, FALSE, & mii); // Добавяне на елемент
      }
			
												}
												break;

			case ID_TEST1A_Y4: {
      HMENU hMenu = GetMenu(hWnd);
      UINT res = GetMenuState(hMenu, ID_TEST1A_Y8, MF_BYCOMMAND);
      if (res & MF_CHECKED) // Проверяваме дали Menu Item е checked
        CheckMenuItem(hMenu, ID_TEST1A_Y8, MF_BYCOMMAND | MF_UNCHECKED); // Uncheck-ваме го ако е
      else
        CheckMenuItem(hMenu, ID_TEST1A_Y8, MF_BYCOMMAND | MF_CHECKED); // Check-ваме го ако не е
      break;
    }

		case ID_TEST1A_Y6:
      MessageBox(hWnd, _T("I am y6."), _T("Info"), MB_OK | MB_ICONINFORMATION);
      break;

			case ID_TEST1A_Y8:
      MessageBox(hWnd, _T("I am y6."), _T("Info"), MB_OK | MB_ICONINFORMATION);
      break;

			case 7:
      MessageBox(hWnd, _T("I am y7."), _T("Info"), MB_OK | MB_ICONINFORMATION);
      break;

			case ID_CONTEXTMENU_NEW:
				MessageBox(hWnd, _T("I am NEW."), _T("Info"), MB_OK | MB_ICONINFORMATION);
      break;

			case ID_TEST1A_Y10: {
				DialogBox(hInst, MAKEINTRESOURCE(IDD_DIALOG1), hWnd, TestOne);
			break;
													}
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
		return (INT_PTR)TRUE;

	case WM_COMMAND:
		if (LOWORD(wParam) == IDOK || LOWORD(wParam) == IDCANCEL)
		{
			EndDialog(hDlg, LOWORD(wParam));
			return (INT_PTR)TRUE;
		}
		break;
	}
	return (INT_PTR)FALSE;
}

INT_PTR CALLBACK TestOne(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
{
		BOOL *LPt=NULL;
BOOL SIG=TRUE;
	UNREFERENCED_PARAMETER(lParam);
	switch (message)
	{
	case WM_INITDIALOG:
		SetDlgItemInt(hDlg,IDC_EDIT4, 15,TRUE);
		SetDlgItemInt(hDlg,IDC_EDIT3, 3,TRUE);
		SetDlgItemInt(hDlg,IDC_EDIT2, 8,TRUE);
		SetDlgItemText(hDlg,IDC_EDIT5,"2.33");
		SetDlgItemText(hDlg,IDC_EDIT6,"1.78");
		CheckDlgButton(hDlg,IDC_CHECK1, MFS_CHECKED);
		CheckDlgButton(hDlg,IDC_CHECK2, MFS_CHECKED);
		CheckDlgButton(hDlg,IDC_CHECK3, MFS_CHECKED);
		return (INT_PTR)TRUE;

	case WM_COMMAND:
		if (LOWORD(wParam) == IDOK || LOWORD(wParam) == IDCANCEL)
		{
			EndDialog(hDlg, LOWORD(wParam));
			return (INT_PTR)TRUE;	
		}
		else if (LOWORD(wParam) == IDC_BUTTON2)
		{
			int sum = 0;

			int firstNumber = 0;
			if (IsDlgButtonChecked(hDlg, IDC_CHECK1))
        {
					firstNumber = GetDlgItemInt(hDlg,IDC_EDIT4,LPt, SIG);
        }
			
			int secondNumber = 0;
			if (IsDlgButtonChecked(hDlg, IDC_CHECK2))
        {
					secondNumber = GetDlgItemInt(hDlg,IDC_EDIT2,LPt, SIG);
        }
			int thirdNumber = 0;
				if (IsDlgButtonChecked(hDlg, IDC_CHECK3))
        {
					thirdNumber = GetDlgItemInt(hDlg,IDC_EDIT3,LPt, SIG);
        }

				if (thirdNumber == 0) {
				  					MessageBox(hDlg,"Divisor cannot be 0!", "ERROR!", MB_OK | MB_ICONERROR);
										SetDlgItemInt(hDlg,IDC_EDIT1, sum,TRUE);
				} else {
					sum = (firstNumber + secondNumber) / thirdNumber;
					SetDlgItemInt(hDlg,IDC_EDIT1, sum,TRUE);
				}

		}
		else if (LOWORD(wParam) == IDC_BUTTON1)
		{
			int sum = 0;

			int firstNumber = 0;
			if (IsDlgButtonChecked(hDlg, IDC_CHECK1))
        {
					firstNumber = GetDlgItemInt(hDlg,IDC_EDIT4,LPt, SIG);
        }
			
			int secondNumber = 0;
			if (IsDlgButtonChecked(hDlg, IDC_CHECK2))
        {
					secondNumber = GetDlgItemInt(hDlg,IDC_EDIT2,LPt, SIG);
        }
			int thirdNumber = 0;
				if (IsDlgButtonChecked(hDlg, IDC_CHECK3))
        {
					thirdNumber = GetDlgItemInt(hDlg,IDC_EDIT3,LPt, SIG);
        }
				sum = (thirdNumber - firstNumber) * secondNumber;
				SetDlgItemInt(hDlg,IDC_EDIT1, sum,TRUE);

		}
		else if (LOWORD(wParam) == IDC_BUTTON3) {
			
			char inputStringNumbers[10], *stopstring;

			GetDlgItemText(hDlg,IDC_EDIT5,inputStringNumbers,MAX_LOADSTRING);
			double firstNumber=strtod(inputStringNumbers,&stopstring);

			GetDlgItemText(hDlg,IDC_EDIT6,inputStringNumbers,MAX_LOADSTRING);
			double second=strtod(inputStringNumbers,&stopstring);
			double sum = 2 * (firstNumber * firstNumber - second) + 1.3;

			sprintf_s(inputStringNumbers, "%8.3f",sum);
			SetDlgItemText(hDlg,IDC_EDIT7,inputStringNumbers);

			int thirdNumber = 0;
				if (IsDlgButtonChecked(hDlg, IDC_CHECK3))
        {
					thirdNumber = GetDlgItemInt(hDlg,IDC_EDIT3,LPt, SIG);
        }

				sum = 2 * (thirdNumber * thirdNumber - firstNumber) + 4.45;
				sprintf_s(inputStringNumbers, "%8.3f",sum);
			SetDlgItemText(hDlg,IDC_EDIT8,inputStringNumbers);
		}
		break;

	
	}
	return (INT_PTR)FALSE;
}

