#include <graphics.h>
#include <iostream>
using namespace std;

/*
 *Да се представят във вид на съпоставяща вертикална хистограма (фиг.6.1)
 *данните за приетите студенти последните 4 години в три категории –
 * бакалаври, магистри и доктори в задаен графичен прозорец.
 * Всяко стълбче се образува от наслагване на три данни, които се отнасят към
 * една категория, например прием на бакалаври, магистри и англоезично обучение
 * в една учебна година. Другите стълбчета са аналогични данни за други учебни години.
 * Мащабния коефициент се определя от най-големия брой студенти (бакалаври+магистри+англоезично обучение),
 * приети през четирите наблюдавани години. В случая данните са организирани в три едномерни масива
 *с по 4 елемента. Могат да се представят и като 4 едномерни масива с по 3 елемента или
 *като двумерен масив, или да се четат от файл или клавиатура.
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

	double s = acceptedStudentsMax / Py;

	initwindow(winwidth, winheight);
	line(x0, y0, x0 + Px, y0);
	line(x0, y0, x0, y0 - Py);

	int l = Py / D;
	char text[10];
	for (int i = 0; i <= l; i++)
	{
		line(x0, y0 - i *D, x0 - 3, y0 - i *D);
		cout << i << " " << D << " " << s << endl;
		gcvt(i *D *s, 3.2, text);
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
		setfillstyle(1, RED);
		bar(x0 + i *(Ds + Dc) - Ds, y0 - (b[i - 1]) / s, x0 + i *(Ds + Dc), y0);
		setfillstyle(1, GREEN);
		bar(x0 + i *(Ds + Dc) - Ds, y0 - (b[i - 1] + m[i - 1]) / s, x0 + i *(Ds + Dc), y0 - (b[i - 1] / s));
		setfillstyle(1, YELLOW);
		bar(x0 + i *(Ds + Dc) - Ds, y0 - (b[i - 1] + m[i - 1] + a[i - 1]) / s, x0 + i *(Ds + Dc), y0 - (b[i - 1] + m[i - 1]) / s);
	}

	getch();
	return 0;
}
