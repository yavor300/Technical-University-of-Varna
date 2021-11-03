#include <iostream>
using namespace std;

int getBiggerValue(int firstNumber, int secondNumber);

int main()
{
	int a, b, c, d, max1, max2;
	cin >> a >> b >> c >> d;

	max1 = getBiggerValue(a, b);
	max2 = getBiggerValue(c, d);

	cout << "Max1: " << max1 << endl;
	cout << "Max2: " << max2;
}

int getBiggerValue(int firstNumber, int secondNumber)
{
	if (firstNumber > secondNumber) return firstNumber;
	else return secondNumber;
}
