#include <stdio.h>
#include <unistd.h>
#include <signal.h>
#include <string.h>

int signalSent = 0;

void myHandler(int signo)
{

  printf("Begin of handler\n");
  if (!signalSent)
  {
    signalSent = 1;
    printf("Interrupting the handler by sending SIGUSR1 signal to the same process.\n");
    kill(getpid(), SIGUSR1);
  }
  printf("End of handler\n");
}

int main(void) {

  struct sigaction sa;
  memset(&sa, 0, sizeof(sa));
  sa.sa_handler = &myHandler;
  sa.sa_flags = SA_NODEFER;
  sigset_t set;
  sigemptyset(&set);
  sa.sa_mask = set;
  sigaction(SIGUSR1, &sa, NULL);
  kill(getpid(), SIGUSR1);

  return 0;
}
