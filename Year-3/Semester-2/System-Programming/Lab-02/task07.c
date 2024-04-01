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
    printf("Waiting for parent process to exit...\n");
    sleep(2);
    printf("New parent pid: %d.\n", getppid());
    exit(0);
  } else {
    sleep(1);
    printf("Parent process is terminating...\n");
    exit(0);
  }

  return 0;
}
