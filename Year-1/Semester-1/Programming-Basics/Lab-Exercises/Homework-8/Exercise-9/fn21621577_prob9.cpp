#include <iostream>
#include <string>
#include <time.h>
#include <math.h>
using namespace std;

/*
* Задача 9
*
* Съставете програма с меню, в която да се декларира масив от 10 цели числа.
* Нека в нея да се реализират следните функционалности:
* 1.Въвеждане на стойностите на елементите от клавиатурата. Приемат се четни числа между 2 и 1000;
* 2.Извеждане на стойностите на масива
* 3.Извеждане на разликата между всеки от елементите на масива и стойността на най-малкия елемент от него
* 4.Сортиране на елементите на масива съобразно резултата от формулата  a[i]2-5*a[i]+1
* където a[i] e i-тия елемент от масива 
*/

const int ARRAY_SIZE = 10;

void input_array(int numbers[]);
void print_array(int numbers[]);
void find_difference_between_all_elements(int numbers[]);
int find_min(int numbers[]);
void sort_array(int numbers[], int sorted_numbers[]);

int main()
{
    setlocale(LC_ALL, "BG");
    int numbers[ARRAY_SIZE];
    int sorted_numbers[ARRAY_SIZE];

    int choice;
    do
    {
        printf("Въведете %d, за да добавите елементи в масива.\nВъведете %d, за да изведете стойностите на елементие в масива.\nВъведете %d, за да изведете разликата между всеки от елементите на масива и стойността на най-малкия елемент от него.\nВъведете %d, за да сортирате масива.\nВъведете %d, за да спрете програмата.\n",
            1, 2, 3, 4, 5);
        do
        {
            printf("Въведете валидна меню опция [%d - %d]: ", 1, 5);
            cin >> choice;
        } while (choice < 1 || choice > 5);

        switch (choice)
        {
        case 1:
            input_array(numbers);
            break;

        case 2:
            cout << endl;
            print_array(numbers);
            break;

        case 3:
            cout << endl;
            find_difference_between_all_elements(numbers);
            cout << endl << "Min: " << find_min(numbers);
            break;

        case 4:
            cout << endl;
            sort_array(numbers, sorted_numbers);
            print_array(sorted_numbers);
            break;
        }
        cout << endl;
    } while (choice != 5);
}

void input_array(int numbers[])
{
    for (int i = 0; i < ARRAY_SIZE; i++)
    {
        cin >> numbers[i];
    }
}

void print_array(int numbers[])
{
    for (int i = 0; i < ARRAY_SIZE; i++)
    {
        cout << numbers[i] << " ";
    }
}

void find_difference_between_all_elements(int numbers[])
{
    for (int i = 0; i < ARRAY_SIZE; i++)
    {
        for (int j = 0; j < ARRAY_SIZE; j++)
        {
            cout << numbers[i] << " - " << numbers[j] << " = " << numbers[i] - numbers[j] << endl;
        }
    }
}

int find_min(int numbers[])
{
    int min = numbers[0];
    for (int i = 1; i < ARRAY_SIZE; i++)
    {
        if (numbers[i] < min) min = numbers[i];
    }
    return min;
}

void sort_array(int numbers[], int sorted_numbers[])
{
    for (int i = 0; i < ARRAY_SIZE; i++) sorted_numbers[i] = numbers[i];

    for (int i = 0; i < ARRAY_SIZE - 1; i++)
    {
        bool swapped = false;
        for (int j = 0; j < ARRAY_SIZE - 1 - i; j++)
        {
            int first = pow(sorted_numbers[j], 2) - 5. * sorted_numbers[j] + 1;
            int second = pow(sorted_numbers[j + 1], 2) - 5. * sorted_numbers[j + 1] + 1;
            if (first > second)
            {
                int temp = sorted_numbers[j];
                sorted_numbers[j] = sorted_numbers[j + 1];
                sorted_numbers[j + 1] = temp;
                swapped = true;
            }
        }
        if (!swapped) break;
    }
}
