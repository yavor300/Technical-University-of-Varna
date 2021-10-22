/******************************************************************************

                              Online C++ Compiler.
               Code, Compile, Run and Debug C++ program online.
Write your code in this editor and press "Run" button to compile and execute it.

*******************************************************************************/

#include <iostream>
#include <iomanip>

using namespace std;

int main()
{
    double num = 4.567;
    cout << num << endl;
    cout << setprecision(2) << num << endl;
    cout << setprecision(3) << num << endl;
    cout << setprecision(5) << num << endl;
    cout << setprecision(6) << num << endl;

    return 0;
}
