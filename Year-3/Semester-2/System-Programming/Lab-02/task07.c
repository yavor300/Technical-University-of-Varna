#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

int main(void)
{
  pid_t pid, pid2;
  pid = fork();
  if (pid == -1) {
    perror("fork");
    exit(EXIT_FAILURE);
  } else if (pid == 0) {
    printf("Child\n");
    sleep(5); // Child sleeps for 5 seconds
    printf("%d\n", getppid()); // Child prints its parent's PID
    exit(0);
  } else {
    sleep(2); // Parent sleeps for 2 seconds
    printf("Parent created child with pid %d\n", pid);
    exit(0); // Parent exits
  }
  return 0;
}
