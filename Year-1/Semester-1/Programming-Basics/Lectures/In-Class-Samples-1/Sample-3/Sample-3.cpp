#include <iostream>
using namespace std;

void main()
{
	char symbol1, symbol2, symbol3 = ' ';
	cout << "Enter two initials, without any periods:\n";
	cin >> symbol1 >> symbol2;
	cout << "The two initials are:\n" << symbol1 << symbol2 << endl;
	cout << "Once more with a space:\n" << symbol1 << symbol3 << symbol2 << endl;
	cout << "That's all.";
}
