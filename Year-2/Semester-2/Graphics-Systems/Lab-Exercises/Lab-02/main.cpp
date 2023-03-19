#include <iostream>
#include <graphics.h>

using namespace std;


void draw_circle() {
  initwindow(800,800);
  circle(200,200,100);
  getch();
}

void draw_circle_with_fill() {
  initwindow(600,600);
  setlinestyle(0,0,3);
  setcolor(15);
  circle(100,100,50);
  setfillstyle(1,12);
  floodfill(0,0,15);
  getch();
}

int main()
{
  initwindow(900,900);

  setlinestyle(0,0,3);
  setcolor(WHITE);

  arc(400,200,135,315,141);
  arc(200,400,315,135,141);
  arc(200,200,225,45,141);
  arc(400,400,45,225,141);

  setfillstyle(1,GREEN);
  floodfill(300,200,WHITE);
  putpixel(300,200,WHITE);
  settextjustify(0,0);
  outtextxy(300,200,"ab");

  setfillstyle(1,BLUE);
  floodfill(400,300,WHITE);
  putpixel(400,300,WHITE);
  outtextxy(400,300,"ab");

  setfillstyle(1,RED);
  floodfill(300,400,WHITE);
  putpixel(300,400,WHITE);
  outtextxy(300,400,"ab");

  setfillstyle(1,LIGHTBLUE);
  floodfill(200,300,WHITE);
  putpixel(200,300,WHITE);
  outtextxy(200,300,"ab");

  getch();
  return 0;
}


