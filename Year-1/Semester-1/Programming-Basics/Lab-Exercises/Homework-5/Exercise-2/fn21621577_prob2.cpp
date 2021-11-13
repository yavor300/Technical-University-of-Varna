#include <iostream>
using namespace std;

/*
* Задача 2
*
* Съставете  програма  с  функция,  която  да  определя  дали  едно  естествено  число
* в интервала от 2 до 20 е просто или не. Ако числото не е в интервала, да се изведе съобщение
* „Въведеното  число  не  е  в  интервала“.  Ако  числото  е  просто,  да  се  изведе  съобщение
* „Въведеното число е просто“, а ако е съставно, да се изведе съобщение „Въведеното число е съставно“. 
*/

const int startInterval = 2;
const int endInterval = 20;

string determineNumberTypeInInterval(int start, int end, int number);
bool isInInterval(int start, int end, int number);

int main()
{
    setlocale(LC_ALL, "BG");

    int number;
    cin >> number;
    
    if (!isInInterval(startInterval, endInterval, number))
    {
        cout << "Въведенето число не е в интервала";
        return 0;
    }
     
    cout << determineNumberTypeInInterval(startInterval, endInterval, number);

    return 0;
}

string determineNumberTypeInInterval(int start, int end, int number)
{
    int quotients = 0;

    for (int i = start; i <= end; i++)
    {
        if (number % i == 0) quotients++;
    }

    if (quotients == 1) return "Въведеното число е просто";
    else return "Въведеното число е съставно";
}

bool isInInterval(int start, int end, int number)
{
    return number >= start && number <= end;
}
