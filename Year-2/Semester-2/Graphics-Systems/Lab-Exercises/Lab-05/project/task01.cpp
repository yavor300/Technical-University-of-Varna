#include <iostream>
#include <graphics.h>

using namespace std;

int main()
{

  double a[] = {-5, -12, 18, -8, 13, 23, 14, -10};
  char labels[][20] = {"a", "b", "c", "d", "e", "f", "g", "h"};
  int n = sizeof(a)/sizeof(a[0]);

  int winwidth = 800, winheight = 600;
  int pX = 600, pY = 400, d = 50, dS = 40, dC = 30, x0 = 100, y0 = 450;

  int i, x1, y1, x2, y2;


  // намиране на диапазона на изменение на данните
  double aMin = a[0];
  double aMax = a[0];
  for (int i = 1; i < n; i++) {
    if (a[i] > aMax) aMax = a[i];
    if (a[i] < aMin) aMin = a[i];
  }

  // всички стойност са положителни, разширяваме диапазона до amin=0, за да
  //може най-малкото стълбче да се види като стълбче, а не като отсечка върху скалата с надписите
  if (aMin > 0) aMin = 0;
  // всички стойност са отрицателни, разширяваме диапазона до amax=0, за да
  //може най-малкото стълбче да се види като стълбче, а не като отсечка върху оста с надписите
  if (aMax < 0) aMax = 0;

  //определяне на скалния коефициент за скалата със стойностите(вертикалната)
  double s = (aMax - aMin) / pY;

  // определяне на новото положение на хоризонталната ос,за да може да се изобразяват само
  //положителни, само отрицателни или смесени данни
  int y0n = y0 + aMin / s;

  // инициализация на графичната система чрез отваряне на графичен прозорец със зададен размер
  initwindow(winwidth,winheight);


  //изчертаване на графичния прозорец
  line(x0,y0n,x0+pX,y0n);//хоризонтална ос
  line(x0,y0,x0,y0-pY);//вертикална ос

  int iY = pY / d; // брой деления по скалата със стойностите

  char text[10];
  //изчертаване и надписване на деленията по оста със стойностите
  for(i=0; i<=iY; i++)
  {
    //изчертаване на деленията
    line(x0, y0-d*i, x0-3, y0-d*i);
    //преобразуване на реалната стойност, съответстваща на делението в символен низ
    gcvt(aMin + i*d*s,3.2, text);
    settextjustify(2,1);
    // извеждане на стойността, съответстваща на делението
    outtextxy(x0-10,y0-d*i+5, text);
  }

  //надписване на оста с надписите
  for(i=1; i<=n; i++)
  {
    settextjustify(1,2);
    // извеждане на надписите, съответстващи на данните
    outtextxy(x0+i*(dS+dC)-dS/2,y0+5, labels[i-1]);
  }

  // изобразяване на стълбчетата, съответстващи на данните
  for(i=1; i<n+1; i++)
  {
    x1=x0+i*(dS+dC)-dS;// x координата на горен ляв ъгъл
    y1=y0n-(a[i-1])/s; //y координата на горен ляв ъгъл
    x2=x0+i*(dS+dC); // x координата на долен десен ъгъл
    y2=y0n; // y координата на долен десен ъгъл
    setfillstyle(1, i+1);
    bar(x1,y1,x2,y2);
  }

    getch();
    return 0;
  }