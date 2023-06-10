#include <graphics.h>
#include <iostream>
using namespace std;

/*
 *Да се представят във вид на съпоставяща хоризонтална хистограма (фиг.6.2)
 данните за приетите студенти последните 4 години в три категории
 – бакалаври, магистри и доктори в задаен графичен прозорец.
 Условието на задачата е като в задача 6.1, но визуализираните стълбчета са хоризонтални.
 */
int main()
{
	int b[4] = { 25, 22, 18, 27 };

	int m[4] = { 5, 12, 18, 8 };

	int a[4] = { 5, 6, 9, 8 };

	char labels[][20] = { "2016", "2017", "2018", "2019" };

	int winwidth = 800;
	int winheight = 600;
	int Px = 600, Py = 500, D = 50, Ds = 60, Dc = 60, x0 = 100, y0 = 550;
	int n = sizeof(b) / sizeof(b[0]);

	int acceptedStudentsCount[n];
	for (int i = 0; i < n; i++)
	{
		acceptedStudentsCount[i] = b[i] + m[i] + a[i];
	}

	double acceptedStudentsMax = acceptedStudentsCount[0];
	for (int i = 1; i < n; i++)
	{
		if (acceptedStudentsCount[i] > acceptedStudentsMax) acceptedStudentsMax = acceptedStudentsCount[i];
	}

	double s = acceptedStudentsMax / Px;

	initwindow(winwidth, winheight);
	line(x0, y0, x0 + Px, y0);
	line(x0, y0, x0, y0 - Py);

	int l = Px / D;
	char text[10];
	for (int i = 0; i <= l; i++)
	{
		line(x0 + i *D, y0, x0 + i *D, y0 + 3);
		gcvt(i *D *s, 3.2, text);
		settextjustify(1, 2);
		outtextxy(x0 + i *D, y0 + 5, text);
	}

	for (int i = 1; i <= n; i++)
	{
		settextjustify(2, 1);
		outtextxy(x0 - 10, y0 - i *(Ds + Dc) + Ds / 2, labels[i - 1]);
	}

	for (int i = 1; i < n + 1; i++)
	{
		setfillstyle(1, RED);
		bar(x0, y0 - i *(Ds + Dc), x0 + (b[i - 1]) / s, y0 - i *(Ds + Dc) + Ds);
		setfillstyle(1, GREEN);
		bar(x0 + b[i - 1] / s, y0 - i *(Ds + Dc), x0 + (b[i - 1] + m[i - 1]) / s, y0 - i *(Ds + Dc) + Ds);
		setfillstyle(1, YELLOW);
		bar(x0 + (b[i - 1] + m[i - 1]) / s, y0 - i *(Ds + Dc), x0 + (b[i - 1] + m[i - 1] + a[i - 1]) / s, y0 - i *(Ds + Dc) + Ds);
	}

	getch();
	return 0;
}
