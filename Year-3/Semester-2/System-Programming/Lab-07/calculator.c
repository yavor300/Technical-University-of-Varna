#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <string.h>

int main() {
  mkfifo("myfifo", 0755);
  int fd = open("myfifo", O_RDONLY);
  int first, second;
  char buff[1];
  read(fd, &first, sizeof(int));
  read(fd, buff, sizeof(buff));
  read(fd, &second, sizeof(int));
  double res;
  if (buff[0] == '+') {
    res = first + second;
  } else if (buff[0] == '-') {
    res = first - second;
  } else if (buff[0] == 'x') {
    res = first * second;
  } else if (buff[0] == '/') {
    res = (double) first / (double) second;
  }
  close(fd);
  fd = open("myfifo", O_WRONLY);
  write(fd, &res, sizeof(double));
  close(fd);
  return 0;
}
