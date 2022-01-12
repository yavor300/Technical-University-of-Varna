#include <iostream>
#include <iomanip>
#include <time.h>
#include <Windows.h>
using namespace std;

const int DAYS = 5;
const int WORKING_HOURS = 8;
const double INITIAL_SUM = 0;

const double MIN_PRODUCT_PRICE = 1.00;
const double MAX_PRODUCT_PRICE = 40.00;

void fill_in_matrix(double B[][WORKING_HOURS]);
void print_matrix(double B[][WORKING_HOURS]);
void find_max_amount_for_each_day(double sells[][WORKING_HOURS], double max_daily_amount[]);
void caclulate_total_amount_for_each_day(double sells[][WORKING_HOURS], double total_daily_amount[]);
void print_data_for_each_week(double max_daily_amount[], double total_daily_amount[]);
double calculate_weekly_sales_value(double total_daily_amount[]);
double find_max_weekly_amount(double max_daily_amount[]);

/*
* Условие на задача: https://drive.google.com/file/d/1-EjPsmDMtZXUYYizM7qss86QFVhDQGnU/view?usp=sharing
*/
int main()
{
	setlocale(LC_ALL, "BG");
	SetConsoleOutputCP(1251);

	double sells[DAYS][WORKING_HOURS];
	fill_in_matrix(sells);

	cout << "Реализирани продажби:\n\n";
	print_matrix(sells);

	double max_daily_amount[DAYS];
	double total_daily_amount[DAYS];

	find_max_amount_for_each_day(sells, max_daily_amount);
	caclulate_total_amount_for_each_day(sells, total_daily_amount);
	print_data_for_each_week(max_daily_amount, total_daily_amount);

	printf("\nОбщ седмичен оборот: %.2f лв.\nСедмична макс стойност за продажби на час: %.2f лв.\n",
		calculate_weekly_sales_value(total_daily_amount), find_max_weekly_amount(max_daily_amount));
}

void fill_in_matrix(double B[][WORKING_HOURS])
{
	srand(time(NULL));
	for (int i = 0; i < DAYS; i++)
	{
		for (int j = 0; j < WORKING_HOURS; j++)
		{
			B[i][j] = MIN_PRODUCT_PRICE + (((double)rand() / RAND_MAX) * (MAX_PRODUCT_PRICE - MIN_PRODUCT_PRICE));
		}
	}
}

void print_matrix(double B[][WORKING_HOURS])
{
	for (int i = 0; i < DAYS; i++)
	{
		for (int j = 0; j < WORKING_HOURS; j++)
		{
			cout << setw(9) << fixed << setprecision(2) << B[i][j] << " лв.";
		}
		cout << endl;
	}
}

void find_max_amount_for_each_day(double sells[][WORKING_HOURS], double max_daily_amount[])
{
	for (int i = 0; i < DAYS; i++)
	{
		double max = DBL_MIN;
		for (int j = 0; j < WORKING_HOURS; j++)
		{
			if (sells[i][j] > max) max = sells[i][j];
		}
		max_daily_amount[i] = max;
	}
}

void caclulate_total_amount_for_each_day(double sells[][WORKING_HOURS], double total_daily_amount[])
{
	for (int i = 0; i < DAYS; i++)
	{
		double sum = INITIAL_SUM;
		for (int j = 0; j < WORKING_HOURS; j++)
		{
			sum += sells[i][j];
		}
		total_daily_amount[i] = sum;
	}
}

void print_data_for_each_week(double max_daily_amount[], double total_daily_amount[])
{
	for (int i = 0; i < DAYS; i++)
	{
		printf("\nДен %d:\n  Макс за час: %.2f лв.\n  Общ дневен оборот: %.2f лв.\n", i + 1, max_daily_amount[i], total_daily_amount[i]);
	}
}

double calculate_weekly_sales_value(double total_daily_amount[])
{
	double sum = INITIAL_SUM;
	for (int i = 0; i < DAYS; i++)
	{
		sum += total_daily_amount[i];
	}
	return sum;
}

double find_max_weekly_amount(double max_daily_amount[])
{
	double max = DBL_MIN;
	for (int i = 0; i < DAYS; i++)
	{
		if (max_daily_amount[i] > max) max = max_daily_amount[i];
	}
	return max;
}