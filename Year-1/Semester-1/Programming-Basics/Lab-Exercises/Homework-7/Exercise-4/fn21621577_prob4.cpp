#include <iostream>
#include <string>
#include <Windows.h>
using namespace std;

/*
* Задача 4
*
* В клуба по литература спорят коя от двете гласни се среща най-често в речта – а или о.
* Напише програма с масив, която приема през клавиатурата символен низ (string) и с помощта
* на функция да извежда коя от двете букви се среща по-често в него. При равенство да изписва нужното съобщение.
*/

const int SYMBOLS_COUNT = 59;

int countLetter(string input, char letter);

int main()
{
	setlocale(LC_ALL, "BG");
	SetConsoleOutputCP(1251);
	SetConsoleCP(1251);

	string input;
	getline(cin, input);

	int occuranceO = countLetter(input, 'о');
	int occuranceA = countLetter(input, 'а');

	if (occuranceO > occuranceA) cout << "О се среща по-често";
	else if (occuranceA > occuranceO) cout << "А се среща по-често";
	else cout << "Равенство";

}

int countLetter(string input, char letter)
{
	int count = 0;
	for (int i = 0; i < input.length(); i++)
	{
		if (tolower(input[i]) == tolower(letter)) count++;
	}
	return count;
}
