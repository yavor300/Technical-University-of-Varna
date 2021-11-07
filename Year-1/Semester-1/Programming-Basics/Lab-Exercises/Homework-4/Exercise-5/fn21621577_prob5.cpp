#include <iostream>
using namespace std;

/*
* Задача 5
*
* Създайте  програма,  която  да  следи  паричните  наличности  на  Богаташ  Богаташев  в рамките на месец.
* Променлива от подходящ тип да се инициализира със сумата от 5000 лв. (наличност в началото на месеца).
* Добавете две функции: за получаване на месечна заплата salary(), която да увеличава сумата с 3000 лв.
* и за извършване на ежеседмични разходи costs(), която да намалява сумата с 500 лв. (приемаме, че в месеца има 4 седмици).
* Програмата да изведе паричната наличност в края на месеца, предвид входящите и изходящи парични потоци.
*/

const double salaryValue = 3000;
const double costsValue = 500;
const int weeks = 4;

void salary(double& money);
void costs(double& money);

void main()
{
	double initialMoney = 5000;

	for (int i = 0; i < weeks; i++)
	{
		costs(initialMoney);
	}

	salary(initialMoney);

	cout << "Money at the end of the month: " << initialMoney;
}

void salary(double& money)
{
	money += salaryValue;
}

void costs(double& money)
{
	money -= costsValue;
}
