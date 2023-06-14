#include <graphics.h>
#include <iostream>
#include <math.h>

/*
Окръжност с координати на центъра  О(70,70) и радиус R=60  (при initwindow(700,700);)
1. Да се придвижи надолу  по у координатата с по един пиксел,
като се показва движението до положение на центъра О(70,350) .
2. Да се мащабира преместената окръжност  спрямо  точката с мин. х  с коефициент   Sх=4.
3. Да се завърти  малката окръжност спрямо голямата на 360 градуса,
като се показва движението през 1 градус.
*/
using namespace std;
int main()
{
	int winwidth = 700, winheigth = 700;
	initwindow(winwidth, winheigth);

	int xc = 70, yc = 70, r = 60;
	circle(xc, yc, r);

	for (int i = yc; i < 350; i++)
	{
		delay(5);
		setcolor(BLACK);
		circle(xc, yc, r);
		setcolor(WHITE);
		yc++;
		circle(xc, yc, r);
	}

	int xf = xc - r;
	double sx = 4;
	double nr = (xc - xf) *sx;
	xc = xf + (xc - xf) *sx;
	circle(xc, yc, nr);

	delay(200);
	cleardevice();
	/*
	 *(xc, yc) център на въртенето
	 */
	circle(xc, yc, nr);

	/*
	 *(x1c, yc) точка, която се върти
	 */
	int x1c = xf + r;

	for (int i = 0; i < 360; i++)
	{
		double xprim = xc + (x1c - xc) *cos(i *3.14 / 180) - (yc - yc) *sin(i *3.14 / 180);
		/*
		 *За въртене в обратна посока.
		 *double yprim = yc + (x1c - xc) *sin(-i *3.14 / 180) - (yc - yc) *sin(i *3.14 / 180);
		 */
		double yprim = yc + (x1c - xc) *sin(i *3.14 / 180) - (yc - yc) *sin(i *3.14 / 180);
		setcolor(WHITE);
		circle(xprim, yprim, r - 3);
		delay(50);
		setcolor(BLACK);
		circle(xprim, yprim, r - 3);
	}

	getch();
	return 0;
}
