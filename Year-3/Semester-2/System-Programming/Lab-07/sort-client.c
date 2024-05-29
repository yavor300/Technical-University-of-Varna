#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <string.h>

int main()
{
  float numbers[10] = {10.0, 9.0, 8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0};
  int fd = open("myfifo", O_WRONLY);
  for (int i = 0; i < 10; i++)
  {
    printf("%f\n", numbers[i]);
  }
  write(fd, numbers, sizeof(numbers));
  close(fd);
  fd = open("myfifo", O_RDONLY);
  read(fd, numbers, sizeof(numbers));
  close(fd);
  for (int i = 0; i < 10; i++)
  {
    printf("%f\n", numbers[i]);
  }
  return 0;
}
