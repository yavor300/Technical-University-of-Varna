#include <iostream>
#include <string>
using namespace std;

/*
* Задача 5
*
* Да се напише програма, която декларира структура студент с факултетен номер и успех.
* В main да се дефинира масив от 5 елемента от структура студент.
* Данните за студентите да се въведат от клавиатурата, след което студентите да се сортират и изведат низходящо по успех. 
*/

struct Student
{
	string id;
	double average;
};

const int ARRAY_SIZE = 5;

void input_array(Student students[]);
void sort_descending(Student arr[], Student sortarr[]);
void print_students(Student students[]);

int main()
{
	setlocale(LC_ALL, "BG");
	cout.setf(ios::fixed);
	cout.precision(2);

	Student students[ARRAY_SIZE];
	Student sorted_students[ARRAY_SIZE];
	input_array(students);
	sort_descending(students, sorted_students);
	print_students(sorted_students);
}

void input_array(Student students[])
{
	string id;
	double average;
	Student student;

	for (int i = 0; i < ARRAY_SIZE; i++)
	{
		cout << "Факултетен номер: ";
		getline(cin, id);
		cout << "Среден успех: ";
		cin >> average;
		cin.ignore();
		student.id = id;
		student.average = average;
		students[i] = student;
	}
}

void sort_descending(Student arr[], Student sortarr[])
{
	for (int i = 0; i < ARRAY_SIZE; i++) sortarr[i] = arr[i];

	for (int i = 0; i < ARRAY_SIZE - 1; i++)
	{
		bool swapped = false;
		for (int j = 0; j < ARRAY_SIZE - 1 - i; j++)
		{
			if (sortarr[j].average < sortarr[j + 1].average)
			{
				Student temp = sortarr[j];
				sortarr[j] = sortarr[j + 1];
				sortarr[j + 1] = temp;
				swapped = true;
			}
		}
		if (!swapped) break;
	}
}

void print_students(Student students[])
{
	for (int i = 0; i < ARRAY_SIZE; i++) printf("Факултетен номер: %s, Успех: %.2f\n", students[i].id.c_str(), students[i].average);
}
