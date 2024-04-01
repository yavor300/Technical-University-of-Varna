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

  signal(SIGUSR1, myHandler);
  signal(SIGUSR2, myHandler);

  pid_t pid;
  printf("Creating child process from parent with pid: %d.\n", getpid());
  pid = fork();
  if (pid == -1) {
    perror("fork");
    exit(EXIT_FAILURE);
  } else if (pid == 0) {
    printf("Sending SIGUSR1 signal from child with pid %d to parent with pid %d.\n", getppid(), getpid());
    kill(getppid(), SIGUSR1);
    sleep(2);
    printf("Exitting from child process with pid: %d.\n", getpid());
    exit(0);
  } else {
    sleep(1);
    printf("Sending SIGUSR2 signal from parent with pid %d to child with pid %d.\n", getpid(), pid);
    kill(pid, SIGUSR2);
    sleep(3);
    printf("Exitting from parent process with pid: %d.\n", getpid());
    wait(NULL);
    exit(0);
  }

  return 0;
}
