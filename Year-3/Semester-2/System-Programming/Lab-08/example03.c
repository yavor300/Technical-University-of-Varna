#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>

int main()
{

  int fd[2];
  pipe(fd);
  pid_t pid;
  pid = fork();

  if (pid == -1)
  {
    perror("fork");
    exit(EXIT_FAILURE);
  }
  else if (pid == 0)
  {
    close(fd[0]);
    close(STDOUT_FILENO);
    dup2(fd[1], STDOUT_FILENO);
    close(fd[1]);
    execlp("ls", "ls", "-la", "/", NULL);
  } else if (pid > 0) {
    close(fd[1]);
    close(STDIN_FILENO);
    dup2(fd[0], STDIN_FILENO);
    close(fd[0]);
    wait(NULL);
    execlp("wc", "wc", "-l", NULL);
  }

  return 0;
}
