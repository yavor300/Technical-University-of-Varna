#include <iostream>

#include <graphics.h>

#include <math.h>

#include <cmath>

using namespace std;

/*
 * 1. Да се представи функцията y =sin(x) в диапазонa -3.14<=x<=3.14
 * и при брой стойности 65.
 * Графичен прозорец: x0=140, y0=500, Px=400, Py=350, Dx=80 , Dy=70 ;
 */
int main() {
  double xMin = -3.14;
  double xMax = 3.14;
  int n = 65;
  double x[n];
  double y[n];

  int x0 = 140, y0 = 500, Px = 400, Py = 350, Dx = 80, Dy = 70;
  initwindow(800, 600);

  int iP = Px / Dx;
  int jP = Py / Dy;

  double dx = (xMax - xMin) / (n - 1);

  // запълване на x[] и y[] със стойности
  for (int i = 0; i < n; i++) {
    x[i] = xMin + i * dx;
    y[i] = sin(x[i]);
  }

  double yMin = y[0];
  double yMax = y[0];

  for (int i = 1; i < n; i++) {
    if (y[i] < yMin) yMin = y[i];
    if (y[i] > yMax) yMax = y[i];
  }

  line(x0, y0, x0 + Px, y0);
  line(x0, y0, x0, y0 - Py);

  double sX = (xMax - xMin) / Px;
  double sY = (yMax - yMin) / Py;

  // изчертаване и надписване на деленията
  char text[10];
  for (int i = 0; i <= iP; i++) {
    line(x0 + i * Dx, y0, x0 + i * Dx, y0 + 3);
    gcvt(xMin + i * Dx * sX, 5.2, text);
    settextjustify(1, 2);
    outtextxy(x0 + i * Dx, y0 + 5, text);
  }

  for (int i = 0; i <= jP; i++) {
    line(x0, y0 - i * Dy, x0 - 3, y0 - i * Dy);
    gcvt(yMin + i * Dy * sY, 4.2, text);
    settextjustify(2, 1);
    outtextxy(x0 - 10, y0 - i * Dy + 5, text);
  }

  for (int i = 0; i < n; i++) {
    int xprim = x0 + (x[i] - xMin) / sX;
    int yprim = y0 - (y[i] - yMin) / sY;
    circle(xprim, yprim, 2);
  }

  for (int i = 0; i < n - 1; i++) {
    int xa = x0 + (x[i] - xMin) / sX;
    int ya = y0 - (y[i] - yMin) / sY;
    int xb = x0 + (x[i + 1] - xMin) / sX;
    int yb = y0 - (y[i + 1] - yMin) / sY;
    line(xa, ya, xb, yb);
  }

  getch();
  return 0;
}
