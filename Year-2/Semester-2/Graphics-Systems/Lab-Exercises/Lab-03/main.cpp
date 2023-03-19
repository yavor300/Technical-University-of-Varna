#include <iostream>
#include <graphics.h>
#include <math.h>

using namespace std;

struct Point {
  int x;
  int y;
};

int main()
{
  float x[] = {-10, -3, 12, 20, 24, 32, 41, 48, 55, 63};
  float y[] = {13, 20, -6, 7, 18, 5, 23, 10, -3, 2};

  int n=sizeof(x)/sizeof(x[0]);
  int i,j;

  Point points[n];
  for (i = 0; i < n; i++) {
    points[i].x = x[i];
    points[i].y = y[i];
  }

  for ( i = 0; i < n - 1; i++)
  {
    bool swapped = false;
    for (j = 0; j < n - 1 - i; j++)
    {
      if (points[j].x > points[j + 1].x)
      {
        Point temp = points[j];
        points[j] = points[j + 1];
        points[j + 1] = temp;
        swapped = true;
      }
    }
    if (!swapped) break;
  }

  int winwidth=800,winheight=900;
  initwindow(winwidth,winheight);

  int Px=600,Py=500,Dx=60,Dy=100,x0=50,y0=600;
  char text[10];

  line(x0,y0,x0+Px,y0);
  line(x0,y0,x0,y0-Py);

  float xMin = points[0].x;
  for (i = 1; i < n; i++) {
    if (points[i].x < xMin) {
      xMin = points[i].x;
    }
  }

  float xMax = points[0].x;
  for (i = 1; i < n; i++) {
    if (points[i].x > xMax) {
      xMax = points[i].x;
    }
  }

  float yMin = points[0].y;
  for (i = 1; i < n; i++) {
    if (points[i].y < yMin) {
      yMin = points[i].y;
    }
  }

  float yMax = points[0].y;
  for (i = 1; i < n; i++) {
    if (points[i].y > yMax) {
      yMax = points[i].y;
    }
  }

  float sX = (xMax - xMin) / Px;
  float sY = (yMax - yMin) / Py;

  float iP = abs(Px / Dx);
  float jP = abs(Py / Dy);

  for(i = 0; i <= iP; i++)
  {
    line(x0 + i*Dx, y0, x0+i*Dx,y0+3);   //изчертаване на деленията
    gcvt(xMin + i*Dx*sX, 5.2,text);      //преобразуване на реалната стойност, съответстваща на //делението в символен низ
    settextjustify(1,2);
    outtextxy(x0 + i*Dx, y0+5, text);// извеждане на стойността, съответстваща на делението
  }

  for(i = 0; i <= jP; i++)
  {
    line(x0, y0-i*Dy, x0-3, y0- i * Dy);  //изчертаване на деленията
    gcvt(yMin+i*Dy*sY, 5.2,text);         //преобразуване на реалната стойност, съответстваща на //делението в символен низ
    settextjustify(2,1);
    outtextxy(x0-10, y0 - Dy*i+5, text); // извеждане на стойността, съответстваща на делението
  }


  moveto(x0,y0 - (points[0].y - yMin)/sY);
  for (int i = 0; i < n; i++) {
    float startX = x0 + (points[i].x - xMin)/sX;
    float startY = y0 - (points[i].y - yMin)/sY;
    circle(startX,startY,5);
    lineto(startX,startY);
    moveto(startX,startY);
  }
  getch();
  return 0;
}


