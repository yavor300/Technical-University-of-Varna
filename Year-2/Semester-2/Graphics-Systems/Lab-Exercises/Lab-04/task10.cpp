#include <iostream>

#include <graphics.h>

#include <math.h>

#include <cmath>

using namespace std;

/*
 * Да се представи функцията y=х^3-4*x^2+10x в диапазонa
 * -10<=x<=15 и при брой стойности  40. Графичен прозорец:
 * x0=140, y0=500, Px=400, Py=350, Dx=80 , Dy=70
 * Да се премести вертикалната ос през стойност x=0
 */
int main() {

  double xMin = -10;
  double xMax = 15;
  int n = 40;
  double dx = (xMax - xMin) / (n - 1);

  double x[n];
  double y[n];
  for (int i = 0; i < n; i++)
  {
    x[i] = xMin + i * dx;
    y[i] = x[i] * x[i] * x[i] - 4 * x[i] * x[i] + 10 * x[i];
  }

  double yMax = y[0];
  double yMin = y[0];

  for (int i = 1; i < n; i++)
  {
    if (y[i] > yMax) yMax = y[i];
    if (y[i] < yMin) yMin = y[i];
  }

  int x0 = 140, y0 = 500, Px = 400, Py = 350, Dx = 80, Dy = 70;
  int iP = Px / Dx;
  int jP = Py / Dy;
  double sX = (xMax - xMin) / Px;
  double sY = (yMax - yMin) / Py;

  int x0n = x0;
  if (xMin < 0) x0n = x0 + (0 - xMin) / sX;

  initwindow(800, 600);
  line(x0, y0, x0 + Px, y0);
  line(x0n, y0, x0n, y0 - Py);


  char text[10];
  for (int i = 0; i <= iP; i++)
  {
    line(x0 + i * Dx, y0, x0 + i * Dx, y0 + 3);
    gcvt(xMin + i * Dx * sX, 5.2, text);
    settextjustify(1,2);
    outtextxy(x0 + i * Dx, y0 + 5, text);
  }

  for (int i = 0; i <= jP; i++)
  {
    line(x0n, y0 - i * Dy, x0n - 5, y0 - i * Dy);
    gcvt(yMin + i * Dy * sY, 4.2, text);
    settextjustify(2, 1);
    outtextxy(x0n - 10, y0 - i * Dy, text);
  }

  for (int i = 0; i < n; i++)
  {
    int xprim = x0 + (x[i] - xMin) / sX;
    int yprim = y0 - (y[i] - yMin) / sY;
    circle(xprim, yprim, 2);
  }

  for (int i = 0; i < n - 1; i++)
  {
    int xa = x0 + (x[i] - xMin) / sX;
    int ya = y0 - (y[i] - yMin) / sY;
    int xb = x0 + (x[i + 1] - xMin) / sX;
    int yb = y0 - (y[i + 1] - yMin) / sY;
    line (xa, ya, xb, yb);
  }
  getch();
  return 0;
}

