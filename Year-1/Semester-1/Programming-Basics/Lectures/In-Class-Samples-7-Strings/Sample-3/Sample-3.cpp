#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <string>
using namespace std;

void main()
{
	string phrase;
	string adjective("fried"), noun("ants");
	string wish = "Bon appetit!";

	phrase = "I love " + adjective + " " + noun + "!";
	cout << phrase << endl
		<< wish << endl;

	string input;
	getline(cin, input, '!');
	cout << input;
}
