#include <iostream>
#include <conio.h>
using namespace std;

bool isLetterValid(int asciiValue);

void main()
{
	setlocale(LC_ALL, "BG");

	const int lettersCount = 5;
	char letters[lettersCount];

	int lettersCounter = lettersCount;
	while (lettersCounter)
	{
		cout << "Въведи малка буква: ";
		char letter = _getche();
		cout << endl;

		if (!isLetterValid(letter)) {
			cout << "Невалидна буква!" << endl;
			continue;
		}

		lettersCounter--;
		letters[lettersCounter] = letter;
	}

	for (int i = lettersCount - 1; i >= 0; i--)
	{
		printf("Въведена %d малка буква - %c -> Голяма буква: %c\n", lettersCount - i, letters[i], char(letters[i] - 32));
	}
}

bool isLetterValid(int asciiValue)
{
	return asciiValue >= 97 && asciiValue <= 122;
}
