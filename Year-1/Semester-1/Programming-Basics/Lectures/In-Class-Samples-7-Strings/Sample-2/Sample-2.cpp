#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <string>
#include <cstring>
using namespace std;

int main()
{
	char a[80];
	cin.getline(a, 80);
	cout << a << "END OF INPUT\n";
	
	char string_number[2];
	cin.getline(string_number, 2); // 12 -> 1
	int x = atoi(string_number);
	cout << x * 2 << endl;
}
