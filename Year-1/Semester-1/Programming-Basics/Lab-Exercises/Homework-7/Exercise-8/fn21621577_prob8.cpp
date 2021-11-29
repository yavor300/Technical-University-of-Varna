#include <iostream>
#include <string>
using namespace std;

/*
* Задача 8
*
* В училище  „Алберт  Айнщайн“  децата  получават  на  всеки  две  седмици  оценка  за работата си в
* часовете по математика. Създайте програма с функции, с помощта на които оценките на ученик Х да бъдат
* записани в масив от 10 елемента (съответстващи на 20-те седмици от срока) и да се анализира успеваемостта му.
* 
*	Функция за въвеждане на оценките - да допускат само стойности между 2 и 6;
*	С функция да се проверява дали ученикът има двойки през срока и ако да, да изведе колко на брой са те;
*	Функция  първа  двойка  –  ако  ученикът  има двойки,  да  се  изведе  през  коя  седмица  е получена първата двойка.
*	Функция последна двойка – ако ученикът има двойки, да се изведе през коя седмица е получена последната двойка. 
*/

const int GRADES_COUNT = 10;

int getWeekOfLastPoorGrade(double grades[]);
int getWeekOfFirstPoorGrade(double grades[]);
int getPoorGradesCount(double grades[]);
void inputArray(double numbers[]);
bool validateGrade(double grade);

int main()
{
	setlocale(LC_ALL, "BG");

	double grades[GRADES_COUNT];
	inputArray(grades);

	cout << "Брой двойки: " << getPoorGradesCount(grades) << endl;
	cout << "Първа двойка: седмица " << getWeekOfFirstPoorGrade(grades) << endl;
	cout << "Последна двойка: седмица " << getWeekOfLastPoorGrade(grades);
}

int getWeekOfLastPoorGrade(double grades[])
{
	for (int i = GRADES_COUNT - 1; i >= 0; i--)
	{
		if (grades[i] < 2.50) return (i + 1) * 2;
	}
	return -1;
}

int getWeekOfFirstPoorGrade(double grades[])
{
	for (int i = 0; i < GRADES_COUNT; i++)
	{
		if (grades[i] < 2.50) return (i + 1) * 2;
	}
	return -1;
}

int getPoorGradesCount(double grades[])
{
	int result = 0;
	for (int i = 0; i < GRADES_COUNT; i++)
	{
		if (grades[i] < 2.50) result++;
	}
	return result;
}

void inputArray(double numbers[])
{
	for (int i = 0; i < GRADES_COUNT; i++)
	{
		cin >> numbers[i];
	}
}

bool validateGrade(double grade)
{
	return grade >= 2. && grade <= 6.;
}
