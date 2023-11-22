
#include <stdio.h>
#include "output.h"

// Function to output numbers
void outputNumbers(int *numbers, int size) {
    for(int i = 0; i < size; i++) {
        printf("%d ", numbers[i]);
    }
    printf("\n");
}
