#include <iostream>
#include <iomanip>

using namespace std;

int main ()
{
  double price (78.5779008);
  cout << endl << price;
  cout.setf (ios::showpoint);
  cout.precision(2);
  cout << endl << price;
  cout.precision (5);
  cout << endl << price;
  cout.unsetf (ios::showpoint);
  cout.setf(ios::fixed);
  cout.precision(5);
  cout << endl << price;
  cout.unsetf(ios::fixed);
  cout.setf(ios::scientific);
  cout << endl << price;
  cout.precision(2);
  cout << endl << price << endl;
  cout.unsetf(ios::scientific);
  cout.precision(0);
  cout << price;
  cerr << endl << price;
  
  return 0;
}
