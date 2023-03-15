#include <iostream>
#include <graphics.h>

using namespace std;


void draw_circle() {
  initwindow(800, 800);
  circle(200, 200, 100);
  getch();
}

void draw_circle_with_fill() {
  initwindow(600, 600);
  setlinestyle(0, 0, 3);
  setcolor(15);
  circle(100, 100, 50);
  setfillstyle(1, 12);
  floodfill(0, 0, 15);
  getch();
}

int main()
{
  //draw_circle();
  //draw_circle_with_fill();

  initwindow(500, 500);

  // Изчертаване на окръжност, дъга от окръжност, част от кръг.
  arc(400,200,135,315,141);
  arc(200,400,315,135,141);
  arc(200,200,225,45,141);
  arc(400,400,45,225,141);

  setlinestyle(0, 0, 2);
  setfillstyle(1, 2);
  //floodfill(300,200,15);
  putpixel(300,200,15);
  settextjustify(0, 0);
  outtextxy(300, 200, "ab");


  getch();
  return 0;
}


