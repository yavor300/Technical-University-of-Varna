#include <iostream>
#include <graphics.h>
using namespace std;

/*
 * Задача за представяне на финансовите резултати на една фирма във вид на
 *хоризонтални хистограми. Разликата със задача 5.1 е, че същите входни данни
 * се изобразяват като хоризонтални стълбчета. Визуализирани са три варианта –
 *само с положителни данни (фиг.5.2а), с положителни и
 *отрицателни данни (фиг.5.2в) и само с отрицателни данни (фиг.5.2с).
 */
int main()
{
	double a[] = { 5, 12, 18, 8, 13, 23, -14, 10 };

	char labels[][20] = { "a", "b", "c", "d", "e", "f", "g", "h" };

	int Px = 600, Py = 500, D = 50, Ds = 30, Dc = 30, x0 = 100, y0 = 550;

	int n = sizeof(a) / sizeof(a[0]);

	double aMin = a[0];
	double aMax = a[0];

	for (int i = 1; i < n; i++)
	{
		if (a[i] < aMin) aMin = a[i];
		if (a[i] > aMax) aMax = a[i];
	}

	if (aMin > 0) aMin = 0;
	if (aMax < 0) aMax = 0;

	double s = (aMax - aMin) / Py;
	int x0n = x0 - aMin / s;

	int winwidth = 800, winheight = 600;
	initwindow(winwidth, winheight);
	line(x0, y0, x0 + Px, y0);
	line(x0n, y0, x0n, y0 - Py);

	int l = Px / D;
	char text[10];

	for (int i = 0; i <= l; i++)
	{
		line(x0 + i *D, y0, x0 + i *D, y0 + 3);
		gcvt(aMin + i *D *s, 3.2, text);
		settextjustify(1, 2);
		outtextxy(x0 + i *D, y0 + 5, text);
	}

	for (int i = 1; i <= n; i++)
	{
		settextjustify(2, 2);
		outtextxy(x0 - 10, y0 - i *(Ds + Dc), labels[i - 1]);
	}

	for (int i = 1; i < n + 1; i++)
	{
		int x1 = x0n;
		int y1 = y0 - i *(Ds + Dc);
		int x2 = x0n + a[i - 1] / s;
		int y2 = y0 - i *(Ds + Dc) + Ds;
		setfillstyle(1, i + 1);
		bar(x1, y1, x2, y2);
	}

	getch();

	return 0;
}
