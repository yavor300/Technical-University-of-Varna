#include <stdio.h>
#include <unistd.h>
#include <signal.h>

void myhandler(int signo)
{
  psignal(signo, "Signal handled");
}

int main(void) {

  signal(SIGALRM, myhandler);
  alarm(1);
  sleep(2);

  return 0;
}
