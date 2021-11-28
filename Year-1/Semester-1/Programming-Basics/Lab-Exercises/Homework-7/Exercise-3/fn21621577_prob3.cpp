#include <iostream>
#include <string>
#include <Windows.h>
using namespace std;

/*
* Задача 3
*
* Петя и Мария решават да си изпращат закодирани съобщения в социалните мрежи.
* За целта заменят всяка голяма и малка буква ‘т’ с ‘$’ и всяка голяма и малка буква ‘н’ с ‘*’.
* Създайте  програма  с  функции  за  закодиране  и  разкодиране  на  дадено  въведено  от клавиатурата съобщение,
* като предвидите меню с възможност за избор коя от двете операции да се извърши.   
*/

const int SYMBOLS_COUNT = 59;

void encodeString(string input);
void decodeString(string input);

int main()
{
	setlocale(LC_ALL, "BG");
	SetConsoleOutputCP(1251);
	SetConsoleCP(1251);

	string input, operation;
	getline(cin, input);
	getline(cin, operation);

	if (operation.compare("encode") == 0) encodeString(input);
	else if (operation.compare("decode") == 0) decodeString(input);
}

void encodeString(string input)
{
	for (int i = 0; i < input.length(); i++)
	{
		if (input[i] == 'н' || input[i] == 'Н') input[i] = '*';
		else if (input[i] == 'т' || input[i] == 'Т') input[i] = '$';
	}
	cout << input;
}

void decodeString(string input)
{
	for (int i = 0; i < input.length(); i++)
	{
		if (input[i] == '*')
		{
			if (i == 0) input[i] = 'Н';
			else input[i] = 'н';
		}
		else if (input[i] == '$') 
		{
			if (i == 0) input[i] = 'Т';
			else input[i] = 'т';
		}
	}
	cout << input;
}
