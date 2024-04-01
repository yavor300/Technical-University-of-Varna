#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#define MAX_COMMAND_LENGTH 1024
#define MAX_ARGS 64

int main(void) {

  char command[MAX_COMMAND_LENGTH];
  char *args[MAX_ARGS];
  pid_t pid;
  int status;

  while (1) {

    printf("SimpleShell> ");
    fflush(stdout);
    // Read a command from the user
    if (!fgets(command, MAX_COMMAND_LENGTH, stdin)) {
      printf("\n");
      exit(0); // Exit on EOF
    }
    // Remove trailing newline character
    if (command[strlen(command) - 1] == '\n') {
      command[strlen(command) - 1] = '\0';
    }
    // Parse the command into program and arguments
    char *token = strtok(command, " ");
    int i = 0;
    while (token != NULL && i < MAX_ARGS - 1) {
      args[i++] = token;
      token = strtok(NULL, " ");
    }
    args[i] = NULL;
    if (args[0] == NULL) {
      continue;
    }

    // Fork a new process
    pid = fork();
    if (pid == -1) {
      perror("fork");
      continue;
    } else if (pid == 0) {
      if (execvp(args[0], args) == -1) {
        perror("execvp");
        exit(EXIT_FAILURE);
      }
    } else {
      do {
        waitpid(pid, &status, WUNTRACED);
      } while (!WIFEXITED(status) && !WIFSIGNALED(status));
    }
  }

  return 0;
}
