#include <stdio.h>
#include <unistd.h>
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
      printf("Other source of signal\n");
  }
}

int main(void) {

  struct sigaction sa;
  memset(&sa, 0, sizeof(sa));
  sa.sa_sigaction = &myHandler;
  sa.sa_flags = SA_SIGINFO;
  sigset_t set;
  sigemptyset(&set);
  sa.sa_mask = set;
  sigaction(SIGALRM, &sa, NULL);
  alarm(1);
  pause();

  return 0;
}
