#include <iostream>
#include <math.h>
#include <iomanip>
using namespace std;

/*
* Задача 6
*
* Съставете програма, която да изведе резултатите по-долу.
* За всяка подточка съставете функция: -използвайте
*	for за а) подточка
*	-while за б) подточка
*	-do while за в) подточка.
*/

void main()
{
	//a
	for (int i = 0; i < 5; i++)
	{
		for (int j = 4 - i; j > -1; j--)
		{
			cout << "%";
		}
		cout << endl;
	}
	
	cout << endl;

	// б
	int beginRowCounter = 0;
	while (beginRowCounter < 3)
	{
		int beginSymbolCounter = 0;
		while (beginSymbolCounter <= beginRowCounter)
		{
			cout << "%";
			beginSymbolCounter++;
		}
		cout << endl;
		beginRowCounter++;
	}

	int reverseRowCounter = 2;
	while (reverseRowCounter > 0)
	{
		int beginSymbolCounter = reverseRowCounter;
		while (beginSymbolCounter > 0)
		{
			cout << "%";
			beginSymbolCounter--;
		}
		cout << endl;
		reverseRowCounter--;
	}

	cout << endl;

	// в
	int fieldSize = 10;
	int spacesCounter = 0;
	int rows = 0;
	do
	{
		int currentSpaces = 0;
		do
		{
			if (spacesCounter == 0) break;
			cout << " ";
			currentSpaces++;
		} while (currentSpaces < spacesCounter);

		int symbolsCounter = spacesCounter;
		do
		{
			cout << "%";
			symbolsCounter++;
		} while (symbolsCounter < fieldSize - spacesCounter);

		int currentLastSpaces = 0;
		do
		{
			cout << " ";
			currentLastSpaces++;
		} while (currentLastSpaces < 10 - currentLastSpaces - symbolsCounter);

		cout << endl;

		spacesCounter++;
		rows++;
	} while (rows < 5);
}
