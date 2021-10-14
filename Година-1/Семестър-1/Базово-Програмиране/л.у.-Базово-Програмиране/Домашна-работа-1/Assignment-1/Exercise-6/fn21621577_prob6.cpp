#include <iostream>
using namespace std;

/*
* Задача 6
* Съставете  програма, в  която  от  конзолата  се  въвежда символ (примерно латинска буква).
* Като резултат да се изведе ASCII кода на въведения символ.
*/

int main()
{
	// Въвеждане на символ
	char symbol;
	cin >> symbol;
	
	// Конвертиране на символът към неговата ASCII стойност
	short symbolASCII = (short)symbol;

	// Извеждане на ASCII стойността
	cout << symbolASCII;
}