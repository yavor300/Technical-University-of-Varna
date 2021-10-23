#include <iostream>
#include <conio.h>
#include <fstream>
using namespace std;

/*
* Задача 3.11
* Съставете С/C++ програма за: въвеждане на 10 символа без „ехо”, записването им в масива bukwi,
* извеждане на масива на екрана и извеждане на символите на принтер
* (при извеждане на данни на принтер се включва заглавния файл fstream.h).
* За поелементна обработка на масива използвайте оператор за цикъл for 
*/

void main()
{
    char bukwi[10];
	cout << "Enter 10 letters :" << endl;

	// cin.get(bukwi, 11); // buffered input (Enter must be clicked)

	ofstream prn("PRN");
	for (int counter = 0; counter < sizeof(bukwi); counter++)
	{
		bukwi[counter] = _getch();
		_putch(bukwi[counter]);
		prn.put(bukwi[counter]);
	}
}