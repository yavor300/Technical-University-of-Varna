#include <graphics.h>
#include <iostream>
#include <dos.h>
#include <math.h>
using namespace std;
int main()
{
 int winwidth=800,winheight=600; // параметри на прозореца на графичната система
 double xc=50, yc=50, r = 40;//център и радиус на окръжността
 int i;
// инициализация на графичната система чрез отваряне на графичен прозорец със зададен размер
 initwindow(winwidth,winheight);

//преместване на окръжността до средата на прозореца хоризонално с показване на движението
//(движение по x)
for(i = 0; i < (getmaxx()/2 - 50); i++)
 {
delay(5);
setcolor(0);
circle(xc, yc, r);
setcolor(15);
xc++;
circle(xc, yc, r);
 }
//мащабиране на окръжността спрямо точката с минимална стойност на y - yf и коефициент на
//мащабиране Sy=1.2 (мащабиране по y)
double xf, yf;
xf = xc; yf = yc - r;
double Sy=1.2,nr;
while((yc+nr) < winheight-100)
 {
delay(400);
nr = (yc-yf) * Sy;
yc=yf+(yc-yf)*Sy;
circle(xc, yc, nr);
 }
//въртене на точка около точка - центъра на първата окръжност около центъра на последната с
//изчертаване на окръжностите
//и показване на движението
delay(200);
 cleardevice();
circle(xc, yc, nr);
int yc1=yf+r;
double x, y;
for(i = 0; i < 360; i ++)
 {
x = xc + (xc - xc)*cos(i*3.14/180) - (yc1 -yc)*sin(i*3.14/180);
y = yc + (xc - xc)*sin(i*3.14/180) + (yc1 -yc)*cos(i*3.14/180);
setcolor(WHITE);
 circle(x, y, r-3);
delay(50);
setcolor(BLACK);
 circle(x, y, r-3);
}
getch();
return 0;
}
