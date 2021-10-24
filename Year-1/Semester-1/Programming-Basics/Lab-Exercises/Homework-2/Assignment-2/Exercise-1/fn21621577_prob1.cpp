#include <iostream>
#include <iomanip>
using namespace std;

/*
* Задача 1
* Съставете  програма,  която  извежда  класиране  в  състезание  по художествена гимнастика
*/

void createTableRow(int rank, string gymnastName, string nation, double ribbon, double ball, double batons, double hoop, double total);
void createTableHead(string columns[]);

int main()
{
    cout.setf(ios::left);
    cout.setf(ios::fixed);
    cout.precision(3);
    string columns[] = { "Rank", "Gymnast", "Nation", "Ribbon", "Ball", "Batons", "Hoop", "Total"};
    
    createTableHead(columns);
    createTableRow(1, "Dina Averina", "Russia", 21.650, 22.950, 23.000, 23.800, 91.400);
    createTableRow(2, "Arina Averina", "Russia", 20.850, 23.100, 24.050, 23.100, 91.100);
    createTableRow(3, "Linoy Ashra", "Israel", 21.050, 23.100, 23.500, 22.050, 89.700);
    createTableRow(4, "Boryana Kaleyn", "Bulgaria", 19.900, 22.400, 22.350, 21.625, 86.275);
    createTableRow(5, "Vlada Nikolchenko", "Ukraine", 19.450, 22.250, 19.500, 22.950, 84.150);
}

void createTableRow(int rank, string gymnastName, string nation, double ribbon, double ball, double batons, double hoop, double total)
{
    cout << setw(5) << rank << setw(18) << gymnastName << setw(9) << nation << setw(7) << ribbon << setw(7) << ball
        << setw(7) << batons << setw(7) << hoop << setw(7) << total << endl;
}

void createTableHead(string columns[])
{
    cout << setw(5) << columns[0] << setw(18) << columns[1] << setw(9) << columns[2] << setw(7) << columns[3] << setw(7) << columns[4]
        << setw(7) << columns[5] << setw(7) << columns[6] << setw(7) << columns[7] << endl;
}
