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
  draw_circle_with_fill();
  return 0;
}

