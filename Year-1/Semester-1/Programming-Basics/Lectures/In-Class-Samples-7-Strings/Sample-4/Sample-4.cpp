#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <string>
using namespace std;

void main()
{
	string first_name, last_name;
	cout << "Enter your first and last name:\n";
	cin >> first_name >> last_name;

	cout << "Your last name is spelled:\n";
	int i = 0;
	for (i = 0; i < last_name.length(); i++)
	{
		cout << last_name[i] << " ";
		last_name[i] = '-';
	}
	cout << endl;
	for (i = 0; i < last_name.length(); i++)
	{
		cout << last_name.at(i) << " ";
	}
	cout << endl;
	cout << "Good day " << first_name << endl;
}
