#include <iostream>
using namespace std;

int sum_pass_by_value(int a, int b) {
    return a++ + b;
}

int sum_pass_by_reference(int& a, int b) {
    return a++ + b;
}

void main()
{
    int a = 5, b = 6;
    int result_by_value = sum_pass_by_value(a, b);
    cout << result_by_value << " " << a << endl;
    int result_by_reference = sum_pass_by_reference(a, b);
    cout << result_by_reference << " " << a;
}
