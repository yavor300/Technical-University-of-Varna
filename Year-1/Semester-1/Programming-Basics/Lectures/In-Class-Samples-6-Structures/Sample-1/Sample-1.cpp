#include <iostream>
using namespace std;

struct CDAccount
{
	double balance;
	double interest_rate;
	int term;
};

void get_data(CDAccount& account);

void main()
{
	CDAccount account;
	get_data(account);

	double rate_fraction, interest;
	rate_fraction = account.interest_rate / 100.0;
	interest = account.balance * rate_fraction * (account.term / 12.0);
	account.balance = account.balance + interest;

	cout.setf(ios::fixed);
	cout.setf(ios::showpoint);
	cout.precision(2);
	cout << "When your CD matures in "
		<< account.term << " months,\n"
		<< "it will have a balance of $"
		<< account.balance << endl;

}

void get_data(CDAccount& account)
{
	cout << "Enter account balance: $";
	cin >> account.balance;
	cout << "Enter account interest rate: ";
	cin >> account.interest_rate;
	cout << "Enter the number of months until maturity\n"
		<< "(must be 12 or fewer months): ";
	cin >> account.term;
}
