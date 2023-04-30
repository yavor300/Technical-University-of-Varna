// Win32Project1.cpp : Defines the entry point for the application.
//

#include "stdafx.h"

#include "Win32Project1.h"

#include <vector>

#define MAX_LOADSTRING 100
typedef tagLOGBRUSH LOGBRUSH;
// Global Variables:
HINSTANCE hInst; // current instance
TCHAR szTitle[MAX_LOADSTRING]; // The title bar text
TCHAR szWindowClass[MAX_LOADSTRING]; // The title bar text
POINT pp[3];

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
  LoadString(hInstance, IDC_WIN32PROJECT1, szWindowClass, MAX_LOADSTRING);
  MyRegisterClass(hInstance);

  // Perform application initialization:
  if (!InitInstance(hInstance, nCmdShow)) {
    return FALSE;
  }

  hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_WIN32PROJECT1));

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
  wcex.hIcon = LoadIcon(hInstance, MAKEINTRESOURCE(IDI_WIN32PROJECT1));
  wcex.hCursor = LoadCursor(NULL, IDC_ARROW);
  wcex.hbrBackground = (HBRUSH)(COLOR_WINDOW + 1);
  wcex.lpszMenuName = MAKEINTRESOURCE(IDC_WIN32PROJECT1);
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
  TCHAR szHello[MAX_LOADSTRING];
  static HBRUSH oldBrush;
  static HPEN oldPen;
  static HPEN newPen;
  static POINTS ptsPrevEnd;
  static BOOL fPrevShp = FALSE;
  static int x, y;

  switch (message) {
  case WM_COMMAND:
    wmId = LOWORD(wParam);
    wmEvent = HIWORD(wParam);
    switch (wmId) {
    case IDM_ABOUT:
      DialogBox(hInst, MAKEINTRESOURCE(IDD_ABOUTBOX), hWnd, About);
      break;
    case IDM_EXIT:
      DestroyWindow(hWnd);
      break;
    default:
      return DefWindowProc(hWnd, message, wParam, lParam);
    }
    break;
  case WM_PAINT: {

    PAINTSTRUCT ps;
    HDC hdc = BeginPaint(hWnd, & ps);
    RECT rt;
    GetClientRect(hWnd, & rt);

		/**
		* Default black pen in the application.
		*/

    HPEN hpen = CreatePen(PS_SOLID, 2, RGB(0, 0, 0));

		/**
		* Pen used to remove the brush outline color.
		*/
		HPEN fencePen = CreatePen(PS_SOLID, 1, RGB(255, 255, 255));

		/**
		* Brushes for different picture elements.
		* Some of them contain bitmaps.
		*/
    HBRUSH waterColorBrush = CreateSolidBrush(RGB(0, 111, 253));
    HBRUSH windowBrush = CreatePatternBrush(LoadBitmap(hInst, (const char * ) IDB_BITMAP1));
    HBRUSH roofBrush = CreatePatternBrush(LoadBitmap(hInst, (const char * ) IDB_BITMAP2));
		HBRUSH smokeBrush = CreatePatternBrush(LoadBitmap(hInst, (const char * ) IDB_BITMAP3));
    HBRUSH fenceBrush = CreateHatchBrush(HS_CROSS, RGB(84, 0, 2));
    
		/**
		* he GetStockObject(NULL_BRUSH) function returns a handle to a null brush, which can
		* be used to unselect a previously selected brush.
		*/
		HBRUSH hNullBrush = (HBRUSH) GetStockObject(NULL_BRUSH);

    /**
     * @brief Sets the background color of the specified device context (DC).
     *
     * This function sets the background color of the device context specified
     * by `hdc` to the color specified by the RGB value `RGB(255, 255, 255)`,
     * which is white. The background color is used by text and graphics drawing
     * functions that do not specify an explicit background color.
     *
     * @param hdc A handle to the device context whose background color is to be set.
     *
     * @return If the function succeeds, the return value is the previous background
     *         color of the specified DC. If the function fails, the return value
     *         is `CLR_INVALID`.
     */
    SetBkColor(hdc, RGB(255, 255, 255));

    /**
     * @brief Draws a rectangle using the specified device context.
     *
     * This function uses the `Rectangle` function to draw a rectangle on the
     * device context specified by `hdc`. The rectangle is drawn with its
     * upper-left corner at coordinates (600, 200) and its lower-right corner
     * at coordinates (400, 400).
     *
     * @param hdc The handle to the device context to draw the rectangle on.
     */
		SelectObject(hdc, hpen);
    Rectangle(hdc, 400, 200, 600, 400);
    Rectangle(hdc, 520, 300, 570, 400);
		Rectangle(hdc, 550, 120, 580, 170);

		SelectObject(hdc, smokeBrush);
		SelectObject(hdc, CreatePen(PS_SOLID, 1, RGB(255, 255, 255)));
		Rectangle(hdc, 550, 90, 580, 120);

		/**
		* Drawing a triangle using Polygon.
		*/
    pp[0].x = 350;
    pp[0].y = 200;
    pp[1].x = 500;
    pp[1].y = 100;
    pp[2].x = 650;
    pp[2].y = 200;
    SelectObject(hdc, roofBrush);
    Polygon(hdc, pp, 3);

    SelectObject(hdc, windowBrush);
		SelectObject(hdc, hpen);
    Rectangle(hdc, 420, 230, 470, 280);

    SelectObject(hdc, waterColorBrush);
    Ellipse(hdc, 200, 400, 400, 500);

    SelectObject(hdc, fenceBrush);
    SelectObject(hdc, fencePen);
    Rectangle(hdc, 200, 300, 390, 390);
    Rectangle(hdc, 610, 300, 800, 390);

    EndPaint(hWnd, & ps);
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