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
    exit(23);
  } else {
    printf("Parent\n");
    int status;
    printf("Parent created child with pid %d\n", pid);
    wait(&status);
    if (WIFEXITED(status)) {
      int statusValue = WEXITSTATUS(status);
      printf("Child exit status value: %d\n", statusValue);
    }
  }
  return 0;
}
