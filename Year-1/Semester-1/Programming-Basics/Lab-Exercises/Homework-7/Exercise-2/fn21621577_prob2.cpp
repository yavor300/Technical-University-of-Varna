#include <iostream>
#include <string>
using namespace std;

/*
* Задача 2
*
* Съставете програма, която декларира стринг до 59 символа и го чете от клавиатурата.
* Ако в стринга се въвеждат само думи, разделени с интервал, да се намери и изведе броят на въведените думи. 
*/

const int SYMBOLS_COUNT = 59;

int countWords(string input);

int main()
{
	setlocale(LC_ALL, "BG");
	
	string input;
	getline(cin, input);

	if (input.length() > SYMBOLS_COUNT)
	{
		cout << "Въведеният низ е по-полям от " << SYMBOLS_COUNT << " символа.";
		return 0;
	}

	cout << "Words: " << countWords(input);;
}

int countWords(string input)
{
	if (input.empty())
	{
		return 0;
	}

	int result = 1;
	for (int i = 0; i < input.length() - 1; i++)
	{
		if (input[i] == ' ' && input[i+1] != '\n')
		{
			result++;
		}
	}
	return result;
}
