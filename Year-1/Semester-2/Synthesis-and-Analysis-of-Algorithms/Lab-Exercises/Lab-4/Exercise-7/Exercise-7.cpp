#include <iostream>
using namespace std;

/*
* Задача 7
*
* Създаване на назъбена матрица с употребата на двоен указател
*/

void main()
{
	int** par2;
	par2 = new int*[4];

	par2[0] = new int[2]{ 1, 2 };
	par2[1] = new int[3]{ 4, 5, 6 };

	cout << par2[0][0] << endl;
	cout << par2[0][1] << endl;
	cout << par2[1][0] << endl;
	cout << par2[1][1] << endl;
	cout << par2[1][2] << endl;

	delete[]par2;
}
