#include <iostream>
using namespace std;

const double TAX_RATE = 0.05;

double total_cost(int number_par, double price_par);

void main()
{
    double price, bill;
    int number;

    cout << "Enter the number of items purchased: ";
    cin >> number;
    cout << "Enter the price per item $";
    cin >> price;

    bill = total_cost(number, price);

    cout.setf(ios::fixed);
    cout.setf(ios::showpoint);
    cout.precision(2);
    cout << number << " items at "
        << "$" << price << " each.\n"
        << "Final bill, including tax, is $" << bill
        << endl;
}

double total_cost(int number_par, double price_par)
{
    return (price_par * number_par) * (1 + TAX_RATE);
}
