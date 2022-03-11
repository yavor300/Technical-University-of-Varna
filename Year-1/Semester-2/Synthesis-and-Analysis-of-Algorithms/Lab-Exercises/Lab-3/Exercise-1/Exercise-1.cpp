#include <iostream>
using namespace std;

/*
* Задача 1
* 
* Рекурсивно търсене на MAX елемент в целочислен масив
*/
int max(int numbers[], int start_index, int end_index)
{
    if (start_index == end_index) return numbers[start_index];

    int average = (start_index + end_index) / 2;
    int max_left = max(numbers, start_index, average);
    int max_right = max(numbers, average + 1, end_index);

    if (max_left > max_right) return max_left;
    else return max_right;
}

int main()
{
    int arr[] = { 1, 2, 3, 4, 13, 6, 7, 8 };
    cout << "Max: " << max(arr, 0, 7);
}
