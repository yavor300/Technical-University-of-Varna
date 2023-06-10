#include <graphics.h>
#include <iostream>
using namespace std;

/*
Да се представят във вид на съпоставяща вертикална хистограма (фиг.6.3) данните
за приориходи и разходи по 8 пера на една фирма в задаен графичен прозорец.
Хистограма приход/разход с вертикални стълбчета – фиг.6.3
Данните са организирани в два масива, съответно по един за приходите и за
разходите –положителни реални стойности. Скалния коефициент се определя от сумата
от максималния приход и максималния разход. Хоризонталната ос се премества на ново
положение, съответстващо на максималния разход, преобразуван в пиксели. Всеки приход се
изобразява като вертикално стълбче нагоре от новото положение на хоризонталната ос,
а съответстващия му разход – надолу от оста. Стойностите по деленията по скалата със
стойностите започват от новото положение на хоризонталната ос – на пиксел с координати
(x0,y0n) съответства стойност нула. В долния край на вертикалната ос. (координати x0,y0)
съответства стойността на максималния разход, а в горния край (координати x0,y0-Ру)
съответства стойността на максималния приход.
 */
int main()
{
	double prihodi[] = { 5, 12, 18, 8, 13, 23, 14, 10 };

	double razhodi[] = { 2, 1, 8, 3, 6, 6, 5, 4 };

	int n = sizeof(prihodi) / sizeof(prihodi[0]);
	int winwidth = 800, winheight = 600;
	int Px = 600, Py = 400, D = 50, Ds = 40, Dc = 30, x0 = 100, y0 = 450;

	double prihodimax = prihodi[0];
	double razhodimax = razhodi[0];

	for (int i = 1; i < n; i++)
	{
		if (prihodi[i] > prihodimax) prihodimax = prihodi[i];
		if (razhodi[i] > razhodimax) razhodimax = razhodi[i];
	}

	double s = (prihodimax + razhodimax) / Py;

	int y0n = y0 - razhodimax / s;

	initwindow(winwidth, winheight);

	line(x0, y0n, x0 + Px, y0n);
	line(x0, y0, x0, y0 - Py);

	int h = prihodimax / s;
	int l = h / D;

	char text[10];
	for (int i = 0; i <= l; i++)
	{
		line(x0, y0n - i *D, x0 - 3, y0n - i *D);
		gcvt(i *D *s, 3.2, text);
		settextjustify(2, 0);
		outtextxy(x0 - 5, y0n - i *D, text);
	}

	int h1 = razhodimax / s;
	int J = h1 / D;

	for (int i = 0; i <= J; i++)
	{
		line(x0, y0n + i *D, x0 - 3, y0n + i *D);
		gcvt(i *D *s, 3.2, text);
		settextjustify(2, 0);
		outtextxy(x0 - 5, y0n + i *D, text);
	}

	for (int i = 1; i < n + 1; i++)
	{
		int xa = x0 + i *(Ds + Dc) - Ds;
		int ya = y0n - prihodi[i - 1] / s;
		int xb = x0 + i *(Ds + Dc);
		int yb = y0n;
		setfillstyle(1, i);
		bar(xa, ya, xb, yb);
		xa = x0 + i *(Ds + Dc) - Ds;
		ya = y0n + razhodi[i - 1] / s;
		xb = x0 + i *(Ds + Dc);
		yb = y0n;
		setfillstyle(8, i);
		bar(xa, ya, xb, yb);
	}

	getch();
	return 0;
}
