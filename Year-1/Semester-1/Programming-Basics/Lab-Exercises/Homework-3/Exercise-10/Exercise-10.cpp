#include <iostream>
using namespace std;

/*
* Задача 9
*
* Съставете програма с функция за извеждане на възрастов период.
* Функцията приема като входен параметър цяло число възраста на дете:
*	Ако възрастта е в интервала (0;1) в конзолата да се изпише Baby
*	Ако възрастта е в интервала [1;3) в конзолата да се изпише Toddler
*	Ако възрастта е в интервала [3;5) в конзолата да се изпише Preschool
*	Ако възрастта е в интервала [5;12] в конзолата да се изпише Gradeschooler
*	Ако възрастта е в интервала [13;18) в конзолата да се изпише Teen
*	Ако възрастта е в интервала [18;21) в конзолата да се изпише Young Adult
*	Ако възрастта е по-голяма от 21 в конзолата да се изпише Adult
*
*/

#define distance 987.0

double inputNumber();
string getAgePeriod(int age);
bool isAgeValid(int age);

void main()
{
	cout << "Enter your age: ";

	int age;
	cin >> age;

	if (!isAgeValid(age))
	{
		cout << "Invalid age";
		return;
	}

	cout << "Age period: " << getAgePeriod(age);
}

string getAgePeriod(int age)
{
	if (age == 0)
	{
		cout << "Baby";
	}
	else if (age >= 1 && age < 3)
	{
		return "Toddler";
	}
	else if (age >= 3 && age < 5)
	{
		return "Preschool";
	}
	else if (age >= 5 && age <= 12)
	{
		return "Gradeschooler";
	}
	else if (age >= 13 && age < 18)
	{
		return "Teen";
	}
	else if (age >= 18 && age < 21)
	{
		return "Young Adult";
	}
	else
	{
		return "Adult";
	}

}

bool isAgeValid(int age)
{
	return age >= 0;
}

double inputNumber()
{
	double number;
	cin >> number;
	return number;
}
