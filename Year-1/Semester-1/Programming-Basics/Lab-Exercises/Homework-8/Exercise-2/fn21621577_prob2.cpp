#include <iostream>
#include <string>
using namespace std;

/*
* Задача 2
*
* Създайте програма с функция, която при въведен символен низ (латински букви)
* извежда броя на гласните букви в него.
*/
int count_vocals(string text);
int main()
{
	setlocale(LC_ALL, "BG");

	string text;
	getline(cin, text);
	printf("Броя на гласните е: %d", count_vocals(text));
}

int count_vocals(string text)
{
	int result = 0;
	for (int i = 0; i < text.length(); i++)
	{
		char symbol = tolower(text[i]);

		if (symbol == 'a' || symbol == 'e' || symbol == 'i' || symbol == 'o' || symbol == 'u') result++;
	}
	return result;
}
