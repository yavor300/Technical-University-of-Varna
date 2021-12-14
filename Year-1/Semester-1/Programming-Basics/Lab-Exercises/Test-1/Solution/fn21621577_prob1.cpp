#include <iostream>
#include <time.h>
#include <string>
using namespace std;

/*
* Текущият контрол на студентите от специалност СИТ по дисциплина Х се формира от пет контролни, всяко от което може да им донесе до 20 т.
* Създайте програма, посредством която да се съхраняват и анализират резултатите на студентите.
* Предвидете структура, посредством която да се съхранява информация за:
*
* факултетният номер на студента;
* името на студента;
* получените точки на всяко от предвидените контролни.
*
* Във функцията main() създайте масив от пет елемента от типа на структурата. Съставете функции, с помощта на които:
*
* Да бъдат въведени данните на студентите. Нека получените точки да се генерират като случайни числа между 0 и 20;
* Да се изведе списък на студентите и текущият им контрол (сумата на получените точки);
* За всеки от студентите да се изведе номерът на контролното с най-висока оценка.
*/

const int STUDENTS_COUNT = 5;
const int ASSIGNMENTS_COUNT = 5;

const double RANDOM_MIN_POINTS = 0;
const double RANDOM_MAX_POINTS = 20;

const int FILL_IN_ARRAY_CHOICE = 1;
const int PRINT_STUDENTS_INFO_CHOICE = 2;
const int PRINT_BEST_ASSIGNMENT_INDEX_CHOICE = 3;
const int EXIT_FROM_MENU_CHOICE = 4;

struct Student {
	string id;
	string name;
	double points[ASSIGNMENTS_COUNT];
};

void initialize_students(Student students[]);
double sum_points(Student student);
void print_students(Student students[]);
int find_assignment_with_highest_score(double points[]);

int main()
{
    Student students[STUDENTS_COUNT];

    setlocale(LC_ALL, "BG");
    cout.setf(ios::fixed);
    int choice;
    do
    {
        printf("Въведете %d, за да добавите студенти.\nВъведете %d, за да изведете списък на студентите с техния контрол.\nВъведете %d, за да изведете номерът на контролното с най-висока оценка за всеки студент.\nВъведете %d, за да спрете програмата.\n",
            FILL_IN_ARRAY_CHOICE, PRINT_STUDENTS_INFO_CHOICE, PRINT_BEST_ASSIGNMENT_INDEX_CHOICE, EXIT_FROM_MENU_CHOICE);
        do
        {
            printf("Въведете валидна меню опция [%d - %d]: ", FILL_IN_ARRAY_CHOICE, EXIT_FROM_MENU_CHOICE);
            cin >> choice;
        } while (choice < FILL_IN_ARRAY_CHOICE || choice > EXIT_FROM_MENU_CHOICE);

        switch (choice)
        {
		case FILL_IN_ARRAY_CHOICE:
			cout << endl;
			cin.ignore();
			initialize_students(students);
			break;

		case PRINT_STUDENTS_INFO_CHOICE:
			cout << endl;
			print_students(students);
			break;

		case PRINT_BEST_ASSIGNMENT_INDEX_CHOICE:
			cout << endl;
			for (int i = 0; i < STUDENTS_COUNT; i++)
			{
				printf("Факултетен номер: %s\nИме: %s\nНай-добро контролно: %d\n", students[i].id.c_str(), students[i].name.c_str(), find_assignment_with_highest_score(students[i].points));
			}
			break;
			
        }
        cout << endl;
    } while (choice != EXIT_FROM_MENU_CHOICE);
}

void initialize_students(Student students[])
{
	for (int i = 0; i < STUDENTS_COUNT; i++)
	{
		Student student;
		string id, name;

		cout << "Факултетен номер: ";
		getline(cin, id);
		cout << "Име на студента: ";
		getline(cin, name);

		student.id = id;
		student.name = name;


		srand(time(NULL));
		double min = RANDOM_MIN_POINTS;
		double max = RANDOM_MAX_POINTS;
		for (int j = 0; j < ASSIGNMENTS_COUNT; j++)
		{
			student.points[j] = min + (((double)rand() / RAND_MAX) * (max - min));
		}

		students[i] = student;
	}
}

double sum_points(Student student)
{
	double result = 0;
	for (int i = 0; i < ASSIGNMENTS_COUNT; i++)
	{
		result += student.points[i];
	}
	return result;
}

void print_students(Student students[])
{
	for (int i = 0; i < STUDENTS_COUNT; i++)
	{
		printf("Факултетен номер: %s\nИме: %s\nКонтрол: %.2f\n", students[i].id.c_str(), students[i].name.c_str(), sum_points(students[i]));
	}
}

int find_assignment_with_highest_score(double points[])
{
	int index = 0;
	double max = points[0];
	for (int i = 1; i < ASSIGNMENTS_COUNT; i++)
	{
		if (points[i] > max)
		{
			max = points[i];
			index = i;
		}
	}
	return index + 1;
}
