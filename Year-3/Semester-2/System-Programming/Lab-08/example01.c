#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>

int main() {

  pid_t pid;
  int fd[2];

  pipe(fd);
  pid = fork();

  if (pid == -1) {
    perror("fork");
    exit(EXIT_FAILURE);
  } else if (pid == 0) {
    int received;
    close(fd[1]);
    read(fd[0], &received, sizeof(int));
    close(fd[0]);
    printf("Received from parent: %d\n", received);
  } else if (pid > 0) {
    int value = 123;
    close(fd[0]);
    write(fd[1], &value, sizeof(int));
    close(fd[1]);
    wait(NULL);
  }
  return 0;
}
