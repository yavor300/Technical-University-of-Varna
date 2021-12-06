#include <iostream>
#include <string>
#include <time.h>
#include <math.h>
using namespace std;

/*
* Задача 10
*
* Създайте  програма  с  дефинирана  структура,  описваща  правоъгълен  паралелепипед
* (широчина, дължина, височина). Създайте масив от 10 елемента от типа на структурата
* и с запълнете стойностите със случайни числа между 10 и 100.
* С помощта на функции сортирайте масива във възходящ ред по признак:
* а) обем на паралелепипеда (a*b*h);
* б) лице на околната повърхнина на паралелепипеда (2*(a+b)*h)
*/

struct Parallelepiped
{
    double width;
    double length;
    double height;
};

const int ARRAY_SIZE = 10;

void input_array(Parallelepiped parallelepipeds[]);
void print_array(Parallelepiped parallelepipeds[]);
void sort_array_by_volume(Parallelepiped parallelepipeds[], Parallelepiped sorted_parallelepipeds[]);
void sort_array_by_area(Parallelepiped parallelepipeds[], Parallelepiped sorted_parallelepipeds[]);

int main()
{
    setlocale(LC_ALL, "BG");
    Parallelepiped parallelepipeds[ARRAY_SIZE];
    Parallelepiped sorted_parallelepipeds[ARRAY_SIZE];
    input_array(parallelepipeds);
    cout << "Sorted by volume:" << endl;
    sort_array_by_volume(parallelepipeds, sorted_parallelepipeds);
    print_array(sorted_parallelepipeds);
    cout << "Sorted by area:" << endl;
    sort_array_by_area(parallelepipeds, sorted_parallelepipeds);
    print_array(sorted_parallelepipeds);

}

void input_array(Parallelepiped parallelepipeds[])
{
    Parallelepiped p;
    double min = 10;
    double max = 100;
    for (int i = 0; i < ARRAY_SIZE; i++)
    {
        p.height = min + (((double)rand() / RAND_MAX) * (max - min));
        p.width = min + (((double)rand() / RAND_MAX) * (max - min));
        p.length = min + (((double)rand() / RAND_MAX) * (max - min));

        parallelepipeds[i] = p;
    }
}

void print_array(Parallelepiped parallelepipeds[])
{
    for (int i = 0; i < ARRAY_SIZE; i++)
    {
        Parallelepiped p = parallelepipeds[i];
        printf("Parallelepiped %d: Width: %.2f, Length: %.2f, Height: %.2f\n",
            i + 1, p.width, p.length, p.height);
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

void sort_array_by_volume(Parallelepiped parallelepipeds[], Parallelepiped sorted_parallelepipeds[])
{
    for (int i = 0; i < ARRAY_SIZE; i++) sorted_parallelepipeds[i] = parallelepipeds[i];

    for (int i = 0; i < ARRAY_SIZE - 1; i++)
    {
        bool swapped = false;
        for (int j = 0; j < ARRAY_SIZE - 1 - i; j++)
        {
            int first = sorted_parallelepipeds[j].height * sorted_parallelepipeds[j].length * sorted_parallelepipeds[j].width;
            int second = sorted_parallelepipeds[j + 1].height * sorted_parallelepipeds[j + 1].length * sorted_parallelepipeds[j + 1].width;
            
            if (first > second)
            {
                Parallelepiped temp = sorted_parallelepipeds[j];
                sorted_parallelepipeds[j] = sorted_parallelepipeds[j + 1];
                sorted_parallelepipeds[j + 1] = temp;
                swapped = true;
            }
        }
        if (!swapped) break;
    }
}

void sort_array_by_area(Parallelepiped parallelepipeds[], Parallelepiped sorted_parallelepipeds[])
{
    for (int i = 0; i < ARRAY_SIZE; i++) sorted_parallelepipeds[i] = parallelepipeds[i];

    for (int i = 0; i < ARRAY_SIZE - 1; i++)
    {
        bool swapped = false;
        for (int j = 0; j < ARRAY_SIZE - 1 - i; j++)
        {
            int first = 2 * (sorted_parallelepipeds[j].length + sorted_parallelepipeds[j].width) * sorted_parallelepipeds[j].height;
            int second = 2 * (sorted_parallelepipeds[j + 1].length + sorted_parallelepipeds[j + 1].width) * sorted_parallelepipeds[j + 1].height;

            if (first > second)
            {
                Parallelepiped temp = sorted_parallelepipeds[j];
                sorted_parallelepipeds[j] = sorted_parallelepipeds[j + 1];
                sorted_parallelepipeds[j + 1] = temp;
                swapped = true;
            }
        }
        if (!swapped) break;
    }
}
