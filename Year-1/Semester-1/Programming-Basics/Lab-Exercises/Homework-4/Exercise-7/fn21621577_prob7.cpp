#include <iostream>
#include <iomanip>
#include <Windows.h>
using namespace std;

/*
* Задача 7
*
* Създайте програма, която да съдържа кратък тест за ученици по история.
* Създайте функция която за извежда с форматни спецификатори условието на въпроса.
* Създайте символна променлива, която да съхранява отговора, подаден от учениците.
* 
* Чрез втора функция която приема въведения отговор да се проверява:
*	- ако подаденият отговор е а, да извежда съобщение „Верен отговор“,
*	- ако подаденият отговор е б и в да извежда съобщение „Грешен отговор“,
*	- при всеки друг отговор да извежда съобщение „Невалиден отговор“.
*
*/

void checkAnswer(char answer);
void printQuestion();

void main()
{
	setlocale(LC_ALL, "BG");
	SetConsoleOutputCP(1251);
	SetConsoleCP(1251);

	printQuestion();

	char answer = cin.get();
	
	checkAnswer(answer);
}

void checkAnswer(char answer)
{
	if (answer == 'а') cout << "Верен отговор";
	else if (answer == 'б' || answer == 'в') cout << "Грешен отговор";
	else cout << "Невалиден отговор";
}

void printQuestion()
{
	cout << "Васил Левски е роден през:" << endl;
	
	cout.fill('-');
	cout.setf(ios::right);

	cout << setw(13) << "a) 1837 г." << endl;
	cout << setw(13) << "б) 1778 г." << endl;
	cout << setw(13) << "в) 1901 г." << endl;
}
