// project.cpp : Defines the entry point for the application.
//

#include "stdafx.h"

#include "project.h"

#define MAX_LOADSTRING 100

// Global Variables:
HINSTANCE hInst; // current instance
TCHAR szTitle[MAX_LOADSTRING]; // The title bar text
TCHAR szWindowClass[MAX_LOADSTRING]; // the main window class name

// Forward declarations of functions included in this code module:
ATOM MyRegisterClass(HINSTANCE hInstance);
BOOL InitInstance(HINSTANCE, int);
LRESULT CALLBACK WndProc(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK About(HWND, UINT, WPARAM, LPARAM);

int APIENTRY _tWinMain(_In_ HINSTANCE hInstance,
  _In_opt_ HINSTANCE hPrevInstance,
  _In_ LPTSTR lpCmdLine,
  _In_ int nCmdShow) {
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
  if (!InitInstance(hInstance, nCmdShow)) {
    return FALSE;
  }

  hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_PROJECT));

  // Main message loop:
  while (GetMessage( & msg, NULL, 0, 0)) {
    if (!TranslateAccelerator(msg.hwnd, hAccelTable, & msg)) {
      TranslateMessage( & msg);
      DispatchMessage( & msg);
    }
  }

  return (int) msg.wParam;
}

//
//  FUNCTION: MyRegisterClass()
//
//  PURPOSE: Registers the window class.
//
ATOM MyRegisterClass(HINSTANCE hInstance) {
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

  return RegisterClassEx( & wcex);
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
BOOL InitInstance(HINSTANCE hInstance, int nCmdShow) {
  HWND hWnd;

  hInst = hInstance; // Store instance handle in our global variable

  hWnd = CreateWindow(szWindowClass, szTitle, WS_OVERLAPPEDWINDOW,
    CW_USEDEFAULT, 0, CW_USEDEFAULT, 0, NULL, NULL, hInstance, NULL);

  if (!hWnd) {
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
LRESULT CALLBACK WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam) {
  int wmId, wmEvent;
  PAINTSTRUCT ps;
  HDC hdc;

  switch (message) {
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
    wmId = LOWORD(wParam);
    wmEvent = HIWORD(wParam);
    // Parse the menu selections:
    switch (wmId) {
    case IDM_ABOUT:
      DialogBox(hInst, MAKEINTRESOURCE(IDD_ABOUTBOX), hWnd, About);
      break;
    case IDM_EXIT:
      DestroyWindow(hWnd);
      break;
    case IDM_A1:
      MessageBox(hWnd, _T("I am A1."), _T("Info"), MB_OK | MB_ICONINFORMATION);
      break;
    case IDM_A2: {
      HMENU hMenu = GetMenu(hWnd);
      DeleteMenu(hMenu, IDM_A8, MF_BYCOMMAND); // Изтриване на MenuItem
      break;
    }
    case IDM_A3: {
      HMENU hMenu = GetMenu(hWnd);
      UINT res = GetMenuState(hMenu, IDM_A4, MF_BYCOMMAND);
      if (res & MF_CHECKED) // Проверяваме дали Menu Item е checked
        CheckMenuItem(hMenu, IDM_A4, MF_BYCOMMAND | MF_UNCHECKED); // Uncheck-ваме го ако е
      else
        CheckMenuItem(hMenu, IDM_A4, MF_BYCOMMAND | MF_CHECKED); // Check-ваме го ако не е
      break;
    }
    case IDM_A4: {
      HMENU hMenu = GetMenu(hWnd);
      UINT res = GetMenuState(hMenu, IDM_A7, MF_BYCOMMAND); // Проверка за състоянието на MenuItem

      if (res & MF_GRAYED) {
        EnableMenuItem(hMenu, IDM_A7, MF_BYCOMMAND | MF_ENABLED); // Enable на MenuItem
      } else {
        EnableMenuItem(hMenu, IDM_A7, MF_BYCOMMAND | MF_GRAYED); // Disable на MenuItem (може и MF_DISABLED)
      }
    }
    break;
    case IDM_A5:
      MessageBox(hWnd, _T("I am A5."), _T("Info"), MB_OK | MB_ICONINFORMATION);
      break;
    case IDM_A8:
      MessageBox(hWnd, _T("I am A8."), _T("Info"), MB_OK | MB_ICONINFORMATION);
      break;
    case IDM_A9: {
      HMENU hMenu = GetMenu(hWnd);

      if (GetMenuState(hMenu, IDM_A9 + 1, MF_BYCOMMAND) == -1) // Проверка за вече добавен елемент
      {
        MENUITEMINFO mii;
        ZeroMemory( & mii, sizeof(mii));
        mii.cbSize = sizeof(mii);
        mii.fMask = MIIM_ID | MIIM_TYPE | MIIM_STATE;
        mii.wID = IDM_A9 + 1;
        mii.fType = MFT_STRING;
        mii.dwTypeData = TEXT("A10");
        mii.fState = MFS_ENABLED;
        //InsertMenuItem(hMenu, IDM_ADD, TRUE, &mii);
        InsertMenuItem(hMenu, IDM_A2, FALSE, & mii); // Добавяне на елемент
      }
    }
    break;
    case IDM_A12: {
      MessageBox(hWnd, _T("I am A12."), _T("Info"), MB_OK | MB_ICONINFORMATION);
      break;
    }
    default:
      return DefWindowProc(hWnd, message, wParam, lParam);
    }
    break;
  case WM_PAINT:
    hdc = BeginPaint(hWnd, & ps);
    // TODO: Add any drawing code here...
    EndPaint(hWnd, & ps);
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
INT_PTR CALLBACK About(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam) {
  UNREFERENCED_PARAMETER(lParam);
  switch (message) {
  case WM_INITDIALOG:
    return (INT_PTR) TRUE;

  case WM_COMMAND:
    if (LOWORD(wParam) == IDOK || LOWORD(wParam) == IDCANCEL) {
      EndDialog(hDlg, LOWORD(wParam));
      return (INT_PTR) TRUE;
    }
    break;
  }
  return (INT_PTR) FALSE;
}