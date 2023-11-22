#include <stdio.h>
#include "input.h"
#include "sort.h"
#include "output.h"

#define SIZE 10

int main() {
    int numbers[SIZE];

    // Input, sort and output the numbers
    inputNumbers(numbers, SIZE);
    sortNumbers(numbers, SIZE);
    printf("Sorted in ascending order:\n");
    outputNumbers(numbers, SIZE);

    return 0;
}
