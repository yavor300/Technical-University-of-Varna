#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <stdio.h>
using namespace std;

int main()
{
	int* number = new int;
	printf("Enter number: ");
	scanf("%d", number);
	printf("You enrered: %d", *number);
}
