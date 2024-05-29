#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <string.h>
#include <stdlib.h>

int main(int argc, char* argv[]) {
  
  if (argc != 4) {
    printf("Enter math operation\n");
    exit(EXIT_FAILURE);
  }

  int first = atoi(argv[1]);
  int second = atoi(argv[3]);
  char *op = argv[2];
  int fd = open("myfifo", O_WRONLY);
  write(fd, &first, sizeof(int));
  write(fd, op, strlen(op));
  write(fd, &second, sizeof(int));
  close(fd);
  double res;
  fd = open("myfifo", O_RDONLY);
  read(fd, &res, sizeof(double));
  printf("The result is: %f\n", res);
  close(fd);
}
