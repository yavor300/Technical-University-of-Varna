#include <iostream>
#include <stdio.h>
using namespace std;

int main()
{
    double number = 35443545443.1434234234;
    int number2 = -1;
    char ch = 'A';
    char str[] = "Hello!";
    printf("\t%.3f\n\t%.3E\n\t%.3G\n\t%u\n\t%.2f\n\t%c\n\t%s", number, number, number, number2, number, ch, str);
}
