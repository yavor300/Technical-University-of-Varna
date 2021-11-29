#include <iostream>
#include <string>
using namespace std;

/*
* Задача 9
*
* По повод обявен търг по Закона за обществените поръчки за закупуване на принтери са получени 7 оферти (цена за брой),
* чиито стойности са присвоени на елементи от масив, които се въвеждат с помощта на функция.
* С помощта на функции, приемащи като параметър масив и броя на елементите, намерете и изведете изведете:
* 
*	Най-ниската офертна цена и общата дължима сума при този вариант, ако се планира да се поръчат 10 000 бр.;
*	Най-високата оферта цена и общата дължима сума при този вариант, ако се планира да се поръчат 10 000 бр.;
*	Номерът на доставчика (от 1 до 7), чиято цена е най-ниска;
*	Номерът на доставчика (от 1 до 7), чиято цена е най-висока. 
*/

const int ARRAY_LENGTH = 7;

double findHighestPrice(double offers[], int& highestPriceSupplier);
double findLowestPrice(double offers[], int& lowestPriceSupplier);
void inputArray(double numbers[]);

int main()
{
	setlocale(LC_ALL, "BG");

	double offers[ARRAY_LENGTH];
	inputArray(offers);

	int lowestPriceSupplier, highestPriceSupplier;
	double lowestPrice = findLowestPrice(offers, lowestPriceSupplier);
	double highestPrice = findHighestPrice(offers, highestPriceSupplier);
	cout.setf(ios::fixed, ios::floatfield);
	cout.setf(ios::showpoint);
	cout.precision(2);
	cout << "Доставчик " << lowestPriceSupplier << " предлага най-ниска цена " << lowestPrice << " лв., " << lowestPrice * 10000 << " лв." << endl;
	cout << "Доставчик " << highestPriceSupplier << " предлага най-висока цена " << highestPrice << " лв., " << highestPrice * 10000 << " лв.";
}

double findHighestPrice(double offers[], int& highestPriceSupplier)
{
	double max = offers[0];
	highestPriceSupplier = 1;
	for (int i = 1; i < ARRAY_LENGTH; i++)
	{
		if (offers[i] > max)
		{
			max = offers[i];
			highestPriceSupplier = i + 1;
		}
	}
	return max;
}

double findLowestPrice(double offers[], int& lowestPriceSupplier)
{
	double min = offers[0];
	lowestPriceSupplier = 1;
	for (int i = 1; i < ARRAY_LENGTH; i++)
	{
		if (offers[i] < min)
		{
			min = offers[i];
			lowestPriceSupplier = i + 1;
		}
	}
	return min;
}

void inputArray(double numbers[])
{
	for (int i = 0; i < ARRAY_LENGTH; i++)
	{
		cin >> numbers[i];
	}
}
