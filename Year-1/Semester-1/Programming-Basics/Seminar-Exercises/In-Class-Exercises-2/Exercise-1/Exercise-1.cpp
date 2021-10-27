#include <iostream>
using namespace std;

int main()
{
    setlocale(LC_ALL, "BG");

    cout << "Въведете три числа за ден, месец, година" << endl;
    int d, m, g, days;
    cin >> d >> m >> g;

    switch (m)
    {
    case 1: case 3: case 5: case 7: case 8: case 10: case 12:
        days = 31;
        break;

    case 4: case 6: case 9: case 11:
        days = 30;
        break;

    case 2:
        if (g % 4 == 0 && g % 100 == 0) days = 29;
        else days = 28;
        break;

    default:
        cout << "Невалиден месец.";
        return 0;
    }

    if (d == days)
    {
        d = 1;
        if (m == 12)
        {
            m = 1;
            g++;
        }
        else
        {
            m++;
        }
    }
    else
    {
        d++;
    }

    printf("%d.%d.%d", d, m, g);

    return 0;
}
