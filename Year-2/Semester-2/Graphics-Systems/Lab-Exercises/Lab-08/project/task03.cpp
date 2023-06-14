#include <graphics.h>
#include <iostream>
#include <dos.h>
#include <math.h>

/*
Окръжност с координати на центъра  О(600,600) и радиус R=60  (при initwindow(700,700);)
1. Да се придвижи окръжността  наляво  по х координатата с по един пиксел,
като се показва движението до положение на центъра О(350,600) .
2. Да се мащабира преместената окръжност  спрямо  точката с макс. у  с коефициент   Sy=4.
3. Да се завърти  малката окръжност спрямо голямата на 360 градуса,
като се показва движението през 1 градус.
*/
using namespace std;
int main()
{
	int winwidth = 700, winheigth = 700;
	initwindow(winwidth, winheigth);

	int xc = 600, yc = 600, r = 60;
	circle(xc, yc, r);

	for (int i = getmaxx() - (winwidth - xc); i > getmaxx() / 2; i--)
	{
		delay(5);
		setcolor(BLACK);
		circle(xc, yc, r);
		setcolor(WHITE);
		xc--;
		circle(xc, yc, r);
	}

	int xf, yf = yc + r;
	double sy = 4, nr = r;

	while ((yc - nr) > 180)
	{
		delay(400);
		nr = (yf - yc) *sy;
		yc = yf - (yf - yc) *sy;
		circle(xc, yc, nr);
	}

	delay(200);
	cleardevice();
	circle(xc, yc, nr);
	int y1c = yf - r;
	double x, y;
	for (int i = 0; i < 360; i++)
	{
		x = xc + (xc - xc) *cos(i *3.14 / 180) - (y1c - yc) *sin(i *3.14 / 180);
		y = yc + (xc - xc) *sin(i *3.14 / 180) + (y1c - yc) *cos(i *3.14 / 180);
		setcolor(WHITE);
		circle(x, y, r - 3);
		delay(50);
		setcolor(BLACK);
		circle(x, y, r - 3);
	}

	getch();
	return 0;
}
