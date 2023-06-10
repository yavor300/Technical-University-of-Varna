#include <iostream>
#include <graphics.h>
using namespace std;

int main()
{
	double a[] = { -5, -12, 18, 8, 13, 23, 14, 10 };

	char labels[][20] = { "a", "b", "c", "d", "e", "f", "g", "h" };

	int Px = 600, Py = 400, D = 50, Ds = 40, Dc = 30, x0 = 100, y0 = 450;

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
	int y0n = y0 + aMin / s;

	int winwidth = 800, winheight = 600;
	initwindow(winwidth, winheight);
	line(x0, y0n, x0 + Px, y0n);
	line(x0, y0, x0, y0 - Py);

	int l = Py / D;
	char text[10];

	for (int i = 0; i <= l; i++)
	{
		line(x0, y0 - i *D, x0 - 3, y0 - D *i);
		gcvt(aMin + i *D *s, 3.2, text);
		settextjustify(2, 1);
		outtextxy(x0 - 10, y0 - D *i + 5, text);
	}

	for (int i = 1; i <= n; i++)
	{
		settextjustify(1, 2);
		outtextxy(x0 + i *(Ds + Dc) - Ds / 2, y0 + 5, labels[i - 1]);
	}

	for (int i = 1; i < n + 1; i++)
	{
		int x1 = x0 + i *(Ds + Dc) - Ds;
		int y1 = y0n - (a[i - 1]) / s;
		int x2 = x0 + i *(Ds + Dc);
		int y2 = y0n;
		setfillstyle(1, i + 1);
		bar(x1, y1, x2, y2);
	}

	getch();

	return 0;
}
