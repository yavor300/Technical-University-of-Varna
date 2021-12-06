#include <iostream>
#include <string>
#include <time.h>
#include <math.h>
using namespace std;

/*
* Задача 8
* 
* Съставете  програма,  която  да  проследява  изменението  в  котировките  на валутите  на
* финансовите  пазари  в  рамките  на  месец.  Предвидете  структура, посредством която да се
* съхранява информация за наименованието на дадената валута, нейната котировка в началото на месеца,
* нейната котировка в края на месеца.
* Създайте  масив  с  10  елемента  от  типа  на  структурата.
* Техните  стойности  да  се въвеждат с помощта на функция,
* като задайте котировките на валутите да се запълват със случайни числа в интервала [1;20].
* Имената на валутите да се взимат от масива:
* string     currency[10]     =     {     "AUD",     "BRL",     "CAD",     "CHF", "CNY","CZK","DKK","GBP","HKD", "HRK" };
* Предвидете функция за извеждане на стойностите в масива. 
* С помощта на функция сортирайте валутите във възходящ ред съобразно абсолютната стойност на изменението на техните котировки в рамките на месеца
*/

struct Currency
{
	string name;
	double begin_month_value;
	double end_month_value;
};

const int ARRAY_SIZE = 10;

void input_array(Currency currencies[], string currency_names[]);
void print_array(Currency currencies[]);
void sort_descending(Currency currencies[], Currency sorted_currencies[]);

int main()
{
	setlocale(LC_ALL, "BG");

	Currency currencies[ARRAY_SIZE];
	Currency sorted_currencies[ARRAY_SIZE];
	string currency_names[10] = { "AUD", "BRL", "CAD", "CHF", "CNY", "CZK", "DKK", "GBP", "HKD", "HRK" };

	input_array(currencies, currency_names);
	sort_descending(currencies, sorted_currencies);
	print_array(sorted_currencies);
}

void input_array(Currency currencies[], string currency_names[])
{
	Currency currency;

	srand(time(0));
	double min = 1;
	double max = 20;

	for (int i = 0; i < ARRAY_SIZE; i++)
	{
		currency.name = currency_names[i];
		currency.begin_month_value = min + (((double)rand() / RAND_MAX) * (max - min));
		currency.end_month_value = min + (((double)rand() / RAND_MAX) * (max - min));

		currencies[i] = currency;
	}
}

void print_array(Currency currencies[])
{
	for (int i = 0; i < ARRAY_SIZE; i++) printf("Валута: %s\nKотировка в началото на месеца: %.2f\nKотировка в края на месеца: %.2f\nАбсолютна стойност: %.2f\n",
		currencies[i].name.c_str(), currencies[i].begin_month_value, currencies[i].end_month_value, abs(currencies[i].begin_month_value - currencies[i].end_month_value));
}

void sort_descending(Currency currencies[], Currency sorted_currencies[])
{
	for (int i = 0; i < ARRAY_SIZE; i++) sorted_currencies[i] = currencies[i];

	for (int i = 0; i < ARRAY_SIZE - 1; i++)
	{
		bool swapped = false;
		for (int j = 0; j < ARRAY_SIZE - 1 - i; j++)
		{
			double abs_first = abs(sorted_currencies[j].begin_month_value - sorted_currencies[j].end_month_value);
			double abs_second = abs(sorted_currencies[j + 1].begin_month_value - sorted_currencies[j + 1].end_month_value);
			if (abs_first < abs_second)
			{
				Currency temp = sorted_currencies[j];
				sorted_currencies[j] = sorted_currencies[j + 1];
				sorted_currencies[j + 1] = temp;
				swapped = true;
			}
		}
		if (!swapped) break;
	}
}
