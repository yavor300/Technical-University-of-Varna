#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <signal.h>
#include <string.h>

void myHandler(int signo, siginfo_t *info, void *ctx)
{

  printf("Signal: %d - %s\n", signo, strsignal(info->si_signo));
}


int main(void)
{

  struct sigaction sa;
  memset(&sa, 0, sizeof(sa));
  sa.sa_sigaction = &myHandler;
  sa.sa_flags = SA_SIGINFO;
  sigset_t set;
  sigemptyset(&set);
  sa.sa_mask = set;
  sigaction(SIGUSR1, &sa, NULL);
  sigaction(SIGUSR2, &sa, NULL);

for (int i = 0; i < 5; i++)
{

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
    if (i == 4) exit(0);
  }
}

  return 0;
}
