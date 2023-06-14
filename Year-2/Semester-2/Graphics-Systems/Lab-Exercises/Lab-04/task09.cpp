#include <iostream>

#include <graphics.h>

#include <math.h>

#include <cmath>

using namespace std;

/*
 * 5. Да се представи функцията y =sin(x) в диапазонa -3.14<=x<=3.14
 * и при брой стойности 65.
 * Графичен прозорец: x0=140, y0=500, Px=400, Py=350, Dx=80 , Dy=70 ;
 * В същия прозорец да се добави и функцията у1=cos(x)
 * за -3.14<=x<=3.14 и брой стойности -87.
 */
int main() {
  double x1Min = -3.14;
  double x1Max = 3.14;
  double x2Min = x1Min;
  double x2Max = x1Max;
  int n = 65;
  int n2 = 87;
  double x[n];
  double y[n];
  double x2[n2];
  double y2[n2];

  int x0 = 140, y0 = 500, Px = 400, Py = 350, Dx = 80, Dy = 70;

  int iP = Px / Dx;
  int jP = Py / Dy;

  initwindow(800, 600);
  line(x0, y0, x0 + Px, y0);
  line(x0, y0, x0, y0 - Py);

  double dx = (x1Max - x1Min) / (n - 1);
  double dx2 = (x2Max - x2Min) /(n2 - 1);

  // запълване на x[] и y[] със стойности
  for (int i = 0; i < n; i++) {
    x[i] = x1Min + i * dx;
    y[i] = sin(x[i]);
  }

  for (int i = 0; i < n2; i++)
  {
    x2[i] = x2Min + i * dx2;
    y2[i] = cos(x2[i]);
  }

  double y1Min = y[0];
  double y1Max = y[0];

  for (int i = 1; i < n; i++) {
    if (y[i] < y1Min) y1Min = y[i];
    if (y[i] > y1Max) y1Max = y[i];
  }

  double y2Min = y2[0];
  double y2Max = y2[0];

  for (int i = 1; i < n; i++)
  {
    if (y2Min < y2[i]) y2Min = y2[i];
    if (y2Max > y2[i]) y2Max = y2[i];
  }

  double xMin = x1Min;
  double xMax = x1Max;

  if (x2Min < x1Min) xMin = x2Min;
  if (x2Max > x1Max) xMax = x2Max;

  double yMin = y1Min;
  double yMax = y1Max;

  if (y2Min < y1Min) yMin = y2Min;
  if (y2Max > y1Max) yMax = y2Max;

  double sX = (xMax - xMin) / Px;
  double sY = (yMax - yMin) / Py;

  // изчертаване и надписване на деленията
  char text[10];
  for (int i = 0; i <= iP; i++) {
    line(x0 + i * Dx, y0, x0 + i * Dx, y0 + 3);
    gcvt(x1Min + i * Dx * sX, 5.2, text);
    settextjustify(1, 2);
    outtextxy(x0 + i * Dx, y0 + 5, text);
  }

  for (int i = 0; i <= jP; i++) {
    line(x0, y0 - i * Dy, x0 - 3, y0 - i * Dy);
    gcvt(y1Min + i * Dy * sY, 4.2, text);
    settextjustify(2, 1);
    outtextxy(x0 - 10, y0 - i * Dy + 5, text);
  }

  for (int i = 0; i < n; i++) {
    int xprim = x0 + (x[i] - xMin) / sX;
    int yprim = y0 - (y[i] - yMin) / sY;
    circle(xprim, yprim, 2);
  }

  for (int i = 0; i < n - 1; i++) {
    int xa = x0 + (x[i] - x1Min) / sX;
    int ya = y0 - (y[i] - y1Min) / sY;
    int xb = x0 + (x[i + 1] - x1Min) / sX;
    int yb = y0 - (y[i + 1] - y1Min) / sY;
    line(xa, ya, xb, yb);
  }

  setcolor(YELLOW);
  for (int i = 0; i < n2; i++)
  {
    int xprim = x0 + (x2[i] - xMin) / sX;
    int yprim = y0 - (y2[i] - yMin) / sY;
    circle(xprim, yprim, 2);
  }

  for (int i = 0; i < n2 - 1; i++)
  {
    int xa = x0 + (x2[i] - xMin) / sX;
    int ya = y0 - (y2[i] - yMin) / sY;
    int xb = x0 + (x2[i + 1] - xMin) / sX;
    int yb = y0 - (y2[i + 1] - yMin) /sY;
    line(xa, ya, xb, yb);
  }

  getch();
  return 0;
}

