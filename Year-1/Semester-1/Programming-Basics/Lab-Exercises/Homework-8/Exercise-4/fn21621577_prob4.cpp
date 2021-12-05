#include <iostream>
#include <string>
#include <time.h>
using namespace std;

/*
* Задача 4
*
* Да се напише програма, която декларира структура за студент с факултетен номер и среден успех от следването.
* В main да се дефинира масив от 5 елемента от структура студент.
* Данните за студентите да се въведат от клавиатурата, след което да се изведе студентът с най-висок успех от следването. 
*/

struct Student
{
	string id;
	double average;
};

const int ARRAY_SIZE = 5;

void input_array(Student students[]);
void print_array(double numbers[], int& length);
Student find_student_with_highest_grade(Student students[]);

int main()
{
	setlocale(LC_ALL, "BG");
	cout.setf(ios::fixed);
	cout.precision(2);

	Student students[ARRAY_SIZE];
	input_array(students);
	Student max = find_student_with_highest_grade(students);
	printf("Факултетен номер: %s\nСреден успех: %.2f", max.id.c_str(), max.average);
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

Student find_student_with_highest_grade(Student students[])
{
	Student max = students[0];
	for (int i = 1; i < ARRAY_SIZE; i++)
	{
		if (students[i].average > max.average) max = students[i];
	}
	return max;
}
