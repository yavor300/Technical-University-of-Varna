#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

int main(void)
{
  pid_t pid;
  pid = fork();
  if (pid == -1) {
    perror("fork");
    exit(EXIT_FAILURE);
  } else if (pid == 0) {
    printf("Child\n");
  } else {
    printf("Parent\n");
  }
  return 0;
}
