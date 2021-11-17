#include <iostream>
using namespace std;

void inputNumbers(int& num1, int& num2);
void changeValues(int& num1, int& num2);
void printValues(int& num1, int& num2);

int main()
{
    setlocale(LC_ALL, "BG");

    cout << "Въведи две числа: ";
    int num1, num2;
    inputNumbers(num1, num2);

    changeValues(num1, num2);
    printValues(num1, num2);
}

void inputNumbers(int& num1, int& num2)
{
    cin >> num1;
    cin >> num2;
}

void changeValues(int& num1, int& num2)
{
    int temp = num1;
    num1 = num2;
    num2 = temp;
}

void printValues(int& num1, int& num2)
{
    cout << "Сменени на стойностите: " << num1 << " " << num2;
}