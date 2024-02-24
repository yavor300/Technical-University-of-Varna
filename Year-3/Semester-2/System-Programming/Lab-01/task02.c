#include <stdio.h>
int main(void)
{
  int res = 0;
  for (int i = 1; i <= 100; i++) {
    res += i;
  }
  printf("%d", res);
  
  return 0;
}
