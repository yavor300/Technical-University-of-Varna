#include <iostream>
using namespace std;

int main()
{
	double number;
	cin >> number;

	if (number == 0)
	{
		cout << "You can not divide by 0";
		return 0;
	}
	
	double result = 20 / number;
	cout << result;

	return 0;
}
