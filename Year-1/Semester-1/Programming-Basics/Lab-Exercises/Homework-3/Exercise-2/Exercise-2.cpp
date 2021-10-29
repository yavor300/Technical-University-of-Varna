#include <iostream>
#include <conio.h>
using namespace std;

/*
* Задача 2
* 
* Съставете програма с функции за:
*	Извеждане на съобщение „Start?“. Функцията не приема параметри и не връща стойност.
*	Извеждане на съобщение „Final!“. Функцията не приема стойност и не връща резултат.
* 
* Във функция main се извиква изписването на старт и се изчаква натискане на бутон от клавиатурата, след което се изписва финал.
*/
void printStart();
void printFinal();

int main()
{
	printStart();
	cout << endl;

	_getch();

	printFinal();
}

void printStart()
{
	cout << "Start?";
}

void printFinal()
{
	cout << "Final!";
}