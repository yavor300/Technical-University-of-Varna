#include <graphics.h>
#include <iostream>
using namespace std;

/*
Да се представят във вид на съпоставяща хоризонтална хистограма (фиг.6.3)
данните за приориходи и разходи по 8 пера на една фирма в задаен графичен прозорец.
Хистограма приход/разход с хоризонтални стълбчета – фиг.6.4
Данните са организирани в два масива, съответно по един за приходите и за
разходите –положителни реални стойности. Скалния коефициент се определя от сумата
от максималния приход и максималния разход. Вертикалната ос се премества на ново
положение, съответстващо на максималния разход, преобразуван в пиксели. Всеки приход се
изобразяват като хоризонтално стълбче надясно от новото положение на вертикалната
ос, а съответстващия му разход – наляво от оста.Стойностите по деленията по скалата
със стойностите започват от новото положение на вертикалната ос – на пиксел с
координати (x0n,y0) съответства стойност нула. В левия край на хоризонталната ос
(координати x0,y0) съответства стойността на максималния разход, а в десния край
(координати x0+Рх,y0) съответства стойността на максималния приход.
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

	double s = (prihodimax + razhodimax) / Px;

	int x0n = x0 + razhodimax / s;

	initwindow(winwidth, winheight);

	line(x0, y0, x0 + Px, y0);
	line(x0n, y0, x0n, y0 - Py);

	int h = prihodimax / s;
	int l = h / D;

	char text[10];
	for (int i = 0; i <= l; i++)
	{
		line(x0n + i *D, y0, x0n + i *D, y0 + 3);
		gcvt(i *D *s, 3.2, text);
		settextjustify(1, 2);
		outtextxy(x0n + i *D, y0 + 5, text);
	}

	int h1 = razhodimax / s;
	int J = h1 / D;

	for (int i = 0; i <= J; i++)
	{
		line(x0n - i *D, y0, x0n - i *D, y0 + 3);
		gcvt(i *D *s, 3.2, text);
		settextjustify(1, 2);
		outtextxy(x0n - i *D, y0 + 5, text);
	}

	for (int i = 1; i < n + 1; i++)
	{
		int xa = x0n;
		int ya = y0 - i *(Ds + Dc);
		int xb = x0n + (prihodi[i - 1]) / s;
		int yb = y0 - i *(Ds + Dc) + Ds;
		setfillstyle(1, i);
		bar(xa, ya, xb, yb);
		xa = x0n - razhodi[i - 1] / s;
		ya = y0 - i *(Ds + Dc);
		xb = x0n;
		yb = y0 - i *(Ds + Dc) + Ds;
		setfillstyle(8, i);
		bar(xa, ya, xb, yb);
	}

	getch();
	return 0;
}
