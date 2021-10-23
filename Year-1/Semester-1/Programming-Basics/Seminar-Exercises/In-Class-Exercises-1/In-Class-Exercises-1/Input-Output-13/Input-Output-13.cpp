#include <iostream>
using namespace std;

/*
* Задача 8
* Какво е предимството на cin.getline() пред cin >> при въвеждане на низове?
*/

void main()
{
	char fullNameWithGetLine[25];
	cin.getline(fullNameWithGetLine, 25); // Yavor Yordanov Chamov
	cout << fullNameWithGetLine << endl; // Yavor Yordanov Chamov

	char fullNameWithCin[25];
	cin >> fullNameWithCin; // Yavor Yordanov Chamov
	cout << fullNameWithCin; // Yavor
}