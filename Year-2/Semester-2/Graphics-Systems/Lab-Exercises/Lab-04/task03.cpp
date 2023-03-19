#include <iostream>
#include <graphics.h>
#include <math.h>

using namespace std;

struct Point {
  double x;
  double y;
};

int main()
{
  // y = 3*x^2+10*x-100
  // -6 <= x <= 10
  int n = 40;
  double xMaxValue = 10;
  double xMinValue = -6;
  double dx =(xMaxValue - xMinValue) / (n - 1);


  double x[n];
  double y[n];

  int i,j;
  for (i = 0; i < n; i++)
  {
    x[i] = xMinValue + i * dx ;
    y[i] = 3*x[i]*x[i] + 10*x[i] -100;  //3*x^2+10*x-100
  }

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

  int Px=600,Py=600,Dx=60,Dy=60,x0=50,y0=800;
  char text[10];

  //line(x0,y0,x0+Px,y0);
  //line(x0,y0,x0,y0-Py);

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

  int x0n=x0;
  if (xMin<0) x0n=x0+(0-xMin)/sX;
  int y0n=y0;
  if (yMin<0) y0n=y0-(0-yMin)/sY;
  line(x0,y0n,x0+Px,y0n);//хоризонтална ос
  line(x0n,y0,x0n,y0-Py);//вертикална ос

  for(i = 0; i <= iP; i++)
  {
    line(x0 + i*Dx, y0n, x0+i*Dx,y0n+3);
    //line(x0 + i*Dx, y0, x0+i*Dx,y0+3);   //изчертаване на деленията
    gcvt(xMin + i*Dx*sX, 5.2,text);      //преобразуване на реалната стойност, съответстваща на //делението в символен низ
    settextjustify(1,2);
    outtextxy(x0 + i*Dx-10, y0n+5, text);
    //outtextxy(x0 + i*Dx, y0+5, text);// извеждане на стойността, съответстваща на делението
  }

  for(i = 0; i <= jP; i++)
  {
    line(x0n, y0-i*Dy, x0n-3, y0- i * Dy);
    //line(x0, y0-i*Dy, x0-3, y0- i * Dy);  //изчертаване на деленията
    gcvt(yMin+i*Dy*sY, 5.2,text);         //преобразуване на реалната стойност, съответстваща на //делението в символен низ
    settextjustify(2,1);
    outtextxy(x0n-10, y0 - Dy*i+5, text);
    //outtextxy(x0-10, y0 - Dy*i+5, text); // извеждане на стойността, съответстваща на делението
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


