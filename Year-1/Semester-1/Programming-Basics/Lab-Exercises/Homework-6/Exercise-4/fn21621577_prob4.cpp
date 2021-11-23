#include <iostream>
#include <string>
using namespace std;

/*
* Задача 4
*
* Съставете програма с функция за извеждане на масива от символи в обратен ред на въвеждането.
* Функцията да приема параметри масив и броя на елементите в масива.
* В main се въвеждат и извеждат стойностите на масива.
*/

void printArray(string input);

int main()
{
	string input;
	getline(cin, input);
	printArray(input);
}

void printArray(string input)
{
	for (int i = input.length() - 1; i >= 0; i--)
	{
		cout << input[i];
	}
}
