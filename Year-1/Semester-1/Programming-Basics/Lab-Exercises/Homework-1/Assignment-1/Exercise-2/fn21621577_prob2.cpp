﻿#include <iostream>
using namespace std;

/*
Задача 2
Съставете програма, в която да се въвеждат две реални числа за
страна и височина на триъгълник и да се извеждалицето му.
*/

int main()
{
	// Деклариране на променливи за страната и височината на триъгълника
	double side, height;
	cin >> side >> height;

	// Пресмятане на лицето на триъгулника и запазване в променлива
	double area = (side * height) / 2;

	// Отпечатване лицето на тритгълника
	cout << area;
}