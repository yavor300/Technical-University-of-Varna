#include <iostream>
using namespace std;

/*
* Задача 1
* 
* Задача за раницата
*/

const int number_of_items = 4;
const int weights[] = { 10, 20, 30, 40 };
const int values[] = { 50, 60, 70, 90 };
const int capacity = 40;

int max(int x, int y)
{
    return x > y ? x : y;
}

int knapsack(const int weights[], const int values[])
{
    int knapsack[number_of_items + 1][capacity + 1];
    for (int i = 0; i <= number_of_items; i++)
    {
        for (int j = 0; j <= capacity; j++)
        {
            if (i == 0 || j == 0) knapsack[i][j] = 0;
            else if (weights[i - 1] <= j)
                knapsack[i][j] = max(values[i - 1] + knapsack[i - 1][j - weights[i - 1]], knapsack[i - 1][j]);
            else knapsack[i][j] = knapsack[i - 1][j];
        }
    }
    return knapsack[number_of_items][capacity];
}

int main()
{
    cout << knapsack(weights, values);
}

