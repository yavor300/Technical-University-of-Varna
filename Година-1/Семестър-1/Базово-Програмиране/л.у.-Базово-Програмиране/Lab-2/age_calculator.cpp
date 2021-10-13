#include <iostream>
using namespace std;

/*
Съставете програма, която при въведена от клавиатурата година на раждане
да извежда възрастта на съответното лице през 2021 г.
Например при въведена година 1990 да извежда съобщение
„You are born in 1990, so you are 31 years old”.
*/
int main()
{
    short bornYear;
    cin >> bornYear;
    short currentYear = 2021;

    cout << "You are born in " << bornYear << ", so you are " << currentYear - bornYear << " years old.";
}
