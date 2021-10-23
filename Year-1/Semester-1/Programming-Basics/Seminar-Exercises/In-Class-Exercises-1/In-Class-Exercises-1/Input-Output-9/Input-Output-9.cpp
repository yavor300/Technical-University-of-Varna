#include <iostream>
using namespace std;

/*
* Задача 3.10
* Съставете програма за въвеждане на числа, с запитване за коректност на числото (yes/no) като използвате 2 потребителски дефинирани функции:
* newLine() за игнориране на празни интервали и край на реда и getNumber(int &number) – за въвеждане на конкретното число с въпрос за неговата коректност.
*/
void newLine();
void getInt(int &number);

void main()
{
    int n;
    getInt(n);
    cout << "The final value equals " << n << endl << "End of program";
}

void newLine()
{
    char symbol;
    do
    {
        cin.get(symbol);
    } while (symbol != '\n');
}

void getInt(int &number)
{
    char ans;
    do
    {
        cout << "Enter a number: ";
        cin >> number;
        cout << "You entered " << number << ". Is that a correct number? (yes/no): ";
        cin >> ans;
        newLine();
    } while ((ans == 'N') || (ans == 'n'));
}
