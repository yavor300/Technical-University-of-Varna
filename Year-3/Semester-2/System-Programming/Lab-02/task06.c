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
    printf("Child was created with pid %d.\n", pid);
    printf("Child stopping execution...\n");
    exit(23);
  } else {
    sleep(2);
    printf("Parent checking for zombie processes.\n");
    system("ps a");
    int status;
    wait(&status);
    if (WIFEXITED(status)) {
      int statusValue = WEXITSTATUS(status);
      printf("Child exit status value: %d.\n", statusValue);
    }
    printf("Parent checking for zombie processes after wait() command is executed.\n");
    system("ps a");
    exit(0);
  }

  return 0;
}
