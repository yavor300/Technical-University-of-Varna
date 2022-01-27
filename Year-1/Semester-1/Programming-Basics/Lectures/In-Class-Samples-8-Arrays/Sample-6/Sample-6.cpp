#include <iostream>
#include <iomanip>
using namespace std;

const int NUMBER_STUDENTS = 4, NUMBER_QUIZZES = 3;

void compute_st_ave(const int grade[][NUMBER_QUIZZES], double st_ave[]);
void compute_quiz_ave(const int grade[][NUMBER_QUIZZES], double quiz_ave[]);
void display(const int grade[][NUMBER_QUIZZES], const double st_ave[],
	const double quiz_ave[]);

void main()
{
	int grade[NUMBER_STUDENTS][NUMBER_QUIZZES] = { {23.4, 65.2, 21.4}, {11, 23.12, 54.22}, {12, 87, 76.22}, {99, 100, 54.30} };
	double st_ave[NUMBER_STUDENTS];
	double quiz_ave[NUMBER_QUIZZES];
	compute_st_ave(grade, st_ave);
	compute_quiz_ave(grade, quiz_ave);
	display(grade, st_ave, quiz_ave);
}

void compute_st_ave(const int grade[][NUMBER_QUIZZES], double st_ave[])
{
	for (int st_num = 1; st_num <= NUMBER_STUDENTS; st_num++)
	{
		double sum = 0;
		for (int quiz_num = 1; quiz_num <= NUMBER_QUIZZES; quiz_num++)
		{
			sum += grade[st_num - 1][quiz_num - 1];
		}
		st_ave[st_num - 1] = sum / NUMBER_QUIZZES;
	}
}

void compute_quiz_ave(const int grade[][NUMBER_QUIZZES], double quiz_ave[])
{
	for (int quiz_num = 1; quiz_num <= NUMBER_QUIZZES; quiz_num++)
	{
		double sum = 0;
		for (int st_num = 1; st_num <= NUMBER_QUIZZES; st_num++)
		{
			sum += grade[st_num - 1][quiz_num - 1];
		}
		quiz_ave[quiz_num - 1] = sum / NUMBER_STUDENTS;
	}
}

void display(const int grade[][NUMBER_QUIZZES], const double st_ave[],
	const double quiz_ave[])
{
	cout.setf(ios::fixed);
	cout.setf(ios::showpoint);
	cout.precision(1);

	cout << setw(10) << "Student"
		<< setw(5) << "Ave"
		<< setw(15) << "Quizzes\n";

	for (int st_num = 1; st_num <= NUMBER_STUDENTS; st_num++)
	{
		cout << setw(10) << st_num
			<< setw(5) << st_ave[st_num - 1] << " ";
		for (int quiz_num = 1; quiz_num <= NUMBER_QUIZZES; quiz_num++)
		{
			cout << setw(5) << grade[st_num - 1][quiz_num - 1];
		}
		cout << endl;
	}

	cout << "Quiz averages = ";
	for (int quiz_num = 1; quiz_num <= NUMBER_QUIZZES; quiz_num++)
	{
		cout << setw(5) << quiz_ave[quiz_num - 1];
	}
	cout << endl;
}
