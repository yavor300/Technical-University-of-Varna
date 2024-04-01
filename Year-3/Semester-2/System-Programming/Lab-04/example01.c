#include <stdio.h>
#include <unistd.h>
#include <signal.h>

int main(void) {

  signal(SIGINT, SIG_IGN);
  for (int i = 0; i < 10; i++)
  {
    if (i == 5) signal(SIGINT, SIG_DFL);
    printf("Iteration: %d\n", i + 1);
    sleep(1);
  }

  return 0;
}
