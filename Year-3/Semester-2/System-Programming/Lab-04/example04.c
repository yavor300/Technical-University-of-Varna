#include <stdio.h>
#include <unistd.h>
#include <signal.h>

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

  signal(SIGUSR1, myHandler);
  kill(getpid(), SIGUSR1);

  return 0;
}
