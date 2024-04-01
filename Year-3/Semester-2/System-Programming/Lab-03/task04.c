#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

void executeCommand(const char *path, const char *command, const char *arg);

int main(void)
{

  executeCommand("/usr/bin/mkdir", "mkdir", "dir");
  executeCommand("/usr/bin/ls", "ls", "./");
  executeCommand("/usr/bin/mkdir", "mkdir", "dir/left");
  executeCommand("/usr/bin/mkdir", "mkdir", "dir/right");
  executeCommand("/usr/bin/ls", "ls", "./dir");
  executeCommand("/usr/bin/mkdir", "mkdir", "dir/left/l1");
  executeCommand("/usr/bin/mkdir", "mkdir", "dir/left/l2");
  executeCommand("/usr/bin/ls", "ls", "./dir/left");
  executeCommand("/usr/bin/mkdir", "mkdir", "dir/right/r1");
  executeCommand("/usr/bin/mkdir", "mkdir", "dir/right/r2");
  executeCommand("/usr/bin/ls", "ls", "./dir/right");

  return 0;
}

// Function to execute a command in a child process
void executeCommand(const char *path, const char *command, const char *arg)
{
    
  pid_t pid = fork();
  if (pid == -1) {
    perror("fork");
    exit(EXIT_FAILURE);
  } else if (pid == 0) {
    printf("Child with pid %d is executing '%s %s'.\n", getpid(), command, arg);
    execl(path, command, arg, NULL);
    // If execl returns, it must have failed
    perror("execl");
    exit(EXIT_FAILURE);
  } else {
    wait(NULL);
  }
}
