// project.cpp : Defines the entry point for the application.
//

#include "stdafx.h"

#include "project.h"

#include "commctrl.h"

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
INT_PTR CALLBACK Task(HWND, UINT, WPARAM, LPARAM);

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
    case IDM_PROGRESS:
      DialogBox(hInst, MAKEINTRESOURCE(IDD_DIALOG1), hWnd, Task);
      break;
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

/**
 * Sets the ID of the timer.
 */
#define TIMER1 500

INT_PTR CALLBACK Task(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam) // pri startirane na about box
{
  static int step = 10;
  switch (message) {
  case WM_INITDIALOG: {
    SetDlgItemInt(hDlg, IDC_TIMER, 0, NULL);

    /**
     * Sets the range of a progress bar control in a dialog box.
     *
     * @param hDlg Handle to the dialog box that contains the control.
     * @param nIDDlgItem ID number of the progress bar control.
     * @param nMinValue Minimum value of the progress bar range.
     * @param nMaxValue Maximum value of the progress bar range.
     *
     * @return The return value is the result of the message sent to the control.
     */
    SendDlgItemMessage(hDlg, IDC_PROGRESS1, PBM_SETRANGE, 0, MAKELPARAM(0, 1000));

    /**
     * Advances the current position of a progress bar control in a dialog box by a specified amount.
     *
     * @param hDlg Handle to the dialog box that contains the control.
     * @param nIDDlgItem ID number of the progress bar control.
     * @param nIncrement Amount by which to advance the progress bar position.
     *
     * @return The return value is the result of the message sent to the control.
     */
    SendDlgItemMessage(hDlg, IDC_PROGRESS1, PBM_DELTAPOS, 0, 0);

    SendDlgItemMessage(hDlg, IDC_PROGRESS2, PBM_SETRANGE, 0, MAKELPARAM(0, 1000));
    SendDlgItemMessage(hDlg, IDC_PROGRESS2, PBM_DELTAPOS, 1000, 0);

    return TRUE;
  }

  case WM_COMMAND: {
    if (LOWORD(wParam) == IDOK || LOWORD(wParam) == IDCANCEL) {
      EndDialog(hDlg, LOWORD(wParam));
      return TRUE;
    }

    if (LOWORD(wParam) == IDC_BUTTON1) {
      /**
       * Sets a timer for a specified dialog box.
       *
       * @param hDlg Handle to the dialog box to set the timer for.
       * @param nIDEvent Timer identifier. This value must be unique for the specified dialog box.
       * @param uElapse Time-out value, in milliseconds.
       * @param lpTimerFunc Pointer to the function to be notified when the time-out value elapses.
       *
       * @return If the function succeeds, the return value is a nonzero integer.
       *         If the function fails, the return value is zero.
       */
			if (SendDlgItemMessage(hDlg, IDC_PROGRESS1, PBM_GETPOS, 0, 0) >= 1000
				&& SendDlgItemMessage(hDlg, IDC_PROGRESS2, PBM_GETPOS, 0, 0) <= 0) {
			break;
			}
      SetTimer(hDlg, TIMER1, 20, NULL);
      break;
    }
    if (LOWORD(wParam) == IDC_BUTTON2) {
      KillTimer(hDlg, TIMER1);
      SetDlgItemInt(hDlg, IDC_TIMER, 0, NULL);
			SendDlgItemMessage(hDlg, IDC_PROGRESS1, PBM_SETRANGE, 0, MAKELPARAM(0, 1000));
			SendDlgItemMessage(hDlg, IDC_PROGRESS1, PBM_DELTAPOS, -1000, 0);
			SendDlgItemMessage(hDlg, IDC_PROGRESS2, PBM_SETRANGE, 0, MAKELPARAM(0, 1000));
      SendDlgItemMessage(hDlg, IDC_PROGRESS2, PBM_DELTAPOS, 1000, 0);
			step = 10;
      break;
    }
    break;
  }

  case WM_TIMER: {

    int counter = GetDlgItemInt(hDlg, IDC_TIMER, NULL, FALSE);
    counter += 5;
    SetDlgItemInt(hDlg, IDC_TIMER, counter, NULL);

    if (step > 0) {
      /**
       * @brief Retrieves the current position of a progress bar control.
       *
       * This function sends a `PBM_GETPOS` message to the progress bar control
       * with the specified ID in the dialog box specified by `hDlg`. The function
       * retrieves the current position of the progress bar control and returns it.
       *
       * @param hDlg The handle to the dialog box containing the progress bar control.
       * @param idCtl The ID of the progress bar control.
       *
       * @return The current position of the progress bar control, in the range from
       *         zero to the upper limit of the progress bar control.
       */
      if (SendDlgItemMessage(hDlg, IDC_PROGRESS1, PBM_GETPOS, 0, 0) >= 1000) {
        step = -10;
        SendDlgItemMessage(hDlg, IDC_PROGRESS2, PBM_SETSTEP, step, 0);
        SendDlgItemMessage(hDlg, IDC_PROGRESS2, PBM_STEPIT, 0, 0);
        break;
      } else {
        /**
         * @brief Sets the step value for a progress bar control.
         *
         * This function sends a `PBM_SETSTEP` message to the progress bar control
         * with the specified ID in the dialog box specified by `hDlg`. The step value
         * determines how much the progress bar advances or retreats when the
         * `PBM_STEPIT` message is sent.
         *
         * @param hDlg The handle to the dialog box containing the progress bar control.
         * @param idCtl The ID of the progress bar control.
         * @param step The step value to set. A positive value increases the progress
         *             bar value when the `PBM_STEPIT` message is sent, while a negative
         *             value decreases the progress bar value.
         *
         * @return The return value is the result of the message sent to the progress
         *         bar control. The exact meaning depends on the message sent.
         */
        SendDlgItemMessage(hDlg, IDC_PROGRESS1, PBM_SETSTEP, step, 0);

        /**
         * @brief Advances a progress bar control by one step.
         *
         * This function sends a `PBM_STEPIT` message to the progress bar control
         * with the specified ID in the dialog box specified by `hDlg`. The progress
         * bar control advances by one step, where the size of each step is determined
         * by the current step value set by `PBM_SETSTEP`.
         *
         * @param hDlg The handle to the dialog box containing the progress bar control.
         * @param idCtl The ID of the progress bar control.
         *
         * @return The return value is the result of the message sent to the progress
         *         bar control. The exact meaning depends on the message sent.
         */
        SendDlgItemMessage(hDlg, IDC_PROGRESS1, PBM_STEPIT, 0, 0);
      }
    } else {
      if (SendDlgItemMessage(hDlg, IDC_PROGRESS2, PBM_GETPOS, 0, 0) > 0) {
        SendDlgItemMessage(hDlg, IDC_PROGRESS2, PBM_SETSTEP, step, 0);
        SendDlgItemMessage(hDlg, IDC_PROGRESS2, PBM_STEPIT, 0, 0);
			} else {
			KillTimer(hDlg, TIMER1);
			}
    }
  }
  }
  return FALSE;
}