#include <iostream>
#include <string.h>
using namespace std;

int main()
{
    cout << "Hello World!\n";
    char stringvar[] = "Hello";
    strcat(stringvar, " and Good Bye!");
    cout << stringvar;
}
