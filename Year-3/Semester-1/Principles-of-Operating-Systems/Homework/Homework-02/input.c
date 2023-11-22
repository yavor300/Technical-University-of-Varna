
#include <stdio.h>
#include "input.h"

// Function to input numbers
void inputNumbers(int *numbers, int size) {
    printf("Enter %d integers:\n", size);
    for(int i = 0; i < size; i++) {
        scanf("%d", &numbers[i]);
    }
}
