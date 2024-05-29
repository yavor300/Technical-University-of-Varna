#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <string.h>

void bubbleSort(float numbers[], int n)
{
  int i, j;
  int swapped;
  for (i = 0; i < n - 1; i++)
  {
    swapped = 0;
    for (j = 0; j < n - i - 1; j++)
    {
      if (numbers[j] > numbers[j + 1])
      {
        float temp = numbers[j + 1];
        numbers[j + 1] = numbers[j];
        numbers[j] = temp;
        swapped = 1;
      }
    }
    if (swapped == 0)
      break;
  }
}

int main()
{
  mkfifo("myfifo", 0755);
  int fd = open("myfifo", O_RDONLY);
  float numbers[10];
  read(fd, numbers, sizeof(numbers));
  bubbleSort(numbers, 10);
  close(fd);
  fd = open("myfifo", O_WRONLY);
  write(fd, numbers, sizeof(numbers));
  close(fd);
  return 0;
}