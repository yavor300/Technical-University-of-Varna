#include <iostream>
using namespace std;

/*
* Задача 9
*
* Съставете програма,която да извежда цифрата на единиците, десетиците и стотиците на
* положителни трицифрени числа. Ако се въведе число по голямо от 999 или по малко от 100,
* да се подкани за повторно въвеждане: 
*/

int edinitci(int number);
int desetitci(int number);
int stotitci(int number);
bool isNumberValid(int number);

void main()
{
	setlocale(LC_ALL, "BG");

	int number;
	cin >> number;
	while (!isNumberValid(number))
	{
		system("cls");
		cout << "Невалидно число! Въведи трицифрено число: ";
		cin >> number;
	}

	printf("Единици: %d\nДесетици: %d\nСтотици: %d", edinitci(number), desetitci(number), stotitci(number));
}

int edinitci(int number)
{
	return number % 10;
}

int desetitci(int number)
{
	return number / 10 % 10;
}

int stotitci(int number)
{
	return number / 100 % 10;
}

bool isNumberValid(int number)
{
	return number >= 100 && number <= 999;
}
