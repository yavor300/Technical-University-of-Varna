#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <signal.h>
#include <string.h>

void myHandler(int signo, siginfo_t *info, void *ctx)
{

  printf("Signal: %d - %s\n", signo, strsignal(info->si_signo));
  switch (info->si_code)
  {
    case SI_KERNEL:
      printf("The source of signal is kernel\n");
      break;
    case SI_USER:
      printf("The source of signal is user\n");
      break;
    case SI_TIMER:
      printf("The source of signal is timer\n");
      break;
    case SI_QUEUE:
      printf("The source of signal is queue\n");
      break;
    default:
      printf("Other source of signal, si_code: %d\n", info->si_code);
  }
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
  sigaction(SIGCHLD, &sa, NULL);

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
