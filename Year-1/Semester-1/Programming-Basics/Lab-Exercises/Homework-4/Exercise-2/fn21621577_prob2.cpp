#include <iostream>
#include <iomanip>
using namespace std;

/*
* Задача 2
*
* Съставете програма, в която са дефинирани три променливи от тип int, double и char.
* Създайте  указатели  към  всяка  от  тях.  Изведете  размера  в  байтове  на  шестте
* променливи съгласно  модела,  показан  по-долу.
* За  оформлението  на  изхода  използвайте  форматни спецификатори.
*/

int main()
{
	int a = 42;
	double b = 3.14;
	char c = 'K';

	int* pa = &a;
	double* pb = &b;
	char* pc = &c;

	cout.setf(ios::left);
	cout << setw(10) << "Variable" << setw(10) << "size" << setw(10) << "Pointer" << setw(10) << "size" << endl;
	cout << setw(10) << "a" << setw(10) << sizeof(a) << setw(10) << "pa" << setw(10) << sizeof(pa) << endl;
	cout << setw(10) << "b" << setw(10) << sizeof(b) << setw(10) << "pb" << setw(10) << sizeof(pb) << endl;
	cout << setw(10) << "c" << setw(10) << sizeof(c) << setw(10) << "pc" << setw(10) << sizeof(pc) << endl;
}
