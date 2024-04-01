#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <signal.h>

void myHandler(int signo)
{
  psignal(signo, "Signal handled");
}

int main(void)
{

  signal(SIGCHLD, myHandler);

  pid_t pid;
  printf("Creating child process from parent with pid: %d.\n", getpid());
  pid = fork();
  if (pid == -1) {
    perror("fork");
    exit(EXIT_FAILURE);
  } else if (pid == 0) {
    printf("Exitting from child process with pid: %d.\n", getpid());
    exit(0);
  } else {
    wait(NULL);
    exit(0);
  }

  return 0;
}
