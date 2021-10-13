#include <iostream>
using namespace std;

/*
Задача 7
Съставете програма която да изчисли 𝑦 = √(𝑎+2)−𝑐2
при предварително инициализирани a и c.
*/
int main()
{
    int a, c;
    cin >> a >> c;
    int result = sqrt(a + 2) - c * 2;
    cout << result;
}
