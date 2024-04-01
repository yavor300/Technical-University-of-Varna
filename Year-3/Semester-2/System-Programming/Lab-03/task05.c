#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int spawn(char *program, char **args);

int main(void)
{

  char* args[] = { "ls", "-la", "./" };
  int pid = spawn("/usr/bin/ls", args);
  printf("Spawn created process with pid %d.\n", pid);
  return 0;
}

int spawn(char *program, char **args) {

  pid_t pid = fork();
  if (pid == -1) {
    perror("fork");
    exit(EXIT_FAILURE);
  } else if (pid == 0) {
    printf("Child with pid %d is executing '%s'.\n", getpid(), program);
    execv(program, args);
    perror("execv");
    exit(EXIT_FAILURE);
  } else {
    wait(NULL);
    return pid;
  }
}
