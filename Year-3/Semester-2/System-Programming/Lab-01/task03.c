#include <stdio.h>

int isLeapYear(int x) {

  return (x%4==0 && x%100!=0 || x%400==0);
}

int getDaysInMonthAndYear(int month, int year) {

  switch(month) {
    case 1: case 3: case 5: case 7: case 8: case 10: case 12:
      printf("31 Days");
      break;
    case 4: case 6: case 9: case 11:
      printf("30 Days");
      break;
    case 2:
      if (isLeapYear(year)) printf("29 Days");
      else printf("28 Days");
  }
}

int main(void) {

  getDaysInMonthAndYear(1, 2024);
  return 0;
}
