#include <iostream>
#include <string>
using namespace std;

/*
* Задача 5
*
* Съставете програма с функция за извеждане на ASCII кодовете на масив от символи.
* Функцията да приема параметри масив и броя на елементите в масива.
* В main се въвеждат и извеждат стойностите на масива.
*/

void printAsciiValues(string input);

int main()
{
	string input;
	getline(cin, input);
	printAsciiValues(input);
}

void printAsciiValues(string input)
{
	for (int i = 0; i < input.length(); i++)
	{
		cout << input[i] << " -> " << (int) input[i] << endl;
	}
}
