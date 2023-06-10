#include <graphics.h>
#include <iostream>
#include <dos.h>
#include <math.h>
using namespace std;
int main()
{
	int winwidth = 800;
	int winheight = 600;
	double xc = 50, yc = 50, r = 40;
	initwindow(winwidth, winheight);
	for (int i = 0; i < getmaxx() / 2 - 50; i++)
	{
		delay(5);
		setcolor(0);
		circle(xc, yc, r);
		setcolor(15);
		xc++;
		circle(xc, yc, r);
	}

	double xf, yf;
	xf = xc;
	yf = yc - r;	// 10
	double Sy = 1.2, nr;
	while ((yc + nr) < winheight - 100)
	{
		delay(400);
		nr = (yc - yf) *Sy;
		yc = yf + (yc - yf) *Sy;
		circle(xc, yc, nr);
	}

	delay(200);
	cleardevice();
	circle(xc, yc, nr);
	int yc1 = yf + r;
	double x, y;
	for (int i = 0; i < 360; i++)
	{
		x = xc + (xc - xc) *cos(i *3.14 / 180) - (yc1 - yc) *sin(i *3.14 / 180);
		y = yc + (xc - xc) *sin(i *3.14 / 180) + (yc1 - yc) *cos(i *3.14 / 180);
		setcolor(WHITE);
		circle(x, y, r - 3);
		delay(50);
		setcolor(BLACK);
		circle(x, y, r - 3);
	}

	getch();
	return 0;
}
