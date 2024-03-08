#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

void forkRecursively(int currentDepth, int maxDepth) {
  if (currentDepth >= maxDepth) {
    return;
  }

  pid_t pid = fork();
  if (pid == -1) {
    perror("fork");
    exit(EXIT_FAILURE);
  } else if (pid == 0) {
    forkRecursively(currentDepth + 1, maxDepth);
    exit(0);
  } else {
    wait(NULL); // Parent waits for the child process to finish
  }
}

int main(void) {
  int maxDepth = 5;
  forkRecursively(0, maxDepth);
  return 0;
}
