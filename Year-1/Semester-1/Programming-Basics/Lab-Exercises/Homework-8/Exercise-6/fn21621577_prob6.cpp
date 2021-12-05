#include <iostream>
#include <string>
using namespace std;

/*
* Задача 6
*
* Да се напише програма, която декларира структура студент с факултетен номер и масив от 5 цели числа,
* в който да се съхраняват оценките му от първи семестър на обучението му.
* С функции за:
*	Въвеждане на структурата. Функцията не приема параметри. Валидни оценки да бъдат само цели числа между 2 и 6.
*		Връща като резултат променлива от типа на структурата.
*	Извеждане на средния успех на студента.
*		Приема като параметър променлива от типа на структурата и връща като резултат средния успех на студента. 
*/
const int GRADES_COUNT = 5;

struct Student
{
	string id;
	double grades[GRADES_COUNT];
};


Student create_student();
void input_array(Student students[]);
void sort_descending(Student arr[], Student sortarr[]);
void print_student(Student student);

int main()
{
	setlocale(LC_ALL, "BG");
	cout.setf(ios::fixed);
	cout.precision(2);

	Student student = create_student();
	print_student(student);
}

Student create_student()
{
	string id;
	double grade;
	Student student;

	cout << "Факултетен номер: ";
	getline(cin, id);
	student.id = id;

	for (int i = 0; i < GRADES_COUNT; i++)
	{
		do
		{
			cout << "Въведете валидна оценка: ";
			cin >> grade;
		} while (grade < 2 || grade > 6);
		
		student.grades[i] = grade;
	}

	return student;
}

double calculate_average(Student student)
{
	double sum = 0;
	for (int i = 0; i < GRADES_COUNT; i++) sum += student.grades[i];
	return sum / GRADES_COUNT;
}

void print_student(Student student)
{
	printf("Факултетен номер: %s, Успех: %.2f\n", student.id.c_str(), calculate_average(student));
}
