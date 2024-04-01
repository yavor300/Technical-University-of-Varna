#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <signal.h>

int main(void)
{

  pid_t pid;
  pid = fork();
  if (pid == -1) {
    perror("fork");
    exit(EXIT_FAILURE);
  } else if (pid == 0) {
    pause();
  } else {
    int status;
    kill(pid, SIGKILL);
    wait(&status);
    if (WIFSIGNALED(status))
    {
      int sig = WTERMSIG(status);
      printf("Child exited by signal: %d - %s\n", sig, strsignal(sig));
    }
    exit(0);
  }

  return 0;
}
