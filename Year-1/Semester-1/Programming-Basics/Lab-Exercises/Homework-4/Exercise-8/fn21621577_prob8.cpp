#include <iostream>
#include <math.h>
using namespace std;

/*
* Задача 8
*
* Да се създаде програма в която се въвежда целочислена променлива а.
* С помощта на функция с параметър указател заменете стойността ѝ с резултата от израза |√𝑎−15|, когато а > 0,
* функцията да връща true ако променливата а > 0 и false ако а <=0 Изведете съобщение дали е позволено действието и новата стойност на a.
*
*/

void changeValue(int* a);
bool isNumberValid(int a);

int main()
{
	int a;
	cin >> a;

	if (!isNumberValid(a))
	{
		cout << "Number must be greater than or equal to zero.";
		return 0;
	}

	cout << "Number is valid." << endl;
	changeValue(&a);
	cout << "The new value of the number is " <<  a;
}

void changeValue(int* a)
{
	*a = abs(sqrt(*a) - 15);
}

bool isNumberValid(int a)
{
	return a >= 0;
}
